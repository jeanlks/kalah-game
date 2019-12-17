import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RoomComponent } from './room/room.component';
import { BoardComponent } from './board/board.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'rooms', component: RoomComponent},
  { path: 'game/:id', component: BoardComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
