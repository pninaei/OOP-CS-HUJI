package pepse.world.trees;


/**
 * The JumpingSubject interface is implemented by GameObjects that can be jumped on.
 * It provides methods for registering and notifying JumpObservers.
 */
public interface JumpingSubject {

    /**
     * Registers a JumpObserver with the JumpingSubject.
     *
     * @param observer The JumpObserver to register.
     */
    void registerJumpObserver(JumpObserver observer);

    /**
     * Notifies all registered JumpObservers that the JumpingSubject has been jumped on.
     */
    void notifyJumpObservers();

    /**
     * Removes a JumpObserver from the JumpingSubject.
     *
     * @param observer The JumpObserver to remove.
     */
    void removeJumpObserver(JumpObserver observer);
}
