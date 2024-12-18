import java.util.Scanner;

public class Chat {
    public static void main(String[] arg) {
        ChatterBot[] bots = new ChatterBot[2];

        // init bot1 with his array of responding to legal and illegal requests
        String[] illegal_respond_bot_1 = {"say what? " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST +
                "? what is " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + "?",
                "ask to say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + " again"};

        String[] legal_respond_bot_1 = {"say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "? okay: " +
                ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                "sure: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE};

        ChatterBot bot1 = new ChatterBot("Bot_Bobi1", legal_respond_bot_1, illegal_respond_bot_1);

        // init bot2 with his array of responding
        String[] illegal_respond_bot_2 = {"what to say? a " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + "?"
                , "sorry, say that again properly: say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST};

        String[] legal_respond_bot_2 = {"okay, here goes: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
        "Do i have too? ok ok: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE};

        ChatterBot bot2 = new ChatterBot("Bot_Dobi2", legal_respond_bot_2, illegal_respond_bot_2);

        bots[0] = bot1;
        bots[1] = bot2;

        Scanner scanner = new Scanner(System.in);
        String statement = "say hello";
        for (int i = 0; ; i = (i + 1) % bots.length) {

                statement = bots[i].replyTo(statement);
                System.out.print(bots[i].getName() + ": " + statement);
                scanner.nextLine();
        }
    }
}

