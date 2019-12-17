import { environment } from '../../environments/environment.prod';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { Move } from './move';
const url = environment.url + '/boards';
@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) {
   }

   getAllBoards(id): Observable<any> {
    return this.http.get(url + id);
  }

   createBoard(boardName: string): Observable<any> {
     return this.http.post(`${url}/${boardName}`, null);
   }

   clearAllBoards(): Observable<any> {
     return this.http.delete(url);
   }

   makeMove(move: Move): Observable<any> {
     return this.http.post(`${url}/move`, move);
   }

}
