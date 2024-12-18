package pepse.world.trees;


/**
 * The JumpObserver interface represents an observer of jump events.
 * It provides a method to be called when a jump event occurs.
 * It is used to implement the observer pattern.
 */
public interface JumpObserver {
    
    /**
     * The onJump method is called when a jump event occurs.
     */
    void onJump();
}
