package com.paratussoftware;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SettingsTest {

    @Test
    public void testDefaultSettings()  {
        assertEquals(640, Settings.IMAGE_WIDTH);
        assertEquals(480, Settings.IMAGE_HEIGHT);
        assertEquals(7777, Settings.PORT_NUMBER);
    }


}