package grimoire.views;

import grimoire.CasterInterface;
import grimoire.models.UserSettings;
import grimoire.models.processors.gestures.Gesture;

import java.util.LinkedList;
import java.util.List;

public class Spell {

    private String magicWords;
    private List<SpellGesture> gestures;
    private long timeSinceLastCast = 0;
    private int cooldownTime = UserSettings.SPELLCAST_COOLDOWN_TIME * 1000;
    private final CasterInterface caster;

    public Spell(CasterInterface caster, String magicWords, Gesture... gestures){
        this.caster = caster;
        this.magicWords = magicWords;
        this.gestures = createSpellGesturesFromGestures(gestures);
    }

    public void cast(Gesture wandGesture){
        for (SpellGesture spellGesture : this.gestures) {
            if(!spellGesture.isReady()){
                spellGesture.handle(wandGesture);
                return;
            }
        }
        castSpell();
    }

    private void castSpell(){
        if(timeSinceLastCast <= System.currentTimeMillis() + cooldownTime){
            if(this.caster.cast(this)){
//                System.out.println("Cast: " + this.magicWords);
                timeSinceLastCast = System.currentTimeMillis();
                resetSpellGestures();
            } else {
//                System.out.println("Error casting: " + this.magicWords);
            }
        }
    }

    private void resetSpellGestures(){
        gestures.forEach(g -> g.reset());
    }

    private List<SpellGesture> createSpellGesturesFromGestures(Gesture[] gestures){
        LinkedList<SpellGesture> spellGestures = new LinkedList<>();
        for (Gesture gesture : gestures) {
            spellGestures.add(new SpellGesture(gesture));
        }
        return spellGestures;
    }

    public String getMagicWords() {
        return magicWords;
    }

    private class SpellGesture{
        private Gesture gesture;
        int gestureCount;

        SpellGesture(Gesture gesture){
            this.gestureCount = 0;
            this.gesture = gesture;
        }

        boolean isReady(){
            return this.gestureCount >= UserSettings.SPELLCASTING_THRESHOLD;
        }

        void handle(Gesture gesture){
            if(this.gesture.isCloseTo(gesture)){
                this.gestureCount++;
            } else {
                decrementCounter();
            }
        }

        void decrementCounter(){
            this.gestureCount--;
            if(gestureCount < 0)
                gestureCount = 0;
        }

        void reset(){
            this.gestureCount = 0;
        }
    }



}
