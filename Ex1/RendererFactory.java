
public class RendererFactory {

    /**
     * Constructor for objects of class RendererFactory
     */
    public RendererFactory() {
    }

    /**
     * Create a renderer of a given type.
     * 
     * @param type - the type of the renderer
     * @param size - the size of the board
     * @return
     */
    public Renderer buildRenderer(String type, int size) {
        Renderer renderer;
        switch (type.toLowerCase()) {
            case "none":
                renderer = new VoidRenderer();
                break;
            case "console":
                renderer = new ConsoleRenderer(size);
                break;
            default:
                renderer = null;
                break;
        }
        return renderer;
    }

}
