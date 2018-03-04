package com.paratussoftware.grimoire.positronicBrain.neurons;

import java.util.LinkedList;
import java.util.List;

public class OutputNeuron {

    private final List<Synapse> dendrites;

    public OutputNeuron() {
        dendrites = new LinkedList<>();
    }

    public List<Synapse> getDendrites() {
        return dendrites;
    }

    public void addDendrite(Synapse synapse) {
        this.dendrites.add(synapse);
    }
}
