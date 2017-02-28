package grimoire;

import grimoire.models.processors.gestures.Gesture;
import grimoire.runes.Rune;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RuneTest {
    
    @Test
    public void testEquals() throws Exception{
        Rune rune1 = new Rune("", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT);
        Rune rune2 = new Rune("", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT);

        Rune rune3 = new Rune("", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.LEFTWARDS);
        Rune rune4 = new Rune("", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.LEFTWARDS, Gesture.RIGHTWARDS);

        Rune rune5 = new Rune("");

        assertEquals(rune1, rune2);

        assertNotEquals(rune1, rune3);
        assertNotEquals(rune2, rune3);
        assertNotEquals(rune1, rune4);
        assertNotEquals(rune1, rune5);

        assertNotEquals(rune3, rune1);
        assertNotEquals(rune3, rune2);
        assertNotEquals(rune4, rune1);
        assertNotEquals(rune5, rune1);
    }

    @Test
    public void testHashCode() throws Exception{
        Rune rune1 = new Rune("", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT);
        Rune rune2 = new Rune("", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT);
        Rune rune3 = new Rune("", Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT, Gesture.UPWARDS);
        HashMap<Rune, String> runeStringHashMap = new HashMap<>();

        runeStringHashMap.put(rune1, "foo");
        runeStringHashMap.put(rune2, "bar");
        runeStringHashMap.put(rune3, "roo");

        assertTrue(runeStringHashMap.containsKey(rune1));
        assertTrue(runeStringHashMap.containsKey(rune2));
        assertTrue(runeStringHashMap.containsKey(rune3));

        assertEquals("bar", runeStringHashMap.get(rune1));
        assertEquals("bar", runeStringHashMap.get(rune2));
        assertEquals("roo", runeStringHashMap.get(rune3));
    }
    
    @Test
    public void testMatchesGesture() throws Exception{
        Rune rune1 = new Rune("", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT);

        assertTrue(rune1.matchesGestures(Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT));
        assertFalse(rune1.matchesGestures(Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT, Gesture.UPWARDS));
    }
    

    @Test
    public void testEqualsIgnoresCommand() throws Exception{
        Rune rune1 = new Rune("foo", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT);
        Rune rune2 = new Rune("bar", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT);

        assertEquals(rune1, rune2);
    }

    //these work!!!
//    @Test
//    public void testCommand() throws Exception{
//        Rune rune = new Rune("rhythmbox-client --play");
//
//        rune.cast();
//    }
//
//    @Test
//    public void testOtherCommand() throws Exception{
//        Rune rune = new Rune("rhythmbox-client --pause");
//
//        rune.cast();
//    }




}