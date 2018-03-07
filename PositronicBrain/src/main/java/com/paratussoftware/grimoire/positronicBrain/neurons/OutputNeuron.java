package com.paratussoftware.grimoire.positronicBrain.neurons;

import com.paratussoftware.grimoire.positronicBrain.functions.NeuronFunction;

import java.util.LinkedList;
import java.util.List;

public class OutputNeuron {

    private final List<Synapse> dendrites;
    private final NeuronFunction activationFunction;
    private double value;

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



    public void trigger() {
        double inputValues = this.sumInputValues();
        this.value = activationFunction.process(inputValues);
    }

    public double getValue(){
        return this.value;
    }

    public double deltaOutputSum(double targetValue){
        double delta = targetValue - this.value;
        double derivative = this.activationFunction.derivitave(sumInputValues());
        return derivative * delta;
    }

    double sumInputValues(){
        double inputSum = 0.0;
        for (Synapse eachSynapse : this.dendrites) {
            inputSum += eachSynapse.weightedValue();
        }
        return inputSum;
    }
}
