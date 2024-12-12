import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  catchError,
  map,
  Observable,
  tap,
  throwError,
} from 'rxjs';
import { Department } from '../../models/department.interface';
import {
  HttpClient,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';

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
    return this.http.get<any[]>(this.apiUrl).pipe(
      // Mapiranje podataka sa servera na odgovarajući format
      map((departments) =>
        departments.map((dept) => ({
          name: dept.name,
          shortName: dept.shortName,
          supervisorId: dept.supervisor.id,
          supervisorFullName: `${dept.supervisor.firstName} ${dept.supervisor.lastName}`,
          secretaryId: dept.secretary.id,
          secretaryFullName: `${dept.secretary.firstName} ${dept.secretary.lastName}`,
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

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('Server error details:', error.error);

    // Ako server vrati objekat sa message, koristimo to, u suprotnom ceo odgovor
    let errorMessage =
      error.error?.message || error.error || 'An unknown error occurred!';

    return throwError(() => new Error(errorMessage));
  }
}
