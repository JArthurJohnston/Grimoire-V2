package com.paratussoftware.ui.cli;

import com.paratussoftware.Grimoire;

import java.util.Scanner;

public class CLIMenuManager {

    private final Scanner inputScanner;

    public CLIMenuManager(){
        inputScanner = new Scanner(System.in);
    }

    public void showMainMenu(){
        String mainMenuContent = "\n\n******* Main Menu ********";
        mainMenuContent += "\n1: Start";
        mainMenuContent += "\n2: Start UI";
        mainMenuContent += "\n3: Stop UI";
        mainMenuContent += "\n0: Exit";
        mainMenuContent += "\n";
        mainMenuContent += "Enter your selection: ";

        System.out.print(mainMenuContent);
        handleInput();
    }

    private void handleInput(){
        String input = inputScanner.nextLine();
        executeUserChoice(input);
    }

    private void executeUserChoice(String userChoice){
        switch (userChoice){
            case "0":
                System.out.println("Goodbye");
                Grimoire.stop();
                System.exit(0);
                break;
            case "1":
                System.out.print("Enter the camera index: ");
                String cameraIndex = inputScanner.nextLine();
                Grimoire.startDetection(Integer.parseInt(cameraIndex));
                break;
            case "2":
                Grimoire.startUI();
                break;
            case "3":
                Grimoire.stopUI();
                break;
            default:
                System.out.println("Invalid Input");
                break;
        }
    }

}
