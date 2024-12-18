package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;

import java.awt.*;

/**
 * The Night class represents the night environment in a game world.
 * It provides a method to create a night GameObject.
 */
public class Night {
    // Constants
    /** The opacity of the night (at midnight). */
    private static final Float MIDNIGHT_OPACITY = 0.5f;


    /**
     * Creates a night GameObject with the specified window dimensions and cycle length.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of the day-night cycle in seconds.
     * @return A night GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        // Create a night GameObject with black color
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        // Set coordinate space to CAMERA_COORDINATES
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        // Set tag to identify the night GameObject
        night.setTag("night");
        // Create a transition for changing the opacity of the night GameObject
        new Transition<Float>(night, night.renderer()::setOpaqueness, 0f,
                MIDNIGHT_OPACITY, Transition.CUBIC_INTERPOLATOR_FLOAT,
                PepseGameManager.TIME_OF_DAY_IN_SEC/2,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        return night; // Return the created night GameObject
    }
}

