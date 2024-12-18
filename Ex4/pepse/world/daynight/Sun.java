package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;
import pepse.world.Terrain;

import java.awt.*;

import java.awt.Color;

/**
 * The Sun class represents the sun in a game world.
 * It provides a method to create a sun GameObject.
 */
public class Sun {
    // Constants
    private static final float SUN_SIZE = 50;

    /**
     * Creates a sun GameObject with the specified window dimensions and cycle length.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of the day-night cycle in seconds.
     * @return A sun GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        // Calculate the initial position of the sun at the center of the window
        Vector2 sunLocation = new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2);
        // Create a sun GameObject with yellow color
        GameObject sun = new GameObject(sunLocation, new Vector2(SUN_SIZE, SUN_SIZE),
                new OvalRenderable(Color.YELLOW));
        // Set coordinate space to CAMERA_COORDINATES
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        // Set tag to identify the sun GameObject
        sun.setTag("sun");
        // Calculate the initial center of the sun at the center of the window and top of the screen
        Vector2 initialSunCenter = new Vector2(windowDimensions.x() / 2, 0);
        // Create a transition for rotating the sun around its center
        new Transition<Float>(sun,
                (Float angle) -> sun.setCenter(initialSunCenter.subtract(sunLocation).rotated(angle).
                        add(sunLocation)),
                0f, 360f, Transition.LINEAR_INTERPOLATOR_FLOAT,
                PepseGameManager.TIME_OF_DAY_IN_SEC, Transition.TransitionType.TRANSITION_LOOP,
                null);
        return sun; // Return the created sun GameObject
    }
}

