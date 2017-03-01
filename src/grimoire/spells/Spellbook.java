package grimoire.spells;

import grimoire.models.UserSettings;
import grimoire.models.processors.gestures.Gesture;

import java.util.List;

public class Spellbook {

    private List<Spell> spells;
    private long globalSpellCooldown = 0;

    public Spellbook(List<Spell> spells){
        this.spells = spells;
    }

    public void handle(Gesture wandGesture){
        for (Spell eachRune : spells) {
            eachRune.handle(wandGesture);
            if(System.currentTimeMillis() > globalSpellCooldown + UserSettings.SPELLCAST_COOLDOWN_TIME){
                if(eachRune.canBeCast()){
                    globalSpellCooldown = System.currentTimeMillis();
                    eachRune.cast();
                    fizzleSpells();
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
