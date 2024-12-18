package pepse.world.trees;


import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;
import java.util.Random;


/**
 * The Leaf class represents a leaf GameObject in a game world.
 * It extends the Block class and implements the JumpObserver interface.
 * Leaves can change their angle and dimensions over time and rotate when the avatar jumps.
 */
public class Leaf extends Block implements JumpObserver {

    private static final float ANGLE_CHANGE_MIN = -10f;
    private static final float ANGLE_CHANGE_MAX = 10f;
    private static final float DIMENSION_CHANGE_FACTOR = 0.95f;
    private static final float JUMP_ROTATION_ANGLE = 90f;
    private static final float TRANSITION_DURATION = 2f;
    private static final float ANGLE_CHANGE_SCHEDULE = 3f;

    private Random random;

    /**
     * Constructs a Leaf object with the specified position and renderable.
     *
     * @param position   The position of the leaf.
     * @param renderable The renderable component of the leaf.
     */
    public Leaf(Vector2 position, Renderable renderable) {
        super(position, renderable);
        this.random = new Random();
    }

    /**
     * Changes the angle of the leaf over time.
     */
    public void changeAngle() {
        new Transition<>(this, this.renderer()::setRenderableAngle,
                ANGLE_CHANGE_MIN, ANGLE_CHANGE_MAX,
                Transition.CUBIC_INTERPOLATOR_FLOAT, TRANSITION_DURATION,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    /**
     * Changes the dimensions of the leaf over time.
     */
    public void changeDimension() {
        new Transition<>(this, this::setDimensions,
                new Vector2(Block.SIZE, Block.SIZE).mult(DIMENSION_CHANGE_FACTOR),
                new Vector2(Block.SIZE, Block.SIZE), Transition.CUBIC_INTERPOLATOR_VECTOR,
                TRANSITION_DURATION, Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

    /**
     * Schedules transitions for the leaf to change its angle and dimensions over time.
     */
    public void scheduledTaskTransitions() {
        new ScheduledTask(this, random.nextFloat()
                * ANGLE_CHANGE_SCHEDULE,
                false, this::changeAngle);
        new ScheduledTask(this, random.nextFloat()
                * ANGLE_CHANGE_SCHEDULE,
                false, this::changeDimension);
    }

    /**
     * Rotates the leaf when the avatar jumps.
     * Rotates the leaf by 90 degrees around itself.
     */
    @Override
    public void onJump() {
        // Rotate the leaf by 90 degrees around itself when the avatar jumps
        float currentAngle = this.renderer().getRenderableAngle();
        float newAngle = currentAngle + JUMP_ROTATION_ANGLE;

        // Perform the rotation using a transition
        new Transition<>(this, this.renderer()::setRenderableAngle,
                currentAngle,
                newAngle, Transition.LINEAR_INTERPOLATOR_FLOAT, 1,
                Transition.TransitionType.TRANSITION_ONCE, null);
    }
}




