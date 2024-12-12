import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from '../../models/subject.model';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  private apiUrl = 'http://localhost:8080/subject';
  private behaviorSubject = new BehaviorSubject<Subject[]>([]);

  constructor(private http: HttpClient) {}

  // Getter za Observable sa podacima
  get subjects$(): Observable<Subject[]> {
    return this.behaviorSubject.asObservable();
  }

  // Učitaj sve predmete sa backend-a
  getSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(this.apiUrl).pipe(
      tap((subjects) => this.behaviorSubject.next(subjects)),
      catchError(this.handleError)
    );
  }

  getSubjectsIfNeeded(): void {
    if (this.behaviorSubject.value.length === 0) {
      this.getSubjects().subscribe();
    }
  }

  // Kreiranje novog predmeta
  createSubject(subject: Subject): Observable<Subject> {
    return this.http.post<Subject>(this.apiUrl, subject).pipe(
      tap((createdSubject) => {
        const updatedSubjects = [...this.behaviorSubject.value, createdSubject];
        this.behaviorSubject.next(updatedSubjects);
      }),
      catchError(this.handleError)
    );
  }

  // Ažuriranje ESPB vrednosti
  updateEspb(subjectId: number, espb: number): Observable<Subject> {
    const url = `${this.apiUrl}/updateEspb/${subjectId}`;
    return this.http.patch<Subject>(url, { espb }).pipe(
      map((updatedSubject: Subject) => updatedSubject), // Backend vraća ceo `SubjectDTO` objekat
      tap((updatedSubject) => {
        // Ažuriranje lokalnog BehaviorSubject
        const updatedSubjects = this.behaviorSubject.value.map((subject) =>
          subject.id === updatedSubject.id ? updatedSubject : subject
        );
        this.behaviorSubject.next(updatedSubjects);
      }),
      catchError(this.handleError)
    );
  }

  // Brisanje predmeta
  deleteSubject(id: number): Observable<string> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete(url, { responseType: 'text' }).pipe(
      tap(() => {
        // Ukloni predmet iz lokalne liste
        const updatedSubjects = this.behaviorSubject.value.filter(
          (subject) => subject.id !== id
        );
        this.behaviorSubject.next(updatedSubjects);
      }),
      catchError(this.handleError)
    );
  }

  // Preuzmi predmet po ID-u
  getSubjectById(id: number): Observable<Subject> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Subject>(url).pipe(
      map((subject: Subject) => subject), // Backend vraća `SubjectDTO`
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('Server error details:', error.error);

    // Ako server vrati objekat sa message, koristimo to, u suprotnom ceo odgovor
    let errorMessage =
      error.error?.message || error.error || 'An unknown error occurred!';

    return throwError(() => new Error(errorMessage));
  }
}
