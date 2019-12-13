import { Component, Inject } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Board } from '../shared/board';

@Component({
    selector: 'app-modal',
    templateUrl: 'app-modal.html',
  })
  export class AppModalComponent {
    constructor(
      public dialogRef: MatDialogRef<AppModalComponent>,
      @Inject(MAT_DIALOG_DATA) public data: Board) {}

    finish(): void {
      this.dialogRef.close();
    }
  }
