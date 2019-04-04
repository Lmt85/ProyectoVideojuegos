/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author luismariotrujillo
 */
public class Game implements Runnable {

    private BufferStrategy bs;		//	to	have	several	buffers	when	displaying
    private Graphics g;									//	to	paint	objects
    private Display display;				//	to	display	in	the	game
    String title;															//	title	of	the	window
    private int width;		//	width	of	the	window
    private int height;									//	height	of	the	window
    private Thread thread;						//	thread	to	create	the	game
    private boolean running;
    private int x;
    private int enemiesDirectionX;
    private int enemiesDirectionY;
    private Enemy enemy;
    private Player player;
    private Rectangle enemiesBlock;
    private Bomb bomb;
    private int iPause;

    private KeyManager keyManager;
    private int lives;
    //private int bigLives;

    /**
     *
     */
    public LinkedList<Enemy> enemies;
    private Shot shot;
    private String sScore;
    private int iScore;
    private Font f;
    private int iNumOfBricks;
    private boolean pause;
    private boolean resume;

    @Override
    public void run() {
        init();
        //frames per second
        int fps = 60;
        //time for each tick in nano seconds
        double timeTick = 1000000000 / fps;
        //initializing delta
        double delta = 0;
        //define now to use inside the loop
        long now;
        //initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();

        lives = 3;

        while (running) {
            //setting the time now to the actual time
            now = System.nanoTime();
            //acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            //updating the last time
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    /**
     *
     * @return
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    private void tick() {

        if (lives > 0) {

            keyManager.tick();
            if (iPause == 0) {
                if (getKeyManager().p) {
                    pause ^= true;
                    iPause = 45;
                }
            }
            else{
                iPause--;
            }

            if (getKeyManager().g) {
                saveGame();
            }
            if (getKeyManager().c) {
                loadGame();
            }
            if (getKeyManager().r) {
                restart();
            }
            if (!pause) {
                player.tick();

                if (getKeyManager().space && shot.getSpeed() == 0) {
                    shot = new Shot(player.getX() + 25, player.getY(), 5, 15, 10, this, player);

                }
                shot.tick();
                int r = (int) (Math.random() * 30) + 1;

                for (Enemy enemy : enemies) {

                    enemy.tick();
                    if (shot.collision(enemy.getRectangle())) {
                        enemy.getHit();
                        Assets.ding.play();
                        iScore += 5;
                    }

                    if (enemy.getId() == r && bomb.getSpeed() == 0) {
                        bomb = new Bomb(enemy.getX(), enemy.getY(), 20, 20, 1, this, player);
                        //bomb.setX(enemy.getX());
                        //bomb.setY(enemy.getY());
                    }

                }
                if (bomb.collision(player.getRectangle())) {
                    lives--;
                    Assets.ding.play();
                    player.setX(getWidth() / 2);
                }
                bomb.tick();

            }

        }

    }

    private void render() {
        //get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /*	if	it	is	null,	we	define	one	with	3	buffers	to	display	images	of
	the	game,	if	not	null,	then	we	display every	image	of	the	game	but
	after	clearing	the	Rectanlge,	getting	the	graphic	object	from	the	
	buffer	strategy	element.	
	show	the	graphic	and	dispose	it	to	the	trash	system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            //hacemos un if que determina si el jugador todavia tiene vidas
            //si ese es el caso, el juego prosigue normalmente

            //Dibujamos el score, abajo a la derecha
            //
            g.setFont(f);
            g.setColor(Color.yellow);
            g.drawString(sScore + iScore, 1200, 700);

            if (lives > 0) {
                //hacemos un for que dibuja las vidas del jugador
                if (iScore == 150) {
                    win();
                } else {
                    for (int x = 0; x < lives; x++) {
                        g.drawImage(Assets.lifes, x * 55, 670, 50, 50, null);
                    }

                    player.render(g);

                    for (Enemy enemy : enemies) {
                        enemy.render(g);
                    }

                    shot.render(g);
                    bomb.render(g);

                    if (pause) {
                        g.drawString("presione p para reanudar el juego", this.width / 2 - 100, this.height / 2);
                    }

                }

            } else {
                //si se ha muerto el jugador, ya no rendereamos los items y solo rendereamos el gameover
                g.drawImage(Assets.gameOver, 0, 0, width, height, null);
                g.drawString("presione r para reiniciar el juego", this.width / 2 - 100, this.height - 100);
                keyManager.tick();
                if (getKeyManager().r) {
                    restart();
                }

            }
            bs.show();
            g.dispose();
        }
    }

    /**
     * setting	the	thread	for	the	game
     */
    public void restart() {
        lives = 4;
        iScore = 0;
        reduceLives();
        for (int i = 0; i < 30; i++) {
            enemies.get(i).reset();
        }
        shot.hitEnemy();
        bomb.reset();
        //creamos el enemigo en cualquier punto arriba del borde superior de la pantalla
        setEnemiesDirectionX(1);
        setEnemiesDirectionY(0);

    }

    /**
     *
     */
    public void enemyWall() {
        for (int i = 0; i < 30; i++) {
            enemies.get(i).hitWall();
            if (enemies.get(i).getY() > 640) {
                lives = 0;
            }
        }
    }

    /**
     *
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping	the	thread
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


    /*	/**
    *	to	create	title,	width	and	height	and	set	the	game	is	still	not	running
    *	@param	title	to	set	the	title	of	the	window
    *	@param	width	to	set	the	width	of	the	window
    *	@param	height		to	set	the	height	of	the	window
     */
    /**
     *
     * @param title
     * @param width
     * @param height
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        //hacemos la lista de enemigos
        enemies = new LinkedList<Enemy>();
        //hacemos la lista de disparos

        //hacemos las variables de score
        sScore = "Score: ";
        iScore = 0;
        //Hacemos aleatoria la cantidad de enemigos cada vez que se crea el juego
        iNumOfBricks = 30;

    }

    /**
     *
     */
    public void reduceLives() {
        lives--;

    }

    /**
     *
     * @return
     */
    public int getLives() {
        return lives;
    }

    /**
     *
     * @param live
     */
    public void setLives(int live) {
        this.lives = live;
    }

    private void saveGame() {
        try {
            FileWriter fw = new FileWriter("src/videogame/save.txt");

            fw.write(String.valueOf(iScore) + '\n');

            fw.write(String.valueOf(player.getX()) + '\n');
            fw.write(String.valueOf(player.getY()) + '\n');

            fw.write(String.valueOf(getLives()) + '\n');

            fw.write(String.valueOf(bomb.getX()) + '\n');
            fw.write(String.valueOf(bomb.getY()) + '\n');

            fw.write(String.valueOf(shot.getX()) + '\n');
            fw.write(String.valueOf(shot.getY()) + '\n');

            fw.write(String.valueOf(enemiesDirectionX) + '\n');
            fw.write(String.valueOf(enemiesDirectionY) + '\n');

            for (int i = 0; i < 30; i++) {
                fw.write(String.valueOf(enemies.get(i).getX()) + '\n');
                fw.write(String.valueOf(enemies.get(i).getY()) + '\n');
            }

            fw.close();

        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    private void loadGame() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/videogame/save.txt"));

            setiScore(Integer.parseInt(br.readLine()));

            player.setX(Integer.parseInt(br.readLine()));
            player.setY(Integer.parseInt(br.readLine()));

            setLives(Integer.parseInt(br.readLine()));

            bomb.setX(Integer.parseInt(br.readLine()));
            bomb.setY(Integer.parseInt(br.readLine()));

            shot.setX(Integer.parseInt(br.readLine()));
            shot.setY(Integer.parseInt(br.readLine()));

            enemiesDirectionX = Integer.parseInt(br.readLine());
            enemiesDirectionY = Integer.parseInt(br.readLine());

            for (int i = 0; i < 30; i++) {
                enemies.get(i).setX(Integer.parseInt(br.readLine()));
                enemies.get(i).setY(Integer.parseInt(br.readLine()));
            }

            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * initializing the display window
     */
    private void init() {
        display = new Display(title, width, height);
        Assets.init();
        player = new Player(getWidth() / 2 - 75, getHeight() - 80, 1, 60, 40, this);

        for (int i = 0; i < 10; i++) {
            enemies.add(new Enemy(80 * i + 230, 100, 40, 25, i + 1, this, player));
        }
        for (int i = 0; i < 10; i++) {
            enemies.add(new Enemy(80 * i + 230, 140, 40, 25, i + 11, this, player));
        }
        for (int i = 0; i < 10; i++) {
            enemies.add(new Enemy(80 * i + 230, 180, 40, 25, i + 21, this, player));
        }
        shot = new Shot(-1000, -1000, 5, 15, 0, this, player);
        bomb = new Bomb(-1000, -1000, 5, 15, 0, this, player);
        //creamos el enemigo en cualquier punto arriba del borde superior de la pantalla
        display.getJframe().addKeyListener(keyManager);
        setEnemiesDirectionX(1);
        setEnemiesDirectionY(0);

        iPause = 0;

    }

    /**
     *
     * @param iScore
     */
    public void setiScore(int iScore) {
        this.iScore = iScore;
    }

    /**
     *
     */
    public void win() {
        g.drawImage(Assets.win, 0, 0, width, height, null);
        g.drawString("presione r para reiniciar el juego", this.width / 2 - 100, this.height - 100);
        keyManager.tick();
        if (getKeyManager().r) {
            restart();
        }

    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return
     */
    public Rectangle getRectangle() {
        return enemiesBlock;
    }

    /**
     *
     * @param direction
     */
    public void setEnemiesDirectionX(int direction) {
        this.enemiesDirectionX = direction;
    }

    /**
     *
     * @return
     */
    public int getEnemiesDirectionX() {
        return enemiesDirectionX;
    }

    /**
     *
     * @param direction
     */
    public void setEnemiesDirectionY(int direction) {
        this.enemiesDirectionY = direction;
    }

    /**
     *
     * @return
     */
    public int getEnemiesDirectionY() {
        return enemiesDirectionY;
    }

}
