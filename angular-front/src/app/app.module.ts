import { AppModalComponent } from './board/app-modal';
import {  GameService } from './shared/game.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import { MatInputModule, MatButtonModule, MatSelectModule, MatIconModule,
         MatDialog, MatDialogModule, MatProgressSpinnerModule, MatMenuModule, MatTableModule, MatToolbarModule 
} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { BoardComponent } from './board/board.component';
import { CommonModule } from '@angular/common';
import { RoomComponent } from './room/room.component';

@NgModule({
  declarations: [
    AppComponent,
    AppModalComponent,
    HomeComponent,
    BoardComponent,
    RoomComponent
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
    MatProgressSpinnerModule,
    ReactiveFormsModule
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
  providers: [GameService],
  bootstrap: [AppComponent]
})
export class AppModule { }
