import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable, of} from "rxjs";
import {delay, map} from "rxjs/operators";
import { environment } from 'src/environments/environment';

export interface Todo {
  id: number;
  task: string;
  priority: 1 | 2 | 3;
}

interface ResponseModel {
  Data: any;
  isSuccess: boolean;
  Message: string;
}

export class TodoModel {
  id: number = 0;
  task: string = '';
  priority = 1 | 2 | 3;
}

let mockData: Todo[] = [
  { id: 0, task: 'Implement loading - frontend only', priority: 1 },
  { id: 1, task: 'Implement search - frontend only', priority: 2 },
  { id: 2, task: 'Implement delete on click - frontend only', priority: 1 },
  { id: 3, task: 'Replace mock service by integrating backend', priority: 3 },
];

function removeFromMockData(id: number) {
  mockData = mockData.filter(todo => todo.id !== id);
}

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  private readonly apiUrl = environment.apiUrl + '/todos';
  private readonly GET_TODO_LIST_ENDPOINT: string = 'getList';
  private readonly ADD_TODO_ENDPOINT: string = 'addTodo';
  private readonly DELETE_TODO_ENDPOINT: string = 'deleteTodo?id=';

  constructor(private readonly httpClient: HttpClient) {

  }
  getAll(): Observable<Todo[]> {
    return of(undefined).pipe(delay(2000), map(() => mockData));
  }

  remove(id: number): Observable<boolean> {
    return new Observable<boolean>(observer => {
      setTimeout(() => {
        if (Math.random() < .8) {
          removeFromMockData(id);
          observer.next(true);
        } else {
          observer.next(false);
          observer.error('Failed');
        }
        observer.complete();
      }, 2000)
    })
  }

  getTodoList(): Observable<ResponseModel> {
    return this.httpClient.get<ResponseModel>(`${this.apiUrl}/${this.GET_TODO_LIST_ENDPOINT}`);
  }
  addTodoItem(todoItem: TodoModel): Observable<ResponseModel> {
    return this.httpClient.post<ResponseModel>(`${this.apiUrl}/${this.ADD_TODO_ENDPOINT}`, todoItem);
  }
  deleteTodoItem(id: number) {
    return this.httpClient.delete<ResponseModel>(`${this.apiUrl}/${this.DELETE_TODO_ENDPOINT}${id}`);
  }
  getTodoById(id: number) {
    
  }
}

