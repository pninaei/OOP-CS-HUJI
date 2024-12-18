package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * The SunHalo class represents a halo around the sun in a game world.
 * It provides a method to create a sun halo GameObject.
 */
public class SunHalo {
    // Constants
    private static final float HALO_SIZE = 100;

    /**
     * Creates a sun halo GameObject around the specified sun GameObject.
     *
     * @param sun The sun GameObject around which the halo is created.
     * @return A sun halo GameObject.
     */
    public static GameObject create(GameObject sun) {
        // Create a sun halo GameObject with a transparent yellow oval renderable
        GameObject sunHalo = new GameObject(sun.getCenter(), new Vector2(HALO_SIZE, HALO_SIZE),
                new OvalRenderable(new Color(255, 255, 0, 20))); // Transparent yellow color
        // Set coordinate space to CAMERA_COORDINATES
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        // Set tag to identify the sun halo GameObject
        sunHalo.setTag("sunHalo");
        // Add a component to update the sun halo's center position based on the sun's center
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
        return sunHalo; // Return the created sun halo GameObject
    }
}

