package yura.android.elements.AI;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.Observable;

import yura.android.elements.AI.Errors.OutputError;
import yura.android.elements.AI.NeuralNetwork.BackPropagation;
import yura.android.elements.AI.NeuralNetwork.Layer;
import yura.android.elements.AI.NeuralNetwork.Neuron;
import yura.android.elements.AI.NeuralNetwork.Neuron_deprecated;


public class AiTest {

    public static class AISpace{
        public String space;
    }

    @Test
    public void neuron() throws Exception {
        double[] weight = {1.0, 1.0};
        Neuron neuron = Neuron.cteate( weight );

        Assert.assertTrue(neuron.weights.length == 2);
        Assert.assertTrue(neuron.weights[0] == 1);
        Assert.assertTrue(neuron.weights[1] == 1);

        double[] input = {0.1, 0.2};
        double res = neuron.output(input);
        Assert.assertEquals(res,0.3, 0.001);
    }

    @Test
    public void layer() throws Exception {
        double[][] weight = {
                {1.0, 1.1},
                {1.1, 1.2},
                {1.3, 1.4}
        };

        Neuron[] neurons = new Neuron[] {Neuron.cteate( weight[0] ), Neuron.cteate( weight[1] ), Neuron.cteate( weight[2] )};

        Layer layer = Layer.create(neurons);
        Assert.assertEquals(layer.inputCount(),2);
        Assert.assertEquals(layer.outputCount(),3);

        double[] input = {1.0, 1.0};
        double[] res = layer.output(input);

        Assert.assertEquals(res[0],2.1, 0.001);
        Assert.assertEquals(res[1],2.3, 0.001);
        Assert.assertEquals(res[2],2.7, 0.001);
    }

    @Test
    public void error() throws Exception {

        double[] output     = new double[] { 1.0, 5.0, 1.0 };
        double[] exp_output = new double[] { 1.0, 2.0, 2.0};

        double[] errors = OutputError.error(output, exp_output);

        Assert.assertEquals(errors[0],  0, 0.001);
        Assert.assertEquals(errors[1],  3, 0.001);
        Assert.assertEquals(errors[2], -1, 0.001);
    }

    
    @Test(expected = Layer.InvalidLayerParameter.class)
    public void layer_check_neuron() throws Exception {
        double[] weight1 = {1.0, 1.1};
        Neuron neuron1 = Neuron.cteate( weight1 );
        double[] weight2 = {1.1, 1.2, 1.3};
        Neuron neuron2 = Neuron.cteate( weight2 );

        Layer layer = Layer.create(new Neuron[] {neuron1, neuron2});
    }

    @Test
    public void layer1NeuronError() throws Exception {

        double[] weight = {0.5, 0.5};
        Neuron_deprecated.Layer layer = new Neuron_deprecated.Layer.Builder(2,1)
                .weigh(weight)
                .build();

        double[] error = {1};
        double[] errors  = BackPropagation.error(layer, error);

        Assert.assertTrue(errors.length == 2);
        Assert.assertEquals(error[0] * weight[0], errors[0], 0.01);
    }

    @Test
    public void layer2NeuronError() throws Exception {
        double[] weight1 = {0.5, 0.4};
        double[] weight2 = {0.3, 0.2};
        Neuron_deprecated.Layer layer = new Neuron_deprecated.Layer.Builder(2,2)
                .weigh(0, weight1)
                .weigh(1, weight2)
                .build();

        double[] error = {2,1};
        double[] errors  = BackPropagation.error(layer, error);

        Assert.assertTrue(errors.length == 2);
        Assert.assertEquals(error[0] * weight1[0] + error[1] * weight2[0], errors[0], 0.01);
        Assert.assertEquals(error[0] * weight1[1] + error[1] * weight2[1], errors[1], 0.01);
    }

    @Test
    public void neuronCreate() throws Exception {
        double[] weight = {1, 1};
        Neuron_deprecated neuron = new Neuron_deprecated(weight);

        double[] input  = {0.5, 0.5};
        double out = neuron.out(input);
        Assert.assertEquals(1.0, out, 0.01);
    }

    @Test
    public void layerCreate() throws Exception {
        double[] weight = {1, 1};
        double[] weight2 = {2, 2};

        Neuron_deprecated.Layer layer = new Neuron_deprecated.Layer.Builder(2,2)
                .weigh(0, weight)
                .weigh(1, weight2)
                .build();

        double[] input  = {1.0, 1.0};
        double[] out = layer.out(input);

        Assert.assertEquals(2.0, out[0], 0.01);
        Assert.assertEquals(4.0, out[1], 0.01);
    }


    @Test
    public void layerTrain() throws Exception {

        double[] initWeight =  {0.1, 0.2, 0.3};
        Neuron_deprecated.Layer layer = new Neuron_deprecated.Layer.Builder(3,2)
                .weigh(initWeight)
                .build();

        double[] input  = {1.0, 1.0, 1.0};
        double[] exp_out = {1.2, 1.2};
        BackPropagation backPropagation = new BackPropagation();

        double[] error = backPropagation.error(layer, input, exp_out);
        System.out.println("init. Error =" + BackPropagation.sum(error));

        for (int i = 0; i < 100; i++) {
            backPropagation.train(layer, input, exp_out);
        }

        error = backPropagation.error(layer, input, exp_out);
        System.out.println("trained. Error =" + BackPropagation.sum(error));
    }

    @Test
    public void layerTrainMulti() throws Exception {

        double[] initWeight =  {0.5, 0.1};
        Neuron_deprecated.Layer layer = new Neuron_deprecated.Layer.Builder(2,1)
                .weigh(initWeight)
                .build();

        double[][] input   = {{0.1, 0.1}, {0.2, 0.2}, {0.2, 0.1}, {0.1, 0.2}};
        double[][] exp_out = {{0.2},      {0.4},      {0.3},      {0.3}};

        BackPropagation backPropagation = new BackPropagation();
        double[][] error = backPropagation.error(layer, input, exp_out);
        System.out.println("init. Error = " + BackPropagation.sum(error));

        for (int i = 0; i < 20; i++) {
            backPropagation.train(layer, input, exp_out);
        }

        error = backPropagation.error(layer, input, exp_out);
        System.out.println("trained. Error = " + BackPropagation.sum(error));
    }

    @Test
    public void layerTrainHiddenLayer() throws Exception {

        double[] initWeight =  {1, 1};

        Neuron_deprecated.Layer layerHidden = new Neuron_deprecated.Layer.Builder(2,3)
                .weigh(initWeight)
                .build();

        double[] initWeight1 =  {1, 1, 1};
        Neuron_deprecated.Layer layer = new Neuron_deprecated.Layer.Builder(3,1)
                .weigh(initWeight1)
                .build();

        double[][] inputs   = {{0.1, 0.1}, {0.2, 0.2}, {0.2, 0.1}, {0.1, 0.2}};
        double[][] exp_outs = {{0.2},      {0.4},      {0.3},      {0.3}};

        {
            double[][] errors = new double[exp_outs.length][exp_outs[0].length];
            for (int k = 0; k < inputs.length; k++) {
                double[] input = inputs[k];
                double[] exp_out = exp_outs[k];

                double[] hiddenOut = layerHidden.out(input);
                double[] out = layer.out(hiddenOut);

                errors[k] = BackPropagation.error(out, exp_out);
            }
            System.out.println("init. Error = " + BackPropagation.sum(errors));
        }

        for (int kfgfgf = 0; kfgfgf < 100; kfgfgf++) {
            for (int k = 0; k < inputs.length; k++) {

                double[] input = inputs[k];
                double[] exp_out = exp_outs[k];

                // get out
                double[] hiddenOut = layerHidden.out(input);
                double[] out = layer.out(hiddenOut);

                // error
                double[] out_errors = BackPropagation.error(out, exp_out);
                double[] next_layer_errors = BackPropagation.error(layer, out_errors);

                // train
                BackPropagation.train(layer, out_errors);
                BackPropagation.train(layerHidden, next_layer_errors);
            }
        }

        {
            double[][] errors = new double[exp_outs.length][exp_outs[0].length];
            for (int k = 0; k < inputs.length; k++) {
                double[] input = inputs[k];
                double[] exp_out = exp_outs[k];

                double[] hiddenOut = layerHidden.out(input);
                double[] out = layer.out(hiddenOut);

                errors[k] = BackPropagation.error(out, exp_out);
            }
            System.out.println("trained. Error = " + BackPropagation.sum(errors));
        }

    }

    @Test
    public void neuronBase() throws Exception {

        double[] input  = {0.5, 0.5};
        double exp_out  = 1;

        double[] weight = {0, 0};
        Neuron_deprecated initNeuron = new Neuron_deprecated(weight);
        Neuron_deprecated trainedNeuron = initNeuron.clone();

        int countTrain = 5;
        for (int i = 0; i < countTrain; i++) {
            trainedNeuron.train(input, exp_out);
        }

        System.out.println("init. Error =" + initNeuron.calculateError(input, exp_out));
        System.out.println("train (" + countTrain +  " iterations) . Error = " + trainedNeuron.calculateError(input, exp_out));
    }


    @Test
    public void neuronRefactorBase() throws Exception {

        double[] input  = {0.5, 0.5};
        double exp_out  = 1;

        double[] weight = {0, 0};
        Neuron_deprecated initNeuron = new Neuron_deprecated(weight);
        Neuron_deprecated trainedNeuron = initNeuron.clone();

        int countTrain = 5;
        for (int i = 0; i < countTrain; i++) {
            trainedNeuron.train(input, exp_out);
        }

        System.out.println("init. Error =" + initNeuron.calculateError(input, exp_out));
        System.out.println("train (" + countTrain +  " iterations) . Error = " + trainedNeuron.calculateError(input, exp_out));
    }

    @Test
    public void neuronMultiParams() throws Exception {

        double[][] input  = {{0.1, 0.1}, {0.1, 0.2}, {0.2, 0.1}, {1, 1}};
        double[] exp_out  = {0.2, 0.3, 0.3, 2};

        double[] weight = {0, 0};
        Neuron_deprecated initNeuron = new Neuron_deprecated(weight);
        Neuron_deprecated trainedNeuron = initNeuron.clone();

        int countTrain = 100;

        for (int i = 0; i < input.length ; i++) {
            trainedNeuron.train(input[i], exp_out[i]);
        }

        System.out.println("init. Error =" + (Arrays.toString(initNeuron.calculateError(input, exp_out))));
        System.out.println("trained (" + countTrain * input.length +" iterations). Error =" + (Arrays.toString(trainedNeuron.calculateError(input, exp_out))));
    }

    @Test
    public void neuronLayer_2Input1Neuron() throws Exception {
        double[][] input  = { {0.5, 0.5}, {0.1, 0.2}, {0.2, 0.1}, {1, 1} };
        double[][] exp_out  = { {1}, {0.3}, {0.3}, {2} };

        Neuron_deprecated.Layer layerInit = new Neuron_deprecated.Layer(input[0].length, exp_out[0].length);
        Neuron_deprecated.Layer layerTrained = layerInit.clone();

        for (int i = 0; i < 10; i++) {
            layerTrained.train(input, exp_out);
        }

        System.out.println("inited. Error =" + (Arrays.deepToString(layerInit.calculateError(input, exp_out))));
        System.out.println("trained. Error =" + (Arrays.deepToString(layerTrained.calculateError(input, exp_out))));
    }
}