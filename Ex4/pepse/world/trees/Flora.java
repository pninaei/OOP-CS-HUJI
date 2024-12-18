package pepse.world.trees;

import java.awt.Color;
import java.util.function.IntFunction;
import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;
import pepse.world.Block;
import java.util.ArrayList;
import java.util.Random;
import pepse.world.trees.JumpingSubject;

/**
 *
 * The Flora class represents the plant life in the game world, including trees with trunks,
 * leaves, and fruits. It manages the generation and behavior of trees within the game world.
 * Trees consist of trunks, leaves, and fruits, each represented by GameObjects.
 * The class implements the JumpObserver interface, allowing it to respond to avatar jumps.
 */
public class Flora implements JumpObserver {

    private static final double TREE_PLANT_PROBABILITY = 0.1;
    private static final double LEAVES_DENSITY_THRESHOLD = 0.3;
    private static final double FRUIT_PLANT_PROBABILITY = 0.5;
    private static final Color TRUNK_COLOR = new Color(100, 50, 20);
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final int GAP_BETWEEN_LEAVES = 2;
    private static final int LEAF_ROWS = 5;
    private static final int LEAF_COLUMNS = 5;
    private static final int TRUNK_HEIGHT_BOUND = 5;
    private static final int FACTOR_MULTIPLIER_LEAF = 3;
    private static final Color INIT_FRUIT_COLOR = Color.ORANGE;
    private final IntFunction<Float> groundHeightAtX;
    private final Avatar avatar;
    private final Random random;
    private ArrayList<ArrayList<Block>> listOfTrees;
    /**
     * The tag for the trunk GameObject.
     */
    public static final String TRUNK_TAG = "trunk";
    /**
     * The tag for the leaf GameObject.
     */
    public static final String LEAF_TAG = "leaf";


    /**
     * Constructs a Flora object with the specified ground height function and avatar.
     *
     * @param groundHeightAtX The ground height function.
     * @param avatar          The avatar.
     */
    public Flora(IntFunction<Float> groundHeightAtX, Avatar avatar) {

        this.groundHeightAtX = groundHeightAtX;
        this.avatar = avatar;
        this.random = new Random();
        this.listOfTrees = new ArrayList<>();
    }

    /**
     * Creates trees within the specified range of x-coordinates.
     *
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return An ArrayList containing the GameObjects representing the created trees.
     */
    public ArrayList<GameObject> createInRange(int minX, int maxX) {
        ArrayList<GameObject> trees = new ArrayList<>();

        // Iterate over the range of x-coordinates
        for (int x = minX; x <= maxX; x += Block.SIZE) {
            // Check probability for tree generation
            if (random.nextDouble() < TREE_PLANT_PROBABILITY) {
                int groundHeight = groundHeightAtX.apply(x).intValue();
                int trunkHeight = random.nextInt(TRUNK_HEIGHT_BOUND) + TRUNK_HEIGHT_BOUND - 1;

                // Skip creating tree if avatar's position overlaps
                if (x == avatar.getAvatarLocation().x()) {
                    continue;
                }

                // Create the tree and add it to the list of trees
                ArrayList<Block> tree = createTree(x, trunkHeight, groundHeight, trees);
                createLeaves(x, trunkHeight, groundHeight, trees); // Create leaves for the tree
                listOfTrees.add(tree);
            }
        }
        return trees;
    }

    /**
     * Creates a tree at the specified x-coordinate.
     *
     * @param x            The x-coordinate.
     * @param trunkHeight  The height of the trunk.
     * @param groundHeight The ground height.
     * @param trees        The list of GameObjects representing trees.
     * @return An ArrayList containing the GameObjects representing the created tree.
     */
    private ArrayList<Block> createTree(int x, int trunkHeight, int groundHeight,
                                        ArrayList<GameObject> trees) {
        ArrayList<Block> singleTree = new ArrayList<>();
        Renderable trunk = new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR));

        // Create the trunk blocks and add them to the list
        for (int i = 0; i < trunkHeight; i++) {
            int posY = groundHeight - (trunkHeight * Block.SIZE) + (i * Block.SIZE);
            Vector2 position = new Vector2(x, posY);
            Block block = new Block(position, trunk);
            block.setTag(TRUNK_TAG);
            trees.add(block);
            singleTree.add(block);
        }
        return singleTree;
    }


    /**
     * Creates leaves at the top of the tree at the specified x-coordinate.
     *
     * @param x            The x-coordinate.
     * @param trunkHeight  The height of the trunk.
     * @param groundHeight The ground height.
     * @param trees        The list of GameObjects representing trees.
     */
    private void createLeaves(int x, int trunkHeight, int groundHeight,
                              ArrayList<GameObject> trees) {
        // Calculate the starting position for leaves
        int leafY = groundHeight - (trunkHeight * Block.SIZE) -
                (FACTOR_MULTIPLIER_LEAF * Block.SIZE) -
                (FACTOR_MULTIPLIER_LEAF * GAP_BETWEEN_LEAVES);
        int leafX = x - (2 * Block.SIZE) - (2 * GAP_BETWEEN_LEAVES);

        // Iterate to create rows of leaves
        for (int i = 0; i < LEAF_ROWS; i++) {
            // Iterate to create columns of leaves
            for (int j = 0; j < LEAF_COLUMNS; j++) {
                // Calculate the position for each leaf
                Vector2 loc = new Vector2(leafX + (j * (Block.SIZE + GAP_BETWEEN_LEAVES)),
                        leafY + (i * (Block.SIZE + GAP_BETWEEN_LEAVES)));

                // Check if a leaf should be created based on density threshold
                if (random.nextDouble() > LEAVES_DENSITY_THRESHOLD) {
                    Renderable leafRenderable = new RectangleRenderable(
                            ColorSupplier.approximateColor(LEAF_COLOR));
                    Leaf leaf = new Leaf(loc, leafRenderable);
                    avatar.registerJumpObserver(leaf); // Register leaf as a jump observer
                    leaf.scheduledTaskTransitions();
                    leaf.setTag(LEAF_TAG);
                    trees.add(leaf);
                }
                // Create fruits if conditions are met
                else if (i < 3 && j != 2) {
                    createFruits(loc, trees);
                }
            }
        }
    }

    /**
     * Creates fruits at the specified location if the probability condition is met.
     *
     * @param loc   The location for creating fruits.
     * @param trees The list of GameObjects representing trees.
     */
    private void createFruits(Vector2 loc, ArrayList<GameObject> trees) {
        // Check if the probability condition for fruit creation is met
        if (random.nextDouble() > FRUIT_PLANT_PROBABILITY) {
            Renderable fruitImg = new OvalRenderable(ColorSupplier.
                    approximateColor(INIT_FRUIT_COLOR));
            Fruit fruit = new Fruit(loc, new Vector2(Block.SIZE, Block.SIZE), fruitImg,
                    avatar::setEnergy);
            avatar.registerJumpObserver(fruit); // Register fruit as a jump observer
            fruit.setTag(Fruit.FRUIT_TAG);
            trees.add(fruit);
        }
    }

    /**
     * When the avatar is jumping, the color of the trunk changes to a different shade of brown.
     * The color change occurs for all trees in the list of trees.
     */
    @Override
    public void onJump() {
        Random random = new Random();
        for (ArrayList<Block> tree : listOfTrees) {
            // Generate random RGB values within a certain range for brown shades
            int red = random.nextInt(50) + 100; // Random value between 100 and 150
            int green = random.nextInt(40) + 50; // Random value between 50 and 90
            int blue = random.nextInt(1); // Random value between 0 and 1
            for (Block block : tree) {
                // Create a color representing a different shade of brown
                Color trunkChangeColor = new Color(red, green, blue);
                // Set the new color to the trunk renderer
                block.renderer().setRenderable(new RectangleRenderable(trunkChangeColor));
            }
        }
    }
}
