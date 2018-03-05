package com.paratussoftware.grimoire.positronicBrain.serilization;

import com.paratussoftware.grimoire.positronicBrain.Brain;
import com.paratussoftware.grimoire.positronicBrain.neurons.Neuron;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BrainWriterTest {

    private Brain brain;

    @Before
    public void setUp(){
        brain = new Brain(1, 1, 2);
    }

    @Test
    public void writeBrain(){
        String brinJson = BrainWriter.write(brain);

    }

}