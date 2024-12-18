package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;

import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import static bricker.main.Constants.*;


/**
 * The Heart class represents a game object resembling a heart within a gaming environment. As a subclass of
 * GameObject, it inherits fundamental properties for positioning and rendering. The primary purpose of the
 * Heart class is to handle collisions with a designated main paddle, incrementing its associated life
 * counter upon collision, and subsequently removing itself from the game world.
 */
public class Heart extends GameObject {

    /**
     * field heartLife: Represents a counter associated with the heart's life or usage.
     */
    private Counter heartLife;
    /**
     * field gameObjectCollection: Manages a collection of game objects, facilitating the removal of the
     * heart from the game world.
     */
    private GameObjectCollection gameObjectCollection;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Counter heartLife,
                 GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable);
        this.heartLife = heartLife;
        this.gameObjectCollection = gameObjectCollection;
    }


    /**
     * Determines whether the heart should collide with a specific game object.
     *
     * @param other The other game object involved in the collision.
     * @return True if the heart should collide with the specified object, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(MAIN_PADDLE_TAG);
    }

    /**
     * Handles the event when a collision occurs with the main paddle.
     * Increments the heart's associated life counter and removes the heart from the game world.
     *
     * @param other     The other game object involved in the collision (main paddle).
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (heartLife.value() < 4){
            heartLife.increment();
        }
        gameObjectCollection.removeGameObject(this, Layer.DEFAULT);

    }

    /**
     * Updates the heart's position over time (empty implementation).
     *
     * @param deltaTime The time passed since the last update.
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }
}

