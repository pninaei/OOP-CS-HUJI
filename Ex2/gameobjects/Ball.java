package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * This class represents a ball in the Bricker game. It extends the GameObject class and handles
 * collisions with other game objects, updating its velocity and keeping track of the number of collisions.
 */
public class Ball extends GameObject {

    private Sound soundCollision;
    private Counter ballHits;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound soundCollision, Counter ballHits) {
        super(topLeftCorner, dimensions, renderable);
        this.soundCollision = soundCollision;
        this.ballHits = ballHits;
    }


    /**
     * Handles collision events when the ball collides with other game objects.
     * Adjusts the velocity, plays a collision sound, and increments the collision counter.
     *
     * @param other     The other game object involved in the collision.
     * @param collision Details of the collision, including the collision normal.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        soundCollision.play();
        ballHits.increment();
    }

    /**
     * Retrieves the current value of the collision counter.
     *
     * @return The number of collisions the ball has experienced.
     */
    public int getCollisionCounter(){
        return this.ballHits.value();
    }
}
