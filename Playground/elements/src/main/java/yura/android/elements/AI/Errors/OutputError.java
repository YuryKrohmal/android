package yura.android.elements.AI.Errors;

import yura.android.elements.AI.NeuralNetwork.Layer;

public class OutputError {



    public static double[] error(double[] values, double[] expected) throws InvalidParameter {
        if (values.length == 0) throw new InvalidParameter("Array is empty");
        if (values.length != expected.length) throw new InvalidParameter("Invalid array size");

        double[] err = new double[values.length];
        for (int i = 0; i < err.length ; i++) {
            err[i] = values[i] - expected[i];
        }
        return err;
    }

    public static class InvalidParameter extends Exception{
        public InvalidParameter(String s) {
            super(s);
        }
    }
}
