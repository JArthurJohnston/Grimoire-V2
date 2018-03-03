package com.paratussoftware.grimoire.positronicBrain.functions;

public class Sigmoid implements NeuronFunction {

    @Override
    public double process(double input) {
        return 1 / (1 + Math.exp(input * -1));
    }
}
