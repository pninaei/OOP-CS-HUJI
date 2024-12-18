package image_char_matching;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * A class that matches a character to a brightness value.
 * The class uses a map that maps a normalized brightness to a priority queue of characters.
 * The class also keeps track of the minimum and maximum brightness of the characters in the charset.
 */
public class SubImgCharMatcher {
    private final CharsData allCharsData;
    private final TreeMap<Double, PriorityQueue<Character>> brightnessToChar;
    // a map that maps a brightness to a
    // priority queue of chars
    private double minBrightness = Double.MAX_VALUE; // Initialize with max value
    private double maxBrightness = Double.MIN_VALUE; // Initialize with min value
    private final HashSet<Character> charsSet;

    /**
     * Constructor that initializes a SubImgCharMatcher with the given charset.
     *
     * @param charset The array of characters that will be used for matching.
     */
    public SubImgCharMatcher(char[] charset) {
        allCharsData = CharsData.getInstance();
        charsSet = new HashSet<>(); // Initialize an empty set
        for (char curChar: charset){
            charsSet.add(curChar);
        }
        if (allCharsData.getCharsValues(charset) != null){
            brightnessToChar = allCharsData.getCharsValues(charset);
            maxBrightness = allCharsData.getMinAndMax(charset)[1];
            minBrightness = allCharsData.getMinAndMax(charset)[0];
        }
        else {
            brightnessToChar = new TreeMap<>();
            updateMinMax(charsSet);
            for (char c : charset) {
                addChar(c); // Add characters and update min/max brightness
            }
            allCharsData.addNewCharsaddSet(charset, brightnessToChar ,
                    new Double[]{minBrightness, maxBrightness});
        }
    }

    /**
     * Given a brightness value, return the character with the closest brightness in absolute value.
     * If there are ties, return the character with the lowest ASCII value.
     *
     * @param brightness The brightness value.
     * @return The character with the closest brightness in absolute value.
     */
    public char getCharByImageBrightness(double brightness) {
        double minDistance = Double.MAX_VALUE;
        PriorityQueue<Character> closestChars = new PriorityQueue<>();

        // Iterate over the brightness values
        for (double curBrightness : brightnessToChar.keySet()) {
            // Calculate the absolute difference between the current brightness and the target brightness
            double distance = Math.abs(curBrightness - brightness);

            // If the current distance is less than the minimum distance found so far,
            // update the minimum distance and reset the set of closest characters
            if (distance < minDistance) {
                minDistance = distance;
                closestChars = brightnessToChar.get(curBrightness);
            }
            // If the current distance is equal to the minimum distance found so far,
            // add the characters corresponding to this brightness to the set of closest characters
            else if (distance == minDistance) {
                if (brightnessToChar.get(curBrightness).peek() <=
                        brightnessToChar.get(minDistance).peek()){
                    closestChars = brightnessToChar.get(curBrightness);
                }
            }
        }

        // Return the character with the lowest ASCII value among the closest characters
        return closestChars.peek();
   }


    private void updateMinMax(HashSet<Character> chars){
        for (char curChar: chars){
            double initBrightness = calculateInitBrightness(curChar); // Calculate initial brightness
            // Update min and max brightness
            minBrightness = Math.min(minBrightness, initBrightness);
            maxBrightness = Math.max(maxBrightness, initBrightness);
        }
    }

    /**
     * Add a new char to the charset.
     *
     * @param c The new char to add.
     */
    public void addChar(char c) {
        TreeMap<Character, Double> charsInitValues = allCharsData.getCharsInitValues();
        double initBrightness;
        if (charsInitValues.containsKey(c)){
            initBrightness = charsInitValues.get(c);
        }
        else {
            initBrightness = calculateInitBrightness(c); // Calculate initial brightness
            allCharsData.getCharsInitValues().put(c, initBrightness);
        }
        charsSet.add(c);
        updateMinMax(charsSet);
        // Add the char to the map of the normalized brightness
        double normalizedBrightness = (initBrightness - minBrightness) / (maxBrightness - minBrightness);
        brightnessToChar.computeIfAbsent(normalizedBrightness, k -> new PriorityQueue<>()).add(c);
    }

    /**
     * Remove a char from the charset.
     *
     * @param c The char to remove.
     */
    public void removeChar(char c) {
        // Remove the char from the map
        double newBrightness = newCharBrightness(c);
        brightnessToChar.get(newBrightness).remove(c);
        if (brightnessToChar.get(newBrightness).isEmpty()) {
            brightnessToChar.remove(newBrightness);
        }

        // Update min or max brightness if necessary
        if (newBrightness == maxBrightness || newBrightness == minBrightness){
            charsSet.remove(c);
            updateMinMax(charsSet);
        }
    }

    /**
     * Calculate the initial brightness of a char.
     *
     * @param c The char.
     * @return The brightness of the char.
     */
    private double calculateInitBrightness(char c) {
        // Calculate the brightness of the char
        boolean[][] charMatrix = CharConverter.convertToBoolArray(c);
        double brightness = 0;

        for (int i = 0; i < charMatrix.length; i++) {
            for (int j = 0; j < charMatrix[0].length; j++) {
                if (charMatrix[i][j]) {
                    brightness++;
                }
            }
        }
        return brightness / (charMatrix.length * charMatrix[0].length);
    }

    /**
     * Calculate the new brightness of a char.
     *
     * @param c The char.
     * @return The new brightness of the char.
     */
    private double newCharBrightness(char c){
        double newBrightnessNumerator = allCharsData.getCharsInitValues().get(c) - minBrightness;
        double newBrightnessDenominator = maxBrightness - minBrightness;
        return newBrightnessNumerator / newBrightnessDenominator;
    }

}
