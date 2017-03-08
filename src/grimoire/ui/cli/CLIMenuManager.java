package grimoire.ui.cli;

import grimoire.Grimoire;

import java.util.Scanner;

public class CLIMenuManager {

    private final Scanner inputScanner;

    public CLIMenuManager(){
        inputScanner = new Scanner(System.in);
    }

    public void showMainMenu(){
        System.out.println("******* Main Menu ********");
        System.out.println("1: Start");
        System.out.println("2: Start");
        System.out.println("0: Exit");
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
                System.exit(0);
                break;
            case "1":
                System.out.println("Coming soon...");
                break;
            case "2":
                Grimoire.startDebugUI();
                break;
            default:
                System.out.println("Invalid Input");
                break;
        }
    }

}
