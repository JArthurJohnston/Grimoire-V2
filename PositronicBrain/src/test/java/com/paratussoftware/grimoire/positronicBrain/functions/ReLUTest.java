package com.paratussoftware.grimoire.positronicBrain.functions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReLUTest {

    private ReLU reLU;

    @Before
    public void setUp(){
        reLU = new ReLU();
    }

    @Test
    public void process(){
        assertEquals(1, reLU.process(1), 0.0);
        assertEquals(0.98, reLU.process(0.98), 0.0);

        assertEquals(0, reLU.process(-1), 0.0);
        assertEquals(0, reLU.process(-0.98), 0.0);

    }

}