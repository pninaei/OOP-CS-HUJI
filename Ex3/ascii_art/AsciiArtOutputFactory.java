package ascii_art;
import ascii_output.*;

/**
 * A factory for creating ASCII art output objects.
 */
public class AsciiArtOutputFactory {
    
    /**
     * Creates an ASCII art output object based on the specified type and parameters.
     *
     * @param type_of_output The type of output to be created.
     * @param params         The parameters to be used for creating the output.
     * @return The created ASCII art output object.
     */	
    public AsciiOutput createAsciiOutput(String type_of_output, String[] params) {
        
        AsciiOutput asciiOutput;
        switch (type_of_output) {
            case "console":
                asciiOutput = new ConsoleAsciiOutput();
                break;
                case "html":
                asciiOutput = new HtmlAsciiOutput(params[0], params[1]);
                break;
        
            default:
                asciiOutput = null;
        }
        return asciiOutput;
    }

    
}
