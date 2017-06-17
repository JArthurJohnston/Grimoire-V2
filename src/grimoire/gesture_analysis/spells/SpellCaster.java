package grimoire.gesture_analysis.spells;

import java.io.IOException;

public class SpellCaster implements CasterInterface {

    public boolean cast(Spell spell){
        try {
            System.out.println("Casting: " + spell.magicWords);
            Runtime.getRuntime().exec(spell.getMagicWords() + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
