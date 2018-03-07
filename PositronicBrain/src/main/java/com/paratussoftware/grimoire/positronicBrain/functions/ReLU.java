package com.paratussoftware.grimoire.positronicBrain.functions;

/**
 * Rectified Linuear Unit
 */
public class ReLU extends NeuronFunction{
    @Override
    public double process(double input) {
        return Math.max(0.0, input);
    }
}
