package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * The Sky class represents the sky in a game world.
 * It provides a method to create a sky GameObject.
 */
public class Sky {
    // Constants
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /**
     * Creates a sky GameObject with the specified window dimensions.
     *
     * @param windowDimensions The dimensions of the game window.
     * @return A sky GameObject.
     */
    public static GameObject create(Vector2 windowDimensions) {
        // Create a sky GameObject with basic sky color
        GameObject sky = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        // Set coordinate space to CAMERA_COORDINATES
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        // Set tag to identify the sky GameObject
        sky.setTag("sky");
        return sky; // Return the created sky GameObject
    }
}

