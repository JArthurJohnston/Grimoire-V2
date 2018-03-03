package com.paratussoftware.grimoire.positronicBrain;

import org.junit.Test;

import static org.junit.Assert.*;

public class SynapseTest {

    @Test
    public void sum() {
        Synapse synapse = Synapse.newWith(0.9, 0.346);

        assertEquals(0.3114, synapse.weightedValue(), 0.0001);
    }

}