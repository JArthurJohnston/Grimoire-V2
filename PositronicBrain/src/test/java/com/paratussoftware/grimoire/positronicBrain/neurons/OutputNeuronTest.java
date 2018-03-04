package com.paratussoftware.grimoire.positronicBrain.neurons;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OutputNeuronTest {

    private OutputNeuron outputNeuron;

    @Before
    public void setUp(){
        outputNeuron = new OutputNeuron();
    }

    @Test
    public void construction() {
        assertTrue(outputNeuron.getDendrites().isEmpty());
    }

    @Test
    public void addDendrite() {
        Synapse synapse = new Synapse();

        outputNeuron.addDendrite(synapse);

        assertTrue(outputNeuron.getDendrites().contains(synapse));
    }

}