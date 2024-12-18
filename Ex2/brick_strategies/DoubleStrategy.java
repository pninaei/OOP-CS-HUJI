package bricker.brick_strategies;

import danogl.GameObject;


/**
 * This class represents a composite collision strategy that combines multiple collision strategies.
 * It implements the CollisionStrategy interface and allows for the sequential execution of multiple
 * collision strategies when a collision occurs.
 */
public class DoubleStrategy implements CollisionStrategy{

    // The first collision strategy to be executed
    private CollisionStrategy collisionStrategy1;
    // The second collision strategy to be executed
    private CollisionStrategy collisionStrategy2;
    // The optional third collision strategy to be executed
    private CollisionStrategy collisionStrategy3;

    /**
     * Constructs a DoubleStrategy object with specified collision strategies.
     *
     * @param collisionStrategy1 The first collision strategy to be executed.
     * @param collisionStrategy2 The second collision strategy to be executed.
     * @param collisionStrategy3 The optional third collision strategy to be executed.
     */
    public DoubleStrategy(CollisionStrategy collisionStrategy1, CollisionStrategy collisionStrategy2,
                          CollisionStrategy collisionStrategy3) {

        this.collisionStrategy1 = collisionStrategy1;
        this.collisionStrategy2 = collisionStrategy2;
        this.collisionStrategy3 = collisionStrategy3;
    }


    /**
     * Handles collision events between game objects by sequentially invoking
     * the onCollision method of each contained collision strategy.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        this.collisionStrategy1.onCollision(gameObject1, gameObject2);
        this.collisionStrategy2.onCollision(gameObject1, gameObject2);
        if (collisionStrategy3 != null){
            this.collisionStrategy3.onCollision(gameObject1, gameObject2);

        }
    }
}
