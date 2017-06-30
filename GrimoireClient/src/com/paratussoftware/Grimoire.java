package com.paratussoftware;

public class Grimoire {

    public static void main(final String[] args) {
        if (args.length == 3) {
            Settings.PORT_NUMBER = Integer.valueOf(args[0]);
            Settings.IMAGE_WIDTH = Integer.valueOf(args[1]);
            Settings.IMAGE_HEIGHT = Integer.valueOf(args[2]);
        } else {
            System.out.println("Invalid Arguments. Expected: port image_width image_height");
        }
    }
}
