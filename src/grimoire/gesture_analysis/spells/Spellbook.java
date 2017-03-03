package grimoire.gesture_analysis.spells;

import grimoire.Grimoire;
import grimoire.gesture_analysis.gestures.Gesture;

import java.util.List;

public class Spellbook {

    private List<Spell> spells;
    private long globalSpellCooldown = 0;

    public Spellbook(List<Spell> spells){
        this.spells = spells;
    }

    public void handle(Gesture wandGesture){
        if(System.currentTimeMillis() > globalSpellCooldown + Grimoire.UserSettings.SPELLCAST_COOLDOWN_TIME){
            for (Spell eachRune : spells) {
                eachRune.handle(wandGesture);
                if(eachRune.canBeCast()){
                    eachRune.cast();
                    fizzleSpells();
                    globalSpellCooldown = System.currentTimeMillis();
                    return;
                }
            }
        }
    }

    private void fizzleSpells(){
        for (Spell spell : spells) {
            spell.fizzle();
        }

    }

}
