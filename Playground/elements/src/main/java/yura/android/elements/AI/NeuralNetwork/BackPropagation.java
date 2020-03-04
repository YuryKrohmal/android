package yura.android.elements.AI.NeuralNetwork;

public class BackPropagation {

    public static void train(Neuron_deprecated.Layer layer, double[] errors) throws Exception {

        if (layer.countOut() != errors.length)
            throw new Exception("invalid parameter");

        for (int i = 0; i < layer.countNeurons(); i++) {
            for (int j = 0; j < layer.countWeight(); j++) {
                layer.neurons[i].weight[j] = layer.neurons[i].weight[j] + layer.neurons[i].weight[j] * errors[i];
            }
        }
    }

    public void train(Neuron_deprecated.Layer layer, double[] input, double[] exp_out) throws Exception {
        if (layer.countInput() != input.length)
            throw new Exception("invalid parameter");
        if (layer.countOut() != exp_out.length)
            throw new Exception("invalid parameter");

        double[] out_error = error(layer, input, exp_out);

        for (int i = 0; i < layer.countNeurons(); i++) {
            for (int j = 0; j < layer.countWeight(); j++) {
                layer.neurons[i].weight[j] =  layer.neurons[i].weight[j] + (layer.neurons[i].weight[j] * out_error[i]);
            }
        }
    }

    public void train(Neuron_deprecated.Layer layer, double[][] input, double[][] exp_out) throws Exception {
        if (layer.countInput() != input[0].length)
            throw new Exception("invalid parameter");
        if (layer.countOut() != exp_out[0].length)
            throw new Exception("invalid parameter");

        if (input.length != exp_out.length)
            throw new Exception("invalid parameter");

        for (int k = 0; k < input.length; k++) {
            double[] curr_input   =  input[k];
            double[] curr_exp_out =  exp_out[k];
            train(layer, curr_input, curr_exp_out);
        }
    }

    public static double[] error(Neuron_deprecated.Layer layer, double[] input, double[] exp_out) throws Exception {
        double[] out = layer.out(input);
        double[] out_error = new double[out.length];
        for (int i = 0; i < out.length; i++) {
            out_error[i] = exp_out[i] - out[i];
        }
        return out_error;
    }

    public static double[][] error(Neuron_deprecated.Layer layer, double[][] input, double[][] exp_out) throws Exception {

        double[][] errors = new double[input.length][layer.countOut()];
        for (int k = 0; k < input.length; k++) {
            double[] curr_input   =  input[k];
            double[] curr_exp_out =  exp_out[k];
            double[] curr_error = error(layer, curr_input, curr_exp_out);
            errors[k] = curr_error;
        }
        return errors;
    }

    public static double[] error(double[] out, double[] exp_out) throws Exception {

        if (out.length != exp_out.length)
            throw new Exception("invalid parameter");

        double[] errors = new double[out.length];
        for (int j = 0; j < out.length; j++) {
            errors[j] = exp_out[j] - out[j];
        }
        return errors;
    }

    public static double[] error(Neuron_deprecated.Layer layer, double[] errors) throws Exception {

        if (layer.countOut() != errors.length)
            throw new Exception("invalid parameter");

        double[] res_errors = new double[layer.countInput()];

        for (int i = 0; i < layer.countNeurons(); i++) {
            for (int j = 0; j < layer.countInput(); j++) {
                res_errors[j] = res_errors[j] + layer.neurons[i].weight[j] * errors[i];
            }
        }
        return res_errors;
    }

    public static double sum(double[] errors){
        double error = 0;
        for (int i = 0; i < errors.length; i++) {
            error+= Math.abs(errors[i]);
        }
        return error;
    }

    public static double sum(double[][] errors){
        double error = 0;
        for (int i = 0; i < errors.length; i++) {
            error += sum(errors[i]);
        }
        return error;
    }

}
