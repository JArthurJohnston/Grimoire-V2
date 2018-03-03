package com.paratussoftware.grimoire.positronicBrain;

/**
 * Represents a connection between Neurons
 */
public class Synapse {

    public static Synapse newWith(double weight, double input){
        Synapse synapse = new Synapse();
        synapse.input = input;
        synapse.weight = weight;
        return synapse;
    }

    double weight;
    double input;

    public double weightedValue() {
        return this.input * this.weight;
    }
}
