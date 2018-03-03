package com.paratussoftware.grimoire.positronicBrain.functions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SigmoidTest {

    private Sigmoid sigmoid;
    private final double DELTA = 0.00001;

    @Before
    public void setUp(){
        sigmoid = new Sigmoid();
    }

    @Test
    public void exeduteFunction() {
        assertEquals(0.66818777216, sigmoid.process(0.7), DELTA);
        assertEquals(0.62245933120, sigmoid.process(0.5), DELTA);
        assertEquals(0.54983399731, sigmoid.process(0.2), DELTA);
        assertEquals(0.33181222783, sigmoid.process(-0.7), DELTA);
        assertEquals(0.73105857863, sigmoid.process(1.0), DELTA);
        assertEquals(0.78583498304, sigmoid.process(1.3), DELTA);
        assertEquals(0.68997448112, sigmoid.process(0.8), DELTA);

        assertEquals(0.5, sigmoid.process(0.0), DELTA);
        assertEquals(0.5, sigmoid.process(-0.0), DELTA);
        assertEquals(0.2689414213699951, sigmoid.process(-1.0), DELTA);
    }

}