import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScientificFieldListComponent } from './scientific-field-list/scientific-field-list.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [ScientificFieldListComponent],
  imports: [CommonModule, SharedModule],
  exports: [ScientificFieldListComponent],
})
export class ScientificFieldModule {}
