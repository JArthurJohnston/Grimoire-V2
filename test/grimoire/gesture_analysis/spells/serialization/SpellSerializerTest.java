package grimoire.gesture_analysis.spells.serialization;

import grimoire.Grimoire;
import grimoire.gesture_analysis.RuneKeeper;
import grimoire.gesture_analysis.gestures.Gesture;
import grimoire.gesture_analysis.spells.Spell;
import grimoire.gesture_analysis.spells.SpellCaster;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class SpellSerializerTest {

    @Test
    public void testWrite() throws Exception{
        SpellSerializer spellSerializer = new SpellSerializer();
        List<Spell> spells = RuneKeeper.readSpellsFromTome();
        spellSerializer.write(spells);

        testRead();
    }

    @Test
    public void testRead() throws Exception{
        SpellSerializer spellSerializer = new SpellSerializer();
        SpellCaster spellCaster = new SpellCaster();
        List<Spell> spells = spellSerializer.read(Grimoire.UserSettings.SPELLFILE_LOCATION, spellCaster);

        assertEquals(2, spells.size());
        Spell spell1 = spells.get(0);
        Spell spell2 = spells.get(1);

        assertEquals(3, spell1.rune.gestures.length);
        assertEquals(new Gesture[]{Gesture.UPWARDS, Gesture.DOWNWARDS_LEFT, Gesture.DOWNWARDS_RIGHT}, spell1.rune.gestures);
        assertEquals("rhythmbox-client --play", spell1.magicWords);

        assertEquals(3, spell2.rune.gestures.length);
        assertEquals(new Gesture[]{Gesture.UPWARDS, Gesture.RIGHTWARDS, Gesture.DOWNWARDS}, spell2.rune.gestures);
        assertEquals("rhythmbox-client --pause", spell2.magicWords);
    }


}