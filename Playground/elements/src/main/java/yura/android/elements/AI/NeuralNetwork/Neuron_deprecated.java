package yura.android.elements.AI.NeuralNetwork;

public class Neuron_deprecated {

    public static class Layer {
        public final Neuron_deprecated[] neurons;


        public Layer(Layer layer) throws CloneNotSupportedException {
            super();

            neurons = new Neuron_deprecated[layer.neurons.length];
            for (int i = 0; i < layer.neurons.length ; i++) {
                neurons[i] = layer.neurons[i].clone();
            }
        }

        public Layer(int countInput, int countNeurons){
            neurons = new Neuron_deprecated[countNeurons];
            for (int i = 0; i < neurons.length; i++) {
                neurons[i] = new Neuron_deprecated(countInput);
            }
        }

        public Layer(double[][] weigh){
            neurons = new Neuron_deprecated[weigh.length];
            for (int i = 0; i < neurons.length; i++) {
                neurons[i] = new Neuron_deprecated(weigh[i]);
            }
        }

        @Override
        public Layer clone() throws CloneNotSupportedException {
            return new Layer(this);
        }


        public int countInput(){
            return neurons[0].countInput();
        }

        public int countWeight(){
            return neurons[0].countInput();
        }

        public int countOut(){
            return neurons.length;
        }
        public int countNeurons(){
            return neurons.length;
        }

        public void train(double[][] input, double[][] exp_out) throws Exception {
            if (input.length != exp_out.length)
                throw new Exception("invalid parameter");

            if (input[0].length != countInput())
                throw new Exception("invalid parameter");
            if (exp_out[0].length != countOut())
                throw new Exception("invalid parameter");

            for (int k = 0; k < countInput(); k++) {
                for (int i = 0; i < countOut(); i++) {
                    neurons[i].train(input[k], exp_out[k][i]);
                }
            }
        }

        public void train(double[][] errors) throws Exception {

            for (int k = 0; k < countInput(); k++) {
                for (int i = 0; i < countOut(); i++) {
                    neurons[i].train(errors[k]);
                }
            }
        }

        public double[] out(double[] input) throws Exception {

            if (input.length != countInput())
                throw new Exception("invalid parameter");

            double[] out = new double[countOut()];

            for (int i = 0; i < countOut(); i++){
                out[i] = neurons[i].out(input);
            }
            return out;
        }

        public double[][] calculateLayers(double[][] input) throws Exception {

            if (input[0].length != countInput())
                throw new Exception("invalid parameter");

            double[][] out = new double[input.length][countOut()];

            for (int k = 0; k < input.length; k++) {
                for (int i = 0; i < countOut(); i++){
                    out[k][i] = neurons[i].out(input[k]);
                }
            }

            return out;
        }

        public double[][] calculateError(double[][] input, double[][] exp_out) throws Exception {
            if (input.length != exp_out.length)
                throw new Exception("invalid parameter");

            if (input[0].length != countInput())
                throw new Exception("invalid parameter");
            if (exp_out[0].length != countOut())
                throw new Exception("invalid parameter");

            double[][] errors = new double[exp_out.length][exp_out[0].length];

            for (int k = 0; k < input.length; k++) {
                for (int i = 0; i < neurons.length; i++) {
                    errors[k][i] = neurons[i].calculateError(input[k], exp_out[k][i]);
                }
            }
            return errors;
        }


        public static class Builder{
            public final Layer layer;

            public Builder(int countInput, int countNeuron){
                layer = new Layer(countInput, countNeuron);
            }

            public Builder weigh(double [] weight) throws Exception {

                if (weight.length != layer.neurons[0].weight.length)
                    throw new Exception("invalid parameter");

                for (int i = 0; i < layer.neurons.length; i++) {
                    System.arraycopy(weight, 0, layer.neurons[i].weight, 0, weight.length);
                }
                return this;
            }

            public Builder weigh(int toNeuron, double [] weight) throws Exception {

                if (weight.length != layer.neurons[0].weight.length)
                    throw new Exception("invalid parameter");

                if (toNeuron > layer.neurons.length)
                    throw new Exception("invalid parameter");

                System.arraycopy(weight, 0, layer.neurons[toNeuron].weight, 0, weight.length);

                return this;
            }

            public Layer build(){
                return layer;
            }
        }
    }

    public final double[] weight;

    public Neuron_deprecated(int countInput){
        super();
        weight = new double[countInput];
    }

    public Neuron_deprecated(double[] arrWeight){
        super();
        weight = arrWeight.clone();
    }

    @Override
    public Neuron_deprecated clone() throws CloneNotSupportedException {
        return new Neuron_deprecated(weight.clone());
    }
    public int countInput(){
        return weight.length;
    }

    public double out(double[] input) throws Exception {
        if (input.length != countInput())
            throw new Exception("invalid parameter");

        double res = 0;
        for (int i = 0; i < countInput(); i++) {
            res +=  input[i] * weight[i];
        }
        return res;
    }

    public Neuron_deprecated train(double[]input, double exp_out) throws Exception {
        if (input.length != countInput())
            throw new Exception("invalid parameter");

        double error = calculateError(input, exp_out);

        for (int i = 0; i < countInput(); i++) {
            double newWeight = this.weight[i] + error * input[i];
            this.weight[i] = newWeight;
        }

        return this;
    }

    public Neuron_deprecated train(double[] errors) throws Exception {
        if (errors.length != countInput())
            throw new Exception("invalid parameter");

        for (int i = 0; i < countInput(); i++) {
            double newWeight = this.weight[i] + this.weight[i] * errors[i];
            this.weight[i] = newWeight;
        }

        return this;
    }

    public double calculateError(double[]input, double exp_out) throws Exception{
        double out = out(input);
        return exp_out - out;
    }

    public double[] calculateError(double[][] input, double[] exp_out) throws Exception{
        double[] result = new double[exp_out.length];

        if (input[0].length != weight.length)
            throw new Exception("invalid parameter");

        if (input.length != exp_out.length)
            throw new Exception("invalid parameter");

        for (int i = 0; i < input.length; i++) {
            double expout = exp_out[i];
            double out = out(input[i]);
            double error = calculateError(input[i], expout);
            result[i] = error;
        }
        //return result * (1 - result) * (exp_out - result);
        return result;
    }
}
