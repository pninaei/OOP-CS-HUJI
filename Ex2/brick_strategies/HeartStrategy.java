package bricker.brick_strategies;

import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.HEART_FALLING_TAG;
import static bricker.main.Constants.HEART_SPEED;


/**
 * This class represents a collision strategy for handling heart-related logic in the Bricker game.
 * It implements the CollisionStrategy interface and is responsible for creating a falling heart and
 * updating the game state when collisions with certain game objects occur, specifically bricks.
 */
public class HeartStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjectCollection;
    private final ImageReader imageReader;
    private final Counter brickCounter;
    private final Counter counterLife;
    private final Vector2 dimHeart;
    private GameObject heart;


    /**
     * Constructs a HeartStrategy object.
     *
     * @param gameObjectCollection Collection of game objects in the scene.
     * @param imageReader          Image reader for loading heart images.
     * @param BrickCounter         Counter to track the number of remaining bricks.
     * @param counterLife          Counter to track player life.
     * @param dimHeart             Dimensions of the falling heart.
     */
    public HeartStrategy(GameObjectCollection gameObjectCollection, ImageReader imageReader,
                         Counter BrickCounter, Counter counterLife, Vector2 dimHeart) {

        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.brickCounter = BrickCounter;
        this.counterLife = counterLife;
        this.dimHeart = dimHeart;
    }


    /**
     * Creates a falling heart and adds it to the game when a collision with a brick occurs.
     */
    private void crateHeart(){

        Renderable HeartImage = imageReader.readImage("assets/heart.png", true);
        GameObject heart = new Heart(Vector2.ZERO, dimHeart, HeartImage, counterLife, gameObjectCollection);
        this.heart = heart;

        // Set the velocity for the falling heart
        heart.setVelocity(new Vector2(0, (float) HEART_SPEED));
        gameObjectCollection.addGameObject(heart, Layer.DEFAULT);
        // Set the tag for identifying falling hearts
        heart.setTag(HEART_FALLING_TAG);

    }

    /**
     * Handles collision events between game objects, specifically with bricks.
     * Create a falling heart and set its position to the center of the collided brick
     * and remove the brick and decrement the number of the remaining bricks in the game.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if (gameObjectCollection.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)) {
            brickCounter.decrement();
          }
        crateHeart();
        heart.setCenter(gameObject1.getCenter());
    }
}
