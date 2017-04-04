package grimoire.gesture_analysis.spells.serialization;

import grimoire.Grimoire;
import grimoire.gesture_analysis.gestures.Gesture;
import grimoire.gesture_analysis.spells.CasterInterface;
import grimoire.gesture_analysis.spells.Rune;
import grimoire.gesture_analysis.spells.Spell;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class SpellSerializer {

    public void write(List<Spell> spells){
        FileWriter spellWriter = null;
        try {
            spellWriter = new FileWriter(Grimoire.UserSettings.SPELLFILE_LOCATION);
            BufferedWriter bufferedWriter = new BufferedWriter(spellWriter);
            for (Spell spell : spells) {
                writeContentToFile(spell, bufferedWriter);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeContentToFile(Spell spell, BufferedWriter bufferedWriter) throws IOException {
        String content = createRuneContent(spell) + ":";
        content += spell.magicWords;
        bufferedWriter.write(content);
        bufferedWriter.newLine();
    }

    private String createRuneContent(Spell spell) {
        String content = "";
        Gesture[] gestures = spell.rune.gestures;
        for (Gesture gesture : gestures) {
            content += gesture.name();
            if(gesture != gestures[gestures.length - 1]){
                content += ",";
            }
        }
        return content;
    }

    public List<Spell> read(String spellFileLocation, CasterInterface caster) {
        LinkedList<Spell> spells = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(spellFileLocation))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] spellComponents = line.split(":");
                Gesture[] gestureArray = gesturesFrom(spellComponents[0]);
                spells.add(new Spell(spellComponents[1], new Rune(gestureArray), caster));

            }
        } catch (IOException e) {
            e.printStackTrace();
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
