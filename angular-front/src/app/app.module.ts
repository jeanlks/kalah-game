import { AppModalComponent } from './board/app-modal';
import { AppService } from './app.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import { MatInputModule, MatButtonModule, MatSelectModule, MatIconModule,
         MatDialog, MatDialogModule, MatProgressSpinnerModule, MatMenuModule, MatTableModule, MatToolbarModule 
} from '@angular/material';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { BoardComponent } from './board/board.component';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    AppModalComponent,
    HomeComponent,
    BoardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatIconModule,
    MatCardModule,
    MatGridListModule,
    HttpClientModule,
    MatDialogModule,
    MatProgressSpinnerModule
  ],
  exports: [
     CommonModule,
     MatToolbarModule,
     MatButtonModule,
     MatCardModule,
     MatInputModule,
     MatDialogModule,
     MatTableModule,
     MatMenuModule,
     MatIconModule,
     MatProgressSpinnerModule
  ],
  entryComponents: [AppModalComponent],
  providers: [AppService],
  bootstrap: [AppComponent]
})
export class AppModule { }
