package image;

import java.util.Objects;

/**
 * Represents a generic tuple of two elements.
 *
 * @param <T> the type of the first element
 * @param <U> the type of the second element
 */
public class Tuple<T, U> {
    private final T first;
    private final U second;

    /**
     * Constructs a new Tuple with the specified first and second elements.
     *
     * @param first  the first element of the tuple
     * @param second the second element of the tuple
     */
    public Tuple(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the first element of the tuple.
     *
     * @return the first element of the tuple
     */
    public T getFirst() {
        return first;
    }

    /**
     * Gets the second element of the tuple.
     *
     * @return the second element of the tuple
     */
    public U getSecond() {
        return second;
    }

    /**
     * Indicates whether some other object is "equal to" this tuple.
     * Two tuples are considered equal if and only if both their first and second elements are equal.
     *
     * @param o the reference object with which to compare
     * @return true if this tuple is equal to the object argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(first, tuple.first) && Objects.equals(second, tuple.second);
    }

    /**
     * Returns a hash code value for the tuple.
     * This method computes a hash code based on the hash codes of the first and second elements.
     *
     * @return a hash code value for this tuple
     */
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}





