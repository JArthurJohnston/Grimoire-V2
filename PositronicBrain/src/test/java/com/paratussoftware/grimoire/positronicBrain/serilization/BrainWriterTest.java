package com.paratussoftware.grimoire.positronicBrain.serilization;

import com.paratussoftware.grimoire.positronicBrain.Brain;
import com.paratussoftware.grimoire.positronicBrain.TestData;
import com.paratussoftware.grimoire.positronicBrain.neurons.Neuron;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BrainWriterTest {

    private Brain brain;

    @Before
    public void setUp(){
        brain = TestData.simpleBrain();
    }

    @Test
    public void writeSynapses() {
        String synapsesJson = BrainWriter.writeSynapsesFrom(brain);
        assertEquals(TestData.simpleBrainJson, synapsesJson);
    }

}