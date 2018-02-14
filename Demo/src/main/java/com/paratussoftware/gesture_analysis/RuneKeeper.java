package com.paratussoftware.gesture_analysis;

import com.paratussoftware.Grimoire;
import com.paratussoftware.gesture_analysis.spells.Spell;
import com.paratussoftware.gesture_analysis.spells.SpellCaster;
import com.paratussoftware.gesture_analysis.spells.serialization.SpellReader;

import java.util.List;

public class RuneKeeper {

    public static List<Spell> readSpellsFromTome(){
        SpellCaster caster = new SpellCaster();
        SpellReader spellSerializer = new SpellReader();
        List<Spell> spells = spellSerializer.read(Grimoire.UserSettings.SPELLFILE_LOCATION, caster);
        return spells;
    }
}
