package grimoire.ui.cli;

import grimoire.Grimoire;

import java.util.Scanner;

public class CLIMenuManager {

    private final Scanner inputScanner;

    public CLIMenuManager(){
        inputScanner = new Scanner(System.in);
    }

    public void showMainMenu(){
        String mainMenuContent = "\n******* Main Menu ********";
        mainMenuContent += "\n1: Start";
        mainMenuContent += "\n2: Start Debug UI";
        mainMenuContent += "\n0: Exit";
        mainMenuContent += "\n";

        System.out.println(mainMenuContent);
        handleInput();
    }

    private void handleInput(){
        String input = inputScanner.nextLine();
        executeUserChoice(input);
    }

    private void clearTheScreen(){
        System.out.print(String.format("\033[H\033[2J"));

//        final String ESC = "\033[";
//        System.out.print(ESC + "2J");

//        final String ANSI_CLS = "\u001b[2J";
//        final String ANSI_HOME = "\u001b[H";
//        System.out.print(ANSI_CLS + ANSI_HOME);
//        System.out.flush();

//        try {
//            Runtime.getRuntime().exec("clear\n\r");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void executeUserChoice(String userChoice){
//        clearTheScreen();
        switch (userChoice){
            case "0":
                System.out.println("Goodbye");
                System.exit(0);
                break;
            case "1":
                System.out.println("Enter the camera index");
                String cameraIndex = inputScanner.nextLine();
                Grimoire.main(new String[]{cameraIndex});
                break;
            case "2":
                System.out.println("Coming soon");
//                Grimoire.startDebugUI();
                break;
            default:
                System.out.println("Invalid Input");
                break;
        }
    }

}
