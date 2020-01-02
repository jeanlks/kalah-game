import { environment } from '../../environments/environment.prod';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { Move } from './move';
import { Tile } from './tile';
import { Board } from './board';
const url = environment.url + '/boards';
@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) {
   }

   getAllBoards(): Observable<any> {
    return this.http.get(url);
  }

   createBoard(boardName: string): Observable<any> {
     return this.http.post(`${url}/${boardName}`, null);
   }

   joinBoard(board: Board): Observable<any> {
    return this.http.post(`${url}/join`, board);
  }

   clearAllBoards(): Observable<any> {
     return this.http.delete(url);
   }

   makeMove(move: Move): Observable<any> {
     return this.http.post(`${url}/move`, move);
   }

   getTiles(): Tile[] {
     return [
      { text: '0', cols: 1, rows: 2, color: 'lightgreen', name: 'P2_BIG_PIT' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'P26' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'P25' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'P24' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'P23' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'P22' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'P21' },
      { text: '0', cols: 1, rows: 2, color: 'lightblue', name: 'P1_BIG_PIT' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'P11' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'P12' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'P13' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'P14' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'P15' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'P16' },
    ];
   }

}
