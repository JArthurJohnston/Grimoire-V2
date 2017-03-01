package grimoire.spells;

import grimoire.models.UserSettings;
import grimoire.models.processors.gestures.Gesture;
import org.junit.Test;

import static org.junit.Assert.*;

public class RuneTest {


    @Test
    public void testCastsSpellsWhenReady() throws Exception{
        UserSettings.SPELLCASTING_THRESHOLD = 5;
        Rune spell = new Rune(Gesture.DOWNWARDS);

        spell.sense(Gesture.DOWNWARDS);
        assertFalse(spell.priceHasBeenPaid());
        spell.sense(Gesture.DOWNWARDS);
        assertFalse(spell.priceHasBeenPaid());
        spell.sense(Gesture.DOWNWARDS);
        assertFalse(spell.priceHasBeenPaid());
        spell.sense(Gesture.DOWNWARDS);
        assertFalse(spell.priceHasBeenPaid());

        spell.sense(Gesture.DOWNWARDS);
        assertTrue(spell.priceHasBeenPaid());
    }

    @Test
    public void testCastsSpellWhenAllGesturesAreReady() throws Exception{
        UserSettings.SPELLCASTING_THRESHOLD = 3;

        Rune spell = new Rune(Gesture.DOWNWARDS, Gesture.LEFTWARDS);

        spell.sense(Gesture.DOWNWARDS);
        assertFalse(spell.priceHasBeenPaid());
        spell.sense(Gesture.DOWNWARDS);
        assertFalse(spell.priceHasBeenPaid());
        spell.sense(Gesture.DOWNWARDS);
        assertFalse(spell.priceHasBeenPaid());

        spell.sense(Gesture.LEFTWARDS);
        assertFalse(spell.priceHasBeenPaid());
        spell.sense(Gesture.LEFTWARDS);
        assertFalse(spell.priceHasBeenPaid());

        spell.sense(Gesture.LEFTWARDS);
        assertFalse(spell.priceHasBeenPaid());
    }

    @Test
    public void testWontCastWhenGivenWrongDirection() throws Exception{
        UserSettings.SPELLCASTING_THRESHOLD = 3;

        Rune spell = new Rune(Gesture.DOWNWARDS);

        spell.sense(Gesture.DOWNWARDS);
        assertFalse(spell.priceHasBeenPaid());
        spell.sense(Gesture.UPWARDS);
        assertFalse(spell.priceHasBeenPaid());
        spell.sense(Gesture.DOWNWARDS);
        assertFalse(spell.priceHasBeenPaid());

        spell.sense(Gesture.DOWNWARDS);
        assertFalse(spell.priceHasBeenPaid());
        spell.sense(Gesture.DOWNWARDS_LEFT);
        assertTrue(spell.priceHasBeenPaid());

    }


}