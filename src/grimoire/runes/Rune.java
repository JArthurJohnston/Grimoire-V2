package grimoire.runes;

import grimoire.models.UserSettings;
import grimoire.models.processors.gestures.Gesture;

import java.io.IOException;
import java.util.Objects;

public class Rune implements RuneInterface {

    private String command;
    protected final Gesture[] gestures;
    private boolean hasBeenCast;
    private long timeSinceLastCast;
    private int cooldownTime = UserSettings.SPELLCAST_COOLDOWN_TIME * 1000;

    public Rune(String command, Gesture... strokes){
        this.command = command;
        this.gestures = strokes;
        hasBeenCast = false;
        timeSinceLastCast = 0;
    }

    @Override
    public boolean equals(Object otherRune){
        if(otherRune == this || otherRune instanceof Rune){
            return matchesRune((Rune)otherRune);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.gestures);
    }

    private boolean matchesRune(Rune otherRune) {
        return matchesGestures(otherRune.gestures);
    }

    public boolean matchesGestures(Gesture... gestures) {
        if(this.gestures.length == gestures.length){
            for (int j = 0; j < this.gestures.length; j++) {
                if(!this.gestures[j].isCloseTo(gestures[j])){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void cast(){
        if(!hasBeenCast){
            try {
                System.out.println("Casting: " + this.command);
                Runtime.getRuntime().exec(this.command + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(timeSinceLastCast <= System.currentTimeMillis() + cooldownTime){
            hasBeenCast = false;
        }
    }

}
