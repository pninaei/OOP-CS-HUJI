import java.util.Arrays;

/**
 * Tournament class - represents a tournament of Tic-Tac-Toe games between
 * players
 * the tournament class runs a series of games between two players with given
 * renderer.
 * in the first round, the first player plays with X mark and the second player
 * plays with O mark and
 * in the end of each game the players switch marks. such that in the rounds
 * with even index the first player
 * is with X mark and in the rounds with odd index the first player is with O
 * mark.
 */

public class Tournament {

    /**
     * the fields of the tournament class:
     * player1 - the first player
     * player2 - the second player
     * renderer - the renderer to use in the tournament
     * rounds - number of rounds in the tournament to run
     */

    private Player player1;
    private Player player2;
    private Renderer renderer;
    private int rounds;

    /**
     * constructor the tournament
     * 
     * @param rounds   - number of rounds in the tournament to run
     * @param renderer - the renderer to use in the tournament
     * @param player1  - the first player
     * @param player2  - the second player
     */

    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * this method runs a full tournament
     * 
     * @param size        - the size of the board
     * @param winStreak   - the number of marks in a row to win
     * @param playerName1 - the name of the first player
     * @param playerName2 - the name of the second player
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {

        int player1Wins = 0;
        int player2Wins = 0;
        int draw = 0;

        for (int i = 0; i < rounds; i++) {
            Player firstPlayer;
            Player secondrPlayer;

            if (i % 2 == 0) {
                firstPlayer = player1;
                secondrPlayer = player2;
            } else {
                firstPlayer = player2;
                secondrPlayer = player1;
            }
            Game game = new Game(firstPlayer, secondrPlayer, size, winStreak, renderer);
            Mark winner = game.run();
            if (winner == Mark.X) {
                if (firstPlayer == player1) {
                    player1Wins++;
                } else {
                    player2Wins++;
                }
            } else if (winner == Mark.O) {
                if (firstPlayer == player1) {
                    player2Wins++;
                } else {
                    player1Wins++;
                }
            } else { // noone won
                draw++;
            }

        }
        System.out.println("######### Results #########");
        System.out.println("Player 1, " + playerName1 + " won: " + player1Wins + " rounds");
        System.out.println("Player 2, " + playerName2 + " won: " + player2Wins + " rounds");
        System.out.println("Ties: " + draw);
    }

    /**
     * main method - runs the tournament
     * 
     * @param args - the arguments from the command line
     */

    public static void main(String[] args) {

        String[] validPlayerNames = { "human", "clever", "whatever", "genius" };

        int round_count = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);

        String rendererName = args[3];
        String playerName1 = args[4];
        String playerName2 = args[5];

        if (!(rendererName.toLowerCase().equals("console") ||
                rendererName.toLowerCase().equals("none"))) {
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return;
        } else if (!Arrays.asList(validPlayerNames).contains(playerName1.toLowerCase())) {
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return;
        } else if (!Arrays.asList(validPlayerNames).contains(playerName2.toLowerCase())) {
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return;
        } else {
            PlayerFactory playerFactory = new PlayerFactory();
            Player player1 = playerFactory.buildPlayer(playerName1.toLowerCase());
            Player player2 = playerFactory.buildPlayer(playerName2.toLowerCase());

            RendererFactory rendererFactory = new RendererFactory();
            Renderer renderer = rendererFactory.buildRenderer(rendererName, size);
            Tournament tournament = new Tournament(round_count, renderer, player1, player2);

            tournament.playTournament(size, winStreak, playerName1.toLowerCase(), playerName2.toLowerCase());
            return;

        }

    }

}
