package yura.android.elements;

public class OperationObserver<Result> {
    private IObserverableOperation<Result> operation;

    private OperationObserver(IObserverableOperation<Result> oper){
        super();
        operation = oper;
    }

    public static <Res> OperationObserver<Res> create(IObserverableOperation<Res> oper){
        return new OperationObserver<Res>(oper);
    }

    public interface IHandler<Result>{
        void result(final Result result);
    }

    public OperationObserver<Result> error(final Exception exception){

        IObserverableOperation<Result> newOperation = new IObserverableOperation<Result>() {
            final OperationObserver<Result> nextOperation = OperationObserver.this;

            @Override
            public void execute(final ICompletionHandler<Result> completionHandler) {
                completionHandler.result(null, exception);
            }
        };

        return OperationObserver.create( newOperation );
    }



    public OperationObserver<Result> next(IHandler<Result> nextHandler){

        IObserverableOperation<Result> newOperation = new IObserverableOperation<Result>() {
            final OperationObserver<Result> nextOperation = OperationObserver.this;

            @Override
            public void execute(final ICompletionHandler<Result> completionHandler) {

                ICompletionHandler<Result> newCompleteHandler = (Result result, Exception error) -> {
                    try {
                        if (error != null) {
                            completionHandler.result(null, error);
                            return;
                        }
                        nextHandler.result(result);
                        completionHandler.result(result, null);
                    } catch (Exception ex) {
                        completionHandler.result(null, ex);
                    }

                };
                nextOperation.operation.execute(newCompleteHandler);
            }
        };

        return OperationObserver.create( newOperation );
    }

    public <NewResult> OperationObserver<NewResult> map(IOperation<NewResult, Result> mapHandler){

        IObserverableOperation<NewResult> newOperation = new IObserverableOperation<NewResult>() {
            final OperationObserver<Result> nextOperation = OperationObserver.this;

            @Override
            public void execute(final ICompletionHandler<NewResult> completionHandler) {

                ICompletionHandler<Result> newCompleteHandler = (Result newResult, Exception error)-> {

                    try {
                        if (error != null) {
                            completionHandler.result(null, error);
                            return;
                        }
                        NewResult res = mapHandler.execute(newResult);
                        completionHandler.result(res, null);
                    } catch (Exception ex) {
                        completionHandler.result(null, ex);
                    }
                };

                nextOperation.operation.execute(newCompleteHandler);
            }
        };

        return OperationObserver.create( newOperation );
    }

    public OperationObserver<Result> filter(IOperation<Boolean, Result> filterHandler){

        IObserverableOperation<Result> newOperation = new IObserverableOperation<Result>() {
            final OperationObserver<Result> nextOperation = OperationObserver.this;

            @Override
            public void execute(final ICompletionHandler<Result> completionHandler) {

                ICompletionHandler<Result> newCompleteHandler = (Result result, Exception error)-> {

                    try {
                        if (error != null) {
                            completionHandler.result(null, error);
                            return;
                        }
                        Boolean filtered = filterHandler.execute(result);
                        if (!filtered){
                            return;
                        }
                        completionHandler.result(result, error);
                    } catch (Exception ex) {
                        completionHandler.result(null, ex);
                    }
                };

                nextOperation.operation.execute(newCompleteHandler);
            }
        };

        return OperationObserver.create( newOperation );
    }

    public OperationObserver<Result> catchError(IHandler<Exception> exceptionHandler){

        IObserverableOperation<Result> newOperation = new IObserverableOperation<Result>() {
            final OperationObserver<Result> nextOperation = OperationObserver.this;

            @Override
            public void execute(final ICompletionHandler<Result> completionHandler) {

                ICompletionHandler<Result> newCompleteHandler = (Result result, Exception error) -> {

                    try {
                        if (error != null) {
                            exceptionHandler.result(error);
                            return;
                        }
                        completionHandler.result(result, error);
                    } catch (Exception ex) {
                        completionHandler.result(null, ex);
                    }
                };

                nextOperation.operation.execute(newCompleteHandler);
            }
        };

        return OperationObserver.create( newOperation );
    }

    public IObserverableOperation<Result> complete(IHandler<Result> resultHandler){

        IObserverableOperation<Result> newOperation = new IObserverableOperation<Result>() {
            final OperationObserver<Result> nextOperation = OperationObserver.this;

            @Override
            public void execute(final ICompletionHandler<Result> completionHandler) {

                ICompletionHandler<Result> newCompleteHandler = (Result result, Exception error) -> {

                    try {
                        if (error != null) {
                            return;
                        }
                        resultHandler.result(result);
                    } catch (Exception ex){
                        if (completionHandler != null) completionHandler.result(null, ex);
                    }
                };


                try {
                    nextOperation.operation.execute(newCompleteHandler);
                } catch (Exception ex){
                    if (completionHandler != null) completionHandler.result(null, ex);
                }
            }
        };

        return newOperation;
    }

    public void build(){
        this.operation.execute(null);
    }
}
