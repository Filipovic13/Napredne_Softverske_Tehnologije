import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  catchError,
  Observable,
  take,
  tap,
  throwError,
} from 'rxjs';
import { EducationTitle } from '../../models/education-title.interface.ts';
import { ScientificField } from '../../models/scientific-field.interface.js';

@Injectable({
  providedIn: 'root',
})
export class ScientificFieldService {
  private apiUrl = 'http://localhost:8080/scientificField';
  private behaviorSubject = new BehaviorSubject<EducationTitle[]>([]);

  constructor(private http: HttpClient) {}

  get scientificFields$(): Observable<ScientificField[]> {
    return this.behaviorSubject.asObservable();
  }

  getScientificFields(): Observable<ScientificField[]> {
    return this.http.get<ScientificField[]>(this.apiUrl).pipe(
      tap((scientificFields) => {
        this.behaviorSubject.next(scientificFields);
      }),
      catchError(this.handleError)
    );
  }

  createScientificField(
    scientificField: ScientificField
  ): Observable<ScientificField> {
    return this.http.post<ScientificField>(this.apiUrl, scientificField).pipe(
      tap((createdScientificField) => {
        this.behaviorSubject.next([
          ...this.behaviorSubject.value,
          createdScientificField,
        ]);
      }),
      catchError(this.handleError)
    );
  }

  deleteScientificField(code: string): Observable<string> {
    const url = `${this.apiUrl}/${code}`;
    return this.http.delete(url, { responseType: 'text' }).pipe(
      tap(() => {
        const updatedFields = this.behaviorSubject.value.filter(
          (sf) => sf.code !== code
        );
        this.behaviorSubject.next(updatedFields);
      })
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    const errorMessage =
      error.error?.message || error.error || 'Unknown error occured!';
    return throwError(() => new Error(errorMessage));
  }
}
