package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Represents an image with methods to read from file, create from pixel array, and save to file.
 * This class is package-private and part of the image package.
 *
 * @author Dan Nirel
 */
public class Image {

    private final Color[][] pixelArray; // Array holding pixel colors
    private final int width; // Width of the image
    private final int height; // Height of the image

    /**
     * Constructs an Image object by reading an image file.
     *
     * @param filename the path to the image file
     * @throws IOException if an error occurs while reading the image file
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();

        pixelArray = new Color[height][width];
        // Populate the pixelArray with colors from the BufferedImage
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j] = new Color(im.getRGB(j, i));
            }
        }
    }


    /**
     * Constructs an Image object from a 2D array of Color objects.
     *
     * @param pixelArray the 2D array of Color objects representing pixel colors
     * @param width      the width of the image
     * @param height     the height of the image
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }


    /**
     * Returns the width of the image.
     *
     * @return the width of the image
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the image.
     *
     * @return the height of the image
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the Color of the pixel at the specified coordinates.
     *
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return the Color of the pixel at the specified coordinates
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }


    /**
     * Saves the image to a file with the specified filename.
     *
     * @param fileName the name of the file to save the image to (without extension)
     */
    public void saveImage(String fileName) {
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName + ".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
