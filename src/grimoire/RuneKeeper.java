package grimoire;

import grimoire.models.processors.gestures.Gesture;
import grimoire.spells.Rune;
import grimoire.spells.Spell;
import grimoire.spells.SpellCaster;

import java.util.ArrayList;
import java.util.List;

class RuneKeeper {

    static List<Spell> readSpellsFromTome(){
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
