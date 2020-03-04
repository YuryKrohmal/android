package yura.android.elements.AI.NeuralNetwork;

public class Layer implements ILayer {

    public final Neuron[] neurons;

    private Layer(Neuron[] neurons) {
        this.neurons = neurons;
    }

    public static class InvalidLayerParameter extends Exception{
        public InvalidLayerParameter(String s) {
            super(s);
        }
    }

    public static class InvalidParameter extends Exception{
        public InvalidParameter(String s) {
            super(s);
        }
    }

    @Override
    public int inputCount() {
        return neurons[0].weights.length;
    }

    @Override
    public int outputCount() {
        return neurons.length;
    }

    @Override
    public double[] output(double[] input) throws InvalidParameter, Neuron.InvalidParameter {
        checkInput(input);
        double[] res = new double[outputCount()];
        for (int i = 0; i < outputCount(); i++) {
            res[i] = this.neurons[i].output(input);
        }

        return res;
    }

    public static Layer create(Neuron[] neurons) throws InvalidLayerParameter {
        checkNeurons(neurons);
        return new Layer(neurons);
    }

    private static void checkNeurons(Neuron[] neurons) throws InvalidLayerParameter {
        if (neurons.length < 1) throw new InvalidLayerParameter("Layer can't create. Reason: empty param neurons");

        int countInput = neurons[0].weights.length;
        for (Neuron n: neurons) {
            if (countInput != n.weights.length) throw new InvalidLayerParameter("Layer can't create. Reason: neuron is invalid size the weith");
        }
    }
    
    private void checkInput(double[] input) throws InvalidParameter {
        if (input.length != inputCount()) throw new InvalidParameter("Input parameter has invalid lenght");
    }
}

