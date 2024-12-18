package pepse.world;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.trees.Fruit;
import pepse.world.trees.JumpObserver;
import pepse.world.trees.JumpingSubject;


/**
 * The Avatar class represents the avatar in a game world.
 * It provides a method to create an avatar GameObject.
 */
public class Avatar extends GameObject implements JumpingSubject{

    // Constants
    private static final float AVATAR_SIZE = 60;
    private static final float AVATAR_SPEED = 300;
    private static final int INITIAL_ENERGY = 100;
    private static final int MIN_ENERGY = 0;
    private static final int GRAVITY = 200;
    private static final float LOSS_ENERGY_WALKING = 0.5f;
    private static final float LOSS_ENERGY_JUMPING = 10;
    private static final int GAIN_ENERGY_JUMPING = 10;
    private static final int MIN_JUMP_ENERGY = 10;
    private static final int AVATAR_MASS = 5;
    private static final int AVATAR_VELOCITY_Y = -100;
    private static final int MAX_ENERGY = 100;

    // Image paths
    private static final String[] STANDING_IMAGES = {"assets/idle_0.png", "assets/idle_1.png",
            "assets/idle_2.png", "assets/idle_3.png"};
    private static final String[] WALKING_IMAGES = {"assets/run_0.png", "assets/run_1.png",
            "assets/run_2.png", "assets/run_3.png", "assets/run_4.png", "assets/run_5.png"};
    private static final String[] JUMPING_IMAGES = {"assets/jump_0.png", "assets/jump_1.png",
            "assets/jump_2.png", "assets/jump_3.png"};
    private static final double IMAGE_CHANGE_TIME = 0.2;

    // Variables
    private UserInputListener inputListener;
    private static Renderable[] standingImage;
    private static Renderable[] walkingImage;
    private static Renderable[] jumpingImage;

    private static AnimationRenderable avatarStanding;
    private static AnimationRenderable avatarWalking;
    private static AnimationRenderable avatarJumping;
    /**
     * The tag for the avatar GameObject.
     */
    public static final String AVATAR_TAG = "Avatar";

    private List<JumpObserver> jumpObservers;
    private float energy;
    private boolean isLeft;
    private Vector2 avatarLocation;


    /**
     * Constructs a new Avatar instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Avatar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.energy = INITIAL_ENERGY;

        // Set physics properties
        transform().setAccelerationY(GRAVITY);
        physics().setMass(AVATAR_MASS);
        transform().setVelocityY(AVATAR_VELOCITY_Y);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);

        // Initialize other attributes
        this.jumpObservers = new ArrayList<>();
        this.avatarLocation = topLeftCorner;
    }

    /**
     * Returns the starting position of the avatar on the screen.
     * @return The location of the avatar.
     */
    public Vector2 getAvatarLocation(){
        return this.avatarLocation;
    }


    /**
     * Registers an observer to the list of observers. Observers will be notified when the
     * avatar jumps, and they will be updated automatically.
     * @param observer The observer to be registered.
     */
    @Override
    public void registerJumpObserver(JumpObserver observer) {
        jumpObservers.add(observer);
    }

    /**
     * Notifies all registered jump observers when the avatar jumps.
     * This method iterates through the list of jump observers and invokes
     * the 'onJump()' method for each observer, triggering their update logic.
     */
    @Override
    public void notifyJumpObservers() {
        for (JumpObserver observer : jumpObservers) {
            observer.onJump();
        }
    }

    /**
     * Removes a jump observer from the list of observers.
     * @param observer The observer to be removed.
     */
    @Override
    public void removeJumpObserver(JumpObserver observer){
        jumpObservers.remove(observer);
    }

    /**
     * Returns the energy of the avatar
     * @return the energy of the avatar
     */
    public float getEnergy(){
        return energy;
    }

    /**
     * Updates the GameObject's state based on the time that has passed since the last update.
     * Moves the avatar according to user input and updates the energy accordingly.
     * @param deltaTime The time that has passed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Calculate horizontal velocity based on user input
        float xVelocity = moveRightOrLeft();

        // Update renderer to reflect avatar's direction
        renderer().setIsFlippedHorizontally(isLeft);

        // Set avatar's horizontal velocity
        transform().setVelocityX(xVelocity);

        // Checks if the user press space to jump
        jump();

        // Check if avatar is standing still and adjust energy
        if (getVelocity().equals(Vector2.ZERO) && xVelocity == 0){
            renderer().setRenderable(avatarStanding);
            if (energy < INITIAL_ENERGY && (energy + 1 > INITIAL_ENERGY)){
                energy = Math.min(MAX_ENERGY, energy +1);
            }
            else if (energy < INITIAL_ENERGY){
                energy += 1;
            }
        }
    }

    /**
     * Moves the avatar right or left according to the user input and updates the
     * energy accordingly.
     * @return The x velocity of the avatar.
     */
    private float moveRightOrLeft(){
        float xVel = 0;
        // Move right
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            isLeft = false;
            // Check if enough energy for movement
            if (energy > MIN_ENERGY){
                renderer().setRenderable(avatarWalking);
                // Update horizontal velocity
                xVel += AVATAR_SPEED;
                // Decrease energy
                energy -= LOSS_ENERGY_WALKING;
            }
        }
        // Move left
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) ){
            isLeft = true;
            // Check if enough energy for movement
            if (energy > MIN_ENERGY){
                renderer().setRenderable(avatarWalking);
                // Update horizontal velocity
                xVel -= AVATAR_SPEED;
                // Decrease energy
                energy -= LOSS_ENERGY_WALKING;
            }
        }
        return xVel;
    }

    /**
     * Makes the avatar jump according to the user input and updates the energy accordingly.
     */
    private void jump(){
        // Check if jump key is pressed and avatar is on the ground
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0) {
            // Check if enough energy for jump
            if (!(energy < MIN_JUMP_ENERGY)) {
                // Change avatar renderable to jumping state
                renderer().setRenderable(avatarJumping);;
                // Set vertical velocity for jump
                transform().setVelocityY(-AVATAR_SPEED);
                transform().setVelocityX(0);
                // Decrease energy
                energy -= LOSS_ENERGY_JUMPING;
                // Notify jump observers
                notifyJumpObservers();
            }
            // Reset horizontal velocity
            transform().setVelocityX(0);
        }
    }


    /**
     * Returns true if the GameObject should collide with the specified other GameObject.
     * Checks if the avatar collides with terrain or fruit.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return super.shouldCollideWith(other) || other.getTag().equals(Block.TERRAIN_TAG) ||
                other.getTag().equals(Fruit.FRUIT_TAG);
    }

    /**
     * Sets the energy of the avatar.
     * If the current energy plus the gain energy jumping is less than or equal to the initial energy,
     * sets the energy to the minimum of 100 and the sum of current energy and gain energy jumping.
     * Otherwise, adds the gain energy jumping to the current energy.
     */
    public void setEnergy(){
        if (energy <= INITIAL_ENERGY && energy + Avatar.GAIN_ENERGY_JUMPING >= INITIAL_ENERGY){
            energy = Math.min(100, energy+ Avatar.GAIN_ENERGY_JUMPING);
        }
        else {
            energy += Avatar.GAIN_ENERGY_JUMPING;
        }
    }

    /**
     * Called when the GameObject collides with another GameObject.
     * Checks if the avatar collides with terrain. If so, stops the avatar's vertical movement.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals(Block.TERRAIN_TAG)) {
            transform().setVelocityY(0);
            super.onCollisionEnter(other, collision);
        }
    }

    /**
     * Creates an avatar GameObject with the specified position, input listener, and image reader.
     *
     * @param topLeftCorner  The position of the avatar, in window coordinates (pixels).
     * @param inputListener  The input listener for handling input events.
     * @param imageReader    The image reader for loading game assets.
     * @return A new avatar GameObject.
     */
    public static GameObject create(Vector2 topLeftCorner,
                                   UserInputListener inputListener, ImageReader imageReader){

        // Load avatar images
        standingImage = new ImageRenderable[STANDING_IMAGES.length];
        for (int i = 0; i < STANDING_IMAGES.length; i++){
            standingImage[i] = imageReader.readImage(STANDING_IMAGES[i], true);
        }
        walkingImage = new ImageRenderable[WALKING_IMAGES.length];
        for (int i = 0; i < WALKING_IMAGES.length; i++){
            walkingImage[i] = imageReader.readImage(WALKING_IMAGES[i], true);
        }
        jumpingImage = new ImageRenderable[JUMPING_IMAGES.length];
        for (int i = 0; i < JUMPING_IMAGES.length; i++){
            jumpingImage[i] = imageReader.readImage(JUMPING_IMAGES[i], true);
        }

        // Create avatar renderables
        avatarStanding = new AnimationRenderable(standingImage, IMAGE_CHANGE_TIME);
        avatarWalking = new AnimationRenderable(walkingImage, IMAGE_CHANGE_TIME);
        avatarJumping = new AnimationRenderable(jumpingImage, IMAGE_CHANGE_TIME);

        // Create and configure avatar GameObject
        Avatar avatar = new Avatar(topLeftCorner, new Vector2(AVATAR_SIZE, AVATAR_SIZE), avatarStanding);
        avatar.inputListener = inputListener;
        avatar.setTag(AVATAR_TAG);
        return avatar;
    }
}
