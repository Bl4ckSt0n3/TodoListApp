import {Component} from '@angular/core';
import {Todo, TodoModel, TodoService} from "./todo.service";
import {Observable} from "rxjs";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {

  // <!--<app-todo-item *ngFor="let todo of todos$ | async" [item]="todo"></app-todo-item>-->
  readonly todos$: Observable<Todo[]>;
  filteredData: Todo[] = [];
  showLoader: boolean = false;
  showLoaderApi: boolean = false;

  todoApiList: Todo[] = [];
  filteredTodoApiList: Todo[] = [];

  todoForm!: FormGroup;

  onSearchInput(inputTerm: any) {

    // I used inputTerm parameter to reach input value however ngModel(two-way binding) also could be used 
    if (inputTerm.data) {
      this.filteredData = this.filteredData.filter(
        (todoData: any) => todoData.task.toLowerCase().includes(inputTerm.data.toLowerCase())
      )
    }
    else {
      this.todos$.subscribe((todo: Todo[]) => {
        this.filteredData = todo;
      })
    }
  }

  onSearchInputApiList(inputTerm: any) {

    if (inputTerm.data) {
      this.filteredTodoApiList = this.filteredTodoApiList.filter(
        (todoData: any) => todoData.task.toLowerCase().includes(inputTerm.data.toLowerCase())
      )
    }
    else {
      this.filteredTodoApiList = this.todoApiList;
    }
  }

  /**
   * remove todo from mock api
   * @param item 
   */
  removeTodoItem(item: Todo) {
    this.showLoader = true;
    this.todoService
        .remove(item.id)
        .subscribe({
          next: (value) => {
            console.log(value);
            if (value) {
              this.todoService.getAll()
                .subscribe((todo: Todo[]) => {
                  this.filteredData = todo;
                  this.showLoader = false;
                }
              )
              
            }

          },
          error: (err) => { 
            this.showLoader = false;
            alert("Something went wrong!");
            console.log(err);
          },
        })
  }

  /**
   * API Requests
   */
  getTodoListFromAPI() {
    this.showLoaderApi = true;
    this.todoService
    .getTodoList()
    .pipe (
      finalize(() => { this.showLoaderApi = false })
    )
    .subscribe({
      next: (value) => {
          this.filteredTodoApiList = this.todoApiList = value.Data;
      },
      error: (err) => {
          alert(err.Message)
      },
    })
  }

  addTodo() {
    if(this.todoForm.invalid) {
      Object.keys(this.fControls)
            .forEach((control: any) => {
              this.fControls[control].markAsTouched();
            });
      alert("Please fill required fields");
      return;
    }
    let formData = this.todoForm.getRawValue()
    let requestModel = new TodoModel();
    Object.assign(requestModel, formData);
    this.todoService
        .addTodoItem(requestModel)
        .pipe(
          finalize(() => {
            this.getTodoListFromAPI();
            this.todoForm.reset();
          })
        )
        .subscribe({
          next: (value) => {
              alert(value.Data);
          },
          error: (err) => {
              alert('Error');
          },
        })
  }
  removeTodoOnAPI(id: number) {
    this.todoService
        .deleteTodoItem(id)
        .pipe(
          finalize(() => {
            this.getTodoListFromAPI();
          })
        )
        .subscribe({
          next: (value) => {
              alert(value.Data);
          },
          error: (err) => {
              alert('Error');
          },
        })
  }

  /**
   * create form
   */
  createForm() {
    this.todoForm = this.fb.group({
      priority: ['', [Validators.required, Validators.pattern('^[0-3]*$')]],
      task: ['', [Validators.required]]
    })
  }

  public get fControls() {
    return this.todoForm.controls;
  }

  constructor(
    private readonly todoService: TodoService,
    private readonly fb: FormBuilder
  ) {
    this.todos$ = todoService.getAll();
    this.todos$.subscribe((todo: Todo[]) => {
      this.filteredData = todo;
    });
    this.getTodoListFromAPI();
    this.createForm();
  }
}
