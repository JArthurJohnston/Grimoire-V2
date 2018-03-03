package com.paratussoftware.grimoire.positronicBrain.functions;

public class HyperbolicTangent implements NeuronFunction{

    @Override
    public double process(double input) {
        double numerator = 1 - Math.exp(input * -2);
        double denominator = 1 + Math.exp(input * 2);
        return numerator / denominator;
    }

}
