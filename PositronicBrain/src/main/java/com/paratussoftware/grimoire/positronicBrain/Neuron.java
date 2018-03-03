package com.paratussoftware.grimoire.positronicBrain;

import com.paratussoftware.grimoire.positronicBrain.functions.NeuronFunction;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The basic data structure of a Neural net.
 * Just like in biology, a Neuron receives data from dendrites, and sends data through axons.
 */
public class Neuron {
    private List<Synapse> dendrites;
    private List<Synapse> axons;
    private final NeuronFunction activationFunction;

    public Neuron(NeuronFunction activationFunction) {
        this.activationFunction = activationFunction;
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

    public void setDendrites(List<Synapse> dendrites) {
        this.dendrites = dendrites;
    }

    public void setAxons(List<Synapse> axons) {
        this.axons = axons;
    }

    public void trigger() {
        double inputValues = this.sumInputValues();
        double activationValue = activationFunction.process(inputValues);
        for (Synapse eachSynapse : this.axons) {
            eachSynapse.input = activationValue;
        }

    }

    public NeuronFunction getActivationFunction() {
        return activationFunction;
    }
}
