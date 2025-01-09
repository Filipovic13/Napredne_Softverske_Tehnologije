import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  catchError,
  map,
  Observable,
  tap,
  throwError,
} from 'rxjs';
import { Department } from '../../models/department.display.interface';
import {
  HttpClient,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { DepartmentRequest } from '../../models/department.request';

@Injectable({
  providedIn: 'root',
})
export class DepartmentService {
  private apiUrl = 'http://localhost:8080/department';
  private behaviorSubject = new BehaviorSubject<Department[]>([]);

  constructor(private http: HttpClient) {}

  get departments$() {
    return this.behaviorSubject.asObservable();
  }

  getDepartments(): Observable<Department[]> {
    return this.http.get<Department[]>(this.apiUrl).pipe(
      // Mapiranje podataka sa servera na odgovarajući format
      map((departments) =>
        departments.map((dept) => ({
          name: dept.name,
          shortName: dept.shortName,
          supervisorId: dept.supervisorId,
          secretaryId: dept.secretaryId,
          supervisorFullName: '',
          secretaryFullName: '',
        }))
      ),
      tap((formattedDepartments) =>
        this.behaviorSubject.next(formattedDepartments)
      ),
      catchError(this.handleError)
    );
  }

  getDepartmentsIfNeeded(): void {
    if (this.behaviorSubject.value.length === 0) {
      this.getDepartments().subscribe();
    }
  }

  createDepartment(department: Department) {
    // Dodavanje supervisorId i secretaryId u telo zahteva
    const requestBody: DepartmentRequest = {
      name: department.name,
      shortName: department.shortName,
      supervisorId: department.supervisorId, // Dodavanje supervisorId
      secretaryId: department.secretaryId, // Dodavanje secretaryId
    };

    return this.http.post<Department>(this.apiUrl, requestBody).pipe(
      tap((createdDepartment) => {
        const newDepartment: Department = {
          ...createdDepartment,
          supervisorFullName: '',
          secretaryFullName: '',
        };

        const updatedDepartments = [
          ...this.behaviorSubject.value,
          newDepartment,
        ];
        this.behaviorSubject.next(updatedDepartments);
      }),
      catchError(this.handleError)
    );
  }

  deleteDepartment(name: string): Observable<string> {
    const url = `${this.apiUrl}/${name}`;
    return this.http.delete(url, { responseType: 'text' }).pipe(
      tap(() => {
        const updatedDepartments = this.behaviorSubject.value.filter(
          (d) => d.name !== name
        );
        this.behaviorSubject.next(updatedDepartments);
      }),
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
