package grimoire.gesture_analysis;

import grimoire.Grimoire;
import grimoire.gesture_analysis.spells.Spell;
import grimoire.gesture_analysis.spells.SpellCaster;
import grimoire.gesture_analysis.spells.serialization.SpellSerializer;

import java.util.List;

public class RuneKeeper {

    public static List<Spell> readSpellsFromTome(){
        SpellCaster caster = new SpellCaster();
        SpellSerializer spellSerializer = new SpellSerializer();
        List<Spell> spells = spellSerializer.read(Grimoire.UserSettings.SPELLFILE_LOCATION, caster);
        return spells;
    }
}
