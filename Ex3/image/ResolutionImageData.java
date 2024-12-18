package image;

import java.util.HashMap;

/**
 * The ResolutionImageData class stores processed image data for different image resolutions.
 * It maps Image objects to their corresponding processed data represented as double arrays.
 */
public class ResolutionImageData {
    private static ResolutionImageData resolutionImageData; // Singleton instance
    // HashMap to store processed image data
    private final HashMap<Tuple<Image, Integer>, double[][]> imageData;

    /**
     * Constructs a new ResolutionImageData object.
     */
    private ResolutionImageData() {
        imageData = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of ResolutionImageData.
     *
     * @return The singleton instance of ResolutionImageData.
     */
    public static ResolutionImageData getInstance() {
        if (resolutionImageData != null) {
            return resolutionImageData;
        }
        resolutionImageData = new ResolutionImageData();
        return resolutionImageData;
    }

    /**
     * Adds new processed data for an image.
     * If the image already exists in the map, it will not be overridden.
     *
     * @param image        The Image object.
     * @param processImage The processed image data represented as a double array.
     */
    public void addNewData(Image image, int resolution, double[][] processImage) {
        Tuple<Image, Integer> newTup = new Tuple<Image, Integer>(image, resolution);
        // Check if the image already exists in the map
        if (!imageData.containsKey(newTup)) {
            // If not, add the image along with its corresponding processed data
            imageData.put(newTup, processImage);
        }
    }

    /**
     * Retrieves the processed image data for a given image.
     *
     * @param image The Image object.
     * @return The processed image data represented as a double array,
     *         or null if the image is not found in the map.
     */
    public double[][] getProcessImage(Image image, int resolution) {
        Tuple<Image, Integer> newTup = new Tuple<Image, Integer>(image, resolution);
        // Check if the image exists in the map
        if (imageData.containsKey(newTup)) {
            // If yes, return the processed image data associated with the image
            return imageData.get(newTup);
        }
        // If the image is not found, return null
        return null;
    }
}
