package grimoire.runes;


import grimoire.models.processors.gestures.Gesture;

public interface RuneInterface {

    void cast();

    boolean matchesGestures(Gesture[] gestures);
}
