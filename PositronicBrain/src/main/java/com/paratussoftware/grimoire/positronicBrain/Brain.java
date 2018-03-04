package com.paratussoftware.grimoire.positronicBrain;

import com.paratussoftware.grimoire.positronicBrain.functions.NeuronFunction;
import com.paratussoftware.grimoire.positronicBrain.functions.Sigmoid;
import com.paratussoftware.grimoire.positronicBrain.neurons.InputNeuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.Neuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.OutputNeuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.Synapse;

import java.util.LinkedList;
import java.util.List;

public class Brain {

    private LinkedList<InputNeuron> inputs;
    private LinkedList<OutputNeuron> outputs;
    private List<List<Neuron>> hiddenLayers;
    private NeuronFunction activationFunction;

    public Brain(int numberOfInputs, int numberOfOutputs, int... hiddenLayerData){
        activationFunction = new Sigmoid();
        initializeHiddenLayers(hiddenLayerData);
        initializeInputNeurons(numberOfInputs);
        initializeOutputNeurons(numberOfOutputs);
    }

    List<InputNeuron> getInputs() {
        return inputs;
    }

    List<OutputNeuron> getOutputs() {
        return outputs;
    }

    List<List<Neuron>> getHiddenLayers() {
        return hiddenLayers;
    }

    private void initializeInputNeurons(int numberOfInputs){
        inputs = new LinkedList<>();
        for (int counter = 0; counter < numberOfInputs; counter++) {
            InputNeuron inputNeuron = new InputNeuron();
            createConnectionsBetween(inputNeuron, this.hiddenLayers.get(0));
            inputs.add(inputNeuron);
        }
    }

    private void createConnectionsBetween(InputNeuron inputNeuron, List<Neuron> neuronLayer) {
        for (Neuron neuron : neuronLayer) {
            Synapse synapse = new Synapse();
            inputNeuron.addAxon(synapse);
            neuron.addDendrite(synapse);
        }
    }

    private void initializeOutputNeurons( int numberOfOutputs){
        outputs = new LinkedList<>();
        for (int counter = 0; counter < numberOfOutputs; counter++) {
            outputs.add(new OutputNeuron());
        }
    }

    private void initializeHiddenLayers(int[] hiddenLayerData){
        hiddenLayers = new LinkedList<>();
        for (int numberOfNeuronsInLayer : hiddenLayerData) {
            LinkedList<Neuron> neurons = new LinkedList<>();
            for (int counter = 0; counter < numberOfNeuronsInLayer; counter++) {
                neurons.add(new Neuron(activationFunction));
            }
            hiddenLayers.add(neurons);
        }
    }
}
