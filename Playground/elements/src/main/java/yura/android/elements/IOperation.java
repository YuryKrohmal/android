package yura.android.elements;


public interface IOperation<Result, Param>{
    Result execute(Param value);
}