package image;

import java.awt.*;

/**
 * Utility class for image processing operations.
 */
public class ImageUtils {
    private static final int WHITE_COLOR = 255;

    /**
     * Splits the given image into smaller images of the specified resolution size.
     *
     * @param resolution The resolution size for the smaller images.
     * @param image      The original image to be split.
     * @return A 2D array containing the smaller images.
     */
    public static Image[][] imageAfterResolution(int resolution, Image image) {
        // Calculate the size of each smaller image
        int smallerImageSize = image.getHeight() / resolution;

        // Initialize the array to hold the smaller images
        Image[][] imageAfterResolution = new Image[resolution][resolution];

        // Iterate over the original image and split it into smaller images
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                // Calculate the starting row and column for the square
                int startRow = i * smallerImageSize;
                int startCol = j * smallerImageSize;

                // Create the pixel array for the smaller image
                Color[][] pixelArray = new Color[smallerImageSize][smallerImageSize];

                // Copy pixel data from the original image to the smaller image
                for (int k = 0; k < smallerImageSize; k++) {
                    for (int l = 0; l < smallerImageSize; l++) {
                        pixelArray[k][l] = image.getPixel(startRow + k, startCol + l);
                    }
                }

                // Create the smaller image and store it in the array
                imageAfterResolution[i][j] = new Image(pixelArray, smallerImageSize, smallerImageSize);
            }
        }
        return imageAfterResolution;
    }

    /**
     * Calculates the brightness of the given image.
     *
     * @param image The image for which to calculate brightness.
     * @return The brightness of the image, a value between 0 and 255.
     */
    public static double getImageBrightness(Image image) {
        double result = 0;

        // Iterate over the pixels of the image and calculate brightness
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                result += image.getPixel(i, j).getRed() * 0.2126 +
                        image.getPixel(i, j).getGreen() * 0.7152 +
                        image.getPixel(i, j).getBlue() * 0.0722;
            }
        }
        // Normalize the result by the total number of pixels and scale to 0-255 range
        return result / (image.getHeight() * image.getWidth() * 255);
    }

    /**
     * Finds the closest power of 2 greater than or equal to the given number.
     * @param number The number for which to find the closest power of 2.
     * @return The closest power of 2 greater than or equal to the given number.
     */
    private static int closestPowerOfTwo(int number) {
        // If the number is already a power of 2, return it
        if ((number & (number - 1)) == 0) {
            return number;
        } else {
            // Find the position of the highest set bit using logarithm base 2
            int position = 0;
            while ((1 << position) < number) {
                position++;
            }

            // Return the next power of 2
            return 1 << position;
        }
    }



    /**
     * Adds padding to the image to make its dimensions powers of 2.
     * Padding is added symmetrically to maintain the center alignment of the original image.
     *
     * @param image The original image to be padded.
     * @return A new Image object with padded dimensions.
     */
    public static Image imagePadding(Image image) {
        // Retrieve dimensions of the original image
        int widthBeforePadding = image.getWidth();
        int heightBeforePadding = image.getHeight();

        // Calculate new dimensions after padding
        int widthAfterPadding = closestPowerOfTwo(widthBeforePadding);
        int heightAfterPadding = closestPowerOfTwo(heightBeforePadding);

        // Checks if the image is already power of 2 and do nothing
        if (widthBeforePadding == widthAfterPadding && heightBeforePadding == heightAfterPadding) {
            return image;
        }

        // Calculate number of pixels to be added on each side for width and height
        int numOfPixelsAddedWidth = (widthAfterPadding - widthBeforePadding) / 2;
        int numOfPixelsAddedHeight = (heightAfterPadding - heightBeforePadding) / 2;

        // Create a new pixel array with padded dimensions and fill it with white color
        Color[][] pixelArray = new Color[heightAfterPadding][widthAfterPadding];
        for (int i = 0; i < heightAfterPadding; i++) {
            for (int j = 0; j < widthAfterPadding; j++) {
                pixelArray[i][j] = new Color(WHITE_COLOR, WHITE_COLOR, WHITE_COLOR);
            }
        }
  
        // Copy the original image pixels to the padded area, maintaining center alignment
        for (int i = 0; i < heightBeforePadding; i++) {
            for (int j = 0; j < widthBeforePadding; j++) {
                pixelArray[i + numOfPixelsAddedHeight][j + numOfPixelsAddedWidth] = image.getPixel(i, j);
            }
        }

        // Create a new image with padded dimensions
        return new Image(pixelArray, widthAfterPadding, heightAfterPadding);
    }

}

