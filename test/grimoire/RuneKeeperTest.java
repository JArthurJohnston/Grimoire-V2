package grimoire;

import grimoire.models.UserSettings;
import grimoire.models.processors.gestures.Gesture;
import grimoire.models.processors.mocks.MockRune;
import grimoire.runes.RuneInterface;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RuneKeeperTest {

    @Test
    public void testFiltersWandGestures() throws Exception{
        UserSettings.RUNE_BUFFER_SIZE = 5;
        RuneKeeper runeKeeper = new RuneKeeper();

        Gesture[] gestures = runeKeeper.handleWandGesture(Gesture.NONE);
        assertEquals(1, gestures.length);
        assertEquals(new Gesture[]{Gesture.NONE}, gestures);

        gestures = runeKeeper.handleWandGesture(Gesture.NONE);
        assertEquals(1, gestures.length);
        assertEquals(new Gesture[]{Gesture.NONE}, gestures);

        gestures = runeKeeper.handleWandGesture(Gesture.UPWARDS);
        assertEquals(2, gestures.length);
        assertEquals(Gesture.NONE, gestures[0]);
        assertEquals(Gesture.UPWARDS, gestures[1]);

        gestures = runeKeeper.handleWandGesture(Gesture.UPWARDS);
        assertEquals(2, gestures.length);
        assertEquals(Gesture.NONE, gestures[0]);
        assertEquals(Gesture.UPWARDS, gestures[1]);

        gestures = runeKeeper.handleWandGesture(Gesture.UPWARDS);
        assertEquals(2, gestures.length);
        assertEquals(Gesture.NONE, gestures[0]);
        assertEquals(Gesture.UPWARDS, gestures[1]);

        gestures = runeKeeper.handleWandGesture(Gesture.DOWNWARDS_LEFT);
        assertEquals(3, gestures.length);
        assertEquals(Gesture.NONE, gestures[0]);
        assertEquals(Gesture.UPWARDS, gestures[1]);
        assertEquals(Gesture.DOWNWARDS_LEFT, gestures[2]);

        gestures = runeKeeper.handleWandGesture(Gesture.NONE);
        assertEquals(3, gestures.length);
        assertEquals(Gesture.UPWARDS, gestures[0]);
        assertEquals(Gesture.DOWNWARDS_LEFT, gestures[1]);
        assertEquals(Gesture.NONE, gestures[2]);

        gestures = runeKeeper.handleWandGesture(Gesture.NONE);
        assertEquals(3, gestures.length);
        assertEquals(Gesture.UPWARDS, gestures[0]);
        assertEquals(Gesture.DOWNWARDS_LEFT, gestures[1]);
        assertEquals(Gesture.NONE, gestures[2]);


        gestures = runeKeeper.handleWandGesture(Gesture.NONE);
        assertEquals(3, gestures.length);
        assertEquals(Gesture.UPWARDS, gestures[0]);
        assertEquals(Gesture.DOWNWARDS_LEFT, gestures[1]);
        assertEquals(Gesture.NONE, gestures[2]);

        gestures = runeKeeper.handleWandGesture(Gesture.NONE);
        assertEquals(2, gestures.length);
        assertEquals(Gesture.DOWNWARDS_LEFT, gestures[0]);
        assertEquals(Gesture.NONE, gestures[1]);
    }

    @Test
    public void testTrackWand() throws Exception{
        MockRune mockRune = new MockRune();
        ArrayList<RuneInterface> runes = new ArrayList<>();
        runes.add(mockRune);
        RuneKeeper runeKeeper = new RuneKeeper(runes);

        runeKeeper.trackWand(Gesture.DOWNWARDS);
    }

}