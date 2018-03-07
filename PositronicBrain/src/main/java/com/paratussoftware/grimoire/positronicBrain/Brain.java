package com.paratussoftware.grimoire.positronicBrain;

import com.paratussoftware.grimoire.positronicBrain.functions.NeuronFunction;
import com.paratussoftware.grimoire.positronicBrain.functions.Sigmoid;
import com.paratussoftware.grimoire.positronicBrain.neurons.InputNeuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.Neuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.OutputNeuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.Synapse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Brain {

    private ArrayList<InputNeuron> inputs;
    private ArrayList<OutputNeuron> outputs;
    private LinkedList<List<Neuron>> hiddenLayers;
    private NeuronFunction activationFunction;

    public Brain(int numberOfInputs, int numberOfOutputs, int... hiddenLayerData){
        activationFunction = new Sigmoid();
        initializeHiddenLayers(hiddenLayerData);
        initializeInputNeurons(numberOfInputs);
        initializeOutputNeurons(numberOfOutputs);
    }

    public List<InputNeuron> getInputs() {
        return inputs;
    }

    public List<OutputNeuron> getOutputs() {
        return outputs;
    }

    public List<List<Neuron>> getHiddenLayers() {
        return hiddenLayers;
    }

    /**
     * triggers forward propagation of input data, calculates the delta output sum, then triggers backwards propagation
     * using the targetOutputs. Note: the delta output sum reflects the state of the neural net before
     * backwards propagation
     * @param inputData
     * @param targetOutputs
     * @return the delta output sum
     */
    public double train(double[] inputData, double[] targetOutputs){
        double[] outputValues = forwardPropagation(inputData);
        for (int index = 0; index < this.outputs.size(); index++) {
            double delta = targetOutputs[index] - outputValues[index];
        }
        return 0.0;
    }

    /**
     * triggers forward propogation of input data.
     * @param inputData
     * @return an array of outputs
     */
    public double[] process(double... inputData) {
        return forwardPropagation(inputData);
    }

    private double[] forwardPropagation(double[] inputData) {
        triggerInputNeurons(inputData);
        triggerEachHiddenLayer();
        return triggerOutputLayers();
    }

    private double[] triggerOutputLayers() {
        double[] outputValues = new double[outputs.size()];
        for (int outputIndex = 0; outputIndex < outputs.size(); outputIndex++) {
            OutputNeuron outputNeuron = outputs.get(outputIndex);
            outputNeuron.trigger();
            outputValues[outputIndex] = outputNeuron.getValue();
        }
        return outputValues;
    }

    private void triggerEachHiddenLayer() {
        for (List<Neuron> hiddenLayer : this.hiddenLayers) {
            for (Neuron neuron : hiddenLayer) {
                neuron.trigger();
            }
        }
    }

    private void triggerInputNeurons(double[] inputData) {
        for (int index = 0; index < inputData.length; index++) {
            this.inputs.get(index).trigger(inputData[index]);
        }
    }

    private void initializeInputNeurons(int numberOfInputs){
        inputs = new ArrayList<>();
        for (int counter = 0; counter < numberOfInputs; counter++) {
            InputNeuron inputNeuron = new InputNeuron();
            createConnectionsBetween(inputNeuron, this.hiddenLayers.get(0));
            inputs.add(inputNeuron);
        }
    }

    private void createConnectionsBetween(InputNeuron inputNeuron, List<Neuron> neuronLayer) {
        for (Neuron neuron : neuronLayer) {
            Synapse synapse = Synapse.newUnTrained();
            inputNeuron.addAxon(synapse);
            neuron.addDendrite(synapse);
        }
    }

    private void createConnectionsBetween(OutputNeuron outputNeuron, List<Neuron> neuronLayer) {
        for (Neuron neuron : neuronLayer) {
            Synapse synapse = Synapse.newUnTrained();
            outputNeuron.addDendrite(synapse);
            neuron.addAxon(synapse);
        }
    }

    private void initializeOutputNeurons( int numberOfOutputs){
        outputs = new ArrayList<>();
        for (int counter = 0; counter < numberOfOutputs; counter++) {
            OutputNeuron outputNeuron = new OutputNeuron(this.activationFunction);
            createConnectionsBetween(outputNeuron, this.hiddenLayers.getLast());
            outputs.add(outputNeuron);
        }
    }

    private void initializeHiddenLayers(int[] hiddenLayerData){
        hiddenLayers = new LinkedList<>();
        List<Neuron> currentLayer = null;
        for (int numberOfNeuronsInLayer : hiddenLayerData) {
            LinkedList<Neuron> neurons = new LinkedList<>();
            for (int counter = 0; counter < numberOfNeuronsInLayer; counter++) {
                neurons.add(new Neuron(activationFunction));
            }
            if(currentLayer != null){
                connectHiddenLayers(currentLayer, neurons);
            }
            currentLayer = neurons;
            hiddenLayers.add(neurons);
        }
    }

    private void connectHiddenLayers(List<Neuron> previousLayer, LinkedList<Neuron> newLayer) {
        for (Neuron eachPreviousLayerNeuron : previousLayer) {
            Synapse synapse = Synapse.newUnTrained();
            for (Neuron eachNewLayerNeuron : newLayer) {
                eachPreviousLayerNeuron.addAxon(synapse);
                eachNewLayerNeuron.addDendrite(synapse);
            }
        }
    }

    public NeuronFunction getActivationFunction() {
        return activationFunction;
    }

    public DevelopingBrain trainWith(double inputData) {
        return new DevelopingBrain(this, inputData);
    }

    class DevelopingBrain {
        private double[] inputData;
        private double[] expectedOutputs;
        private Brain brain;

        DevelopingBrain(Brain brain, double... inputData){
            this.inputData = inputData;
            this.brain = brain;
        }

        DevelopingBrain andExpectOutputs(double... expectedOutputs){
            this.expectedOutputs = expectedOutputs;
            double[] outputValues = this.brain.process(this.inputData);

            return this;
        }
    }

}
