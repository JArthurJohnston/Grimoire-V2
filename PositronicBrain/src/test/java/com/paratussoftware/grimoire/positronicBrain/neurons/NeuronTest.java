package com.paratussoftware.grimoire.positronicBrain.neurons;

import com.paratussoftware.grimoire.positronicBrain.functions.NeuronFunction;
import com.paratussoftware.grimoire.positronicBrain.functions.Sigmoid;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NeuronTest {

    private Neuron neuron;
    private NeuronFunction activationFunction;

    @Before
    public void setUp(){
        activationFunction = new Sigmoid();
        neuron = new Neuron(activationFunction);
        addTestDendritesToNeuron(neuron);
    }

    @Test
    public void construction() {
        Neuron testNeuron = new Neuron(activationFunction);

        assertTrue(testNeuron.getDendrites().isEmpty());
        assertTrue(testNeuron.getAxons().isEmpty());
        assertSame(activationFunction, testNeuron.getActivationFunction());
    }

    @Test
    public void sumInputValues() {
        double summedInputs = neuron.sumInputValues();

        assertEquals(1.410, summedInputs, 0.00001);
    }

    private void addTestDendritesToNeuron(Neuron neuron) {
        neuron.addDendrite(Synapse.newWith(0.78, 0.5));
        neuron.addDendrite(Synapse.newWith(0.8, 0.9));
        neuron.addDendrite(Synapse.newWith(0.3, 1.0));
        neuron.addDendrite(Synapse.newWith(0.0, 0.78));
    }

    @Test
    public void trigger() {
        Synapse axonSynapse = Synapse.newWith(0.7, 0.0);
        neuron.addDendrite(axonSynapse);

        neuron.trigger();

        assertEquals(0.80376594, axonSynapse.input, 0.0001);
    }

}