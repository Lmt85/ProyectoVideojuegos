
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
public class Game implements Runnable{
    //Necessary graphic objects 
    private BufferStrategy bs;  // to have several buffers when displaying in a canvas
    private Graphics g;         // to paint objects in the display canvas
    //display stuff
    private Display display;    // to display in the game in its canvas
    String title;               // title of the window
    final private int width;          // width of the window
    final private int height;         // height of the window
    private KeyManager keyManager;  //key manager asociated with the display
    private MouseManager mouseManager; //mouse manager asociated with the display
    
    //thread stuff
    private Thread thread;      // thread to create the game. points to the instance of this Game as a Runnable
    private boolean running;    // to set the game running status (controls the in thread execution)
    private double tps; //ticks per second
    private final boolean showTPS = false; //controls if the tps will be show on the console
    int fps = 60; //max frames per second the game will run at
    
    //Game objects
    Player player;
    Ball ball;
    BrickManager brickManager;
    
    //Game state
    private boolean paused = false;
    private boolean pauseTrig = false;
    private int gameState = 0; // 0 -> gamae playing, -1 -> game lost, 1 -> game won
    
    Font font; // font used to display the score and game over message
    
    //Save state
    private String fileName;    // Save filename
    private String[] arr;       // To load the player and ball
    
    /**
    * to create title, width and height and set the game is still not running
    * @param title to set the title of the window
    * @param width to set the width of the window
    * @param height  to set the height of the window
    */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        font = new Font("consolas", Font.BOLD, 60);
        fileName = "bbSave.txt";
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
            g = bs.getDrawGraphics();
            
            g.setColor(Color.GREEN);
            g.setFont(font);
            
            //game objects render
            g.drawImage(Assets.background, 0, 0, width, height, null);
            brickManager.render(g);
            player.render(g);
            ball.render(g);
            
            //game state options
            if(gameState != 0){
                if(gameState == -1){//lost
                    g.drawString("Game Over", width / 2 - 160, height / 2);
                }else if(gameState == 1){//won
                    g.drawString("You Won", width / 2 - 130, height / 2);
                }
                g.drawString("Press <SPACE> to restart", width / 2 - 420, height - 200);
            }
            if(paused){
                g.drawString("<PAUSED>", 0, 40);
            }
            if(keyManager.save) {
                
                g.drawString("<SAVED>", width - 260, 40);
            }
            
            g.drawString("Lives: " + player.lives, width - 350, height - 10);
            
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
        display = new Display(title, width, height); 
        
        Assets.init(); 
        
        //create and init game objects
        player = new Player(width / 2, 200, 150, 10, this);
        ball = new Ball(50, 50, this);
        brickManager = new BrickManager(this, width / 10, 50 ,4, 150);
                
        player.init(ball);
        ball.init(player);
        brickManager.init(ball, player);
        
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
        keyManager.tick(); //key manager must precede all user ralated actions
        
        //if the game is paused or the game state is not 0 (playing) the game object wont tick
        if(!paused && gameState == 0){
            player.tick();
            ball.tick();
            brickManager.tick();

        }else{
            /**
             * if the game state is diferent from zero and the user press the 
             * space bar, the game would reset by executing the reboot method 
             * of all game objects
             */
            if(keyManager.keys[KeyEvent.VK_SPACE] && gameState != 0){
                gameState = 0;
                player.reboot();
                ball.reboot();
                brickManager.reboot();
            }
        }       
        //Game state updates
        if(player.lives <= 0){
            gameState = -1; //lost
        }
        if(brickManager.bricks.isEmpty()){
            gameState = 1;
        }
        
        //Save the game
        if(keyManager.save) {
            
            saveGame();
        }
        
        if(keyManager.load  && gameState == 0) {
            loadGame();
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
    
    private void saveGame() {
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(player.toString());
            fw.write(ball.toString());
            for(Brick b : brickManager.bricks){
                fw.write(b.toString());
            }
            fw.write("====\n");
            for(FallingItem fi : brickManager.items){
                fw.write(fi.toString());
            }
            fw.write("====\n");
            
            fw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadGame() {
        try {
            //clears the brick arraylist
            brickManager.bricks = new ArrayList<>();
            brickManager.items = new ArrayList<>();
            
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            
            //Reads player data
            String data = br.readLine();
            arr = data.split(" ");
            player.position.setX(Double.parseDouble(arr[0]));
            player.position.setY(Double.parseDouble(arr[1]));
            player.setWidth(Integer.parseInt(arr[2]));
            player.setHeight(Integer.parseInt(arr[3]));
            player.setLives(Integer.parseInt(arr[4]));
            player.setLaunched(Boolean.parseBoolean(arr[5]));
            
            //Reads ball data
            data = br.readLine();
            arr = data.split(" ");
            ball.position.setX(Double.parseDouble(arr[0]));
            ball.position.setY(Double.parseDouble(arr[1]));
            Vector2 auxVec = new Vector2(Double.parseDouble(arr[2]), Double.parseDouble(arr[3]));
            ball.setVelocity(auxVec);
            ball.setWidth(Integer.parseInt(arr[4]));
            ball.setHeight(Integer.parseInt(arr[5]));
            
            //Reads brick data and adds it to the BrickManager bricks arraylist
            data = br.readLine();
            Brick b;
            while(!data.equals("====")) {
                arr = data.split(" ");
                b = new Brick(Double.parseDouble(arr[0]),Double.parseDouble(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[3]),Integer.parseInt(arr[4]), ball);
                brickManager.bricks.add(b);
                data = br.readLine();
            }
            
            //Reads falling items data and adds it to the BrickManager items arraylist
            data = br.readLine();
            FallingItem fi;
            while(!data.equals("====")){
                arr = data.split(" ");
                fi = new FallingItem(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), Integer.parseInt(arr[2]), this);
                brickManager.items.add(fi);
                data = br.readLine();
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
