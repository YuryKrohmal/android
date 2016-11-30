package com.example;

public interface ITask<ResultType> {

    public interface IResult<T> extends IResultError, IResultSuccess<T>{

    }

    public interface IResultSuccess<T> {
        void onSuccess(T result);
    }

    public interface IResultError {
        void onError(Throwable error);
    }

    void execute(IResult<ResultType> result);
}
