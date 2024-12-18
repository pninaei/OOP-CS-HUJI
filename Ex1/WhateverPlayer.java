import java.util.Random;

/**
 * whatever class is a player that puts a mark in a random position.
 */
public class WhateverPlayer implements Player {

    /**
     * Constructor for objects of class WhateverPlayer
     */

    public WhateverPlayer() {
    } // constructor

    /**
     * Play a turn of a whatever player that puts a mark in a random position.
     * 
     * @param board the board
     * @param mark  the mark to be put on the board
     */

    @Override
    public void playTurn(Board board, Mark mark) {
        int row, column;
        Random rand = new Random(); // create a random object
        do {
            row = rand.nextInt(board.getSize());
            column = rand.nextInt(board.getSize());
        } while (!board.putMark(mark, row, column));

    }
}
