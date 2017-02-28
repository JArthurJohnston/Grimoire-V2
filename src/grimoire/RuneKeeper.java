package grimoire;

import grimoire.models.UserSettings;
import grimoire.models.processors.Buffer;
import grimoire.models.processors.gestures.Gesture;
import grimoire.runes.RuneInterface;

import java.util.*;

public class RuneKeeper {

    private final Buffer<Gesture> gestureBuffer;
    private final List<RuneInterface> runes;

    public RuneKeeper(List<RuneInterface> runes){
        this.runes = runes;
        gestureBuffer = new Buffer<>(UserSettings.RUNE_BUFFER_SIZE);
    }

    public RuneKeeper(){
        this(new ArrayList<>());
    }

    public void trackWand(Gesture gesture){
        castSpell(handleWandGesture(gesture));
    }

    public void trackWand(Gesture... gestures){
        for (Gesture gesture : gestures) {
            trackWand(gesture);
        }
    }

    public void castSpell(Gesture[] gestures){
        for (int i = 0; i < gestures.length; i++) {
            for (int j = 0; j < runes.size(); j++) {
                RuneInterface eachRune = runes.get(j);
                if(eachRune.matchesGestures(Arrays.copyOfRange(gestures, i, gestures.length - 1))){
                    eachRune.cast();
                    return;
                }
            }
        }
    }

    public Gesture[] handleWandGesture(Gesture gesture){
        gestureBuffer.add(gesture);
        return aggregateBufferedGestures();
    }

    private Gesture[] aggregateBufferedGestures() {
        List<Gesture> pattern = new ArrayList<>(UserSettings.RUNE_BUFFER_SIZE);
        Iterator<Gesture> iterator = gestureBuffer.lifoIterator();
        if(iterator.hasNext()){
            pattern.add(iterator.next());
        }
        int patternIndex = 0;
        while (iterator.hasNext()){
            Gesture gesture = iterator.next();
            if(gesture != pattern.get(patternIndex)){
                pattern.add(gesture);
                patternIndex++;
            }
        }
        return pattern.toArray(new Gesture[patternIndex]);
    }


}
