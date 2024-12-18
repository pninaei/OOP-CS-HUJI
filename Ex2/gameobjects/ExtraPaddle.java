package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import static bricker.main.Constants.*;

/**
 * The ExtraPaddle class extends the functionality of the Paddle class, representing an additional paddle
 * element in a gaming environment. This paddle responds to user input, can collide with a ball or puck, and
 * is subject to removal after a specific number of collisions. It also keeps track of the number of extra
 * paddles and collision hits.
 */
public class ExtraPaddle extends Paddle {

    /**
     * field inputListener: Listens for user input, enabling the extra paddle to respond to keyboard events.
     */
    private UserInputListener inputListener;
    /**
     *  field windowDimension: Represents the dimensions of the game window, defining the boundaries
     *  for the paddle's movement.
     */
    private Vector2 windowDimension;
    /**
     * field gameObjectCollection: Manages a collection of game objects, facilitating the removal of the
     * heart from the game world.
     */
    private GameObjectCollection gameObjectCollection;
    /**
     * field numOfExtraPaddle: Keeps track of the number of extra paddles available.
     */
    private Counter numOfExtraPaddle;
    /**
     *  field collisionHit: Counts the number of collisions with the main ball or puck.
     */
    private Counter collisionHit;

    /**
     * Constructs a new ExtraPaddle instance.
     *
     * @param topLeftCorner         Position of the object in window coordinates (pixels).
     *                              Note that (0,0) is the top-left corner of the window.
     * @param dimensions            Width and height in window coordinates.
     * @param renderable            The renderable representing the object. Can be null, in which case
     *                              the GameObject will not be rendered.
     * @param inputListener         Listens for user input to control the extra paddle's movement.
     * @param window_dimension       Dimensions of the game window.
     * @param numOfExtraPaddle      Counter tracking the number of extra paddles.
     * @param gameObjectCollection  Collection of game objects.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, Vector2 window_dimension,
                       Counter numOfExtraPaddle, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable, inputListener, window_dimension);


        this.numOfExtraPaddle = numOfExtraPaddle;
        this.inputListener = inputListener;
        this.windowDimension = window_dimension;
        this.collisionHit = new Counter(0);
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * Handles the event when a collision occurs with the main ball or puck.
     * Increments the collision count and delegates the handling to the superclass.
     *
     * @param other     The other game object involved in the collision (main ball or puck).
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {

        if (other.getTag().equals(MAIN_BALL_TAG) || other.getTag().equals(PUCK_TAG)){
            super.onCollisionEnter(other, collision);
            this.collisionHit.increment();
        }
    }

    /**
     * Updates the extra paddle's position based on user input, sets its velocity,
     * and removes it from the game world after a specific number of collisions.
     *
     * @param deltaTime The time passed since the last update.
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 moveDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (this.getTopLeftCorner().x() + Vector2.LEFT.x() > 0) {
                moveDir = moveDir.add(Vector2.LEFT);
            }
        }

        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (this.getTopLeftCorner().x() + this.getDimensions().x() + Vector2.RIGHT.x()
                    < this.windowDimension.x()){
                moveDir = moveDir.add(Vector2.RIGHT);
            }
        }
        setVelocity(moveDir.mult(MOVEMENT_SPEED));

        // Remove the extra paddle after four collisions
        if (MAX_EXTRA_PADDLE_COLL == this.collisionHit.value()) {
            gameObjectCollection.removeGameObject(this, Layer.DEFAULT);
            this.numOfExtraPaddle.decrement();

        }
    }
}
