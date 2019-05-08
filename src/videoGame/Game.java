package videoGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Font;
import java.awt.Graphics2D;
import static java.lang.Math.abs;
import javax.swing.JOptionPane;
import maths.Vector2;

/**
 * Here lies all objects that belong to a particular instance of a Game,
 * including the window (Display) with it corresponding Graphics and
 * BufferStrategy, keyManager, and mouseManager, as well as the thread were the
 * game operates and as such this class implements the Runnable interface so it
 * can override the run() method in order to interact with the thread in such a
 * way that the graphics are painted every tick.
 *
 * @author Adrián Marcelo Suárez Ponce A01197108
 * @date 28/01/2019
 * @version 1.0
 */
public class Game implements Runnable, Commons {

    //Necessary graphic objects
    private BufferStrategy bs;  // to have several buffers when displaying in a canvas
    private Graphics g;         // to paint objects in the display canvas

    //Display variables
    private Camera camera;      // to create a camera that follows the player
    private Display display;    // to display in the game in its canvas
    private String title;               // title of the window
    private String playerid;            // to store the playerid
    private String playerName;          // to store the player name
    private int height;         // height og the window
    private int width;          // width of the window
    private long startTime;         // for time elapsed
    private long endTime;           // for time elapsed
    private KeyManager keyManager;  // key manager asociated with the display
    private MouseManager mouseManager; // mouse manager asociated with the display
    private Font font; // font used to display the score and game over message

    //Thread variables
    private Thread thread;                 // thread to create the game. points to the instance of this Game as a Runnable
    private boolean running;               // to set the game running status (controls the in thread execution)
    private double tps;                    //ticks per second
    private final boolean showTPS = false; //controls if the tps will be show on the console

    int fps = 60;                          //max frames per second the game will run at
    int frameData;
    boolean keyPressed;


    //Game objects and managers
    private LevelManager levelManager;
    private Player player;              // to store the player
    private EnemyManager enemyManager;  // to manage each enemy
    private dbManager db;
    

    //Game state
    private boolean paused = false; // states whether or not the game is paused
    private boolean pauseTrig = false;
    private int gameState = 0; // 0 -> game playing, -1 -> game lost, 1 -> game won

    //Save state
    private String fileName;    // Save filename
    private String[] arr;       // To load the game objects

    //Internal game atributes
    public String message;  // Stores endgame message

    private int menu;

    private int score;      // Stores game score
    

    /**
     * to create title, width and height and set the game is still not running
     * IMPORTANT: Please do not initialize game objects here, those go on the
     * init method
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game() {
        // Name of the game
        this.title = "Trashedy";

        // Sets game dimensions
        this.width = Commons.BOARD_WIDTH;
        this.height = Commons.BOARD_HEIGHT;
        // Initializes game functions
        running = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        //font = new Font("consolas", Font.BOLD, 10);
    }

    /**
     * returns the width of the display
     *
     * @return integer width
     */
    public int getWidth() {
        return width;
    }
    
    

    /**
     * returns the height of the display
     *
     * @return integer height
     */
    public int getHeight() {
        return height;
    }

    /**
     * provides the key manager of this game
     *
     * @return This game and display <b>keyManager</b>
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * provides the mouse manager of this game
     *
     * @return This game and display <b>mouseManager</b>
     */
    public MouseManager getMouseManager() {
        return mouseManager;
    }

    /**
     * Returns the current gamestate.
     *
     * @return 0 for playing, -1 if lost, 1 if won
     */
    public int getGameState() {
        return gameState;
    }

    /**
     * Sets the current gamestate to the introduced value in the param
     *
     * @param gameState
     */
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Override of the Runnable's ()run method. Were all code to be executed
     * cyclically by the game thread lies
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
        menu = 0;
        frameData = 1;
        keyPressed = true;
        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            //delta acumulates enogh tick fractions until a tick is completed and we can now advance in the tick
            if (delta >= 1) {
                
                //Lets try something
                if(menu != 3){
                    render2();
                    tick2();
                }
                else{
                    tick();
                    render();
                }
                
                frameData++;
                if (frameData > 60){
                    frameData = 1;
                }
                delta--;

                tps = 1000000000 / (now - initTickTime);
                if (showTPS) {
                    System.out.println("tps: " + tps);
                }
                initTickTime = now;
            }
        }
        stop();
    }

    /**
     * setting the thread for the game. Method to be publicly used by the
     * implementer of this Game.
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this); //points thread to this Game instance as a Runnabe so we can use run() as overriden.
            thread.start(); //Thread class mathod that starts the tread
        }
    }

    /**
     * stopping the thread for the game.
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    /**
     * rendering of all game elements.
     */
    private void render() {
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
        } else {
            g = bs.getDrawGraphics(); // gets the graphics of the buffer strategy
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.GREEN);   // sets the painting color to greenda
            g.setFont(font);           // sets the font
            
            g.drawImage(Assets.sand, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);  //paints the background

            
            
            //////////////////////////////////// Rendering Block
            
            g2d.translate(-camera.getX(), -camera.getY());
            
            
            getLevelManager().render(g);    //renders the level
            getEnemyManager().render(g);    //renders the enemies
            getPlayer().render(g);
            
            for(int i = 0; i < getPlayer().getBullets().size(); i++) { //renders each bullet
                if(getPlayer().getBullets().get(i).onScreen()) {
                    getPlayer().getBullets().get(i).render(g);
                }
            }
            
            
            g2d.translate(camera.getX(), camera.getY());
            
            ////////////////////////////////////
            //game state renders
            if (gameState != 0) {
                if (gameState == -1) {//lost screen
                    g.drawString(message, Commons.BOARD_WIDTH / 2 - 50, Commons.BOARD_HEIGHT / 2);
                } else if (gameState == 1) {//won screen
                    g.drawString(message, Commons.BOARD_WIDTH / 2 - 50, Commons.BOARD_HEIGHT / 2);
                }
            } else if (paused) { //triggers if paused and playing, displays paused message
                g.drawString("<PAUSED>", 10, 10);
            }
            if (keyManager.save) { //displays saved message if saved
                g.drawString("<SAVED>", Commons.BOARD_WIDTH - 100, 10);
            }

            //displays the amount of aliens destroyed
//            g.drawString("Destroyed: " + alienManager.destroyed + "/24", 10, Commons.BOARD_HEIGHT - 10);

            g.drawImage(Assets.hud, 0 , 0, Commons.TAB_WIDTH, Commons.TAB_HEIGHT, null);
            for(int o = 1; o < Commons.HEART_MAX; o += 2){
                g.drawImage(Assets.heartEmpty, o * (Commons.HEART_SIZE /2) , Commons.HEART_SIZE / 2, Commons.HEART_SIZE, Commons.HEART_SIZE, null);
            }
            if(player.getHp() != 1)
                for(int i = 1; i < player.getHp(); i+= 2){
                    g.drawImage(Assets.heartFull, i * (Commons.HEART_SIZE /2) , Commons.HEART_SIZE / 2, Commons.HEART_SIZE, Commons.HEART_SIZE, null);   
                }
            if(!(player.getHp() % 2 == 0)){
                g.drawImage(Assets.heartHalf, (player.getHp()) * (Commons.HEART_SIZE / 2), Commons.HEART_SIZE / 2, Commons.HEART_SIZE , Commons.HEART_SIZE, null);
            }
            
            g.drawImage(Assets.hud2, Commons.BOARD_WIDTH - Commons.TAB_WIDTH , 0, Commons.TAB_WIDTH, Commons.TAB_HEIGHT, null);
            g.drawString("Score: ", 0, 0);
            
            bs.show();
            g.dispose();
        }
    }

    /**
     * initializing the display window of the game asset loading Game objects
     * initialization
     */
    private void init() {
        // Initializes the display with the given dimensions
        display = new Display(title, width, height);
        
        // initializes all the assets of the game
        Assets.init();
        camera = new Camera(0,0,this);
        
        // Loads all the sound related assets
        Assets.music.setLooping(true);
        
        //Creates and initializes game objects
        setPlayer(new Player(new Vector2(Commons.START_X, Commons.START_Y), new Vector2(), true, Commons.PLAYER_WIDTH, Commons.PLAYER_HEIGHT, Assets.player,this));
        System.out.println(Commons.PLAYER_WIDTH);
        getPlayer().init();
        enemyManager = new EnemyManager(this);
        db = new dbManager(this);
        db.init();
                   
        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        
         // Level Manager
        levelManager = new LevelManager(this);
        levelManager.loadLevel(Assets.map);
        enemyManager.init();

        // Sets the score
        score = 0;
        while(playerid == null) {
            playerid = JOptionPane.showInputDialog("Enter your id");
        }
        if(!db.searchPlayer()) {
            int dialogResult = JOptionPane.showConfirmDialog (null, "Id not registered \nSave entered id?","Warning",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                  playerName = JOptionPane.showInputDialog("Enter your name");
                  db.registerPlayer();
            } else {
                playerid = JOptionPane.showInputDialog("Enter your id");
            }
        }
        
        startTime = System.currentTimeMillis();
    }

    /**
     * Code to be executed every game tick before render()
     */
    private void tick() {
        getKeyManager().tick(); //key manager must precede all user ralated actions
        
        if (!paused && gameState == 0) { //game playing and not paused 
            camera.tick(player);          //ticks the camera relative to the player
            
            // Sets orientation depending on key pressed
            // north,east,south,west = arrow keys
            // up,right,down,left    = w,a,s,d keys
            // Coded so that arrow keys override direction
            if (getKeyManager().north) {
                getPlayer().setOrientation(Sprite.Orientation.NORTH);
            } else if (getKeyManager().east) {
                getPlayer().setOrientation(Sprite.Orientation.EAST);
            } else if (getKeyManager().south) {
                getPlayer().setOrientation(Sprite.Orientation.SOUTH);
            } else if (getKeyManager().west) {
                getPlayer().setOrientation(Sprite.Orientation.WEST);
            } else if (getKeyManager().up) {
                getPlayer().setOrientation(Sprite.Orientation.NORTH);
            } else if (getKeyManager().right) {
                getPlayer().setOrientation(Sprite.Orientation.EAST);
            } else if (getKeyManager().down) {
                getPlayer().setOrientation(Sprite.Orientation.SOUTH);
            } else if (getKeyManager().left) {
                getPlayer().setOrientation(Sprite.Orientation.WEST);
            }
            
            if ((getKeyManager().north || getKeyManager().east || 
                    getKeyManager().south || getKeyManager().west )
                    && getPlayer().canShoot()) { // If arrow keys are pressed and shot is not on cd
                getPlayer().shoots();
                getPlayer().setShoot(false);
                getPlayer().resetShotcd();
           }
            
            // Player movement
            if (getKeyManager().left) getPlayer().getSpeed().setX(-5);
            else if(!getKeyManager().right) getPlayer().getSpeed().setX(0);
            if (getKeyManager().right) getPlayer().getSpeed().setX(5);
            else if(!getKeyManager().left) getPlayer().getSpeed().setX(0);
            if (getKeyManager().up) getPlayer().getSpeed().setY(-5);
            else if(!getKeyManager().down) getPlayer().getSpeed().setY(0);
            if (getKeyManager().down) getPlayer().getSpeed().setY(5);
            else if(!getKeyManager().up) getPlayer().getSpeed().setY(0);

            // Ticks game objects
            getPlayer().tick();
            if(!getPlayer().getBullets().isEmpty()) {
                for (int i = 0; i < getPlayer().getBullets().size(); i++) {
                    if(getPlayer().getBullets().get(i).isVisible()) {
                        getPlayer().getBullets().get(i).tick();
                    }
                    else getPlayer().getBullets().remove(i);
                }
            }
            //Ticks the manager that controls the enemies and their bullets
            enemyManager.tick();
            checkCollisions();
        } else if(gameState == -1) {
        }

        // Saves game and loads game
        if (keyManager.save && gameState == 0) 
        if (keyManager.load && gameState == 0) loadGame("save.txt");

        // When restart key pressed, music is restarted, gameState is set as playing, and game is loaded
        if (keyManager.restart) {   
            Assets.music.play();
            setGameState(0);
            loadGame("restartGame.txt");
        }

        //Triggers the pause of the game when 'P' is pressed
        if (!paused && keyManager.paused && !pauseTrig) paused = true;
        else if (paused && keyManager.paused && !pauseTrig) paused = false;
        
        if (keyManager.paused) pauseTrig = true;
        else pauseTrig = false;

    }

    /**
     * Method that saves the current game, including each of the object's
     * important attributes
     *
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
     *
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

    public void setPositionRelativeToPlayer(Sprite p) {
        p.setPosition(new Vector2(getPlayer().position.getX() + H_SPACE, getPlayer().position.getY() - V_SPACE));
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
    
    private boolean checkCollision(Sprite s, Sprite s2) { // Checks collisions between two objects
        if(s.getBounds().intersects(s2.getBounds())) {
            return true;
        } else return false;
    }

    private void checkCollisions() {
        getLevelManager().getLevel().stream().map((s) -> {
            
            Vector2 wallCenter = s.getPosition().add(new Vector2(s.getWidth()/2,s.getHeight()/2));
            //Collisions that involve walls
            getEnemyManager().getEnemies().forEach((e) -> {
                //Collisions between walls and enemies
                if(e.getClass().equals(Wheel.class) && checkCollision((Sprite) s, (Sprite) e)) {    // Makes the wheels bounce when touching walls
                    Vector2 enemyCenter = e.getPosition().add(new Vector2(e.getWidth()/2,e.getHeight()/2));
                    Vector2 pos = enemyCenter.sub(wallCenter);
                    if(abs(pos.getX()) > abs(pos.getY())) {
                        if(pos.getX() > 0) {
                            e.setPosition(new Vector2(s.getPosition().getX() - e.getWidth() - 1,e.getPosition().getY()));
                        } else {
                            e.setPosition(new Vector2(s.getPosition().getX() + s.getWidth() + 1,e.getPosition().getY()));
                        }
                        e.setSpeed(new Vector2(e.getSpeed().getX() * -1, e.getSpeed().getY()));
                    } else {
                        if(pos.getY() > 0) {
                            e.setPosition(new Vector2(e.getPosition().getX(),s.getPosition().getY() - e.getHeight() - 1));
                        } else {
                            e.setPosition(new Vector2(e.getPosition().getX(),s.getPosition().getY() + s.getHeight() + 1));
                        }
                        e.setSpeed(new Vector2(e.getSpeed().getX(), e.getSpeed().getY() * -1));
                    }
                } else if(e.getClass().equals(Can.class)|| e.getClass().equals(Boss.class)){
                    for(int i = 0; i < e.getBullets().size(); i++) {
                        if(checkCollision((Sprite) e.getBullets().get(i), (Sprite) s)) {
                            e.getBullets().remove(i);
                        }
                    }
                }
            });
            return s;
        }).map((s) -> {
            //Collisions between walls that are on screen and player
            getPlayer().getBullets().stream().filter((p) -> (checkCollision((Sprite) p, (Sprite) s))).forEachOrdered((p) -> {
                p.setVisible(false);
            }); // Colllisions between walls and player projectiles
            return s;
        }).filter((s) -> (s.onScreen())).filter((s) -> ( checkCollision((Sprite) s, (Sprite) getPlayer()))).forEachOrdered((_item) -> {
            //Makes player bounce
            getPlayer().setPosition(getPlayer().getPosition().add(getPlayer().getSpeed().scalar(-1)));
        });
        //Checks collision between each player bullet and enemy
            for(int i = 0;i < getPlayer().getBullets().size();i++) {
                for(int j = 0; j < getEnemyManager().getEnemies().size();j++) {
                    if(checkCollision((Sprite) getPlayer().getBullets().get(i), (Sprite) getEnemyManager().getEnemies().get(j))) {
                        getEnemyManager().getEnemies().get(j).setHp(getEnemyManager().getEnemies().get(j).getHp() - getPlayer().getBullets().get(i).getDamage());
                        getPlayer().getBullets().get(i).setVisible(false);
                        System.out.println("ShotRegistered");
                    }
                }
            }
            
        // Checks collision between each enemy bullet and player
        for(int i = 0; i < getEnemyManager().getEnemies().size(); i++) {
            if(getEnemyManager().getEnemies().get(i).getClass().equals(Wheel.class)) {
                if(checkCollision((Sprite) getEnemyManager().getEnemies().get(i), (Sprite) getPlayer())) { //Sets the bullet that collides to not existing
                        getPlayer().setHp(getPlayer().getHp() - 1);
                        System.out.println("hit");
                }
            } else {
                for(int j = 0; j < getEnemyManager().getEnemies().get(i).getBullets().size(); j++) {
                    if(checkCollision((Sprite) getEnemyManager().getEnemies().get(i).getBullets().get(j) , (Sprite) getPlayer())) { //Sets the bullet that collides to not existing
                        getEnemyManager().getEnemies().get(i).getBullets().get(j).setVisible(false);
                        getPlayer().setHp(getPlayer().getHp() - getEnemyManager().getEnemies().get(i).getBullets().get(j).getDamage());
                        System.out.println("hit");
                    }
                }
            }
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }
    
        private void render2() {
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
        } else {
            g = bs.getDrawGraphics(); // gets the graphics of the buffer strategy
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.GREEN);   // sets the painting color to greenda
            g.setFont(font);           // sets the font
            switch(menu){
                case 0:
                    g.drawImage(Assets.title, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);  //paints the background
                    break;
                case 1:
                    g.drawImage(Assets.prologue, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);  //paints the background
                    break;
                case 2:
                    g.drawImage(Assets.objetivo, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);  //paints the background
                    break;
                case 4:
                    g.drawImage(Assets.gameover, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);  //paints the background
                    break;
            }
            
            if(frameData > 25){
                g.drawString("Press SPACE to continue . . .", Commons.BOARD_WIDTH / 2 - 50, Commons.BOARD_HEIGHT / 2 + 150);
            }
            //g.drawString("Press SPACE to continue . . .", Commons.BOARD_WIDTH / 2 - 50, Commons.BOARD_HEIGHT / 2 + 100);
            
            bs.show();
            g.dispose();
        }
    }
        private void tick2(){
            getKeyManager().tick(); //key manager must precede all user ralated actions
            if(getKeyManager().space && keyPressed){
                menuSwitch();
                keyPressed = false;
            }
            else if (!getKeyManager().space){
                keyPressed = true;
            }
        }
        
        private void menuSwitch(){
            this.menu++;
            if(this.menu > 5){
                this.menu = 0;
            }
        }
    
}
