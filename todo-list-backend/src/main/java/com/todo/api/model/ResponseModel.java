package com.todo.api.model;


public class ResponseModel<T> {
    public T Data = null;
    public String Message;
    public Boolean isSuccess;
}
