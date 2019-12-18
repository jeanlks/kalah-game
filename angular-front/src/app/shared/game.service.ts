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
      { text: '0', cols: 1, rows: 2, color: 'lightgreen', name: 'bigPit2' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p26' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p25' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p24' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p23' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p22' },
      { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p21' },
      { text: '0', cols: 1, rows: 2, color: 'lightblue', name: 'bigPit1' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p11' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p12' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p13' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p14' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p15' },
      { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p16' },
    ];
   }

}
