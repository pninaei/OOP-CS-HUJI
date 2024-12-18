package ascii_art;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import exceptions.*;
import ascii_output.AsciiOutput;
import image.Image;

/**
 * Represents user actions that can be performed in the ASCII Art application.
 * Implements the UserInterface.
 *
 * This class provides methods for:
 * - Exiting the program.
 * - Displaying characters used for ASCII art.
 * - Changing the resolution of an image.
 * - Changing the image.
 * - Changing the output method for displaying ASCII art.
 * - Running the ASCII art algorithm on an image.
 * - Adding or removing characters based on user input.
 */
public class UserActions implements UserInterface {
    private static final String ALL_ORDER = "all";
    private static final String SPACE_ORDER = "space";
    private static final String HTML_FILE = "html";
    private static final String CONSOLE_FILE = "console";
    private static final String HTML_OUTPUT = "out.html";
    private static final String FONT = "Courier New";
    private AsciiArtAlgorithm asciiArtAlgorithm;
    private static final int FACTOR_CHANGE_RES = 2;
    private static final String INVALID_OUTPUT_STREAM = "Did not change output method due to" +
            " incorrect format.";
    private static final int ASCII_RANGE_END = 126;
    private static final int ASCII_RANGE_SIZE = 95;
    private static final int CHAR_RANGE_LENGTH = 3;
    private static final int  ASCII_START = 32;


    /**
     * Creates a new UserActions object.
     */
    public UserActions() {
    }

    /**
     * exit the program.
     */
    @Override
    public void exit() {
        System.exit(0);
    }

    /**
     * Display the characters used for the ASCII art in ascending order by their ASCII values.
     *
     * @param chars The characters used for the ASCII art.
     */
    @Override
    public void chars(HashSet<Character> chars) {
        List<Character> charList = new ArrayList<>(chars);
        Collections.sort(charList);
        for (char curChar : charList) {
            System.out.print(curChar + " ");
        }
        System.out.println();
    }


    /**
     * Changes the resolution of the given image based on the specified size.
     *
     * @param image      The image whose resolution needs to be changed.
     * @param resolution The current resolution of the image.
     * @param size       The size parameter indicating whether to increase ("up")
     *                  or decrease ("down") the resolution.
     * @throws InvalidResolutionException If the new resolution is invalid
     * (too large or too small).
     */
    @Override
    public int changeResolution(Image image, int resolution, String size)
            throws InvalidResolutionException {

        int minCharInRow = Math.max(1, image.getWidth() / image.getHeight());
        int newResolution = 0;
        if (size.equals("up")) {
            if (resolution * FACTOR_CHANGE_RES > image.getWidth()) {
                throw new InvalidResolutionException("");
            } else {
                newResolution = resolution * FACTOR_CHANGE_RES;
            }
        } else if (size.equals("down")) {
            if (resolution / FACTOR_CHANGE_RES < minCharInRow) {
                throw new InvalidResolutionException("");
            } else {
                newResolution = resolution / FACTOR_CHANGE_RES;
            }
        }
        return newResolution;
    }

    /**
     * Changes the image to a new image.
     *
     * @param imgPath The path of the new image.
     * @return The new image.
     * @throws IOException If the image cannot be read.
     */
    @Override
    public Image changeImg(String imgPath) throws IOException {
        // An IOException is thrown if the image cannot be read. 
        return new Image(imgPath);
    }


    /**
     * Changes the output to be used for displaying the ASCII art.
     *
     * @param type_of_output The type of output to be used.
     * @return The new output to be used for displaying the ASCII art.
     * @throws InvalidParametersException If the type of output is invalid.
     * (i.e. not "html" or "console")
     */
    @Override
    public AsciiOutput changeOutput(String type_of_output)
            throws InvalidParametersException {
        if (!type_of_output.equals(HTML_FILE) && !type_of_output.equals(CONSOLE_FILE)) {
            throw new InvalidParametersException(INVALID_OUTPUT_STREAM);
        }
        AsciiArtOutputFactory asciiArtOutputFactory = new AsciiArtOutputFactory();
        return asciiArtOutputFactory.createAsciiOutput(type_of_output,
                new String[]{HTML_OUTPUT, FONT});
    }


    /**
     * Runs the ASCII art algorithm on the given image.
     *
     * @param image      The image on which the algorithm needs to be run.
     * @param resolution The resolution of the ASCII art.
     * @param charsSet   The characters used for the ASCII art.
     * @return The ASCII art representation of the image.
     */
    @Override
    public char[][] runAsciiAlgorithm(Image image, int resolution, char[] charsSet) {
        // not sure about this part - crate a new instance here (?)
        asciiArtAlgorithm = new AsciiArtAlgorithm(image, resolution, charsSet);
        return asciiArtAlgorithm.run();
    }

    /**
     * Adds characters based on the provided user input.
     *
     * @param userInput The input string representing either a single character, a range of characters,
     *                  or special keywords like "all" or "space".
     * @return An array of characters based on the provided user input. Returns null if the input is invalid.
     */
    public char[] add(String userInput) {
        return addOrRemoveChars(userInput);
    }

    /**
     * Removes characters based on the provided user input.
     *
     * @param userInput The input string representing either a single character, a range of characters,
     *                  or special keywords like "all" or "space".
     * @return An array of characters based on the provided user input. Returns null if the input is invalid.
     */
    public char[] remove(String userInput) {
        return addOrRemoveChars(userInput);
    }

    /**
     * Adds or removes characters based on the provided user input.
     *
     * @param userInput The input string representing either a single character, a range of characters,
     *                  or special keywords like "all" or "space".
     * @return An array of characters based on the provided user input. Returns null if the input is invalid.
     */
    private char[] addOrRemoveChars(String userInput) {
        // If userInput has a length of 1, return an array containing that single character
        if (userInput.length() == 1) {
            char[] newChars = new char[1];
            newChars[0] = userInput.charAt(0);
            return newChars;
        }
        // If userInput is "all", return an array containing characters from ASCII 0 to 95
        if (userInput.equals(ALL_ORDER)) {
            char[] newChars = new char[ASCII_RANGE_SIZE];
            for (int i = 0; i < ASCII_RANGE_SIZE; i++) {
                newChars[i] = (char)(i+ASCII_START);
            }
            return newChars;
        }
        // If userInput is "space", return an array containing a single space character
        if (userInput.equals(SPACE_ORDER)) {
            char[] newChars = new char[1];
            newChars[0] = ' ';
            return newChars;
        }
        // If userInput is in the format "char-char", return an array containing characters in that range
        if (userInput.length() == CHAR_RANGE_LENGTH && userInput.charAt(1) == '-') {
            int firstChar = userInput.charAt(0);
            int secondChar = userInput.charAt(2);
            // Check if both characters are within the ASCII range [0, 127]
            if (firstChar >= 0 && secondChar >= 0 &&
                    firstChar <= ASCII_RANGE_END && secondChar <= ASCII_RANGE_END) {
                int biggerChar = Math.max(firstChar, secondChar);
                int smallerChar = Math.min(firstChar, secondChar);
                int charSetSize = biggerChar - smallerChar + 1;
                char[] newChars = new char[charSetSize];
                for (int i = 0; i < charSetSize; i++) {
                    newChars[i] = (char) (smallerChar + i);
                }
                return newChars;
            }
        }
        return null; // Return null if the input is invalid
    }
}

