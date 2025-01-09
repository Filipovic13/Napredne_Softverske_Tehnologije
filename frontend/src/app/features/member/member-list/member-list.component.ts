import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Member } from 'src/app/core/models/member.interface';
import { MetaData } from 'src/app/core/models/meta.data.interface';
import { MemberService } from 'src/app/core/services/member/member.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';

@Component({
  selector: 'app-member-list',
  templateUrl: './member-list.component.html',
  styleUrls: ['./member-list.component.css'],
})
export class MemberListComponent implements OnInit {
  members: Member[] = [];
  metaData: MetaData[] = [
    { label: 'Name', key: 'firstName', inputType: 'text' },
    { label: 'Surname', key: 'lastName', inputType: 'text' },
    { label: 'Academic Title', key: 'academicTitle', inputType: 'dropdown' },
    { label: 'Education Title', key: 'educationTitle', inputType: 'dropdown' },
    {
      label: 'Scientific Field',
      key: 'scientificField',
      editable: true,
      inputType: 'dropdown',
    },
    {
      label: 'Department',
      key: 'departmentName',
      editable: true,
      inputType: 'dropdown',
    },
  ];

  isEditable: boolean = false;

  constructor(
    private memberService: MemberService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.memberService.members$.subscribe((data) => (this.members = data));
    this.memberService.getMembers().subscribe();
  }

  updateField(row: any): void {
    // Prosleđujemo podatke modal komponenti
    const modalData = {
      isEdit: true, // Koristi modal za izmenu
      columns: [
        {
          label: 'Field to update',
          key: 'fieldToUpdate',
          inputType: 'dropdown',
          options: [
            { label: 'Scientific Field', value: 'scientificField' },
            { label: 'Department', value: 'departmentName' },
          ],
          required: true,
        },
        {
          label: 'Value',
          key: 'value',
          inputType: 'text',
          required: true,
        },
      ],
      data: {}, // Prazni podaci za unos nove vrednosti
    };

    // Otvori modal
    const dialogRef = this.dialog.open(ModalComponent, {
      width: '400px', // Veličina modala
      data: modalData, // Prosleđujemo podatke u modal
    });

    // Obradi rezultat iz modala
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        const fieldToUpdate = result.fieldToUpdate;
        const value = result.value;

        console.log(result);
        // Ažuriraj polje za izabranu instancu člana
        // this.memberService
        //   .updateField(row.id, fieldToUpdate, value)
        //   .subscribe(() => {
        //     alert('Field updated successfully!');
        //     this.refreshMembers();
        //   });
      }
    });
  }
}
