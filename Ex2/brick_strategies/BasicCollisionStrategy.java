package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;




/**
 * This class implements a basic collision strategy for handling collisions with bricks in the Bricker game.
 * It removes the collided brick from the game object collection and decrements the brick counter.
 */
public class BasicCollisionStrategy implements CollisionStrategy{

    ;
    private GameObjectCollection gameObjectCollection;
    private Counter brickCounter;

    /**
     * Constructs a BasicCollisionStrategy object.
     *
     * @param gameObjectCollection Collection of game objects in the scene.
     * @param brickCounter         Counter to track the number of remaining bricks.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjectCollection, Counter brickCounter){
        this.gameObjectCollection = gameObjectCollection;
        this.brickCounter = brickCounter;
    }


    /**
     * Handles collision events between a game object and a brick.
     * Removes the collided brick from the game object collection and decrements the brick counter.
     *
     * @param gameObject1 The game object involved in the collision.
     * @param gameObject2 The brick involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameObjectCollection.removeGameObject(gameObject1, Layer.STATIC_OBJECTS);
        brickCounter.decrement();

    }
}
