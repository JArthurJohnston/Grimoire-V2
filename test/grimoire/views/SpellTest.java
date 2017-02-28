package grimoire.views;

import grimoire.mocks.MockCaster;
import grimoire.models.UserSettings;
import grimoire.models.processors.gestures.Gesture;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpellTest {

    @Test
    public void testCastsSpellsWhenReady() throws Exception{
        UserSettings.SPELLCASTING_THRESHOLD = 5;
        MockCaster mockCaster = new MockCaster();
        Spell spell = new Spell(mockCaster, "", Gesture.DOWNWARDS);

        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);

        spell.cast(Gesture.DOWNWARDS);
        assertSame(spell, mockCaster.lastSpellPassedToCast);
    }

    @Test
    public void testCastsSpellWhenAllGesturesAreReady() throws Exception{
        UserSettings.SPELLCASTING_THRESHOLD = 3;
        MockCaster mockCaster = new MockCaster();
        Spell spell = new Spell(mockCaster, "", Gesture.DOWNWARDS, Gesture.LEFTWARDS);

        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);

        spell.cast(Gesture.LEFTWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.LEFTWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.LEFTWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);

        spell.cast(Gesture.LEFTWARDS);
        assertSame(spell, mockCaster.lastSpellPassedToCast);
    }

    @Test
    public void testWontCastWhenGivenWrongDirection() throws Exception{
        UserSettings.SPELLCASTING_THRESHOLD = 2;
        MockCaster mockCaster = new MockCaster();
        Spell spell = new Spell(mockCaster, "", Gesture.DOWNWARDS);

        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.UPWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.UPWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);


        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);
        spell.cast(Gesture.DOWNWARDS);
        assertNull(mockCaster.lastSpellPassedToCast);

        spell.cast(Gesture.DOWNWARDS);
        assertSame(spell, mockCaster.lastSpellPassedToCast);

    }



}