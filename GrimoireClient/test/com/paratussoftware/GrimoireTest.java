package com.paratussoftware;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GrimoireTest {

    @Test
    public void testMain_SetsArgumentsToSettings() throws Exception {
        Grimoire.main(new String[]{"999", "1024", "768"});

        assertEquals(999, Settings.PORT_NUMBER);
        assertEquals(1024, Settings.IMAGE_WIDTH);
        assertEquals(768, Settings.IMAGE_HEIGHT);
    }

}