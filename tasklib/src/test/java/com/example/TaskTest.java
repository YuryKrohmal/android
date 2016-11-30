package com.example;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void createTaskTest(){
        ITask.IResult mockResult = Mockito.mock(ITask.IResult.class);

        taskSuccess().execute(mockResult);
        Mockito.verify(mockResult).onSuccess(Mockito.any());

        taskFail().execute(mockResult);
        Mockito.verify(mockResult).onError(Mockito.any());
    }

    @Test
    public void createTaskWithParamsTest(){
        ITask.IResult mockResult = Mockito.mock(ITask.IResult.class);
        taskReturnParam("1").execute(mockResult);
        Mockito.verify(mockResult).onSuccess("1");
    }


    Task<String> taskSuccess(){
        return new Task<String>() {
            @Override
            public void execute(IResult<String> result) {
                result.onSuccess("Hello world");
            }
        };
    }

    Task<String> taskFail(){
        return new Task<String>() {
            @Override
            public void execute(IResult<String> result) {
                result.onError(new Exception());
            }
        };
    }

    static <T> Task<T> taskReturnParam(final T param){
        return new Task<T>() {
            @Override
            public void execute(IResult<T> result) {
                result.onSuccess(param);
            }
        };
    }

}