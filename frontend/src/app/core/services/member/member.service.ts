import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { Member } from '../../models/member.interface';

@Injectable({
  providedIn: 'root',
})
export class MemberService {
  private apiUrl = 'http://localhost:8080/member';
  private behaviorSubject = new BehaviorSubject<Member[]>([]);

  constructor(private http: HttpClient) {}

  get members$(): Observable<Member[]> {
    return this.behaviorSubject.asObservable();
  }

  getMembers(): Observable<Member[]> {
    return this.http.get<Member[]>(this.apiUrl).pipe(
      tap((members) => this.behaviorSubject.next(members)),
      catchError(this.handleError)
    );
  }

  createMember(newMember: Member): Observable<Member> {
    return this.http.post<Member>(this.apiUrl, newMember).pipe(
      tap((createdMember) => {
        this.behaviorSubject.next([
          ...this.behaviorSubject.value,
          createdMember,
        ]);
      }),
      catchError(this.handleError)
    );
  }

  updateScienceFields(
    memberId: number,
    academicTitle: string,
    educationTitle: string,
    scientificField: string
  ): Observable<Member> {
    const url = `${this.apiUrl}/updateScienceFields/${memberId}`;
    const updatedData = {
      academicTitle: academicTitle,
      educationTitle: educationTitle,
      scientificField: scientificField,
    };

    return this.http.patch<Member>(url, updatedData).pipe(
      tap((updatedMember) => {
        const updatedMembers = this.behaviorSubject.value.map((m) =>
          m.id === updatedMember.id ? updatedMember : m
        );
        this.behaviorSubject.next(updatedMembers);
      })
    );
  }

  updateDepartment(
    memberId: number,
    departmentName: string
  ): Observable<Member> {
    const url = `${this.apiUrl}/updateDepartment/${departmentName}`;

    return this.http.patch<Member>(url, departmentName).pipe(
      tap((updatedMember) => {
        const updatedMembers = this.behaviorSubject.value.map((m) =>
          m.id === updatedMember.id ? updatedMember : m
        );
        this.behaviorSubject.next(updatedMembers);
      })
    );
  }

  deleteMember(memberId: number): Observable<string> {
    const url = `${this.apiUrl}/${memberId};`;
    return this.http.delete(url, { responseType: 'text' }).pipe(
      tap(() => {
        const updatedMembers = this.behaviorSubject.value.filter(
          (m) => m.id !== memberId
        );
        this.behaviorSubject.next(updatedMembers);
      })
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage =
      error.error?.errorMessage ||
      error.error ||
      'Unknown error message occured!';

    return throwError(() => new Error(errorMessage));
  }
}
