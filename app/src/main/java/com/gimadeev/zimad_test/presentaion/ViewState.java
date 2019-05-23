package com.gimadeev.zimad_test.presentaion;

public class ViewState<T> {

    public enum Status {
        LOADING, SUCCESS, ERROR
    }

    public Status status;
    public T data;
    public Throwable error;

    public ViewState(Status status) {
        this.status = status;
    }

    public ViewState(Status status, Throwable error) {
        this.status = status;
        this.error = error;
    }

    public ViewState(Status status, T data) {
        this.status = status;
        this.data = data;
        this.error = error;
    }
}
