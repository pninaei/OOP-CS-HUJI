import java.util.Random;

/**
 * CleverPlayer is better player than WhateverPlayer.
 * it's winning the random player atleast 55% percent of the time.
 * 
 */

public class CleverPlayer implements Player {

    /**
     * Constructor for objects of class CleverPlayer
     */
    public CleverPlayer() {
    } 

    /**
     * Play a turn of a clever player that puts a mark in the first empty cell in
     * the first row of the board.
     * If there is no empty cell in the first row, it puts a mark in a random
     * position.
     * 
     * @param board - the board
     * @param mark  - the mark to be put on the board
     */

    @Override
    public void playTurn(Board board, Mark mark) {
        int[] move = DoSequenceInFirstRow(board, mark);
        if (move == null) {
            Random rand = new Random(); // create a random object
            int row, column;
            do {
                row = rand.nextInt(board.getSize());
                column = rand.nextInt(board.getSize());

            } while (!board.putMark(mark, row, column));
        } else {
            board.putMark(mark, move[0], move[1]);
        }

    }

    /**
     * This method checks if there is an empty cell in the first row of the board.
     * If there is, it returns the coordinates of the empty cell. If there is no
     * empty cell, it returns null.
     * 
     * @param board - the board
     * @param mark  - the mark
     * @return - the coordinates of the empty cell in the first row of the board
     */

    private int[] DoSequenceInFirstRow(Board board, Mark mark) {

        int[] move = new int[2];

        for (int i = 0; i < board.getSize(); i++) {
            if (board.getMark(0, i) == Mark.BLANK) {
                move[0] = 0;
                move[1] = i;
                return move;

            }
        }
        return null;
    }

}