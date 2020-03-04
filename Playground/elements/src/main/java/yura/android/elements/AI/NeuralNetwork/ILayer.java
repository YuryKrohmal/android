package yura.android.elements.AI.NeuralNetwork;

public interface ILayer {
    int inputCount();
    int outputCount();
    double[] output(double[] input) throws Layer.InvalidParameter, Neuron.InvalidParameter;
}