package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.trees.JumpObserver;

/**
 * The Block class represents a block GameObject in a game.
 * It extends the GameObject class.
 */
public class Block extends GameObject{
    // Constants
    /** The size of the block. */
    public static final int SIZE = 30;

    /**
     * The tag for the terrain GameObject.
     */
    public static final String TERRAIN_TAG = "ground";

    /**
     * Constructs a Block object with the specified top-left corner position and renderable.
     *
     * @param topLeftCorner The top-left corner position of the block.
     * @param renderable    The renderable component of the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        // Call the constructor of the GameObject superclass
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        // Configure physics properties
        physics().preventIntersectionsFromDirection(Vector2.ZERO); // Prevent intersections
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS); // Set mass to immovable
        setTag(TERRAIN_TAG);

    }
}


