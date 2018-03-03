package com.paratussoftware.grimoire.positronicBrain;

import com.paratussoftware.grimoire.positronicBrain.functions.NeuronFunction;
import com.paratussoftware.grimoire.positronicBrain.functions.Sigmoid;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class NeuronTest {

    private Neuron neuron;
    private NeuronFunction activationFunction;

    @Before
    public void setUp(){
        activationFunction = new Sigmoid();
        neuron = new Neuron(activationFunction);
    }

    @Test
    public void construction() {
        assertTrue(neuron.getDendrites().isEmpty());
        assertTrue(neuron.getAxons().isEmpty());
        assertSame(activationFunction, neuron.getActivationFunction());
    }

    @Test
    public void sumInputValues() {
        LinkedList<Synapse> dendrites = setupTestSynapses();

        neuron.setDendrites(dendrites);

        double summedInputs = neuron.sumInputValues();

        assertEquals(1.410, summedInputs, 0.00001);
    }

    private LinkedList<Synapse> setupTestSynapses() {
        LinkedList<Synapse> dendrites = new LinkedList<>();
        dendrites.add(Synapse.newWith(0.78, 0.5));
        dendrites.add(Synapse.newWith(0.8, 0.9));
        dendrites.add(Synapse.newWith(0.3, 1.0));
        dendrites.add(Synapse.newWith(0.0, 0.78));
        return dendrites;
    }

    @Test
    public void trigger() {
        LinkedList<Synapse> dendrites = setupTestSynapses();
        neuron.setDendrites(dendrites);
        Synapse axonSynapse = Synapse.newWith(0.7, 0.0);
        neuron.setAxons(Collections.singletonList(axonSynapse));

        neuron.trigger();

        assertEquals(0.80376594, axonSynapse.input, 0.0001);
    }

}