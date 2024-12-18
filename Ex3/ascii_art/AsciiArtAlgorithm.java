
package ascii_art;

import image.Image;
import image.ImageUtils;
import image.ResolutionImageData;
import image.Tuple;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

/**
 * The AsciiArtAlgorithm class implements an algorithm for generating ASCII art representations of images.
 * It takes an input image, divides it into smaller squares, and replaces each square
 * with a character based on its brightness level.
 */
public class AsciiArtAlgorithm {
    private final ResolutionImageData imagesData; // Object to store processed image data
    private final Image image; // The input image
    private final int resolution; // Resolution for dividing the image into squares
    private final char[] charsForImage; // Characters to be used for representing different brightness levels

    /**
     * Constructs an AsciiArtAlgorithm object with the specified parameters.
     *
     * @param image         The input image to be converted to ASCII art.
     * @param resolution    The resolution for dividing the image into squares.
     * @param charsForImage An array of characters to be used for representing different brightness levels.
     */
    public AsciiArtAlgorithm(Image image, int resolution, char[] charsForImage) {
        this.image = image;
        this.resolution = resolution;
        this.charsForImage = charsForImage;
        imagesData = ResolutionImageData.getInstance(); // Initialize object to store processed image data
    }

    /**
     * Runs the ASCII art algorithm on the input image.
     *
     * @return A 2D character array representing the ASCII art version of the input image.
     */
    public char[][] run() {
        // Get the brightness values of the image after processing
        double[][] brightnessValueOfImage = getImageAfterProcess();
        int numSquaresRows = brightnessValueOfImage.length;
        int numSquaresCols = brightnessValueOfImage[0].length;
        char[][] afterProcessImage = new char[numSquaresRows][numSquaresCols];
        SubImgCharMatcher charMatcher = new SubImgCharMatcher(charsForImage);

        // Iterate over each square in the resolution image to match brightness values to characters
        for (int i = 0; i < numSquaresRows; i++) {
            for (int j = 0; j < numSquaresCols; j++) {
                afterProcessImage[i][j] = charMatcher.getCharByImageBrightness(brightnessValueOfImage[i][j]);
            }
        }

        // Return the resulting ASCII art representation
        return afterProcessImage;
    }

    /**
     * Run the process on the image, first checks if the image with that resolution is in data,
     * if not make all the steps : padding, breaking to small images and calculate brightness.
     * @return 2D array of doubles which represent the image after the process.
     */

    private double[][] getImageAfterProcess() {
        double[][] brightnessValueOfImage = imagesData.getProcessImage(image, resolution);
        if (brightnessValueOfImage != null) {
            return brightnessValueOfImage; // Return stored data if available
        }

        // Initialize a 2D double array to store the brightness values of each square
        brightnessValueOfImage = new double[resolution][resolution];
        // Add padding to the input image to ensure uniform division into squares
        Image paddingImage = ImageUtils.imagePadding(image);
        // Divide the padded image into squares according to the specified resolution
        Image[][] resolutionImage = ImageUtils.imageAfterResolution(resolution, paddingImage);

        // Iterate over each square in the resolution image to calculate brightness values
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                brightnessValueOfImage[i][j] = ImageUtils.getImageBrightness(resolutionImage[i][j]);
            }
        }
        // Store the processed data for future use
        imagesData.addNewData(image, resolution, brightnessValueOfImage);
        return brightnessValueOfImage;
    }
}
