package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import static bricker.main.Constants.*;


/**
 * The Paddle class represents a game object that serves as a movable element within a gaming environment,
 * typically controlled by user input. As a subclass of GameObject, it inherits basic properties for
 * positioning and rendering. The primary purpose of the Paddle class is to handle user input, allowing the
 * player to control its movement within specified bounds.
 */
public class Paddle extends GameObject {

    /**
     * field UserInputListener: Listens for user input, enabling the paddle to respond to keyboard events.
     */
    private UserInputListener inputListener;
    /**
     *  field windowDimensions: Represents the dimensions of the game window, defining the boundaries
     *  for the paddle's movement.
     */
    private Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener
            inputListener, Vector2 window_dimension) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = window_dimension;

    }

    /**
     * Updates the paddle's position based on user input.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Move left if the left arrow key is pressed and within the left window boundary
        Vector2 moveDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (this.getTopLeftCorner().x() + Vector2.LEFT.x() > 0) {
                moveDir = moveDir.add(Vector2.LEFT);
            }
        }
        // Move right if the right arrow key is pressed and within the right window boundary
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (this.getTopLeftCorner().x() + this.getDimensions().x() + Vector2.RIGHT.x()
                    < windowDimensions.x()){
                moveDir = moveDir.add(Vector2.RIGHT);
            }
        }
        // Set the velocity based on the calculated movement direction and speed
        setVelocity(moveDir.mult(MOVEMENT_SPEED));
    }



}
