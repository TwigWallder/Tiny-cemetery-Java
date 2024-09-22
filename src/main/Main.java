package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Mob;
import entity.Player;
import utils.GenerateWorld;
import utils.TimeLeft;

public class Main extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	public final int width = 40;
	public final int height = 30;
	public final int SCREEN_WIDTH = 1280;
	public final int SCREEN_HEIGHT = 720;
    public char[][] grid;

    // Stats player
    public int playerX = 5;
    public int playerY = 5;
    public int xp = 0;

    //floor & info
    public int floor = 0;
    public int mobLeft = 0;
    public int mobSpawn = 7;
    public int nextNumMob = 7;
    public int mobDamage = 10;
    public int timeLeft = 120;
	public int counterTime = 0;

    public int timerHealth = 0;
    public int timerMana = 0;

    // Invincibility variables
    public boolean isInvincible = false;
    public int invincibilityTimer = 0;
    // Duration of invincibility in update cycles
    public static final int INVINCIBILITY_DURATION = 30;
    
    // game state
    private boolean running = true;
    public boolean gameOver = false;
    
    public int yOffset = 35;
    
    private UI ui;
    private TimeLeft timeL;
    private GenerateWorld gw;
    private Player player;
    private Mob mob;

    public Main() {
        init();
        setFocusable(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        addKeyListener(new InputHandler(this, player, ui));

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void init() {
        grid = new char[height][width];
        mob = new Mob(0, 0, this, player);
        player = new Player(this, mob);
        gw = new GenerateWorld(this, player, mob);
        gw.fillEmptyGrid();
        gw.spawnMobs();
        ui = new UI(this, player);
        timeL = new TimeLeft(this);
    }
    
    public void update() {
        if (gameOver) return;

        gw.fillEmptyGrid();
        timeL.getTimer();

        player.update();
        mob.update();

        // game overs trigger
        if (player.health <= 0) {
            gameOver = true;
        }
        if(timeL.getTimer() == 0) {
        	gameOver = true;
        }
        
        // Next floor
        if (mob.mobs.size() == 0) {
            gw.newFloor();
        }
    }

    // debug
    public int scale = 2;
    public void render(Graphics g) {
        g.setFont(new Font("Monospaced", Font.PLAIN, 20));

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        int gridXOffset = 50;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (grid[i][j]) {
                    case '|':
                        g.setColor(Color.MAGENTA);
                        break;
                    case '#':
                        g.setColor(Color.WHITE);
                        break;
                    case ',':
                        g.setColor(Color.GREEN);
                        break;
                    case '@':
                        g.setColor(isInvincible ? Color.WHITE : Color.YELLOW);
                        break;
                    case 'M':
                        g.setColor(Color.RED);
                        break;
                }

                // décalage effet
                int xOffset = (int)(Math.sin(System.currentTimeMillis() * 0.001 + j * 0.1) * scale);
                g.drawString(String.valueOf(grid[i][j]), gridXOffset + j * 20 + xOffset, ((i + 1) * 20) + yOffset);
            }
        }

        ui.drawUI(g);

        for (int i = 0; i < SCREEN_HEIGHT; i += 2) {
            // ligne balayage
            g.setColor(new Color(255, 255, 255, 30)); 
            g.drawLine(0, i, SCREEN_WIDTH, i);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    @Override
    public void run() {
        while (running) {
        	if(!ui.pause) {
                update();
        	}
            repaint();

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (gameOver) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tiny Cemetery - Java version");
        Main game = new Main();
        frame.add(game);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}