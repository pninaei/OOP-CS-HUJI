package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * The StrategiesFactory class serves as a factory for creating various collision strategies used in the
 * game. It encapsulates the creation of different strategies based on numeric identifiers, allowing for easy
 * strategy instantiation and management.
 */
public class StrategiesFactory {

    private GameObjectCollection gameObjectCollection;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Counter brickCounter;
    private Vector2 windowDim;
    private Counter counterLife;
    private UserInputListener inputListener;
    private Counter numOfExtraPaddle;
    private Counter cameraOnBall;
    private Counter BallHits;
    private Vector2 paddleDim;
    private BrickerGameManager brickerGameManager;
    private Vector2 puckSize;
    private Vector2 heartDim;


    /**
     * Constructs a new StrategiesFactory instance.
     *
     * @param gameObjectCollection Manages a collection of game objects.
     * @param imageReader          Reads images for rendering.
     * @param soundReader          Reads sounds for collision-related effects.
     * @param brickCounter         Counter tracking the number of bricks.
     * @param windowDim            Dimensions of the game window.
     * @param counterLife          Counter tracking player life.
     * @param inputListener        Listens for user input.
     * @param numOfExtraPaddle     Counter tracking the number of extra paddles.
     * @param cameraOnBall         Counter indicating whether the camera is focused on the ball.
     * @param BallHits             Counter tracking the number of ball hits.
     * @param paddleDim            Dimensions of the paddle.
     * @param brickerGameManager   Manages the game state.
     * @param puckSize             Dimensions of the puck.
     * @param heartDim             Dimensions of the heart.
     */
    public StrategiesFactory(GameObjectCollection gameObjectCollection, ImageReader imageReader,
                             SoundReader soundReader, Counter brickCounter, Vector2 windowDim,
                             Counter counterLife, UserInputListener inputListener,
                             Counter numOfExtraPaddle, Counter cameraOnBall, Counter BallHits,
                             Vector2 paddleDim, BrickerGameManager brickerGameManager, Vector2 puckSize,
                             Vector2 heartDim){

        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.brickCounter = brickCounter;
        this.windowDim = windowDim;
        this.counterLife = counterLife;
        this.inputListener = inputListener;
        this.numOfExtraPaddle = numOfExtraPaddle;
        this.cameraOnBall = cameraOnBall;
        this.BallHits = BallHits;
        this.paddleDim = paddleDim;
        this.brickerGameManager = brickerGameManager;
        this.puckSize = puckSize;
        this.heartDim = heartDim;
    }

    /**
     * Creates a specific collision strategy based on the provided identifier.
     *
     * @param num Numeric identifier for the desired strategy.
     * @return The created CollisionStrategy instance.
     */
    public CollisionStrategy CrateStrategy(int num) {

        CollisionStrategy collisionStrategy;
        switch (num){
            case 1:
                collisionStrategy = new PuckStrategy(puckSize, imageReader, soundReader, gameObjectCollection,
                        brickCounter, windowDim);
                break;
            case 2:
                collisionStrategy = new ExtraPaddleStrategy(gameObjectCollection, imageReader, inputListener,
                        windowDim, brickCounter, numOfExtraPaddle, paddleDim);
                break;
            case 3:
                collisionStrategy = new CameraStrategy(brickerGameManager, gameObjectCollection, cameraOnBall,
                        BallHits, brickCounter);
                break;
            case 4:
                collisionStrategy = new HeartStrategy(gameObjectCollection, imageReader, brickCounter,
                        counterLife, heartDim);
                break;
            default:
                return null;
        }
        return collisionStrategy;
    }

}
