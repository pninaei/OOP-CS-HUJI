package bricker.brick_strategies;
import bricker.gameobjects.Puck;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Random;

import static bricker.main.Constants.*;


/**
 * This class represents a collision strategy for handling puck-related logic in the Bricker game.
 * It implements the CollisionStrategy interface and is responsible for creating multiple puck balls
 * and updating the game state when collisions with certain game objects occur, specifically bricks.
 */
public class PuckStrategy implements CollisionStrategy {
    private GameObjectCollection gameObjectCollection;
    private Counter brickCounter;
    private Vector2 windowDimension;

    private Vector2 dimensions;

    private ImageReader imageReader;
    private SoundReader soundReader;
    private GameObject[] puck;


    /**
     * Constructs a PuckStrategy object.
     *
     * @param dimensions         Dimensions of the puck balls.
     * @param imageReader        Image reader for loading puck ball images.
     * @param soundReader        Sound reader for loading collision sounds.
     * @param gameObjectCollection Collection of game objects in the scene.
     * @param brickCounter       Counter to track the number of remaining bricks.
     * @param windowDimension    Dimensions of the game window.
     */
    public PuckStrategy(Vector2 dimensions, ImageReader imageReader,
                        SoundReader soundReader, GameObjectCollection gameObjectCollection,
                        Counter brickCounter, Vector2 windowDimension) {

        this.dimensions = dimensions;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.gameObjectCollection = gameObjectCollection;
        this.brickCounter = brickCounter;
        this.windowDimension = windowDimension;
    }

    /**
     * Creates puck balls and adds them to the game when a collision with a brick occurs.
     */
    private void cratePucks() {

        Renderable puckImage = imageReader.readImage(PUCK_BALL_IMG_PATH, true);
        Sound collisionSound = soundReader.readSound(BALL_SOUND_PATH);
        puck = new GameObject[2];
        for (int i = 0; i < 2; i++) {
            GameObject puckBall = new Puck(Vector2.ZERO, dimensions, puckImage, collisionSound
                    , windowDimension, gameObjectCollection);

            this.puck[i] = puckBall;
            // Generate a random angle for puck ball direction
            Random rand = new Random();
            double angle = rand.nextDouble() * Math.PI;
            float ballVelX = (float) Math.cos(angle) * BALL_SPEED;
            float ballVelY = (float)Math.sin(angle) * BALL_SPEED;
            if (rand.nextBoolean()) {
                ballVelX *= -1;
            }
            if (rand.nextBoolean()) {
                ballVelY *= -1;
            }

            puck[i].setTag(PUCK_TAG);
            gameObjectCollection.addGameObject(puckBall, Layer.DEFAULT);
            puckBall.setVelocity(new Vector2(ballVelX, ballVelY));


        }
    }

    /**
     * Handles collision events between game objects, specifically with bricks.
     * Create puck balls and set their position to the center of the collided brick
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

         if(gameObjectCollection.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)) {
             brickCounter.decrement();
         }
        cratePucks();
        puck[0].setCenter(gameObject1.getCenter());
        puck[1].setCenter(gameObject1.getCenter());

    }
}
