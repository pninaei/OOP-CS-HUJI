package bricker.brick_strategies;


import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.MAIN_BALL_TAG;


/**
 * This class represents a collision strategy for handling camera-related logic in the Bricker game.
 * It implements the CollisionStrategy interface and is responsible for managing camera movements and
 * detecting collisions with game objects, specifically the main ball and bricks.
 */
public class CameraStrategy implements CollisionStrategy {

    private BrickerGameManager brickerGameManager;
    private Counter cameraOnBall;
    private Counter BallHits;
    private GameObjectCollection gameObjectCollection;
    private Counter brickCounter;


    /**
     * Constructs a CameraStrategy object.
     *
     * @param gameManager           The main game manager for the Bricker game.
     * @param gameObjectCollection  Collection of game objects in the scene.
     * @param cameraOnBall          Counter to track the camera's presence on the ball.
     * @param BallHits              Counter to track the number of hits on the ball.
     * @param brickCounter          Counter to track the number of remaining bricks.
     */
    public CameraStrategy(BrickerGameManager gameManager, GameObjectCollection gameObjectCollection,
    Counter cameraOnBall, Counter BallHits, Counter brickCounter) {

        this.brickerGameManager = gameManager;
        this.cameraOnBall = cameraOnBall;
        this.gameObjectCollection = gameObjectCollection;
        this.brickCounter = brickCounter;
        this.BallHits = BallHits;
    }


    /**
     * Handles collision events between game objects.
     * if the main ball hit the brick that has the camera strategy and the camera isn't invoked yet
     * than the camera is set to follow the main ball movement.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        if (gameObjectCollection.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)){
            brickCounter.decrement();
        }
        if (gameObject2.getTag().equals(MAIN_BALL_TAG) && brickerGameManager.camera() == null){
            brickerGameManager.setCamera(new Camera(gameObject2, Vector2.ZERO,
                    brickerGameManager.windowDim().mult(1.2f), brickerGameManager.windowDim()));
            cameraOnBall.increaseBy(BallHits.value());
        }

    }
}
