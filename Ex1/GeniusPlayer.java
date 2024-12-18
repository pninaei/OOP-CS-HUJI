import java.util.Random;

/**
 * GeniusPlayer is better than CleverPlayer and WhateverPlayer.
 * it's winning the clever player atleast 55% percent of the time.
 */

public class GeniusPlayer implements Player {

    public GeniusPlayer() {
    } // constructor

    /**
     * genius player plays a turn.
     * genius knows the moves of clever so he will try to block him and also he will
     * try to win.
     * 
     * @param board
     * @param mark
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int[] move = bestMove(board, mark);
        if (move != null) {
            board.putMark(mark, move[0], move[1]);
        } else {
            Random rand = new Random(); // create a random object
            int row, column;
            do {
                row = rand.nextInt(board.getSize());
                column = rand.nextInt(board.getSize());

            } while (!board.putMark(mark, row, column));
        }
    }

    private int[] bestMove(Board board, Mark mark) {
        int[] move = new int[2];
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getMark(i, 2) == Mark.BLANK) {
                move[0] = i;
                move[1] = 2;
                return move;
            }

        }
        return null;
    }

}
