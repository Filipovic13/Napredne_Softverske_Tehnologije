export interface MetaData {
  label: string;
  key: string;
  inputType: string;
  editable?: boolean;
  options?: { label: any; value: any }[]; // Dropdown opcije
  formatter?: (row: any) => string;
}
