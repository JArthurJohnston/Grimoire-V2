package grimoire;

import grimoire.views.Spell;

import java.io.IOException;

public class SpellCaster implements CasterInterface{

    public boolean cast(Spell spell){
        try {
            Runtime.getRuntime().exec(spell.getMagicWords() + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
