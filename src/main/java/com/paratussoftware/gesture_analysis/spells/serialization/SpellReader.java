package com.paratussoftware.gesture_analysis.spells.serialization;

import com.paratussoftware.errors.ExceptionLogger;
import com.paratussoftware.gesture_analysis.spells.Rune;
import com.paratussoftware.gesture_analysis.gestures.Gesture;
import com.paratussoftware.gesture_analysis.spells.CasterInterface;
import com.paratussoftware.gesture_analysis.spells.Spell;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class SpellReader {

    public List<Spell> read(String spellFileLocation, CasterInterface caster) {
        LinkedList<Spell> spells = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(spellFileLocation))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] spellComponents = line.split(":");
                Gesture[] gestureArray = gesturesFrom(spellComponents[0]);
                Spell loadedSpell = new Spell(spellComponents[1], new Rune(gestureArray), caster);
                spells.add(loadedSpell);
            }
        } catch (IOException e) {
            ExceptionLogger.scribble(e);
        }
        return spells;
    }

    private Gesture[] gesturesFrom(String gestureLine){
        String[] gestureStrings = gestureLine.split(",");
        LinkedList<Gesture> gestures = new LinkedList<>();
        for (String gestureName : gestureStrings) {
            Gesture gesture = Gesture.valueOf(gestureName);
            gestures.add(gesture);
        }

        return gestures.toArray(new Gesture[gestures.size()]);
    }
}
