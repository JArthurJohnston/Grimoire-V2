package grimoire.models.processors.gestures;

import java.util.HashMap;

public enum Gesture {

    NONE("NONE"),
    UPWARDS("Up"),
    DOWNWARDS("Down"),
    LEFTWARDS("Left"),
    RIGHTWARDS("Right"),
    UPWARDS_RIGHT("Up Right"),
    UPWARDS_LEFT("Up Left"),
    DOWNWARDS_RIGHT("Down Right"),
    DOWNWARDS_LEFT("Down Left");

    private static HashMap<Gesture, Gesture[]> gestureNeighbourMap = new HashMap<>();
    static {
        gestureNeighbourMap.put(NONE, new Gesture[0]);
        gestureNeighbourMap.put(UPWARDS, new Gesture[]{UPWARDS_LEFT, UPWARDS_RIGHT});
        gestureNeighbourMap.put(DOWNWARDS, new Gesture[]{DOWNWARDS_LEFT, DOWNWARDS_RIGHT});
        gestureNeighbourMap.put(LEFTWARDS, new Gesture[]{UPWARDS_LEFT, DOWNWARDS_LEFT});
        gestureNeighbourMap.put(RIGHTWARDS, new Gesture[]{UPWARDS_RIGHT, DOWNWARDS_RIGHT});
        gestureNeighbourMap.put(UPWARDS_LEFT, new Gesture[]{UPWARDS, LEFTWARDS});
        gestureNeighbourMap.put(UPWARDS_RIGHT, new Gesture[]{UPWARDS, RIGHTWARDS});
        gestureNeighbourMap.put(DOWNWARDS_LEFT, new Gesture[]{DOWNWARDS, LEFTWARDS});
        gestureNeighbourMap.put(DOWNWARDS_RIGHT, new Gesture[]{DOWNWARDS, RIGHTWARDS});
    }

    public final String label;

    Gesture(String label){
        this.label = label;
    }

    public boolean isCloseTo(Gesture otherGesture){
        Gesture[] neighbours = gestureNeighbourMap.get(this);
        return otherGesture == this ||
                otherGesture == neighbours[0] ||
                otherGesture == neighbours[1];
    }


}
