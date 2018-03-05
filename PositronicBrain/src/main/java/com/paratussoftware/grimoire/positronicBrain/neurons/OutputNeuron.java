package com.paratussoftware.grimoire.positronicBrain.neurons;

import com.paratussoftware.grimoire.positronicBrain.functions.NeuronFunction;

import java.util.LinkedList;
import java.util.List;

public class OutputNeuron {

    private final List<Synapse> dendrites;
    private final NeuronFunction activationFunction;

    public OutputNeuron(NeuronFunction activationFunction) {
        dendrites = new LinkedList<>();
        this.activationFunction = activationFunction;
    }

    public List<Synapse> getDendrites() {
        return dendrites;
    }

    public void addDendrite(Synapse synapse) {
        this.dendrites.add(synapse);
    }

    public NeuronFunction getActivationFunction() {
        return activationFunction;
    }
}
