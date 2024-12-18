
/**
 * this class represents a human player and only responsible for getting input
 * from the user
 */

public class HumanPlayer implements Player {

    /**
     * Constructor for objects of class HumanPlayer
     */
    public HumanPlayer() {
    }

    /**
     * Play a turn.
     * 
     * @param board the board
     * @param mark  the mark
     */
    public void playTurn(Board board, Mark mark) {
        int row, column;
        if (mark == Mark.X) {

            System.out.println(Constants.playerRequestInputString(Mark.X.toString()));
        } else if (mark == Mark.O) {
            System.out.println(Constants.playerRequestInputString(Mark.O.toString()));
        }
        do {
            int input = KeyboardInput.readInt();
            row = input / 10;
            column = input % 10;

            if (row < 0 || row > board.getSize() - 1 || column < 0 || column > board.getSize() - 1) {
                System.out.println(Constants.INVALID_COORDINATE); // invalid position

            } else if (board.getMark(row, column) != Mark.BLANK) {
                System.out.println(Constants.OCCUPIED_COORDINATE); // already occupied

            }

        } while (!board.putMark(mark, row, column)); // repeat until it was able to put a mark
    }

}
