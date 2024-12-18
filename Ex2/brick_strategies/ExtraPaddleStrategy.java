package bricker.brick_strategies;
import bricker.gameobjects.ExtraPaddle;
import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * This class represents a collision strategy for handling extra paddle-related logic in the Bricker game.
 * It implements the CollisionStrategy interface and is responsible for creating an extra paddle and
 * updating the game state when collisions with certain game objects occur, specifically bricks.
 */
public class ExtraPaddleStrategy implements CollisionStrategy {


    // Dimensions of the extra paddle
    private Vector2 dimensions;

    // Image reader for loading paddle images
    private ImageReader imageReader;

    // Collection of game objects in the scene
    private GameObjectCollection gameObjectCollection;

    // User input listener for controlling the extra paddle
    private UserInputListener inputListener;

    // Dimensions of the game window
    private Vector2 windowDim;

    // Counter to track the number of remaining bricks
    private Counter brickCounter;

    // Counter to track the number of extra paddles
    private  Counter numOfExtraPaddle;



    /**
     * Constructs an ExtraPaddleStrategy object.
     *
     * @param gameObjectCollection Collection of game objects in the scene.
     * @param imageReader          Image reader for loading paddle images.
     * @param inputListener        User input listener for controlling the extra paddle.
     * @param windowDim            Dimensions of the game window.
     * @param BrickCounter         Counter to track the number of remaining bricks.
     * @param numOfExtraPaddle     Counter to track the number of extra paddles.
     * @param dimensions           Dimensions of the extra paddle.
     */
    public ExtraPaddleStrategy(GameObjectCollection gameObjectCollection, ImageReader imageReader,
                                UserInputListener inputListener, Vector2 windowDim, Counter BrickCounter,
                               Counter numOfExtraPaddle, Vector2 dimensions)  {


        this.dimensions = dimensions;
        this.imageReader = imageReader;
        this.gameObjectCollection = gameObjectCollection;
        this.inputListener = inputListener;
        this.windowDim = windowDim;
        this.brickCounter = BrickCounter;
        this.numOfExtraPaddle = numOfExtraPaddle;
    }

    /**
     * Creates an extra paddle and adds it to the game when a collision with a brick occurs.
     */
    private void crateExtraPaddle(){

        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject ExtraPaddle = new ExtraPaddle(Vector2.ZERO,
                dimensions, paddleImage, inputListener, windowDim,numOfExtraPaddle,
                this.gameObjectCollection);

        ExtraPaddle.setCenter(new Vector2(windowDim.x()/2, windowDim.y()/2));
        gameObjectCollection.addGameObject(ExtraPaddle, Layer.DEFAULT);
        numOfExtraPaddle.increment();
    }

    /**
     * Handles collision events between game objects, specifically with bricks.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if(gameObjectCollection.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)){
            brickCounter.decrement();
        }
        if (this.numOfExtraPaddle.value() == 0){
            crateExtraPaddle();
        }
    }
}
