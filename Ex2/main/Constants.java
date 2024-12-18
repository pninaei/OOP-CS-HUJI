package bricker.main;

/**
 * This class is constant class that the brick game manager use.
 */
public class Constants {
    /**
     * a default constructor for constant class (empty implementation)
     */
    public Constants(){}
    /**
     * Title name of the game.
     */
    public static final String TITLE_NAME = "BrickGame";
    /**
     *  Number of command line arguments indicator when no arguments are provided.
     */
    public static final int NO_ARGUMENTS = 0;
    /**
     *  Number of command line arguments indicator when more than 2 arguments are provided.
     */
    public static final int MORE_THAN_2_ARGS = 2;
    /**
     * Default width of the game window.
     */
    public static final int WIDTH_OF_THE_WINDOW = 700;
    /**
     * Default height of the game window.
     */
    public static final int HEIGHT_OF_THE_WINDOW = 500;
    /**
     * The speed of the ball in the game.
     */
    public static final float BALL_SPEED = 200;
    /**
     *  The size of width and the height of the ball in the game.
     */
    public static final float BALL_SIZE = 25;
    /**
     * The size of the puck ball in the game (derived from BALL_SIZE)
     * the size is calculated by the size of the main ball multiply by 3/4 of it
     */
    public static final float PUCK_BALL_SIZE = BALL_SIZE * (float) 0.75;
    /**
     *  Default number of rows for bricks in the game.
     */
    public static final int DEFAULT_NUM_OF_ROW = 7;
    /**
     * Default number of bricks in each row.
     */
    public static final int DEFAULT_NUM_OF_BRICKS_EACH_ROW = 8;
    /**
     * Starting position for bricks in the game.
     * The space between the left wall from the top left bricks and also the space between the right wall
     * to the top most right brick in the game.
     */
    public static final int START_POSITION_BRICKS = 15;
    /**
     * The space between each brick
     */
    public static final int SPACE_BETWEEN_BRICKS = 3;
    /**
     * Initial number of lives for the player.
     */
    public static final int INITIAL_LIFE = 3;
    /**
     * Number of lives when the color of the numeric life change to yellow - that happens when the player has
     * left 2 lives
     */
    public static final int MIDDLE_LIFE = 2;
    /**
     * Number of lives at the last stage - when the player has left only one life
     */
    public static final int LAST_LIFE = 1;
    /**
     * Width of the paddle.
     */
    public static final float PADDLE_WIDTH = 100;
    /**
     * The movement speed of the Extra paddle
     */
    public static final float MOVEMENT_SPEED = 300;
    /**
     *  Height of the paddle.
     */
    public static final float PADDLE_HEIGHT = 15;
    /**
     * Height of the bricks.
     */
    public static final float BRICK_HEIGHT = 15;
    /**
     * Number of collisions required for resetting the camera back to normal after it has turned on.
     * we till 5 because the first collision with the brick that has the strategy camera is not counted
     */
    public static final int NUM_OF_COLLISIONS_FOR_CAMERA = 5;
    /**
     * Number of collisions required for removing the Extra paddle from the game.
     */
    public static final int MAX_EXTRA_PADDLE_COLL = 4;
    /**
     * Size of the heart object.
     */
    public static final int SIZE_OF_HEART_OBJ = 25;
    /**
     * Location of the numeric heart object in the game on the UI layer from the left wall.
     */
    public static final int NUMERIC_HEART_OBJ_LOCATION = 25;
    /**
     * Tag for identifying the main ball.
     */
    public static final String MAIN_BALL_TAG = "Ball";
    /**
     * Tag for identifying the main paddle.
     */
    public static final String MAIN_PADDLE_TAG = "Paddle";
    /**
     * Tag for identifying the puck ball.
     */
    public static final String PUCK_TAG = "Puck";
    /**
     * Tag for identifying the falling heart.
     */
    public static final String HEART_FALLING_TAG = "Heart";
    /**
     * The speed of the falling heart.
     */
    public static final int HEART_SPEED = 100;
    /**
     *  File path for the background image.
     */
    public static final String BACKGROUND_IMG_PATH = "assets/DARK_BG2_small.jpeg";
    /**
     *  File path for the heart image.
     */
    public static final String HEART_IMG_PATH = "assets/heart.png";
    /**
     *  File path for the ball image.
     */
    public static final String BALL_IMG_PATH = "assets/ball.png";
    /**
     * File path for the ball collision sound.
     */
    public static final String BALL_SOUND_PATH = "assets/blop.wav";
    /**
     *  File path for the paddle image.
     */
    public static final String PADDLE_IMG_PATH = "assets/paddle.png";
    /**
     *  File path for the brick image.
     */
    public static final String BRICK_IMG_PATH = "assets/brick.png";
    /**
     *  File path for the puck ball image.
     */
    public static final String PUCK_BALL_IMG_PATH = "assets/mockBall.png";
    /**
     *  Indicator for double strategy in collision strategies.
     *  by randomizing number between 1 and 5 - if number 5 was picked it means the double strategies is
     *  given to the specific brick as a collision strategy
     */
    public static final int DOUBLE_STRATEGY_INDICATOR = 5;
    /**
     * Number of options for special collision strategies.
     * by randomizing number between 1 and 4 we give for a specific brick another strategy that can't be
     * double strategy
     */
    public static final int SPECIAL_STRATEGY_OPTIONS = 4;
    /**
     * Indicator for basic collision strategy.
     * by randomizing between 0 and 1 - if 0 is selected, a specific brick get the basic collision strategy.
     */
    public static final int BASIC_STRATEGY_INDICATOR = 0;
    /**
     * Indicator for basic collision strategy.
     * by randomizing between 0 and 1 - if 1 is selected, a specific brick get a special collision strategy.
     */
    public static final int SPECIAL_STRATEGY_INDICATOR = 1;
    /**
     * Indicator for the increment between basic and special collision strategy options.
     */
    public static final int INDCR_BETWEEN_BASIC_AND_SPECIAL_STRATEGY = 2;
}
