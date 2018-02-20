package com.paratussoftware.magic;

import com.paratussoftware.config.Settings;
import com.paratussoftware.gestures.Gesture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ManaPoolTest {

    private ManaPool manaPool;
    private Gesture gesture;

    @Before
    public void setUp(){
        gesture = Gesture.DOWNWARDS;
        manaPool = new ManaPool(gesture);
    }

    @Test
    public void construction(){
        assertEquals(0, manaPool.getMana());
    }

    @Test
    public void handleGesture(){
        manaPool.handle(Gesture.DOWNWARDS);
        assertEquals(1, manaPool.getMana());

        manaPool.handle(Gesture.LEFTWARDS);
        assertEquals(1, manaPool.getMana());
    }
    
    @Test
    public void manaNeverDipsBelowZero(){
        manaPool.handle(Gesture.RIGHTWARDS);
        manaPool.handle(Gesture.RIGHTWARDS);
        manaPool.handle(Gesture.RIGHTWARDS);

        assertEquals(0, manaPool.getMana());
    }
    
    
    @Test
    public void spellFizzlesAfterCooldownPeriod(){
        
    }
    



}