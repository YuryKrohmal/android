package yura.android.elements.AI.NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;

public class Neuron {
    public final double[] weights;

    private Neuron(double[] weights) {
        this.weights = weights.clone();
    }

    private Neuron(int countInput, double defaultWeight) {
        this.weights = new double[countInput];
        for (int i = 0; i < weights.length; i++) {
            this.weights[i] = defaultWeight;
        }
    }

    public double output(double[] input) throws InvalidParameter {
        if (input.length != weights.length) throw new InvalidParameter("invalid parameter");

        double res = 0;
        for (int i = 0; i < weights.length; i++) {
            res +=  input[i] * weights[i];
        }
        return res;
    }



    public static Neuron cteate(double[] weights) {
        return new Neuron(weights);
    }

    public static class InvalidParameter extends Exception{
        public InvalidParameter(String s) {
            super(s);
        }
    }
}
