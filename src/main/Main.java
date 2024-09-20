package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Monster;
import entity.Player;
import utils.GenerateWorld;
import utils.TimeLeft;

public class Main extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	public final int width = 40;
	public final int height = 30;
    public char[][] grid;
    public final ArrayList<Monster> monsters = new ArrayList<>();

    // Stats player
    public int playerX = 5;
    public int playerY = 5;
    public int maxHealth = 100;
    public int maxMana = 100;
    public int health = maxHealth;
    public int mana = maxMana;
    public int level = 1;
    public int xp = 0;
    public int nextXp = 100;
    public int attack = 1;
    public int defense = 1;
    public int vitality = 1;
    public int wisdom = 1;
    public int attributPoint = 0;

    //floor & info
    public int floor = 0;
    public int monsterLeft = 0;
    public int monsterSpawn = 7;
    public int nextNumMonster = 7;
    public int monsterDamage = 10;
    public int timeLeft = 100;
	public int counterTime = 0;

    public int timerHealth = 0;
    public int timerMana = 0;

    private boolean running = true;
    public boolean gameOver = false;

    // Invincibility variables
    public boolean isInvincible = false;
    public int invincibilityTimer = 0;
    // Duration of invincibility in update cycles
    public static final int INVINCIBILITY_DURATION = 30;
    
    private UI ui;
    private TimeLeft timeL;
    private GenerateWorld gw;
    private Player player;

    public Main() {
        init();
        setFocusable(true);
        setPreferredSize(new Dimension(1280, 720));
        addKeyListener(new InputHandler(this, player));

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void init() {
        grid = new char[height][width];
        player = new Player(this);
        gw = new GenerateWorld(this);
        gw.fillEmptyGrid();
        gw.spawnMonsters();
        ui = new UI(this);
        timeL = new TimeLeft(this);
    }
    
    public void update() {
        System.out.println(nextNumMonster);
        int numMonster = 0;
        if (gameOver) return;

        gw.fillEmptyGrid();
        player.levelUpSystem();
        player.regenerationHpMp();
        timeL.getTimer();
        if(timeL.getTimer() == 0) {
        	gameOver = true;
        }
        for (Monster monster : monsters) {
            numMonster++;
            monsterLeft = numMonster;
            monster.moveTowardsPlayer(playerX, playerY, grid);
            if (!isInvincible && Math.abs(monster.x - playerX) <= 1 && Math.abs(monster.y - playerY) <= 1) {
            	monster.damagePlayer();
                isInvincible = true;
                invincibilityTimer = INVINCIBILITY_DURATION;
            }
            grid[monster.y][monster.x] = 'M';
        }
        
        if (health == 0) {
            gameOver = true;
        }
        
        if (numMonster == 0) {
            gw.newFloor();
        }
        grid[playerY][playerX] = '@';

        if (isInvincible) {
            invincibilityTimer--;
            if (invincibilityTimer <= 0) {
                isInvincible = false;
            }
        }
    }

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
                g.drawString(String.valueOf(grid[i][j]), gridXOffset + j * 20, (i + 1) * 20);
            }
        }

        ui.drawUI(g);

        if (gameOver) {
            g.setColor(Color.black);
            g.fillRect(0, 0, width * 50, height * 50);
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString("GAME OVER", getWidth() / 2 - 100, getHeight() / 2);
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
            update();
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
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}