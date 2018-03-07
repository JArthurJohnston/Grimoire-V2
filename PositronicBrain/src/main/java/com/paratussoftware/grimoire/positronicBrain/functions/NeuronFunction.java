package com.paratussoftware.grimoire.positronicBrain.functions;

public abstract class NeuronFunction {

    public abstract double process(double input);

    public double derivitave(double value){
        double processedValue = this.process(value);
        return processedValue * (1 - processedValue);
    }
}
