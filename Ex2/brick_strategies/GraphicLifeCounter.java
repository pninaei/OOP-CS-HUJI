package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.SIZE_OF_HEART_OBJ;


/**
 * This class represents a graphical life counter in the Bricker game. It extends the GameObject class
 * and visually represents the player's remaining lives using heart symbols. The counter is updated
 * dynamically based on the player's life count.
 */
public class GraphicLifeCounter extends GameObject {

    private GameObjectCollection gameObjectCollection;
    private Vector2 windowDimensions;
    private GameObject[] hearts;
    private Vector2 dimension;
    private Renderable renderable;
    private int space = 0;
    private Counter counterLife;
    private int maxLength;
    private int capacity = 0;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public GraphicLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                              GameObjectCollection gameObjectCollection, Vector2 windowDimensions,
                              Counter counterLife, int maxLength) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        this.dimension = dimensions;
        this.renderable = renderable;
        this.counterLife = counterLife;
        this.maxLength = maxLength;

    }

    /**
     * Initializes the life counter by creating heart GameObjects based on the initial life count.
     */
    public void init() {
        hearts = new GameObject[maxLength];
        cratesHeart(this.counterLife.value());
    }

    /**
     * Creates heart GameObjects based on the specified number.
     *
     * @param numHeart The number of hearts to create.
     */
    private void cratesHeart(int numHeart) {
        for (int i = 0; i < numHeart; i++) {
            GameObject heart = new GameObject(Vector2.ZERO, new Vector2(SIZE_OF_HEART_OBJ, SIZE_OF_HEART_OBJ)
                    , renderable);
            hearts[this.capacity] = heart;
            gameObjectCollection.addGameObject(hearts[this.capacity], Layer.UI);
            hearts[this.capacity].setCenter(new Vector2(100 + space, windowDimensions.y() - 20));
            space += 27;
            capacity += 1;
        }
    }

    /**
     * Removes heart GameObjects based on the specified number.
     *
     * @param heartsToRemove The number of hearts to remove.
     */
    private void removeHeart(int heartsToRemove) {
        for (int i = 0; i < heartsToRemove; i++) {
            this.gameObjectCollection.removeGameObject(this.hearts[this.counterLife.value()], Layer.UI);
            this.space -= 27;
            capacity -= 1;

        }
    }

    /**
     * Updates the life counter dynamically based on changes in the player's life count.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (capacity > this.counterLife.value()){
            removeHeart(capacity - this.counterLife.value());
        }
        else if (capacity < this.counterLife.value()){
            cratesHeart(this.counterLife.value() - this.capacity);
        }
    }
}
