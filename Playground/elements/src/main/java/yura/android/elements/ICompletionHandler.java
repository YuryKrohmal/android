package yura.android.elements;

public interface ICompletionHandler<Result>{
    void result(final Result result, final Exception error);
}
