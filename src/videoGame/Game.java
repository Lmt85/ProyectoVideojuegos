
package videoGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import maths.Vector2;

/**
 * Here lies all objects that belong to a particular instance of a Game, 
 * including the window (Display) with it corresponding Graphics and BufferStrategy,
 * keyManager, and mouseManager, as well as the thread were the game operates 
 * and as such this class implements the Runnable interface so it can override 
 * the run() method in order to interact with the thread in such a way that the 
 * graphics are painted every tick.
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class Game implements Runnable, Commons {
    //Necessary graphic objects 
    private BufferStrategy bs;  // to have several buffers when displaying in a canvas
    private Graphics g;         // to paint objects in the display canvas
    
    //display stuff
    private Display display;    // to display in the game in its canvas
    String title;               // title of the window
    private int height;
    private int width;
    private KeyManager keyManager;  //key manager asociated with the display
    private MouseManager mouseManager; //mouse manager asociated with the display
    Font font; // font used to display the score and game over message
    
    //thread stuff
    private Thread thread;      // thread to create the game. points to the instance of this Game as a Runnable
    private boolean running;    // to set the game running status (controls the in thread execution)
    private double tps; //ticks per second
    private final boolean showTPS = true; //controls if the tps will be show on the console
    int fps = 60; //max frames per second the game will run at
    
    //Game objects
    Player player; // to store the player
    Shot shot; // to store the shot
//    AlienManager alienManager; // to manage each alien
    
    //Game state
    private boolean paused = false; // states whether or not the game is paused
    private boolean pauseTrig = false;
    private int gameState = 0; // 0 -> game playing, -1 -> game lost, 1 -> game won
    
    //Save state
    private String fileName;    // Save filename
    private String[] arr;       // To load the game objects
    
    //Internal game atributes
    public String message;  // Stores endgame message

    //Sound
    SoundClip music;    // Stores looping music
    SoundClip laser;    // Stores the laser sound
    SoundClip alienOof; // Stores alien death sound
    SoundClip dead;     // Stores player death sound
    /**
    * to create title, width and height and set the game is still not running
    * @param title to set the title of the window
    * @param width to set the width of the window
    * @param height  to set the height of the window
    */
    public Game() {
        this.title = "SpaceInvaders";   //Name of the frame
        
        // Sets game dimensions
        this.width = Commons.BOARD_WIDTH;
        this.height = Commons.BOARD_HEIGHT;
        running = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        font = new Font("consolas", Font.BOLD, 10);
        //Please do not initialize game objects here, those go on the init method
    }
    
    /**
     * returns the width of the display
     * @return integer width
     */
    public int getWidth() {
        return width;
    }

    /**
     * returns the height of the display
     * @return integer height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Override of the Runnable's ()run method.
     * Were all code to be executed cyclically by the game thread lies
     */
    @Override
    public void run() { 
        init(); //display initialization
        
        //time for  each tick in nanoseconds, 
        //ejm: at 50fps each tick takes 0.01666_ seconds wich is equal to 16666666.6_ nanoseconds
        double timeTick = 1000000000 / fps;
        double delta = 0; 
        long now; //current frame time
        long lastTime = System.nanoTime(); //the previos frame time 
        double initTickTime = lastTime;
        while (running) {            
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            //delta acumulates enogh tick fractions until a tick is completed and we can now advance in the tick
            if(delta >= 1){
                
                tick();
                render();
                delta--;
                
                tps = 1000000000 / (now - initTickTime);
                if(showTPS){System.out.println("tps: " + tps);}
                initTickTime = now;
            }  
        }
        stop();
    }
    
    /**
    * setting the thread for the game. Method to be publicly used by the implementer of this Game.
    */
    public synchronized void start(){
        if(!running){
            running = true;
            thread = new Thread(this); //points thread to this Game instance as a Runnabe so we can use run() as overriden.
            thread.start(); //Thread class mathod that starts the tread
        }
    }
    
    /**
    * stopping the thread for the game.
    */
    public synchronized void stop(){
        if(running){
            running = false;
            try{
                thread.join();
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }
    
    /**
     * provides the key manager of this game
     * @return This game and display <b>keyManager</b>
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    /**
     * provides the mouse manager of this game
     * @return This game and display <b>mouseManager</b>
     */
    public MouseManager getMouseManager() {
        return mouseManager;
    }
    
    
    /**
     * rendering of all game elements.
     */
    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        
        /* if it is null, we define one with 3 buffers to display images of
         * the game, if not null, then we display
         * every image of the game but
         * after clearing the Rectanlge, getting the graphic object from the 
         * buffer strategy element. 
         * show the graphic and dispose it to the trash system
         */
        if (bs == null) { //if we dont have a buffer strategy we make our Display's canvas crate one for itself.
            display.getCanvas().createBufferStrategy(3);
        }
        else
        {            
            g = bs.getDrawGraphics(); // gets the graphics of the buffer strategy
            
            g.setColor(Color.GREEN);   // sets the painting color to green
            g.setFont(font);           // sets the font
            
            g.drawImage(Assets.background, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);  //paints the background
            
            //game objects to render
            player.render(g);   //renders the player
            shot.render(g);     //renders the shot if visible
//            alienManager.render(g); //renders the aliens if visible
            
            //game state renders
            if(gameState != 0){
                if(gameState == -1){//lost screen
                    g.drawString(message, Commons.BOARD_WIDTH/2 - 50, Commons.BOARD_HEIGHT/2);
                }else if(gameState == 1){//won screen
                    g.drawString(message, Commons.BOARD_WIDTH/2 - 50, Commons.BOARD_HEIGHT/2);
                }
            }else if(paused){ //triggers if paused and playing, displays paused message
                g.drawString("<PAUSED>", 10, 10);
            }
            if(keyManager.save) { //displays saved message if saved
                g.drawString("<SAVED>", Commons.BOARD_WIDTH - 100, 10);
            }
            
            //displays the amount of aliens destroyed
//            g.drawString("Destroyed: " + alienManager.destroyed + "/24", 10, Commons.BOARD_HEIGHT - 10);
            
            bs.show();
            g.dispose();
        }
    }
    
    /**
    * initializing the display window of the game
    * asset loading
    * Game objects initialization
    */
    private void init() {
        // Initializes the display with the given dimensions
        display = new Display(title, width, height); 
        
        // initializes all the assets of the game
        Assets.init(); 
        
        // Loads all the sound related assets
        music = new SoundClip("/sound/DarkIntentions.WAV");
        music.setLooping(true);
        
        //Creates and initializes game objects
        player = new Player(new Vector2(Commons.START_X,Commons.START_Y), new Vector2(),true,Commons.PLAYER_WIDTH,Commons.PLAYER_HEIGHT,Assets.player, this);
        shot = new Shot(new Vector2(0,0), new Vector2(0,-4), false, 2, 5, Assets.shot, this, player);
//        alienManager = new AlienManager(this);
        
        player.init();
        shot.init();
//        alienManager.init();
        
        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }
    
    /**
     * code to be executed every tick before render()
     */
    private void tick(){
//        if (alienManager.destroyed == NUMBER_OF_ALIENS_TO_DESTROY) { 
//            /*
//                Will set gamestate as 1, which means the player won
//                this happens when all aliens are destroyed
//            */
//            setGameState(1);
//            message = "Game won!";
//        }
        
        keyManager.tick(); //key manager must precede all user ralated actions
        if(!paused && gameState == 0){ //game playing and not paused
            player.tick(); // ticks the player
            shot.tick();   // ticks the shot
//            alienManager.tick(); // ticks the manager
            
            //Checks collision between aliens and shot
//            checkShotCollision();
//            checkBombCollision();
        }
        
        
        if(keyManager.save && gameState == 0) {
            /**
             * will save the game if the game is playing and g key is pressed
             */
            saveGame("save.txt");
        }
        
        if(keyManager.load && gameState == 0) {
            /**
             * will load the game if playing and c key is pressed
             */
            loadGame("save.txt");
        }
        
        if(keyManager.restart) {
            /**
             * Restarts the music, sets the gameState as playing and loads the game
             */
            music.play();
            setGameState(0);
            loadGame("restartGame.txt");
        }
        
        //Triggers the pause of the game when 'P' is pressed
        if(!paused && keyManager.paused && !pauseTrig){
            paused = true;
        }else if(paused && keyManager.paused && !pauseTrig){
            paused = false;
        }
        if(keyManager.paused){
            pauseTrig = true;
        }else{
            pauseTrig = false;
        }
    }
    
    /**
     * Method that saves the current game, including each of the object's important attributes
     * @param fileName 
     */
    private void saveGame(String fileName) {
//        try {
//            FileWriter fw = new FileWriter(fileName); //FileWriter to write each line in fileName path
//            fw.write(player.toString());   //Inserts the player object
//            fw.write(shot.toString());      //Inserts the shot object
//            fw.write(alienManager.toString());//Inserts the alienManager direction and destroyd qty
//            for(Alien a : alienManager.aliens){
//                fw.write(a.toString()); //Inserts each alien in the alienManager
//                fw.write(a.getBomb().toString()); //Inserts each alien's bomb
//            }
//            fw.close(); //Finishes writing in the file and closes it
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
    }
    
    /**
     * Method that loads a game with the param's path
     * @param fileName 
     */
    private void loadGame(String fileName) {
//        try {
//            //clears the aliens list
//            alienManager.aliens = new ArrayList<>();    //Empties the alien array
//            BufferedReader br = new BufferedReader(new FileReader(fileName));   //To Read each line
//            
//            //Reads player data
//            String data = br.readLine();
//            arr = data.split(" ");
//            player.position.setX(Double.parseDouble(arr[0]));   //stores the x coordinate
//            player.position.setY(Double.parseDouble(arr[1]));   //stores the y coordinate
//            player.setVisible(Boolean.parseBoolean(arr[2]));    //stores the visibility
//            player.setDx(Integer.parseInt(arr[3]));             //stores the dx
//            
//            //Reads shot data
//            data = br.readLine();
//            arr = data.split(" ");
//            shot.position.setX(Double.parseDouble(arr[0]));     //stores the x coordinate
//            shot.position.setY(Double.parseDouble(arr[1]));     //stores the y coordinate
//            shot.setVisible(Boolean.parseBoolean(arr[2]));      //stores the visibility
//            
//            //Reads direction and destroyed
//            data = br.readLine();
//            arr = data.split(" ");
//            alienManager.setDirection(Integer.parseInt(arr[0]));
//            alienManager.setDestroyed(Integer.parseInt(arr[1]));
//            
//            //Reads alien and bomb data
//            data = br.readLine();
//            Alien a;
//            while(data != null) {
//                arr = data.split(" ");
//                // creates an alien instance with the read values x, y and visibility
//                a = new Alien(Double.parseDouble(arr[0]),Double.parseDouble(arr[1]), Boolean.parseBoolean(arr[2]), Commons.ALIEN_WIDTH,Commons.ALIEN_HEIGHT,Assets.alien);
//                
//                data = br.readLine();
//                arr = data.split(" ");
//                a.getBomb().position.setX(Double.parseDouble(arr[0]));  //stores the x coordinate
//                a.getBomb().position.setY(Double.parseDouble(arr[1]));  //stores the y coordinate
//                a.getBomb().setVisible(Boolean.parseBoolean(arr[2]));   //stores the visibility
//                
//                alienManager.aliens.add(a); //adds the created alien to the alienManager's array
//                data = br.readLine();
//            }
//            
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Returns the current gamestate.
     * @return 0 for playing, -1 if lost, 1 if won
     */
    public int getGameState() {
        return gameState;
    }

    /**
     * Sets the current gamestate to the introduced value in the param
     * @param gameState 
     */
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }
    
    /**
     * Method that checks the collision between the shot and an alien
     */
    public void checkShotCollision() {
//        if(shot.isVisible()) {
//            int shotX = (int)shot.position.getX();  //Stores the shot's current x position
//            int shotY = (int)shot.position.getY();  //Stores the shot's current y position
//            for(Alien a : alienManager.aliens) { //For each alien in the array
//                if (a.isVisible() ) {            // If the alien is visible
//                    int alienX = (int)a.position.getX(); 
//                    int alienY = (int)a.position.getY();
//                    //checks if the alien contains the shot
//                    if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH)    
//                            && shotY >= alienY && shotY <= (alienY + ALIEN_HEIGHT)) {
//                        shot.setVisible(false); // makes shot invisible
//                        a.setVisible(false);    // makes collisioned alien invisible
//                        alienOof.stop();        // stops the current sound
//                        alienOof.play();        // plays alien death sound
//                        alienManager.destroyed++;// adds 1 to the destroyed alien count
//                    }
//                }
//            }
//        }
    }
    
    /**
     * Method that checks the collision between the bomb and the player
     */
    public void checkBombCollision() {
//        for(Alien a : alienManager.aliens) { // For each alien's bomb
//            Alien.Bomb b = a.getBomb();
//            int bombX = (int)b.position.getX();
//            int bombY = (int)b.position.getY();
//            int playerX = (int)player.position.getX();
//            int playerY = (int)player.position.getY();
//
//            if (b.isVisible()) {    // if the bomb exists/isVisible
//
//                if (bombX >= (playerX)  //it checks if the bomb is contained within the player
//                        && bombX <= (playerX + PLAYER_WIDTH)
//                        && bombY >= (playerY)
//                        && bombY <= (playerY + PLAYER_HEIGHT)) {
//                    player.setVisible(false);   //Sets the player invisible
//                    b.setVisible(false);        //Sets the bomb invisible
//                    setGameState(-1);           //Sets the gamestate as lost
//                    message = "Aliens got you!";//Sets losing message
//                    music.stop();               //Stops the music
//                    dead.play();                //Plays player death sound
//                }
//            }
//        }
    }
}
