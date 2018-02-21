package com.paratussoftware.magic;

import com.paratussoftware.config.Settings;
import com.paratussoftware.gestures.Gesture;

public class ManaPool {
    private Gesture gesture;
    private int mana;
    private long startedChargingTime = -1;
    private boolean startedChargingSpell;

    public ManaPool(Gesture gesture) {
        this.gesture = gesture;
        this.mana = 0;
    }

    public int getMana() {
        return mana;
    }

    public void handle(Gesture gesture) {
        if(gesture == this.gesture){
            if(!startedChargingSpell){
                startedChargingTime = System.currentTimeMillis();
                startedChargingSpell = true;
            }
            mana++;
        } else {
            if(startedChargingTime + Settings.SPELL_COOLDOWN_TIME < System.currentTimeMillis())
                if(mana > 0)
                    mana--;
        }
    }

    public boolean isReadyForCasting() {
        return this.mana >= Settings.SPELL_MANA_THRESHOLD;
    }
}
