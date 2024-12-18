package bricker.brick_strategies;

import danogl.GameObject;



/**
 * This interface defines a contract for implementing collision strategies between game objects.
 * Classes that implement this interface must provide an implementation for the onCollision method,
 * which is called when a collision occurs between two game objects.
 */
public interface CollisionStrategy {

    /**
     * Handles collision events between two game objects.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    void onCollision(GameObject gameObject1, GameObject gameObject2);
}
