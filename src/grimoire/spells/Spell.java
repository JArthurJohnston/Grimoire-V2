package grimoire.spells;

import grimoire.models.UserSettings;
import grimoire.models.processors.gestures.Gesture;

public class Spell {

    private final String magicWords;
    private final Rune rune;
    private CasterInterface caster;
    private long timeSinceLastCast;

    public Spell(String magicWords, Rune rune, CasterInterface caster){
        this.magicWords = magicWords;
        this.rune = rune;
        this.caster = caster;
        timeSinceLastCast = 0;
    }

    public void handle(Gesture wandGesture){
        if(isReadyToBeReCast())
            rune.sense(wandGesture);
    }

    public String getMagicWords() {
        return magicWords;
    }

    public boolean canBeCast(){
        return rune.priceHasBeenPaid() && isReadyToBeReCast();
    }

    private boolean isReadyToBeReCast() {
        return System.currentTimeMillis() >= timeSinceLastCast + UserSettings.SPELLCAST_COOLDOWN_TIME;
    }

    public void cast(){
        System.out.println("Casting: " + this.magicWords);
        rune.emptyMana();
        timeSinceLastCast = System.currentTimeMillis();
        caster.cast(this);
    }

    public void fizzle(){
        rune.emptyMana();
    }


}
