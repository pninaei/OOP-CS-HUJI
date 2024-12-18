pnina_ei
212125678

Question_1: 
	i choose to implement it according to the first design which means i sent to BasicCollisionStrategy	
	constructor the gameObjects() instance of GameObjectCollection and by using it i remove the brick 
	from the collection.
	This design choice allows the BasicCollisionStrategy to directly access and manipulate the game objects
	collection, specifically removing the collided brick from the collection when a collision occurs. 
	the advantage of this approach is that this design allows for more flexibility in the future. If there
	are changes to the game manager or the structure of the game objects collection, the 
	BasicCollisionStrategy does not need to be modified as long as the interface of the GameObjectCollection
	remains consistent.
	the disadvantage of it that The strategies are dependents on the specific implementation of the 
	GameObjectCollection. If there are changes to the internal structure or behavior of the 
	GameObjectCollection in the future, it might require modifications to all the strategies as well.

Question_2: 
	i implemented the graphical life display as a class and that becuase the graphical life is part of the
	game objects. The numeric display is part of the game managar and the GameManager is responsible to 
	update it.

	in the BrickGameManager class:
	
	- numericLifeCounter(int num_of_life): This method updates the numeric representation of the player's 
	remaining lives. It removes the existing numeric representation and adds a new one based on the current
	number of lives. Different colors are used to indicate different levels of life.
	
	- graphicLifeCounter(ImageReader imageReader): This method creates the graphic representation of the
	player's life using heart symbols. It initializes a GraphicLifeCounter instance, which is a GameObject,
	and adds it to the UI layer. The graphic representation is updated dynamically based on changes in the
	player's life count.
	
	The graphicLifeCounter class:

	This class is responsible for handling the graphical representation of the player's life using heart
	symbols. It encapsulates the logic related to rendering and updating the visual aspect of the life
	counter. The medoths that the class has are:
	
	- GraphicLifeCounter Constructor: The constructor initializes the GraphicLifeCounter with the necessary
	parameters, such as dimensions, renderable (heart symbol), and counters. It also takes the maxLength 
	parameter, which represents the maximum number of hearts to display.
	
	- init(): This method initializes the life counter by creating heart GameObjects based on the initial 
	life count. It populates an array of heart GameObjects.
	
	- cratesHeart(int numHeart): This private method creates heart GameObjects based on the specified number.
	It adds each heart GameObject to the UI layer and sets its position accordingly.
	
	- removeHeart(int heartsToRemove): This private method removes heart GameObjects based on the specified
	number. It removes hearts from the UI layer and updates the tracking variables accordingly.
	
	- update(float deltaTime): This method is overridden to update the life counter dynamically based on
	changes in the player's life count. It compares the current capacity of hearts with the counterLife value
	and either adds or removes hearts accordingly.
	
Question_3: 

	The implementation of the basic is:

    BasicCollisionStrategy: This is the basic behavior that each brick has. no matter if ot has a special
	behavior.
    It receives a GameObjectCollection and uses it to remove the specific btick from the collection. and it
	also get a Counter that indicate how many bricks remained in the game. When onCollision is called,
    the number of bricks decrement by one and the brick gets delete from the game.

    The implementation of the special behaviors are:
	
	- PuckStrategy: This class responsiblefor creating 2 pucks ball. When onCollision is called, 2 new pucks
	are created and added to the game by calling the function cratePucks().
    
		* cratePucks(): crates 2 new puck balls and gives them random direction to appear in the game from
		the brick.
	
	- Puck: This class for the Pucks balls. its overrides onCollisionEnter to change it direction incase of
	collision with objects in the game.
	also, override the update method to be removed from the game when the ball is out of the window game.
    
    - ExtraPaddleStrategy: The class responsible to crate an Extra paddle in the middle of the window.
    When onCollision is called, a new paddleis created only if there is not another one yet on the board.
		
		* crateExtraPaddle(): A private method that crate the second paddle and locate it in the middle of
		the window game.
		
    - ExtraPaddle: A class for the Extra paddle. It inherits Paddle and overrides
    onCollisionEnter to remove itself from the game after 4 collisions with the balls (the main ball and the
	pucks ball).

    
    - CameraStrategy: This class responsible for changing the camera to focus on the main ball movement for
	period of time. It override onCollision and checks if there is not another camera that is turn on. if so,
	there won't be another camera triggering.
    

    - HeartStrategy: When onCollision is called, a new heart game object is created and added to the game.
    it override the onCollision and crate an heart that falls from the middle of the brick.
		
		* crateHeart(): Crate the heart objects to fall from the brick straight down.
    
	- Heart: A class for hearts that are dropped from bricks and increase the number of lives.
    shouldCollideWith is overridden so it would only collide with main paddle and not other objects in the
	game.
    onCollisionEnter is overridden so the heart will add life and remove itself from the game upon collision.

Question_4:
	
	To crate the Double strategy i implemented a DoubleStrategy class that gets 3 collision Strategy and it 
	override that method onCollision and activate the onCollision of each CollisionStategy. incase there's is
	2 special behaviors activate only theirs. but when a third special behaviors exist, also activate is.
	
	The design of the code for that behavior is as follow:
	When number 5 (the indicator the double strategy was selected) is randomize, we randomize again 2 special
	behaviors out of the 5 special behaviors (including double strategy).
	if, we got number 5 again we randomize 2 special behaviors again, but this time we choose 2 behavior out
	from 4 behaviors (excluding double strategy) for each.
	and for the third behavior we randomize one strategy out of 4 behaviors (excluding double strategy).
	
	if number 5 isn't selected another time, we gives the brick 2 special behaviors.
    To make sure that the are no more than 3 collision Strategies, i initialized 3 CollisionStrategy
	varibales.
	and check for each situation that can be when we randomizing special behaviors for each brick.
	incase there was only 2 special behaviors, the thirs varibal was set to be null.
	

	
	