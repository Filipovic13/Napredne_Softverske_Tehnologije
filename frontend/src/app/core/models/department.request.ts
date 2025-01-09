// Model za slanje podataka ka backend-u
export interface DepartmentRequest {
  name: string;
  shortName: string;
  supervisorId: number;
  secretaryId: number;
}
