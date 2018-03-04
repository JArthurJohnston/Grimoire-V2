package com.paratussoftware.grimoire.positronicBrain.neurons;

import java.util.Random;

/**
 * Represents a connection between Neurons
 */
public class Synapse {

    private static Random weightGenerator = new Random();

    public static Synapse newWith(double weight, double input){
        Synapse synapse = new Synapse();
        synapse.input = input;
        synapse.weight = weight;
        return synapse;
    }

    public static Synapse newUnTrained(){
        Synapse synapse = new Synapse();
        synapse.weight = weightGenerator.nextDouble();
        return synapse;
    }

    public double weight;
    public double input;

    double weightedValue() {
        return this.input * this.weight;
    }
}
