package bricker.gameobjects;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * The Puck class encapsulates the behavior and attributes of a game object representing a puck in a gaming
 * environment. As an extension of the GameObject class, it inherits fundamental properties for positioning
 * and rendering. This class introduces specific functionalities tailored to the dynamics of a puck within
 * a game, including collision handling, sound feedback, and automatic removal under certain conditions.
 */
public class Puck extends GameObject {


    /**
     *  field Sound: Represents the sound to be played when the puck collides with another object.
     */
    private Sound soundCollision;
    /**
     * field windowDim: Represents the dimensions of the game window.
     */
    private Vector2 winDim;
    /**
     * field GameObjectCollection: Represents a collection of game objects.
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
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound soundCollision,
                Vector2 winDim, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable);

        this.soundCollision = soundCollision;
        this.winDim = winDim;
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * Handles the event when a collision occurs with another game object.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        // Reflect the velocity based on the collision normal
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        // Play the collision sound
        soundCollision.play();
    }

    /**
     * Updates the puck's position over time and removes it from the collection if it exceeds the window's
     * height.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (getCenter().y() >= winDim.y()){
            gameObjectCollection.removeGameObject(this, Layer.DEFAULT);
        }
    }
}



