import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { EducationTitle } from '../../models/education-title.interface.ts.js';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class EducationTitleService {
  private apiUrl = 'http://localhost:8080/educationTitle';
  private behaviorSubject = new BehaviorSubject<EducationTitle[]>([]);

  constructor(private http: HttpClient) {}

  get educationTitles$(): Observable<EducationTitle[]> {
    return this.behaviorSubject.asObservable();
  }

  getEducationTitles(): Observable<EducationTitle[]> {
    return this.http
      .get<EducationTitle[]>(this.apiUrl)
      .pipe(
        tap((educationTitles) => this.behaviorSubject.next(educationTitles))
      );
  }

  createEduactionTitle(
    educationTitle: EducationTitle
  ): Observable<EducationTitle> {
    return this.http.post<EducationTitle>(this.apiUrl, educationTitle).pipe(
      tap((createdTitle) => {
        this.behaviorSubject.next([
          ...this.behaviorSubject.value,
          createdTitle,
        ]);
      }),
      catchError(this.handleError)
    );
  }

  deleteEducationTitle(code: string): Observable<string> {
    const url = `${this.apiUrl}/${code}`;
    return this.http.delete(url, { responseType: 'text' }).pipe(
      tap(() => {
        const updatedTitles = this.behaviorSubject.value.filter(
          (et) => et.code !== code
        );
        this.behaviorSubject.next(updatedTitles);
      }),
      catchError(this.handleError)
    );
  }

  getEducationTitleByName(name: string): Observable<EducationTitle> {
    const url = `${this.apiUrl}/${name}`;
    return this.http
      .get<EducationTitle>(url)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    const errorMessage =
      error.error?.message || error.error || 'Unknown error occured!';
    return throwError(() => new Error(errorMessage));
  }
}
