package com.paratussoftware.magic;

import com.paratussoftware.gestures.Gesture;

public class ManaPool {
    private Gesture gesture;
    private int mana;

    public ManaPool(Gesture gesture) {
        this.gesture = gesture;
        this.mana = 0;
    }

    public int getMana() {
        return mana;
    }

    public void handle(Gesture gesture) {
        if(gesture == this.gesture)
            mana++;
    }
}
