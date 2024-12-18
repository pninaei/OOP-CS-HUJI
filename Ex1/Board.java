
/**
 * Represents a game board of a specified size.
 */

public class Board {
    /**
     * The default size of the board.
     */
    private static final int DEFAULT_SIZE = 4;

    /**
     * size - the size of the board
     * board - the board itself
     */
    private int size;
    private Mark[][] board;

    /**
     * Constructs a new Board object with the default size.
     */
    public Board() { // default constructor
        this.size = Board.DEFAULT_SIZE;
        this.board = new Mark[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.board[i][j] = Mark.BLANK;
            }
        }
    }

    /**
     * Constructs a new Board object with the specified size.
     *
     * @param size the size of the board
     */
    public Board(int size) { // additional constructor
        this.size = size;
        this.board = new Mark[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = Mark.BLANK;
            }
        }
    }

    /**
     * Returns the size of this board.
     * 
     * @return the size of this board
     */

    public int getSize() {
        return this.size;
    }

    /**
     * Returns true if it was able to mark at a specified position.
     *
     * @param mark   the mark to be put
     * @param row    the row of the position
     * @param column the column of the position
     * @return true if it was able to mark at a specified position
     */
    public boolean putMark(Mark mark, int row, int column) {
        if ((row < 0 || row > getSize() - 1) || (column < 0 || column > getSize() - 1)) {
            return false;
        }
        if (this.board[row][column] != Mark.BLANK) {
            return false;
        }
        this.board[row][column] = mark;
        return true;
    }

    /**
     * returns the mark at a specified position.
     *
     * @param row    the row of the position
     * @param column the column of the position
     * @return the mark at a specified position
     */

    public Mark getMark(int row, int column) {
        if (row < 0 || row > getSize() - 1 || column < 0 || column > getSize() - 1) {
            return Mark.BLANK;
        }
        return this.board[row][column];
    }

}
