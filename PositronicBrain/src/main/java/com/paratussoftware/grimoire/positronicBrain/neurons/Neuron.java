package com.paratussoftware.grimoire.positronicBrain.neurons;

import com.paratussoftware.grimoire.positronicBrain.functions.NeuronFunction;

import java.util.LinkedList;
import java.util.List;

/**
 * The basic data structure of a Neural net.
 * Just like in biology, a Neuron receives data from dendrites, and sends data through axons.
 */
public class Neuron {
    private final List<Synapse> dendrites;
    private final List<Synapse> axons;
    private final NeuronFunction activationFunction;

    public Neuron(NeuronFunction activationFunction) {
        this.activationFunction = activationFunction;
        axons = new LinkedList<>();
        dendrites = new LinkedList<>();
    }

    double sumInputValues(){
        double inputSum = 0.0;
        for (Synapse eachSynapse : this.dendrites) {
            inputSum += eachSynapse.weightedValue();
        }
        return inputSum;
    }

    public List<Synapse> getDendrites() {
        return dendrites;
    }

    public List<Synapse> getAxons() {
        return axons;
    }

    public void addDendrite(Synapse synapse){
        this.dendrites.add(synapse);
    }

    public void addAxon(Synapse synapse){
        this.axons.add(synapse);
    }

    public void trigger() {
        double inputValues = this.sumInputValues();
        double activationValue = activationFunction.process(inputValues);
        for (Synapse eachSynapse : this.axons) {
            eachSynapse.input = activationValue;
        }
    }

    NeuronFunction getActivationFunction() {
        return activationFunction;
    }
}
