package grimoire.ui.cli;

import grimoire.Grimoire;

import java.util.Scanner;

public class CLIMenuManager {

    private final Scanner inputScanner;

    public CLIMenuManager(){
        inputScanner = new Scanner(System.in);
    }

    public void showMainMenu(){
        String mainMenuContent = "\n\n******* Main Menu ********";
        mainMenuContent += "\n1: Start";
        mainMenuContent += "\n2: Start Debug UI";
        mainMenuContent += "\n3: Run Demo";
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
                Grimoire.startDefaultApp(new String[]{cameraIndex});
                break;
            case "2":
                System.out.println("Coming soon");
                break;
            case "3":
                System.out.print("Enter your image directory: ");
                String imageDirectory = inputScanner.nextLine();
                Grimoire.startDefaultApp(new String[]{"-demo", imageDirectory});
                break;
            default:
                System.out.println("Invalid Input");
                break;
        }
    }

}
