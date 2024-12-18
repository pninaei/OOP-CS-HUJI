
/**
 * A factory for creating players.
 */
public class PlayerFactory {

    /**
     * Constructor for objects of class PlayerFactory
     */
    public PlayerFactory() {
    }

    /**
     * Create a player of a given type.
     * 
     * @param type the type of the player
     * @return the player
     */
    public Player buildPlayer(String type) {

        Player player;

        switch (type.toLowerCase()) {
            case "human":
                player = new HumanPlayer();
                break;
            case "whatever":
                player = new WhateverPlayer();
                break;
            case "clever":
                player = new CleverPlayer();
                break;
            case "genius":
                player = new GeniusPlayer();
                break;
            default:
                player = null;
                break;
        }
        return player;

    }
}
