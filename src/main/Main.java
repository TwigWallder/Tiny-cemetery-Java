package main;

import javax.swing.*;

import entity.Monster;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Main extends JPanel implements Runnable {
	public final int width = 40;
	public final int height = 30;
    private char[][] grid;
    private final ArrayList<Monster> monsters = new ArrayList<>();

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
    private int floor = 0;
    private int monsterLeft = 0;
    private int monsterSpawn = 7;
    private int nextNumMonster = 7;

    private int timerHealth = 0;
    private int timerMana = 0;

    private boolean running = true;
    public boolean gameOver = false;

    // Invincibility variables
    private boolean isInvincible = false;
    private int invincibilityTimer = 0;
    // Duration of invincibility in update cycles
    private static final int INVINCIBILITY_DURATION = 30;

    public Main() {
        init();
        setFocusable(true);
        setPreferredSize(new Dimension(1280, 720));
        addKeyListener(new InputHandler(this));

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void init() {
        grid = new char[height][width];
        fillEmptyGrid();
        spawnMonsters();
    }

    public void fillEmptyGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    grid[i][j] = '|';
                } else {
                    if (i % 3 == 0 && j % 7 == 0) {
                        grid[i][j] = '#';
                    } else {
                        grid[i][j] = ',';
                    }
                }
            }
        }
    }

    private void spawnMonsters() {
        Random rand = new Random();
        for (int i = 0; i < monsterSpawn; i++) {
            int x = rand.nextInt(width - 2) + 1;
            int y = rand.nextInt(height - 2) + 1;
            monsters.add(new Monster(x, y));
        }
    }
    public void update() {
        System.out.println(nextNumMonster);
        Random rand = new Random();
        int numMonster = 0;
        if (gameOver) return;

        fillEmptyGrid();
        levelUpSystem();
        regenerationHpMp();
        for (Monster monster : monsters) {
            numMonster++;
            monsterLeft = numMonster;
            monster.moveTowardsPlayer(playerX, playerY, grid);
            if (!isInvincible && Math.abs(monster.x - playerX) <= 1 && Math.abs(monster.y - playerY) <= 1) {
                health = Math.max(health - 5, 0);
                isInvincible = true;
                invincibilityTimer = INVINCIBILITY_DURATION;

                if (health == 0) {
                    gameOver = true;
                }
            }
            grid[monster.y][monster.x] = 'M';
        }
        if (numMonster == 0) {
            floor++;
            nextNumMonster *= 1.5;
            monsterSpawn = nextNumMonster;
            spawnMonsters();
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

        drawUI(g);

        if (gameOver) {
            g.setColor(Color.black);
            g.fillRect(0, 0, width * 50, height * 50);
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString("GAME OVER", getWidth() / 2 - 100, getHeight() / 2);
        }
    }

    private void levelUpSystem() {
        if (xp >= nextXp) {
            xp = 0;
            level++;
            nextXp *= 1.5;
            maxHealth *= 1.2;
            maxMana *= 1.3;
            health = maxHealth;
            mana = maxMana;
            attributPoint++;
        }
    }

    private void regenerationHpMp() {
        if (health != maxHealth) {
            timerHealth++;
            if (timerHealth >= 50) {
                health += 1 * vitality;
                if (health >= maxHealth) {
                    health = maxHealth;
                }
                timerHealth = 0;
            }
        }

        if (mana != maxMana) {
            timerMana++;
            if (timerMana >= 25) {
                mana += 1 * wisdom;
                if (mana >= maxMana) {
                    mana = maxMana;
                }
                timerMana = 0;
            }
        }
    }

    private void drawUI(Graphics g) {
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        int uiStartX = width * 20 + 100;
        int uiStartY = 40;

        barre(uiStartX, uiStartY, "HP: ", Color.RED, health, maxHealth, g);

        barre(uiStartX, uiStartY + 30, "MP: ", Color.cyan, mana, maxMana, g);

        g.setColor(Color.GREEN);
        g.drawString("Level: " + level, uiStartX, uiStartY + 110);

        barre(uiStartX, uiStartY + 60, "XP: ", Color.yellow, xp, nextXp, g);

        // Display stats       
        g.setColor(Color.getHSBColor(100, 100, 100));
        g.drawString("Status:  Lvl:  Input:", uiStartX, uiStartY + 170);
        g.drawString("Attack:   " + attack + "     [1]", uiStartX, uiStartY + 200);
        g.drawString("Defense:  " + defense + "     [2]", uiStartX, uiStartY + 220);
        g.drawString("Vitality: " + vitality + "     [3]", uiStartX, uiStartY + 240);
        g.drawString("Wisdom:   " + wisdom + "     [4]", uiStartX, uiStartY + 260);
        g.drawString("========================", uiStartX, uiStartY + 285);
        g.drawString("Attribut Point: " + attributPoint + "", uiStartX, uiStartY + 300);
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY + 150, 300, 165);
        // stats
        g.drawRect(uiStartX - 5, uiStartY - 5, 300, 130);

        // floor & info
        g.setColor(Color.getHSBColor(0, 290, 100));
        g.drawString("Info:    Count:", uiStartX, uiStartY + 520);
        g.drawString("Floor:      " + floor, uiStartX, uiStartY + 550);
        g.drawString("Monster:    " + monsterLeft, uiStartX, uiStartY + 575);
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY + 500, 300, 85);

        // game
        if (isInvincible) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.DARK_GRAY);
        }
        g.drawRect(45, 1, 800, 605);
        g.drawRect(46, 2, 800, 605);
        g.drawRect(47, 3, 800, 605);

        // Display Spell
        g.setColor(Color.lightGray);
        g.drawString("Spell: Cost: Input:", uiStartX, uiStartY + 370);
        g.drawString("Charge   (5)  [A]", uiStartX, uiStartY + 400);
        g.drawString("FireBall (20) [R]", uiStartX, uiStartY + 420);
        g.drawString("Ice Pick (25) [I]", uiStartX, uiStartY + 440);
        g.drawString("Teleport (10) [T]", uiStartX, uiStartY + 460);
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY + 350, 300, 120);
    }

    private void barre(int x, int y, String type, Color color, int val, int valMax, Graphics g) {

        g.setColor(color);
        g.drawString(type, x, y + 15);
        g.fillRect(x + 50, y, val * 200 / valMax, 20);
        g.setColor(Color.WHITE);
        g.drawRect(x + 50, y, 200, 20);

        // background
        g.setColor(Color.black);
        g.drawString(val + "/" + valMax, x + 100, y + 18);

        g.setColor(Color.lightGray);
        g.drawString(val + "/" + valMax, x + 100, y + 16);
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
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (gameOver) {
                break;
            }
        }
    }
    
    public void attackMonster() {
        for (int i = 0; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            if (Math.abs(monster.x - playerX) <= 1 && Math.abs(monster.y - playerY) <= 1) {
                monsters.remove(i);
                xp += 10;
                return;
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
