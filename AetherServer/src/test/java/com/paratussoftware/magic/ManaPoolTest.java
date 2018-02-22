package com.paratussoftware.magic;

import com.paratussoftware.config.Settings;
import com.paratussoftware.gestures.Gesture;
import com.paratussoftware.helpers.ReflectionHelper;
import com.paratussoftware.helpers.SettingsTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ManaPoolTest {

    private ManaPool manaPool;
    private Gesture expectedGesture;

    @Before
    public void setUp(){
        Settings.SPELL_MANA_THRESHOLD = 3;
        Settings.SPELL_COOLDOWN_TIME = 3000;
        expectedGesture = Gesture.DOWNWARDS;
        manaPool = new ManaPool(expectedGesture);
    }

    @After
    public void tearDown(){
        SettingsTestHelper.restoreDefaultSettings();
    }

    @Test
    public void construction(){
        assertEquals(0, manaPool.getMana());
    }

    @Test
    public void handleGesture(){
        manaPool.handle(Gesture.RIGHTWARDS);
        assertEquals(0, manaPool.getMana());
        assertFalse(manaPool.isReadyForCasting());

        manaPool.handle(expectedGesture);
        assertEquals(1, manaPool.getMana());
        assertFalse(manaPool.isReadyForCasting());

        manaPool.handle(Gesture.LEFTWARDS);
        assertEquals(1, manaPool.getMana());
        assertFalse(manaPool.isReadyForCasting());

        manaPool.handle(expectedGesture);
        assertEquals(2, manaPool.getMana());
        assertFalse(manaPool.isReadyForCasting());

        manaPool.handle(expectedGesture);
        assertEquals(3, manaPool.getMana());
        assertTrue(manaPool.isReadyForCasting());
    }

    @Test
    public void handleStartsDecreasingManaAfterCooldownPeriod() {
        manaPool.handle(expectedGesture);
        manaPool.handle(expectedGesture);
        manaPool.handle(expectedGesture);
        assertEquals(3, manaPool.getMana());
        ReflectionHelper.setField(manaPool, "startedChargingTime", System.currentTimeMillis() - 3005);

        manaPool.handle(Gesture.UPWARDS_LEFT);
        assertEquals(2, manaPool.getMana());
        manaPool.handle(Gesture.LEFTWARDS);
        assertEquals(1, manaPool.getMana());
        manaPool.handle(Gesture.UPWARDS);
        assertEquals(0, manaPool.getMana());

        manaPool.handle(Gesture.UPWARDS_RIGHT);
        assertEquals(0, manaPool.getMana());

        manaPool.handle(expectedGesture);
        assertEquals(1, manaPool.getMana());
        manaPool.handle(expectedGesture);
        assertEquals(2, manaPool.getMana());
        manaPool.handle(expectedGesture);
        assertEquals(3, manaPool.getMana());

        manaPool.handle(Gesture.UPWARDS_LEFT);
        assertEquals(2, manaPool.getMana());
        manaPool.handle(Gesture.LEFTWARDS);
        assertEquals(1, manaPool.getMana());
        manaPool.handle(Gesture.UPWARDS);
        assertEquals(0, manaPool.getMana());

    }
    
    @Test
    public void manaNeverDipsBelowZero(){
        manaPool.handle(Gesture.RIGHTWARDS);
        manaPool.handle(Gesture.RIGHTWARDS);
        manaPool.handle(Gesture.RIGHTWARDS);

        assertEquals(0, manaPool.getMana());
    }

}