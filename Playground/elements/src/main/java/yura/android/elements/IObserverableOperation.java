package yura.android.elements;

public interface IObserverableOperation<Result>{
    void execute(ICompletionHandler<Result> completionHandler);
}
