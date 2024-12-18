package ascii_art;

import java.io.IOException;
import java.util.HashSet;

import exceptions.*;
import ascii_output.AsciiOutput;
import image.Image;


/**
 * An interface for the user interface of the program.
 */
public interface UserInterface {

    /**
     * exit the program.
     */
    void exit();

    /**
     * Display the characters used for the ASCII art.
     *
     * @param chars The characters used for the ASCII art.
     */
    void chars(HashSet<Character> chars);

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
    int changeResolution(Image image, int resolution, String size) throws InvalidResolutionException;


    /**
     * Changes the image to be used for ASCII art generation.
     *
     * @param imgPath The path to the new image to be used.
     * @return The new image to be used for ASCII art generation.
     * @throws IOException If there is a problem with the image file.
     */
    Image changeImg(String imgPath) throws IOException;

    /**
     * Changes the output to be used for displaying the ASCII art.
     *
     * @param type_of_output The type of output to be used.
     * @return The new output to be used for displaying the ASCII art.
     * @throws InvalidParametersException If the output type is invalid.
     */
    AsciiOutput changeOutput(String type_of_output) throws InvalidParametersException;

    /**
     * Runs the ASCII art algorithm on the given image with the specified resolution and character set.
     *
     * @param image      The image to be used for ASCII art generation.
     * @param resolution The resolution to be used for ASCII art generation.
     * @param charsSet   The character set to be used for ASCII art generation.
     * @return The ASCII art representation of the image.
     */
    char[][] runAsciiAlgorithm(Image image, int resolution, char[] charsSet);

    /**
     * Adds characters based on the provided user input.
     *
     * @param userInput The input string representing either a single character, a range of characters,
     *                  or special keywords like "all" or "space".
     * @return An array of characters based on the provided user input. Returns null if the input is invalid.
     */
    char[] add(String userInput);

    /**
     * Removes characters based on the provided user input.
     *
     * @param userInput The input string representing either a single character, a range of characters,
     *                  or special keywords like "all" or "space".
     * @return An array of characters based on the provided user input. Returns null if the input is invalid.
     */
    char[] remove(String userInput);
}
