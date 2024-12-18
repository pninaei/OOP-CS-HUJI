package pepse.world;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Terrain class represents the terrain of a game world.
 * It generates blocks to represent the terrain within a specified range of x-coordinates.
 */
public class Terrain {
    // Constants
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final double FACTOR_MULTIPLIER = 0.3;

    // Fields
    private float groundHeightAtX0; // Initial ground height
    private Vector2 windowDimensions; // Window dimensions
    private int seed; // Seed for randomization
    private final NoiseGenerator noiseGenerator;
    /**
     * Constructs a Terrain object with the specified window dimensions and seed.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param seed             The seed for randomization.
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.seed = seed;
        this.windowDimensions = windowDimensions;
        // Calculate the initial ground height at x=0, random number betweem middle board to 1/3 board
        Random random = new Random();
        groundHeightAtX0 = random.nextInt(50) + windowDimensions.y() * 2/3;
        this.noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /**
     * Returns the ground height at the specified x-coordinate.
     *
     * @param x The x-coordinate.
     * @return The ground height at the specified x-coordinate.
     */
    public float groundHeightAt(float x) {
        return (float)(groundHeightAtX0 + (5+Block.SIZE *
                noiseGenerator.noise(x, Block.SIZE*FACTOR_MULTIPLIER)));
    }

    /**
     * Creates blocks to represent the terrain within the specified range of x-coordinates.
     * Blocks are built from the ground level up.
     *
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return A list of blocks representing the terrain within the specified range.
     */
    public List<Block> createInRange(int minX, int maxX) {
        // Create a renderable for terrain
        // List to hold all created blocks
        List<Block> allBlocks = new ArrayList<>();
        // Iterate over x-coordinates within the specified range
        for (int i = minX; i <= maxX / Block.SIZE; i++) {
            // Calculate the starting y-coordinate for the ground height at the current x-coordinate
            float startY = (float) (Math.floor(groundHeightAt(i * Block.SIZE) /
                    Block.SIZE) * Block.SIZE);

            // Iterate from the ground level down to TERRAIN_DEPTH blocks below the ground height
            for (int j = 0; j < TERRAIN_DEPTH; j++) {
                // Calculate the y-coordinate of the current block
                float y = startY + (j * Block.SIZE); //Adjust y-coordinate based on the starting y-coordinate
                // Create a block at the current location
                Vector2 location = new Vector2(i * Block.SIZE, y);
                Renderable terrainImage = new RectangleRenderable(ColorSupplier.
                        approximateColor(BASE_GROUND_COLOR));
                allBlocks.add(new Block(location, terrainImage));


            }
        }
        return allBlocks; // Return the list of created blocks
    }
}


