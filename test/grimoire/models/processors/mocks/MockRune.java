package grimoire.models.processors.mocks;

import grimoire.models.processors.gestures.Gesture;
import grimoire.runes.RuneInterface;

public class MockRune implements RuneInterface {

    public boolean castWasCalled = false;
    public boolean shouldMatchGestures = false;

    @Override
    public void cast() {
        castWasCalled = true;
    }

    @Override
    public boolean matchesGestures(Gesture[] gestures) {
        return false;
    }
}
