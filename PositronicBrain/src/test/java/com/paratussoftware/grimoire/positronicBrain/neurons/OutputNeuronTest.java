package com.paratussoftware.grimoire.positronicBrain.neurons;

import com.paratussoftware.grimoire.positronicBrain.functions.Sigmoid;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OutputNeuronTest {

    private OutputNeuron outputNeuron;

    @Before
    public void setUp(){
        outputNeuron = new OutputNeuron(new Sigmoid());
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

    @Test
    public void deltaOutputSum() {
        Synapse synapse1 = Synapse.newWith(0.3, 0.73);
        Synapse synapse2 = Synapse.newWith(0.5, 0.79);
        Synapse synapse3 = Synapse.newWith(0.9, 0.69);
        outputNeuron.addDendrite(synapse1);
        outputNeuron.addDendrite(synapse2);
        outputNeuron.addDendrite(synapse3);
        outputNeuron.trigger();

        assertEquals(-0.13521795308332574, outputNeuron.deltaOutputSum(0), 0.000001);
    }

}