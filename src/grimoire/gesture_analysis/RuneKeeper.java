package grimoire.gesture_analysis;

import grimoire.gesture_analysis.gestures.Gesture;
import grimoire.gesture_analysis.spells.Rune;
import grimoire.gesture_analysis.spells.Spell;
import grimoire.gesture_analysis.spells.SpellCaster;

import java.util.ArrayList;
import java.util.List;

public class RuneKeeper {

    public static List<Spell> readSpellsFromTome(){
        ArrayList<Spell> spells = new ArrayList<>();
        SpellCaster caster = new SpellCaster();
        spells.add(createSpell(caster, "rhythmbox-client --play",
                Gesture.UPWARDS, Gesture.DOWNWARDS_LEFT, Gesture.DOWNWARDS_RIGHT));
        spells.add(createSpell(caster, "rhythmbox-client --pause",
                Gesture.UPWARDS, Gesture.RIGHTWARDS, Gesture.DOWNWARDS));

        return spells;
    }

    private static Spell createSpell(SpellCaster caster, String magicWords, Gesture... gestures) {
        Rune rune = new Rune(gestures);
        return new Spell(magicWords, rune, caster);
    }

}
