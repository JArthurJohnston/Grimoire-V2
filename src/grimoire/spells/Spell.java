package grimoire.spells;

import grimoire.models.processors.gestures.Gesture;

public class Spell {

    private final String magicWords;
    private final Rune rune;
    private CasterInterface caster;

    public Spell(String magicWords, Rune rune, CasterInterface caster){
        this.magicWords = magicWords;
        this.rune = rune;
        this.caster = caster;
    }

    void handle(Gesture wandGesture){
        rune.sense(wandGesture);
    }

    String getMagicWords() {
        return magicWords;
    }

    boolean canBeCast(){
        return rune.priceHasBeenPaid();
    }

    void cast(){
        System.out.println("Casting: " + this.magicWords);
        rune.emptyMana();
        caster.cast(this);
    }

    void fizzle(){
        rune.emptyMana();
    }


}
