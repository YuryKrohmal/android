package yura.android.elements;

public class Operation {

    public interface IInputParameter<Param>{
        void complete(final Param param, final Exception error);
    }

    public interface ICustomoperation<Value> extends IObserverableOperation<Value>, IInputParameter<Value> {
    }

    public static <Value> OperationObserver<Value> just(final Value value){

        IObserverableOperation<Value> newOperation = new IObserverableOperation<Value>() {
            @Override
            public void execute(ICompletionHandler<Value> completionHandler) {
                completionHandler.result(value, null);
            }
        };

        return OperationObserver.create(newOperation);
    }

    public static <Value> ICustomoperation<Value> observableOperation(){


        class CustomOperation<Value> implements ICustomoperation<Value> {
            ICompletionHandler<Value> operation;

            @Override
            public void execute(ICompletionHandler<Value> completionHandler) {
                operation = completionHandler;
            }

            @Override
            public void complete(Value value, Exception error) {
                if (operation != null) operation.result(value, error);
            }
        }

        CustomOperation newOperation = new CustomOperation();
        return newOperation;
    }
}
