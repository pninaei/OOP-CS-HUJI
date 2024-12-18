package bricker.main;
import static bricker.main.Constants.*;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.DoubleStrategy;
import bricker.brick_strategies.StrategiesFactory;
import bricker.gameobjects.*;
import bricker.brick_strategies.GraphicLifeCounter;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;

import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;


/**
 * The BrickerGameManager class serves as the main game manager for the Bricker game. It manages the game's
 * initialization, update loop, and various game elements such as the ball, paddle, bricks, and life counters.
 */
public class BrickerGameManager extends GameManager {

    private int WIDTH_BRICK;
    private GameObject ball;
    private UserInputListener inputListener;
    private SoundReader sound;
    private int numOfRow = DEFAULT_NUM_OF_ROW;
    private int numOfBricksInRow = DEFAULT_NUM_OF_BRICKS_EACH_ROW;
    private Vector2 window_dimensions;
    private WindowController windowController;
    private Counter counterLife;
    private Counter counterBricks;
    private Counter BallHits;
    private Counter numOfExtraPaddles;
    private Counter CameraOnBall;
    private GraphicLifeCounter graphicLifeCounter;
    private GameObject numericLifeCounter;
    private int maxLengthArrayHeart = 4;


    /**
     * Constructs a new BrickerGameManager instance with a specified window title and dimensions.
     *
     * @param windowTitle      Title of the game window.
     * @param windowDimensions Dimensions of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);

    }

    /**
     * Constructs a new BrickerGameManager instance with a specified window title, dimensions, number of
     * rows, and number of bricks.
     *
     * @param windowTitle      Title of the game window.
     * @param windowDimensions Dimensions of the game window
     * @param rows             Number of rows of bricks.
     * @param numOfBricks      Number of bricks in each row.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numOfBricks, int rows) {
        super(windowTitle, windowDimensions);
        this.numOfBricksInRow = numOfBricks;
        this.numOfRow = rows;

    }

    /**
     * Returns the window dimensions.
     */
    public Vector2 windowDim() {
        return window_dimensions;
    }

    /**
     * Creates walls around the game window.
     */
    private void cratesWalls() {
        // left wall
        GameObject wallLeft = new GameObject(Vector2.ZERO, new Vector2(START_POSITION_BRICKS,
                window_dimensions.y()), null);
        this.gameObjects().addGameObject(wallLeft, Layer.STATIC_OBJECTS);

        // right wall
        GameObject wallRight = new GameObject(
                new Vector2(window_dimensions.x() - START_POSITION_BRICKS, 0),
                new Vector2(START_POSITION_BRICKS, window_dimensions.y()), null);

        this.gameObjects().addGameObject(wallRight, Layer.STATIC_OBJECTS);

        // up wall
        GameObject wallup = new GameObject
                (Vector2.ZERO, new Vector2(window_dimensions.x(), START_POSITION_BRICKS), null);
        this.gameObjects().addGameObject(wallup, Layer.STATIC_OBJECTS);
    }

    /**
     * Creates the main ball game object.
     *
     * @param imageReader       Reads images for rendering.
     * @param soundReader       Reads sounds for collision-related effects.
     * @param window_dimensions Dimensions of the game window
     * @return
     */
    private GameObject createBall(ImageReader imageReader, SoundReader soundReader,
                                  Vector2 window_dimensions) {
        Renderable ballImage = imageReader.readImage(BALL_IMG_PATH, true);
        Sound collisionSounds = soundReader.readSound(BALL_SOUND_PATH);
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), ballImage,
                collisionSounds, BallHits);

        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setTag(MAIN_BALL_TAG);
        this.gameObjects().addGameObject(ball, Layer.DEFAULT);

        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        ball.setCenter(window_dimensions.mult(0.5F));
        return ball;
    }

    /**
     * Creates the background game object.
     *
     * @param imageReader Reads images for rendering.
     */
    private void crateBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMG_PATH, true);
        GameObject background = new GameObject(Vector2.ZERO,
                new Vector2(window_dimensions.x(), window_dimensions.y()), backgroundImage);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

    }

    /**
     * Creates the user paddle game object.
     *
     * @param imageReader       Reads images for rendering.
     * @param inputListener     Listens for user input (UserInputListener).
     * @param window_dimensions Dimensions of the game window
     */
    private void cratePaddle(ImageReader imageReader, UserInputListener inputListener,
                             Vector2 window_dimensions) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMG_PATH, true);
        GameObject userPaddle = new Paddle(Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage, inputListener, window_dimensions);

        gameObjects().addGameObject(userPaddle, Layer.DEFAULT);
        userPaddle.setTag(MAIN_PADDLE_TAG);
        userPaddle.setCenter(new Vector2(window_dimensions.x() / 2, window_dimensions.y() - 30));
    }

    /**
     * Generates a random integer (0 or 1).
     */
    private int BasicInt() {
        Random random = new Random();
        return random.nextInt(INDCR_BETWEEN_BASIC_AND_SPECIAL_STRATEGY);  // 0 or 1
    }

    /**
     * Generates a random integer between 1 and x.
     *
     * @param x the range number to randomize from
     * @return random number between 1 to x
     */
    private int OtherInt(int x) {
        Random random = new Random();
        return random.nextInt(x) + 1;
    }

    /**
     * This method is responsible for creating an array of bricks in the game. It calculates the necessary
     * spacing, padding, and positions to generate rows and columns of bricks. Depending on randomly
     * generated values, it applies different collision strategies to the bricks, creating variations in
     * their behavior.
     *
     * @param imageReader       Reads images for rendering.
     * @param window_dimensions Dimensions of the game window
     */
    private void crateBricksArray(ImageReader imageReader, Vector2 window_dimensions) {

        int borderSpaceRow = (this.numOfBricksInRow + 1)* SPACE_BETWEEN_BRICKS;
        int boardSpace = this.numOfBricksInRow * SPACE_BETWEEN_BRICKS;
        this.WIDTH_BRICK = (int) ((this.window_dimensions.x() - borderSpaceRow - boardSpace)
                / this.numOfBricksInRow);

        int startPosition = (int) ((this.window_dimensions.x() -
                (WIDTH_BRICK * this.numOfBricksInRow + borderSpaceRow)) / 2);

        // Create bricks
        this.counterBricks = new Counter(0);
        Renderable brickImage = imageReader.readImage(BRICK_IMG_PATH, false);
        for (int i = 1; i < this.numOfRow + 1; i++) {
            for (int j = 0; j < this.numOfBricksInRow; j++) {
                float XCor = startPosition + SPACE_BETWEEN_BRICKS + j * (WIDTH_BRICK + SPACE_BETWEEN_BRICKS);
                float YCor = i * (PADDLE_HEIGHT + SPACE_BETWEEN_BRICKS);

                CollisionStrategy collision = null;
                StrategiesFactory strategiesFactory = new StrategiesFactory(this.gameObjects(), imageReader,
                        sound, counterBricks, window_dimensions, counterLife, inputListener,
                        numOfExtraPaddles, CameraOnBall, BallHits, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                        this, new Vector2(PUCK_BALL_SIZE, PUCK_BALL_SIZE),
                        new Vector2(SIZE_OF_HEART_OBJ, SIZE_OF_HEART_OBJ));

                int basic = BasicInt();
                if (basic == BASIC_STRATEGY_INDICATOR) {
                    collision = new BasicCollisionStrategy(this.gameObjects(), counterBricks);

                } else if (basic == SPECIAL_STRATEGY_INDICATOR) { // round one i got double strategy
                    CollisionStrategy collisionStrategy1;
                    CollisionStrategy collisionStrategy2;
                    CollisionStrategy collisionStrategy3;
                    int branch_left = OtherInt(DOUBLE_STRATEGY_INDICATOR);
                    if (branch_left != DOUBLE_STRATEGY_INDICATOR) { // in the left one there isn't 5
                        collisionStrategy1 = strategiesFactory.CrateStrategy(branch_left);
                        // **** going for the right branch
                        int branch_right = OtherInt(DOUBLE_STRATEGY_INDICATOR);
                        if (branch_right != DOUBLE_STRATEGY_INDICATOR) { // int the right one there isn't 5
                            collisionStrategy2 = strategiesFactory.CrateStrategy(branch_right);
                            collisionStrategy3 = null;
                            // create double strategy with 2 strategies
                        } else {
                            // right branch is number 5:
                            int sub_left_right = OtherInt(SPECIAL_STRATEGY_OPTIONS);
                            int sub_right_tight = OtherInt(SPECIAL_STRATEGY_OPTIONS);
                            collisionStrategy2 = strategiesFactory.CrateStrategy(sub_left_right);
                            collisionStrategy3 = strategiesFactory.CrateStrategy(sub_right_tight);
                        }
                    } else { // left branch is number 5;
                        int sub_left_left = OtherInt(SPECIAL_STRATEGY_OPTIONS);
                        int sub_right_left = OtherInt(SPECIAL_STRATEGY_OPTIONS);
                        collisionStrategy1 = strategiesFactory.CrateStrategy(sub_left_left);
                        collisionStrategy2 = strategiesFactory.CrateStrategy(sub_right_left);
                        // right branch has to be one of 1 to 4
                        int branch_right = OtherInt(SPECIAL_STRATEGY_OPTIONS);
                        collisionStrategy3 = strategiesFactory.CrateStrategy(branch_right);
                    }
                    collision = new DoubleStrategy(collisionStrategy1, collisionStrategy2,
                            collisionStrategy3);
                }
                GameObject brick = new Brick(Vector2.ZERO, new Vector2(WIDTH_BRICK, BRICK_HEIGHT), brickImage,
                        collision);
                brick.setTopLeftCorner(new Vector2(XCor, YCor));
                this.counterBricks.increment();
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * Checks for game end conditions and prompts the player accordingly.
     */
    private void checkForGameEnd() {
        String prompt = "";

        if (this.counterBricks.value() == 0) {
            prompt = "you win";
        }
        if (this.inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = "you win";
        }
        if (this.counterLife.value() == 0) {
            prompt = "You lost!";
        }
        if (!prompt.isEmpty()) {
            prompt += " Play again?";
            if (windowController.openYesNoDialog(prompt)) {

                windowController.resetGame();

            } else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * Checks if the ball has fallen below the game window and handles the consequences.
     */
    private void checkFail() {
        if (this.ball.getCenter().y() > this.window_dimensions.y()) {
            float ballVelX = BALL_SPEED;
            float ballVelY = BALL_SPEED;
            Random rand = new Random();
            if (rand.nextBoolean()) {
                ballVelX *= -1;
            }
            if (rand.nextBoolean()) {
                ballVelY *= -1;
            }
            ball.setVelocity(new Vector2(ballVelX, ballVelY));
            ball.setCenter(window_dimensions.mult(0.5F));
            this.counterLife.decrement();

            numericLifeCounter(counterLife.value());
        }
    }

    /**
     * Checks if the camera should be activated based on the number of ball hits.
     */
    private void checkCamera() {
        if (this.camera() != null && this.BallHits.value() - this.CameraOnBall.value() >=
                NUM_OF_COLLISIONS_FOR_CAMERA) {
            this.CameraOnBall.reset();
            this.setCamera(null);
        }
    }

    /**
     * Overrides the update method to include additional game logic.
     *
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkFail();
        checkCamera();
        checkForGameEnd();
        numericLifeCounter(counterLife.value());
        //System.out.println(counterBricks.value());

    }

    /**
     * Creates the graphic representation of player life.
     *
     * @param imageReader Reads images for rendering.
     */
    private void graphicLifeCounter(ImageReader imageReader) {

        Renderable imageHeart = imageReader.readImage(HEART_IMG_PATH, true);
        this.graphicLifeCounter = new GraphicLifeCounter(Vector2.ZERO, new Vector2(0, 0), imageHeart,
                this.gameObjects(), window_dimensions, this.counterLife, maxLengthArrayHeart);

        gameObjects().addGameObject(graphicLifeCounter, Layer.UI);
        this.graphicLifeCounter.init();
    }

    /**
     * Updates the numeric representation of player life.
     *
     * @param num_of_life Number of lives remaining.
     */
    private void numericLifeCounter(int num_of_life) {
        if (this.counterLife != null) {
            gameObjects().removeGameObject(this.numericLifeCounter, Layer.UI);
        }
        String stringNumLife = Integer.toString(num_of_life);
        GameObject lifeLeftText = getLifeLeftText(num_of_life, stringNumLife);
        lifeLeftText.setCenter(new Vector2(NUMERIC_HEART_OBJ_LOCATION, window_dimensions.y() -
                NUMERIC_HEART_OBJ_LOCATION));
        gameObjects().addGameObject(lifeLeftText, Layer.UI);
        this.numericLifeCounter = lifeLeftText;
    }

    /**
     * Creates a game object representing the numeric representation of player life.
     *
     * @param num_of_life   Number of lives remaining.
     * @param stringNumLife String representation of the number of lives.
     * @return
     */
    private GameObject getLifeLeftText(int num_of_life, String stringNumLife) {
        TextRenderable lifeLeftTextImage = new TextRenderable(stringNumLife);
        if (num_of_life >= INITIAL_LIFE) {
            lifeLeftTextImage.setColor(Color.green);
        }
        if (num_of_life == MIDDLE_LIFE) {
            lifeLeftTextImage.setColor(Color.yellow);
        }
        if (num_of_life == LAST_LIFE) {
            lifeLeftTextImage.setColor(Color.red);
        }
        return new GameObject(Vector2.ZERO, new Vector2(SIZE_OF_HEART_OBJ, SIZE_OF_HEART_OBJ),
                lifeLeftTextImage);
    }


    /**
     * Initializes the game by creating various game elements.
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowController = windowController;
        this.window_dimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.sound = soundReader;

        this.counterLife = new Counter(INITIAL_LIFE);
        this.BallHits = new Counter(0);
        this.numOfExtraPaddles = new Counter(0);
        this.CameraOnBall = new Counter(0);

        // crate ball
        this.ball = createBall(imageReader, soundReader, window_dimensions);


        // crate background
        crateBackground(imageReader);
        // crates walls
        cratesWalls();
        // crate user paddle
        cratePaddle(imageReader, inputListener, window_dimensions);
        // crate brick
        crateBricksArray(imageReader, window_dimensions);
        // crate hearts
        graphicLifeCounter(imageReader);

        numericLifeCounter(INITIAL_LIFE);

    }

    /**
     * The main method to start the Bricker game.
     * in case there is no arguments given or there is more than two the game been run with default arguments
     *
     * @param args : Command-line arguments (optional).
     */
    public static void main(String[] args) {
        if (args.length == NO_ARGUMENTS || args.length > MORE_THAN_2_ARGS) {
            new BrickerGameManager(TITLE_NAME, new Vector2(WIDTH_OF_THE_WINDOW, HEIGHT_OF_THE_WINDOW)).run();
        } else {
            new BrickerGameManager(TITLE_NAME, new Vector2(WIDTH_OF_THE_WINDOW, HEIGHT_OF_THE_WINDOW),
                    Integer.parseInt(args[0]), Integer.parseInt(args[1])).run();
        }
    }
}
