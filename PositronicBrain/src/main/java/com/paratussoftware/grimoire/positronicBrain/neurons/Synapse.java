package com.paratussoftware.grimoire.positronicBrain.neurons;

/**
 * Represents a connection between Neurons
 */
public class Synapse {

    static Synapse newWith(double weight, double input){
        Synapse synapse = new Synapse();
        synapse.input = input;
        synapse.weight = weight;
        return synapse;
    }

    double weight;
    double input;

    double weightedValue() {
        return this.input * this.weight;
    }
}
