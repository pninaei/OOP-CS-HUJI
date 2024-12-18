package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;    
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import java.awt.*;


/**
 * The PepseGameManager class extends the GameManager class and
 * provides initialization for the game environment.
 */
public class PepseGameManager extends GameManager {
    /**
     * time of the day in seconds - how long the sun is above the ground
     */
    public static final Float TIME_OF_DAY_IN_SEC = 30f;
    private Avatar avatar;


    /**
     * Initializes the game environment.
     *
     * @param imageReader      The image reader for loading game assets.
     * @param soundReader      The sound reader for loading game sounds.
     * @param inputListener    The user input listener for handling input events.
     * @param windowController The window controller for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        // Call the superclass method to perform base initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // Create and add sky GameObject to the background layer
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);

        // Create terrain and add terrain blocks to the static objects layer
        Terrain newTerrain = new Terrain(windowController.getWindowDimensions(), 0);
        for (Block terrainBlock : newTerrain.createInRange(0,
                (int) windowController.getWindowDimensions().x())) {
            gameObjects().addGameObject(terrainBlock, Layer.STATIC_OBJECTS);
        }

        // Create and add sun GameObject to the background layer
        GameObject sun = Sun.create(windowController.getWindowDimensions(), TIME_OF_DAY_IN_SEC);
        gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(), TIME_OF_DAY_IN_SEC),
                Layer.BACKGROUND);
        gameObjects().addGameObject(SunHalo.create(sun), Layer.BACKGROUND);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);

        // Create and add avatar GameObject to the game
        GameObject avatar = createAvatar(imageReader, inputListener, windowController, newTerrain);
        this.avatar = (Avatar) avatar;
        createTrees(windowController, newTerrain, (Avatar) avatar);
    }


    /**
     * Creates the tree GameObjects and adds them to the game.
     *
     * @param windowController The window controller for managing the game window.
     * @param newTerrain       The terrain of the game.
     * @param avatar           The avatar GameObject.
     */
    private void createTrees(WindowController windowController, Terrain newTerrain, Avatar avatar) {
        // Create flora instance with terrain height function and avatar
        Flora flora = new Flora(newTerrain::groundHeightAt, avatar);
        // Register flora as a jump observer for the avatar
        avatar.registerJumpObserver(flora);

        // Create trees within the specified range and add them to the game
        Iterable<GameObject> floraObjects = flora.createInRange(0,
                (int) windowController.getWindowDimensions().x());
        for (GameObject obj : floraObjects) {
            if (obj.getTag().equals(Flora.LEAF_TAG)) {
                gameObjects().addGameObject(obj, Layer.FOREGROUND); // Add leaves to the foreground layer
            } else {
                // Add fruits and trunks to the static objects layer
                gameObjects().addGameObject(obj, Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * Creates the avatar GameObject and adds it to the game.
     *
     * @param imageReader      The image reader for loading game assets.
     * @param inputListener    The user input listener for handling input events.
     * @param windowController The window controller for managing the game window.
     * @param newTerrain       The terrain of the game.
     * @return The avatar GameObject.
     */
    private GameObject createAvatar(ImageReader imageReader, UserInputListener inputListener,
                                    WindowController windowController, Terrain newTerrain) {
        // Calculate the initial position of the avatar
        Vector2 pos = new Vector2(windowController.getWindowDimensions().x() / 2,
                newTerrain.groundHeightAt(windowController.getWindowDimensions().x() / 2) - 150);

        // Create the avatar GameObject
        GameObject avatar = Avatar.create(pos, inputListener, imageReader);

        // Add the avatar to the default layer
        gameObjects().addGameObject(avatar, Layer.DEFAULT);

        return avatar;
    }


    /**
     * Updates the game state for the next frame.
     *
     * @param deltaTime The time in seconds since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        showEnergy();
    }

    /**
     * Shows the energy of the avatar on the screen.
     */
    private void showEnergy() {
        // Create a text renderable for displaying energy
        TextRenderable energyDisplay = new TextRenderable("");
        energyDisplay.setColor(Color.BLACK);

        // Create a game object for displaying energy
        GameObject energy = new GameObject(new Vector2(20, 20), new Vector2(25, 25), energyDisplay);
        energy.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // Add a component to update the energy display
        energy.addComponent((deltaTime) ->
                energyDisplay.setString(String.format("%.1f", avatar.getEnergy()) + "%"));

        // Add the energy display game object to the UI layer
        gameObjects().addGameObject(energy, Layer.UI);
    }

    /**
     * Main method to start the game by creating an instance of PepseGameManager
     * and running it.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}

