package grimoire.spells;

import grimoire.models.UserSettings;
import grimoire.models.processors.gestures.Gesture;

import java.util.LinkedList;
import java.util.List;

public class Rune {
    private List<ManaPool> manaPools;
    private long timeSinceRuneStarted = 0;

    public Rune(Gesture... gestures){
        manaPools = createManaPools(gestures);
    }

    void sense(Gesture gesture){
        for (ManaPool manaPool : manaPools) {
            manaPool.handle(gesture);
        }
    }

    boolean priceHasBeenPaid(){
        for (ManaPool manaPool : manaPools) {
            if(!manaPool.isReady())
                return false;
        }
        return true;
    }

    void emptyMana(){
        timeSinceRuneStarted = 0;
        for (ManaPool manaPool : manaPools) {
            manaPool.empty();
        }
    }

    private List<ManaPool> createManaPools(Gesture[] gestures){
        LinkedList<ManaPool> manaPools = new LinkedList<>();
        for (Gesture gesture : gestures) {
            manaPools.add(new ManaPool(gesture));
        }
        return manaPools;
    }

    private class ManaPool{
        private Gesture gesture;
        int mana;
        boolean gestureMatched;


        ManaPool(Gesture gesture){
            this.mana = 0;
            this.gesture = gesture;
            this.gestureMatched = false;
        }

        boolean isReady(){
            return this.mana >= UserSettings.SPELLCASTING_THRESHOLD;
        }

        void handle(Gesture gesture){
            if(gesture == this.gesture){
                if(!gestureMatched){
                    timeSinceRuneStarted = System.currentTimeMillis();
                }
                gestureMatched = true;
            }
            if(gestureMatched && this.gesture.equals(gesture)){
                this.mana++;
            } else {
                if(timeSinceRuneStarted + UserSettings.SPELLCAST_COOLDOWN_TIME < System.currentTimeMillis())
                    decrementCounter();
            }
        }

        void decrementCounter(){
            this.mana--;
            if(mana < 0){
                gestureMatched = false;
                mana = 0;
            }
        }

        void empty(){
            gestureMatched = false;
            timeSinceRuneStarted = 0;
            this.mana = 0;
        }
    }
}
