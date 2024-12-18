package image_char_matching;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * The CharsData class stores character data for character matching.
 * It maps sets of characters to their corresponding brightness values.
 */
public class CharsData {
    private static CharsData charsData; // Singleton instance
    private final HashMap<char[], TreeMap<Double, PriorityQueue<Character>>> allCharsData;
    private final TreeMap<Character, Double> charAndInitBrightness;
    // a map that maps a char to its brightness
    private final HashMap<char[], Double[]> charsSetMinMax;

    /**
     * Constructs a new CharsData object.
     */
    private CharsData() {
        charAndInitBrightness = new TreeMap<>();
        allCharsData = new HashMap<>();
        charsSetMinMax = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of CharsData.
     *
     * @return The singleton instance of CharsData.
     */
    public static CharsData getInstance() {
        if (charsData != null) {
            return charsData;
        }
        charsData = new CharsData();
        return charsData;
    }

    /**
     * Adds new character data for a character set.
     * If the character set already exists, it will not be overridden.
     *
     * @param charsSet     The set of characters.
     * @param charsValues  The TreeMap containing brightness values mapped to characters.
     * @param minAndMax    The array containing the minimum and maximum brightness values
     *                    for the character set.
     */
    public void addNewCharsaddSet(char[] charsSet, TreeMap<Double,
            PriorityQueue<Character>> charsValues,
                               Double[] minAndMax) {
        // Check if the character set already exists
        if (!allCharsData.containsKey(charsSet)) {
            // If not, add the character set along with its corresponding brightness values
            allCharsData.put(charsSet, charsValues);
            charsSetMinMax.put(charsSet, minAndMax);
        }
    }

    /**
     * Retrieves the brightness values for a given character set.
     *
     * @param charsSet  The set of characters.
     * @return The TreeMap containing brightness values mapped to characters,
     *         or null if the character set is not found.
     */
    public TreeMap<Double, PriorityQueue<Character>> getCharsValues(char[] charsSet) {
        // Check if the character set exists
        if (allCharsData.containsKey(charsSet)) {
            // If yes, return the brightness values associated with the character set
            return allCharsData.get(charsSet);
        }
        // If the character set is not found, return null
        return null;
    }

    /**
     * Retrieves the minimum and maximum brightness values for a given character set.
     *
     * @param charsSet  The set of characters.
     * @return The array containing the minimum and maximum brightness values for the character set,
     *         or null if the character set is not found.
     */
    public Double[] getMinAndMax(char[] charsSet) {
        if (charsSetMinMax.containsKey(charsSet)) {
            return charsSetMinMax.get(charsSet);
        }
        return null;
    }

    /**
     * Retrieves the initial brightness values for all characters.
     *
     * @return The TreeMap containing initial brightness values mapped to characters.
     */
    public TreeMap<Character, Double> getCharsInitValues() {
        return charAndInitBrightness;
    }

}



