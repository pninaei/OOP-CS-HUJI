package pepse.world.trees;


import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;
import java.awt.*;



/**
 * The Fruit class represents a fruit GameObject in a game.
 * It extends the GameObject class and implements the JumpObserver interface.
 * This class is responsible for managing the behavior of the fruit in the game,
 * such as handling collisions with the avatar, updating its appearance based on jumps,
 * and providing energy to the avatar upon collision.
 */
public class Fruit extends GameObject implements JumpObserver {

    private static final Color INIT_FRUIT_COLOR = Color.ORANGE;
    private static final Color FRUIT_CHANGE_COLOR = Color.RED;
    private Runnable setEnergyFunc;
    private Color curColor;

    /**
     * The tag for the fruit GameObject.
     */
    public static final String FRUIT_TAG = "fruit";

    /**
     * Constructs a new Fruit instance with the specified position, dimensions, renderable,
     * and function to set energy.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param setEnergy     The function to set energy when collided with an avatar.
     */
    public Fruit(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 Runnable setEnergy) {
        super(topLeftCorner, dimensions, renderable);
        curColor = INIT_FRUIT_COLOR;
        this.setEnergyFunc = setEnergy;
    }

    /**
     * Handles the logic when a collision occurs with the fruit.
     * Provides energy to the avatar upon collision and updates the fruit's appearance.
     *
     * @param other     The GameObject involved in the collision.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        if (other.getTag().equals(Avatar.AVATAR_TAG) && this.renderer().getRenderable() != null) {
            // Gives energy to the avatar
            setEnergyFunc.run();
            this.renderer().setRenderable(null);

            // Schedule the fruit to reappear after a certain time
            new ScheduledTask(this, PepseGameManager.TIME_OF_DAY_IN_SEC,
                    false, () -> {
                this.renderer().setRenderable(new
                        OvalRenderable(ColorSupplier.approximateColor(curColor)));
            });
        }
    }

    /**
     * Specifies whether the fruit should collide with a given GameObject.
     * The fruit should collide only with the avatar.
     *
     * @param other The GameObject to check for collision.
     * @return True if the fruit should collide with the specified GameObject (avatar),
     * false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Avatar.AVATAR_TAG);
    }

    /**
     * Updates the state of the fruit over time.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     * Handles the logic when the avatar performs a jump.
     * Changes the color of the fruit and updates its appearance accordingly.
     */
    @Override
    public void onJump() {
        if (this.renderer().getRenderable() != null) {
            // Change the color of the fruit based on its current color
            if (curColor.equals(INIT_FRUIT_COLOR)) {
                this.renderer().setRenderable(new OvalRenderable(
                        ColorSupplier.approximateColor(FRUIT_CHANGE_COLOR)));
            } else {
                this.renderer().setRenderable(new OvalRenderable(
                        ColorSupplier.approximateColor(INIT_FRUIT_COLOR)));
            }
        }
        // Update the current color of the fruit
        curColor = curColor.equals(INIT_FRUIT_COLOR) ? FRUIT_CHANGE_COLOR : INIT_FRUIT_COLOR;
    }
}


