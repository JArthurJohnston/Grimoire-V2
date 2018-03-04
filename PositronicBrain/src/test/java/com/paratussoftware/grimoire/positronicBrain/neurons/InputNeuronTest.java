package com.paratussoftware.grimoire.positronicBrain.neurons;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputNeuronTest {

    private InputNeuron inputNeuron;

    @Before
    public void setUp(){
        inputNeuron = new InputNeuron();
    }

    @Test
    public void construction() {
        assertEquals(0, inputNeuron.getAxons().size());
    }

    @Test
    public void input() {
        Synapse synapse1 = new Synapse();
        Synapse synapse2 = new Synapse();
        inputNeuron.addAxon(synapse1);
        inputNeuron.addAxon(synapse2);

        double triggeredValue = 3.5;
        inputNeuron.trigger(triggeredValue);

        assertEquals(triggeredValue, synapse1.input, 0.0);
        assertEquals(triggeredValue, synapse2.input, 0.0);
    }

}