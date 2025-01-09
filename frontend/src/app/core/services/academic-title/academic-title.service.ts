import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { AcademicTitle } from '../../models/academic-title.interface';

@Injectable({
  providedIn: 'root',
})
export class AcademicTitleService {
  private apiUrl = 'http://localhost:8080/academicTitle';
  private behaviorSubject = new BehaviorSubject<AcademicTitle[]>([]);

  constructor(private http: HttpClient) {}

  get academicTitles$(): Observable<AcademicTitle[]> {
    return this.behaviorSubject.asObservable();
  }

  getAcademicTitles(): Observable<AcademicTitle[]> {
    return this.http.get<AcademicTitle[]>(this.apiUrl).pipe(
      tap((academicTitles: AcademicTitle[]) => {
        this.behaviorSubject.next(academicTitles);
      }),
      catchError(this.handleError)
    );
  }

  createAcademicTitle(academicTitle: AcademicTitle): Observable<AcademicTitle> {
    return this.http.post<AcademicTitle>(this.apiUrl, academicTitle).pipe(
      tap((createdTitle) => {
        this.behaviorSubject.next([
          ...this.behaviorSubject.value,
          createdTitle,
        ]);
      }),
      catchError(this.handleError)
    );
  }

  deleteAcademicTitel(code: string): Observable<string> {
    const url = `${this.apiUrl}/${code}`;
    return this.http.delete(url, { responseType: 'text' }).pipe(
      tap(() => {
        const updatedTitles = this.behaviorSubject.value.filter(
          (at) => at.code !== code
        );
        this.behaviorSubject.next(updatedTitles);
      })
    );
  }

  getAcademicTitleByName(academicTitle: string): Observable<AcademicTitle> {
    const url = `${this.apiUrl}/${academicTitle}`;
    return this.http.get<AcademicTitle>(url).pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    const errorMessage =
      error.error?.message || error.error || 'Unknown error occured!';
    return throwError(() => new Error(errorMessage));
  }
}
