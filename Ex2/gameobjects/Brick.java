package bricker.gameobjects;
import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;



/**
 * The Brick class represents a game object that functions as a brick within a gaming environment. Bricks
 * typically have collision strategies that define their behavior upon collision with other game objects.
 * This class extends the basic functionalities provided by the GameObject class and introduces the concept
 * of collision strategies for customized interactions.
 */
public class Brick extends GameObject {


    /**
     * Represents the collision strategy associated with the brick, defining its behavior upon collision.
     */
    private CollisionStrategy collisionStrategy;


    /**
     * Constructs a new Brick instance.
     *
     * @param topLeftCorner     Position of the object in window coordinates (pixels).
     *                          Note that (0,0) is the top-left corner of the window.
     * @param dimensions        Width and height in window coordinates.
     * @param renderable        The renderable representing the object.
     * @param collisionStrategy Collision strategy defining the brick's behavior upon collision.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions,
                 Renderable renderable, CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;

    }

    /**
     * Handles the event when a collision occurs with another game object.
     * Delegates the collision handling to the associated collision strategy.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        this.collisionStrategy.onCollision(this, other);

    }
}
