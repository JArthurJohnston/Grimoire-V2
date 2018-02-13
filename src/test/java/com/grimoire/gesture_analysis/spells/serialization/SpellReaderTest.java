package grimoire.gesture_analysis.spells.serialization;

import com.paratussoftware.Grimoire;
import com.paratussoftware.gesture_analysis.gestures.Gesture;
import com.paratussoftware.gesture_analysis.spells.Spell;
import com.paratussoftware.gesture_analysis.spells.SpellCaster;
import org.junit.Ignore;
import org.junit.Test;
import com.paratussoftware.gesture_analysis.spells.serialization.SpellReader;

import java.util.List;

import static org.junit.Assert.*;


public class SpellReaderTest {

    @Ignore
    @Test
    public void testRead() throws Exception{
        SpellReader spellReader = new SpellReader();
        SpellCaster spellCaster = new SpellCaster();
        List<Spell> spells = spellReader.read(Grimoire.UserSettings.SPELLFILE_LOCATION, spellCaster);

        assertEquals(3, spells.size());
        Spell spell1 = spells.get(0);
        Spell spell2 = spells.get(1);
        Spell spell3 = spells.get(2);

        assertEquals(3, spell1.rune.gestures.length);
        assertEquals(new Gesture[]{Gesture.UPWARDS, Gesture.DOWNWARDS_LEFT, Gesture.DOWNWARDS_RIGHT}, spell1.rune.gestures);
        assertEquals("rhythmbox-client --play", spell1.magicWords);

        assertEquals(3, spell2.rune.gestures.length);
        assertEquals(new Gesture[]{Gesture.UPWARDS, Gesture.RIGHTWARDS, Gesture.DOWNWARDS}, spell2.rune.gestures);
        assertEquals("rhythmbox-client --pause", spell2.magicWords);

        assertEquals(3, spell3.rune.gestures.length);
        assertEquals(new Gesture[]{Gesture.DOWNWARDS_RIGHT, Gesture.LEFTWARDS, Gesture.DOWNWARDS_RIGHT}, spell3.rune.gestures);
        assertEquals("./lib/lumos.sh", spell3.magicWords);
    }

    @Test
    public void testReadReturnsEmptyListWhenFailsToReadFile() throws Exception{
        SpellReader spellReader = new SpellReader();
        SpellCaster spellCaster = new SpellCaster();
        List<Spell> spells = spellReader.read("file that doesnt exist", spellCaster);

        assertTrue(spells.isEmpty());
    }



}