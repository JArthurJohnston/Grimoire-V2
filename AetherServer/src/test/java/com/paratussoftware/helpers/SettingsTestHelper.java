package com.paratussoftware.helpers;

import com.paratussoftware.config.Settings;

public class SettingsTestHelper {

    private static final int defaultMaxWandSize;
    private static final int defaultImageHeight;
    private static final int defaultImageWidth;
    private static final int defaultGestureDetectionDistance;
    private static final int defaultMinWandSize;
    private static final int defaultClusterContaintsDistance;
    private static final int defaultMotionTrackingDistance;
    private static final int defaultSpellCooldownTime;
    private static final int defaultSpellManaThreshold;

    static {
        defaultMaxWandSize = Settings.MAX_WAND_SIZE;
        defaultMinWandSize = Settings.MIN_WAND_SIZE;
        defaultImageHeight = Settings.IMAGE_HEIGHT;
        defaultImageWidth = Settings.IMAGE_WIDTH;
        defaultGestureDetectionDistance = Settings.GESTURE_DETECTION_DISTANCE;
        defaultClusterContaintsDistance = Settings.CLUSTER_CONTAINTS_DISTANCE;
        defaultMotionTrackingDistance = Settings.MOTION_TRACKING_DISTANCE;
        defaultSpellCooldownTime = Settings.SPELL_COOLDOWN_TIME;
        defaultSpellManaThreshold = Settings.SPELL_MANA_THRESHOLD;
    }

    public static void restoreDefaultSettings(){
        Settings.MAX_WAND_SIZE = defaultMaxWandSize;
        Settings.MIN_WAND_SIZE = defaultMinWandSize;
        Settings.IMAGE_HEIGHT = defaultImageHeight;
        Settings.IMAGE_WIDTH = defaultImageWidth;
        Settings.GESTURE_DETECTION_DISTANCE = defaultGestureDetectionDistance;
        Settings.CLUSTER_CONTAINTS_DISTANCE = defaultClusterContaintsDistance;
        Settings.MOTION_TRACKING_DISTANCE = defaultMotionTrackingDistance;
        Settings.SPELL_COOLDOWN_TIME = defaultSpellCooldownTime;
        Settings.SPELL_MANA_THRESHOLD = defaultSpellManaThreshold;
    }


}
