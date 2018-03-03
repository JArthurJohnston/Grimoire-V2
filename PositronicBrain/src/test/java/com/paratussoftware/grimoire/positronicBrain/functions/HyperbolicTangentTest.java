package com.paratussoftware.grimoire.positronicBrain.functions;

import org.junit.Test;

import static org.junit.Assert.*;

public class HyperbolicTangentTest {

    private static final NeuronFunction function = new HyperbolicTangent();
    private static final double DELTA = 0.00001;

    @Test
    public void process() {
        assertEquals(0.1323046337394567, function.process(0.0), DELTA);
        assertEquals(0, function.process(0.2), DELTA);
        assertEquals(0, function.process(0.3), DELTA);
        assertEquals(0, function.process(0.4), DELTA);
        assertEquals(0, function.process(0.7), DELTA);
        assertEquals(0, function.process(0.9), DELTA);
        assertEquals(0, function.process(1.0), DELTA);

    }

}