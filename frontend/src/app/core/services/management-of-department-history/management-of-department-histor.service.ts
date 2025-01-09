import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap, catchError, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ManagementOfDepartmentHistory } from '../../models/management-of-department-histor.interface';

@Injectable({
  providedIn: 'root',
})
export class ManagementOfDepartmentHistorService {
  private apiUrl =
    'http://localhost:8080/department/managementOfDepartmentHistory';
  private behaviourSubject = new BehaviorSubject<
    ManagementOfDepartmentHistory[]
  >([]);

  constructor(private httpClient: HttpClient) {}

  // Getter for observable management history
  get managementHistory$(): Observable<ManagementOfDepartmentHistory[]> {
    return this.behaviourSubject.asObservable();
  }

  // Fetch all management history for a specific department by department name
  getManagersByDepartment(
    departmentName: string
  ): Observable<ManagementOfDepartmentHistory[]> {
    const url = `${this.apiUrl}/managersByDepartment/${departmentName}`;
    return this.httpClient.get<ManagementOfDepartmentHistory[]>(url).pipe(
      tap((historyList) => this.behaviourSubject.next(historyList)),
      catchError(this.handleError)
    );
  }

  // Fetch all management history for a specific member by member ID
  getManagementHistoryByMember(
    memberId: number
  ): Observable<ManagementOfDepartmentHistory[]> {
    const url = `${this.apiUrl}/historyByMember/${memberId}`;
    return this.httpClient
      .get<ManagementOfDepartmentHistory[]>(url)
      .pipe(catchError(this.handleError));
  }

  // Delete management history by ID
  deleteManagementHistoryById(historyId: number): Observable<string> {
    const url = `${this.apiUrl}/${historyId}`;
    return this.httpClient.delete<string>(url).pipe(
      tap(() => {
        const updatedHistories = this.behaviourSubject.value.filter(
          (h) => h.id !== historyId
        );
        this.behaviourSubject.next(updatedHistories);
      }),
      catchError(this.handleError)
    );
  }

  // Fetch a specific management history by ID
  getManagementHistoryById(
    historyId: number
  ): Observable<ManagementOfDepartmentHistory> {
    const url = `${this.apiUrl}/${historyId}`;
    return this.httpClient
      .get<ManagementOfDepartmentHistory>(url)
      .pipe(catchError(this.handleError));
  }

  // Fetch the latest manager for a department based on role
  getLatestManagerForDepartment(
    departmentName: string,
    managerRole: string
  ): Observable<ManagementOfDepartmentHistory> {
    const url = `${this.apiUrl}/lastManager/${departmentName}/${managerRole}`;
    return this.httpClient
      .get<ManagementOfDepartmentHistory>(url)
      .pipe(catchError(this.handleError));
  }

  // Update a management history entry by ID
  updateManagementHistory(
    historyId: number,
    updatedData: Partial<ManagementOfDepartmentHistory>
  ): Observable<ManagementOfDepartmentHistory> {
    const url = `${this.apiUrl}/${historyId}`;
    return this.httpClient
      .put<ManagementOfDepartmentHistory>(url, updatedData)
      .pipe(
        tap((updatedHistory) => {
          const updatedHistories = this.behaviourSubject.value.map((h) =>
            h.id === updatedHistory.id ? updatedHistory : h
          );
          this.behaviourSubject.next(updatedHistories);
        }),
        catchError(this.handleError)
      );
  }

  // Create a new management history entry
  createManagementHistory(
    newHistory: ManagementOfDepartmentHistory
  ): Observable<ManagementOfDepartmentHistory> {
    return this.httpClient
      .post<ManagementOfDepartmentHistory>(this.apiUrl, newHistory)
      .pipe(
        tap((createdHistory) => {
          this.behaviourSubject.next([
            ...this.behaviourSubject.value,
            createdHistory,
          ]);
        }),
        catchError(this.handleError)
      );
  }

  // Error handler
  private handleError(error: HttpErrorResponse): Observable<never> {
    const errorMessage =
      error.error?.errorMessage || error.error || 'An unknown error occurred!';
    return throwError(() => new Error(errorMessage));
  }
}
