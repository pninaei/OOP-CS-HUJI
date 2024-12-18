import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 * @author Dan Nirel
 */
class ChatterBot {
	/**
	 * REQUEST_PREFIX - represent the prefix of a legal respond
	 * PLACEHOLDER_FOR_REQUESTED_PHRASE - a pattern which will be replaced by a response in legal sentence
	 * PLACEHOLDER_FOR_ILLEGAL_REQUEST - a pattern which will be replaced by a response in illegal sentence
	 */
	static final String REQUEST_PREFIX = "say ";
	static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
	static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";
	Random rand = new Random();
	String name;
	String[] repliesToIllegalRequest;
	String[] legalRequestsReplies;

	/**
	 * A contracture that constructs a Chatterbot with a name and two array of possible answers to reply
	 * base on the legality of the statement
	 * @param name - the name of the ChatterBot
	 * @param legalRequestsReplies - array of possible answers when the statement is legal
	 * @param repliesToIllegalRequest - array of possible answers when the statement is illegal
	 */
	ChatterBot(String name, String[] legalRequestsReplies, String[] repliesToIllegalRequest) {
		// for illegal requests
		this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
		for(int i = 0 ; i < repliesToIllegalRequest.length ; i = i+1) {
			this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
		}
		// for legal requests
		this.legalRequestsReplies = new String[legalRequestsReplies.length];
		for (int i = 0; i < legalRequestsReplies.length; i++) {
			this.legalRequestsReplies[i] = legalRequestsReplies[i];

		}
		this.name = name;
	}

	/**
	 * the function returns the name of the bot
	 */
	String getName(){
		return this.name;
	}

	/**
	 * the function gets a statement to reply to and returns a new statement according to the legality of
	 * the given statement
	 * @param statement - the given string statement
	 * @return new statement to reply
	 */
	String replyTo(String statement) {

		if(statement.startsWith(REQUEST_PREFIX)) {
			return replyToLegalRequest(statement);
		}
		return replyToIllegalRequest(statement);
	}

	/**
	 * the function returns a response to a legal request
	 * @param statement - a legal statement
	 * @return new statement without the prefix word
	 */
	String replyToLegalRequest(String statement){
		//we don’t repeat the request prefix, so delete it from the reply
		String phrase =  statement.replaceFirst(REQUEST_PREFIX, "");
		return replacePlaceholderInARandomPattern(legalRequestsReplies,
				ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE, phrase);
	}
	/**
	 * the function returns a response to an illegal request
	 */
	String replyToIllegalRequest(String statement) {
		return replacePlaceholderInARandomPattern(repliesToIllegalRequest,
				ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST, statement);
	}
	/**
	 * the function gets array of possible answers to respond back, a placeholder pattern which be
	 * replaced and a statement that will replace the placeholder everywhere in the statement that was
	 * picked randomly
	 * @param responds - an array of possible answer to responds back
	 * @param what_to_replace - is a placeholder pattern that will be replaced
	 * @param what_to_put - is the word that replace the placeholder
	 * @return a new sentence with the statement is placed instead if the placeholder
	 */
	String replacePlaceholderInARandomPattern(String[] responds, String what_to_replace, String what_to_put){
		int randomIndex = rand.nextInt(responds.length);
		String reply = responds[randomIndex];
		return reply.replaceAll(what_to_replace, what_to_put);
	}
}
