
/**
 * this class represent one single round of the game.
 * this class has three responsibilities:
 * 1. needs to know when the game is over
 * 2. needs to know who the winner is
 * 3. if there's no winner, needs to know if the game is a draw
 */

public class Game {

    /**
     * The default sequence of k marks to win.
     */
    private static final int winStreak = 3;

    /**
     * field PlayerX - the first player
     * field PlayerO - the second player
     * field renderer - the renderer
     * field board - the board game
     * field _winStreak - the number of marks in a row to win
     * field num_of_marks - the number of marks on the board
     */

    private Player playerX;
    private Player playerO;
    private Renderer renderer;
    private int _winStreak;

    private Board board;
    private int num_of_marks = 0;

    /**
     * Constructs a new Game object with the specified players and renderer with
     * default
     * board size and default win streak.
     *
     * @param playerX  the first player
     * @param playerO  the second player
     * @param renderer the renderer
     */

    public Game(Player playerX, Player playerO, Renderer renderer) {

        this.board = new Board(); // creating a board game with default size

        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this._winStreak = Game.winStreak;
    }

    /**
     * Constructs a new Game object with the specified players and renderer with
     * specified
     * board size and specified win streak.
     * in case the win streak is bigger than the board size or less than 2, the win
     * streak will
     * be set to the board size
     *
     * @param playerX   the first player
     * @param playerO   the second player
     * @param renderer  the renderer
     * @param size      the size of the board
     * @param winStreak the number of marks in a row to win
     */

    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {

        this.board = new Board(size); // creating a board game with specified size

        this.playerX = playerX;
        this.playerO = playerO;
        if (winStreak > this.board.getSize() || winStreak < 2) {
            this._winStreak = this.board.getSize();
        } else {
            this._winStreak = winStreak;
        }
        this.renderer = renderer;
    }

    /**
     * returns the number of marks in a row to win
     *
     * @return the length of the win streak
     */
    public int getWinStreak() {
        return this._winStreak;
    }

    /**
     * return the size of the board
     *
     * @return the size of the board game
     */
    public int getBoardSize() {
        return this.board.getSize();
    }

    /**
     * chekcs if there's more abailable places to put marks on the board
     *
     * @return true if there's no more available places to put marks on the board
     */

    private boolean isFull() {
        return this.num_of_marks == getBoardSize() * getBoardSize();
    }

    /**
     * returns the max length of the sequence of marks of player (X ot O) in
     * horizontal direction
     *
     * @return the max length of the sequence of marks of player (X ot O)
     */
    private int searchHorizontalSequence(Mark mark) {
        int length = 0;
        int maxSequenceLength = 0;
        for (int row = 0; row < getBoardSize(); row++) {
            for (int col = 0; col < getBoardSize(); col++) {
                if (this.board.getMark(row, col) == mark) {
                    length++;
                    if (length > maxSequenceLength) {
                        maxSequenceLength = length;
                    }
                } else {
                    length = 0;
                }
            }
            length = 0;
        }
        return maxSequenceLength;
    }

    /**
     * returns the max length of the sequence of marks of player (X ot O) in
     * vertical direction
     *
     * @return the max length of the sequence of marks of player (X ot O)
     */
    private int searchVerticalSequence(Mark mark) {
        int length = 0;
        int maxSequenceLength = 0;
        for (int col = 0; col < getBoardSize(); col++) {

            for (int row = 0; row < getBoardSize(); row++) {
                if (this.board.getMark(row, col) == mark) {
                    length++;
                    if (length > maxSequenceLength) {
                        maxSequenceLength = length;
                    }
                } else {
                    length = 0;
                }
            }
            length = 0;
        }
        return maxSequenceLength;
    }

    /**
     * returns the max length of the sequence of marks of player (X ot O) in
     * sub-diagonal direction
     * search for diagonal sequence thsat is below the main diagonal
     * 
     * @return the max length of the sequence of marks of player (X ot O)
     */
    private int searchDiagonalRightSequenceDown(Mark mark) {
        // check diagonal from left to right (top to bottom)
        int length = 0;
        int maxSequenceLength = 0;
        for (int row = 0; row < getBoardSize(); row++) {
            length = 0;
            for (int col = 0; col < getBoardSize() - row; col++) {
                if (this.board.getMark(row + col, col) == mark) {
                    length++;
                    if (length > maxSequenceLength) {
                        maxSequenceLength = length;
                    }
                } else {
                    length = 0;
                }
            }
        }
        return maxSequenceLength;
    }

    /**
     * returns the max length of the sequence of marks of player (X ot O) in
     * sub-diagonal direction
     * search for diagonal sequence thsat is above the main diagonal
     *
     * @return the max length of the sequence of marks of player (X ot O)
     */

    private int searchDiagonalRighttSequenceUp(Mark mark) {
        // check diagonal from left to right (bottom to top)
        int length = 0;
        int maxSequenceLength = 0;
        for (int col = 0; col < getBoardSize(); col++) {
            length = 0;
            for (int row = 0; row < getBoardSize() - col; row++) {
                if (this.board.getMark(row, row + col) == mark) {
                    length++;
                    if (length > maxSequenceLength) {
                        maxSequenceLength = length;
                    }
                } else {
                    length = 0;
                }
            }
        }
        return maxSequenceLength;
    }

    /**
     * returns the max length of the sequence of marks of player (X ot O) in
     * sub-diagonal direction
     * search for diagonal sequence thsat is below the main diagonal
     * 
     * @return the max length of the sequence of marks of player (X ot O)
     */

    private int searchDiagonalLeftSequenceDown(Mark mark) {
        // check diagonal from right to left (top to bottom)
        int length = 0;
        int maxSequenceLength = 0;
        for (int row = 0; row < getBoardSize(); row++) {
            length = 0;
            for (int col = 0; col < getBoardSize() - row; col++) {
                if (this.board.getMark(row + col, getBoardSize() - 1 - col) == mark) {
                    length++;
                    if (length > maxSequenceLength) {
                        maxSequenceLength = length;
                    }
                } else {
                    length = 0;
                }
            }
        }
        return maxSequenceLength;
    }

    /**
     * returns the max length of the sequence of marks of player (X ot O) in
     * sub-diagonal direction
     * search for diagonal sequence thsat is above the main diagonal
     * 
     * @return the max length of the sequence of marks of player (X ot O)
     */
    private int searchDiagonalLeftSequenveUp(Mark mark) {
        // check diagonal from right to left (bottom to top)
        int length = 0;
        int maxSequenceLength = 0;
        for (int col = 0; col < getBoardSize(); col++) {
            length = 0;
            for (int row = 0; row < getBoardSize() - col; row++) {
                if (this.board.getMark(row, getBoardSize() - 1 - row - col) == mark) {
                    length++;
                    if (length > maxSequenceLength) {
                        maxSequenceLength = length;
                    }
                } else {
                    length = 0;
                }
            }
        }
        return maxSequenceLength;
    }

    /**
     * returns the max length of the sequence of marks of player X.
     * the function checks all the possible directions and returns the max length
     *
     * @return the max length of the sequence of marks of player X
     */
    private int getLengthOfSequencePlayerX_or_O(Mark mark) {

        int maxSequenceLength = 0;
        int searchHorizontalSequence = this.searchHorizontalSequence(mark);
        int searchVerticalSequence = this.searchVerticalSequence(mark);
        int searchDiagonalRightSequenveDown = this.searchDiagonalRightSequenceDown(mark);
        int searchDiagonalRighttSequenveUp = this.searchDiagonalRighttSequenceUp(mark);
        int searchDiagonalLeftSequenveDown = this.searchDiagonalLeftSequenceDown(mark);
        int searchDiagonalLeftSequenveUp = this.searchDiagonalLeftSequenveUp(mark);

        if (searchHorizontalSequence > maxSequenceLength) {
            maxSequenceLength = searchHorizontalSequence;
        }
        if (searchVerticalSequence > maxSequenceLength) {
            maxSequenceLength = searchVerticalSequence;
        }
        if (searchDiagonalRightSequenveDown > maxSequenceLength) {
            maxSequenceLength = searchDiagonalRightSequenveDown;
        }
        if (searchDiagonalRighttSequenveUp > maxSequenceLength) {
            maxSequenceLength = searchDiagonalRighttSequenveUp;
        }
        if (searchDiagonalLeftSequenveDown > maxSequenceLength) {
            maxSequenceLength = searchDiagonalLeftSequenveDown;
        }
        if (searchDiagonalLeftSequenveUp > maxSequenceLength) {
            maxSequenceLength = searchDiagonalLeftSequenveUp;
        }
        return maxSequenceLength;
    }

    /**
     * checks if the game is over - if there's a winner or if the board is full
     *
     * @return true if the game is over
     */
    private boolean isGameOver() {
        int streak_x = this.getLengthOfSequencePlayerX_or_O(Mark.X);
        int streak_O = this.getLengthOfSequencePlayerX_or_O(Mark.O);
        if (this.isFull() || streak_x == getWinStreak() || streak_O == getWinStreak()) {
            return true;
        }

        return false;

    }

    /**
     * runs the game single round
     *
     * @return the mark of the winner or blank if there's no winner
     */
    public Mark run() {
        while (!this.isGameOver()) {
            this.playerX.playTurn(this.board, Mark.X);
            this.num_of_marks++; // increase the number of marks on the board
            this.renderer.renderBoard(board);
            if (this.isGameOver()) {
                break;
            }
            this.playerO.playTurn(this.board, Mark.O);
            this.num_of_marks++; // increase the number of marks on the board
            this.renderer.renderBoard(board);

        }

        if (this.getLengthOfSequencePlayerX_or_O(Mark.X) == getWinStreak()) {
            return Mark.X;
        } else if (this.getLengthOfSequencePlayerX_or_O(Mark.O) == getWinStreak()) {
            return Mark.O;
        } else {
            return Mark.BLANK;
        }
    }

}
