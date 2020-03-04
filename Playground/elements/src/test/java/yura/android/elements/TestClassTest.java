package yura.android.elements;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestClassTest {

    @Test
    public void name() {
        TestClass testClass = new TestClass();
        Assert.assertEquals("TestClass", testClass.name);
    }
}