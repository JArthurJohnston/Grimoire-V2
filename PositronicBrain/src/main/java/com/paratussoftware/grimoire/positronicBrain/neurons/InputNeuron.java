package com.paratussoftware.grimoire.positronicBrain.neurons;

import java.util.LinkedList;
import java.util.List;

public class InputNeuron {

    private List<Synapse> axons;

    public InputNeuron(){
        this.axons = new LinkedList<>();
    }

    public List<Synapse> getAxons() {
        return axons;
    }

    public void addAxon(Synapse axon){
        this.axons.add(axon);
    }

    public void trigger(double value) {
        for (Synapse axon : this.axons) {
            axon.input = value;
        }
    }
}
