package grimoire.image_analysis.cameras;

import grimoire.ui.views.GrimoireViewInterface;

public interface DetectorInterface {

    void start();

    void stop();

    void viewOpened(GrimoireViewInterface view);
}
