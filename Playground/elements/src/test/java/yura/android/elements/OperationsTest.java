package yura.android.elements;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;


public class OperationsTest {

    @Test
    public void completeTest() {
        Operation.just("just").complete( (result) -> {
            Assert.assertThat(result, is("just") );
        });

        Operation.just("just").complete( (result) -> {
            throw new RuntimeException("Exception");
        });
    }

    @Test
    public void mapTest() {
        Operation.just("value").map( value -> {
            Assert.assertThat(value, is("value") );
            return value + "+map";
        }).complete( (String result) -> {
            Assert.assertThat(result, is("value+map") );
        });
    }

    @Test
    public void catchError() {

        Operation.just(5).error(new RuntimeException("Error")).next(integer -> {
            Assert.assertEquals("Method must not called", "");
        }).catchError( (Exception e) -> {
            Assert.assertThat(e.getMessage(), is("Error") );
        }).complete( (result) -> {
            Assert.assertThat("", is("Complete must not call") );
        });

        Operation.just(1).next(integer -> {
            throw new RuntimeException("Error");
        }).catchError( (Exception e) -> {
            Assert.assertThat(e.getMessage(), is("Error") );
        }).complete( (result) -> {
            Assert.assertThat("", is("Complete must not call") );
        });
    }

    @Test
    public void observable() {

        Operation.ICustomoperation<String> customoperation = Operation.observableOperation();

        OperationObserver.create(customoperation).complete( (result) -> {
            Assert.assertThat(result, anyOf( is("1"), is("3")) );
        });

        customoperation.complete("1", null);
        customoperation.complete("3", null);
    }


    @Test
    public void filterTest() {

        Operation.ICustomoperation<String> customoperation = Operation.observableOperation();

        OperationObserver.create(customoperation).filter(value -> {
            Assert.assertThat(value, anyOf( is("1"), is("2")) );
            return value == "1";
        }).complete( (result) -> {
            Assert.assertThat(result, is("1") );
        });

        customoperation.complete("1", null);
        customoperation.complete("2", null);
    }
}