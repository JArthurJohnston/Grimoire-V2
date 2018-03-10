package com.paratussoftware.grimoire.positronicBrain.neurons;

import org.junit.Test;

import static org.junit.Assert.*;

public class SynapseTest {

    @Test
    public void sum() {
        Synapse synapse = Synapse.newWith(0.9, 0.346);

        assertEquals(0.3114, synapse.weightedValue(), 0.0001);
    }

    @Test
    public void deltaWeight() {
        Synapse synapse = Synapse.newWith(0.3, 0.73105);

        double deltaWeight = synapse.deltaWeight(-0.1344);

        assertEquals(-0.1838, deltaWeight, 0.0001);
    }

    @Test
    public void adjustWeight() {
        Synapse synapse = Synapse.newWith(0.3, 0.73105);

        synapse.adjustWeight(-0.1344);

        assertEquals(0.1162, synapse.weight, 0.0001);
    }

}