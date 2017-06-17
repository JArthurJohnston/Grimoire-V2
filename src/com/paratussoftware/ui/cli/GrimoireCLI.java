package com.paratussoftware.ui.cli;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GrimoireCLI {

    private static final String TITLE_FILE_NAME = "./lib/grimoire_title.txt";

    public static void startGrimoireCLI(String[] args){
        showTitle();
        CLIMenuManager cliMenuManager = new CLIMenuManager();
        while (true){
            cliMenuManager.showMainMenu();
        }
    }

    public static void showTitle(){
        try (BufferedReader br = new BufferedReader(new FileReader(TITLE_FILE_NAME))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {}
    }

}
