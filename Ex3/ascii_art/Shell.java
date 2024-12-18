package ascii_art;

import image.Image;
import ascii_output.*;
import exceptions.*;

import java.io.IOException;
import java.util.HashSet;


/**
 * The Shell class represents the command-line interface for interacting with an ASCII art generation program.
 * It allows users to input commands to manipulate the settings and perform operations on an image using
 * ASCII characters.
 */
public class Shell {

    private static final String EXIT = "exit";
    private static final String SHOW_CHARS = "chars";
    private static final String ADD_CHAR = "add";
    private static final String REMOVE_CHAR = "remove";
    private static final String CHANGE_RESOLUTION = "res";
    private static final String CHANGE_IMG = "image";
    private static final String CHANGE_OUTPUT = "output";
    private static final String RUN_ALGORITHM = "asciiArt";
    private static final String INIT_PROMPT = ">>> ";
    private static final String INVALID_RESOLUTION = "Did not change resolution due to exceeding boundaries.";
    private static final String INVALID_RES_PARAMETER = "Did not change resolution due to incorrect format.";
    private static final String CHANGE_RESOLUTION_PROMPT = "Resolution set to ";
    private static final String EMPTY_CHARS_SET = "Did not execute. Charset is empty.";
    private static final String INVALID_IMG = "Did not execute due to problem with the image file.";
    private static final String INVALID_CHAR_TO_REMOVE = "Did not remove due to incorrect format.";
    private static final String INVALID_CHAR_TO_ADD = "Did not add due to incorrect format.";
    private static final String INVALID_COMMAND = "Did not execute due to incorrect command.";
    private static final String DEFAULT_IMG = "cat.jpeg";
    private static final char[] DEFAULT_CHAR_SET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final int DEFAULT_RESOLUTION = 128;

    private final UserActions userActions;
    private AsciiOutput asciiOutput = new ConsoleAsciiOutput();
    private Image image;
    private final HashSet<Character> charSet;
    private int resolution;


    /**
     * Creates a new Shell object with the specified image, character set, and resolution.
     *
     * @param image       The image to be used for ASCII art generation.
     * @param charSet     The characters to be used for ASCII art generation.
     * @param resolution  The resolution to be used for ASCII art generation.
     * @param userActions The user actions to be used for ASCII art generation.
     */
    Shell(Image image, HashSet<Character> charSet, int resolution, UserActions userActions) {

        this.image = image;
        this.charSet = charSet;
        this.resolution = resolution;
        this.userActions = userActions;

    }

    /**
     * Runs the main loop of the ASCII art generation program, allowing users to input commands.
     */
    public void run() {
        String[] userInput = getUserInput();
        do {
            processUserInput(userInput);
            userInput = getUserInput();
        } while (!userInput[0].equals(EXIT));
    }

    /**
     * Retrieves user input from the console.
     *
     * @return An array containing user input split by whitespace.
     */
    private String[] getUserInput() {
        System.out.print(INIT_PROMPT);
        return KeyboardInput.readLine().split("\\s+");
    }

    /**
     * Processes user input by executing corresponding actions.
     *
     * @param userInput The array containing user input commands.
     */
    private void processUserInput(String[] userInput) {
        try {
            switch (userInput[0]) {
                case EXIT:
                    userActions.exit();
                    break;
                case SHOW_CHARS:
                    userActions.chars(charSet);
                    break;
                case CHANGE_RESOLUTION:
                    changeResolution(userInput);
                    break;
                case CHANGE_IMG:
                    changeImage(userInput);
                    break;
                case CHANGE_OUTPUT:
                    changeOutput(userInput);
                    break;
                case RUN_ALGORITHM:
                    runAsciiAlgorithm();
                    break;
                case ADD_CHAR:
                    addCharacter(userInput);
                    break;
                case REMOVE_CHAR:
                    removeCharacter(userInput);
                    break;
                default:
                    System.out.println(INVALID_COMMAND);
            }
        } catch (InvalidResolutionException e) {
            System.out.println(INVALID_RESOLUTION);
        } catch (IOException e) {
            System.out.println(INVALID_IMG);
        } catch (InvalidParametersException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Changes the resolution of the image based on user input.
     *
     * @param userInput The array containing user input commands.
     * @throws InvalidResolutionException If the provided resolution parameter is invalid.
     */
    private void changeResolution(String[] userInput) throws InvalidResolutionException {
        if (!(userInput[1].equals("up") || userInput[1].equals("down"))) {
            System.out.println(INVALID_RES_PARAMETER);
            return;
        }
        this.resolution = userActions.changeResolution(image, resolution, userInput[1]);
        System.out.println(CHANGE_RESOLUTION_PROMPT + resolution);
    }

    /**
     * Changes the input image based on user input.
     *
     * @param userInput The array containing user input commands.
     * @throws IOException If the specified image file is not found.
     */
    private void changeImage(String[] userInput) throws IOException {
        this.image = userActions.changeImg(userInput[1]);
    }

    /**
     * Changes the output stream for ASCII art based on user input.
     *
     * @param userInput The array containing user input commands.
     * @throws InvalidParametersException If the specified output stream is invalid.
     */
    private void changeOutput(String[] userInput) throws InvalidParametersException {
        this.asciiOutput = userActions.changeOutput(userInput[1]);
    }

    /**
     * Runs the ASCII art generation algorithm based on current settings and displays the result.
     *
     * @throws IOException                If an I/O error occurs during image processing.
     * @throws InvalidParametersException If the resolution is invalid.
     */
    private void runAsciiAlgorithm() throws IOException, InvalidParametersException {
        if (charSet.isEmpty()) {
            throw new InvalidParametersException(EMPTY_CHARS_SET);
        }
        char[] charArray = new char[charSet.size()];
        int index = 0;
        for (char c : charSet) {
            charArray[index++] = c;
        }
        char[][] img = userActions.runAsciiAlgorithm(image, resolution, charArray);
        asciiOutput.out(img);
    }

    /**
     * Adds characters to the character set based on user input.
     *
     * @param userInput The array containing user input commands.
     */
    private void addCharacter(String[] userInput) throws InvalidParametersException {
        char[] newChars = userActions.add(userInput[1]);
        if (newChars == null) {
            throw new InvalidParametersException(INVALID_CHAR_TO_ADD);
        } else {
            for (char c : newChars) {
                charSet.add(c);
            }
        }
    }

    /**
     * Removes characters from the character set based on user input.
     *
     * @param userInput The array containing user input commands.
     */
    private void removeCharacter(String[] userInput) throws InvalidParametersException {
        char[] charsToRemove = userActions.remove(userInput[1]);
        if (charsToRemove == null) {
            throw new InvalidParametersException(INVALID_CHAR_TO_REMOVE);
        } else {
            for (char c : charsToRemove) {
                charSet.remove(c);
            }
        }
    }


    /**
     * The main method of the program, responsible for initializing the necessary components
     * and starting the application.
     * This method performs the following steps:
     * 1. Initializes an Image object with a default image file.
     * 2. Initializes a set of characters used for ASCII art with default characters.
     * 3. Creates a UserActions object to handle user actions.
     * 4. Creates a Shell object to manage the application shell.
     * 5. Calls the run method of the Shell object to start the application.
     *
     * @param args The command-line arguments (not used in this method).
     */
    public static void main(String[] args) {
        Image imageTest;
        try {
            imageTest = new Image(DEFAULT_IMG);
        } catch (IOException e) {
            System.out.println(INVALID_IMG);
            return;
        }
        HashSet<Character> charsSet = new HashSet<>();
        for (char c: DEFAULT_CHAR_SET){
            charsSet.add(c);
        }
        UserActions userActions = new UserActions();
        Shell shell = new Shell(imageTest, charsSet, DEFAULT_RESOLUTION, userActions);
        shell.run();
    }

}



