package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Main extends JPanel implements Runnable {
    private final int width = 40;
    private final int height = 30;
    private char[][] grid;
    private final ArrayList<Monster> monsters = new ArrayList<>();

    private int playerX = 5;
    private int playerY = 5;
    private static int maxHealth = 100;
    private static int maxMana = 100;
    private int health = maxHealth;
    private int mana = maxMana;
    private ArrayList<String> inventory;

    private boolean running = true;
    private boolean gameOver = false; // Flag to indicate game over

    // Invincibility variables
    private boolean isInvincible = false;
    private int invincibilityTimer = 0;
    private static final int INVINCIBILITY_DURATION = 30; // Duration of invincibility in update cycles

    private int score = 0; // Score variable

    public Main() {
        init();
        setFocusable(true);
        setPreferredSize(new Dimension(1280, 720));
        addKeyListener(new TAdapter());

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void init() {
        grid = new char[height][width];
        fillEmptyGrid();
        spawnMonsters();

        inventory = new ArrayList<>();
        inventory.add("Potion");
        inventory.add("Épée");
        inventory.add("Bouclier");
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
        for (int i = 0; i < 5; i++) { // Spawns 5 monsters
            int x = rand.nextInt(width - 2) + 1;
            int y = rand.nextInt(height - 2) + 1;
            monsters.add(new Monster(x, y));
        }
    }

    public void update() {
        if (gameOver) return; // Skip updates if game is over

        fillEmptyGrid();
        regenerationHpMp();
        for (Monster monster : monsters) {
            monster.moveTowardsPlayer(playerX, playerY);
            if (!isInvincible && monster.x == playerX && monster.y == playerY) {
                health = Math.max(health - 5, 0); // Take damage from monster
                isInvincible = true; // Activate invincibility
                invincibilityTimer = INVINCIBILITY_DURATION; // Set the duration of invincibility

                if (health == 0) { // Check if health is 0
                    gameOver = true; // Set game over flag
                }
            }
            grid[monster.y][monster.x] = 'M';
        }
        grid[playerY][playerX] = '@';

        // Handle invincibility timer
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
                        // Set color based on invincibility
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

        if (gameOver) { // Display game over message
            g.setColor(Color.black);
            g.fillRect(0, 0, width * 50, height*50);
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString("GAME OVER", getWidth() / 2 - 100, getHeight() / 2);
        }
    }

    int timerHealth = 0;
    int timerMana = 0;
    private void regenerationHpMp() {
        if (health != maxHealth) {
            timerHealth++;
            if (timerHealth >= 50) {
                health += 1;
                if (health >= maxHealth) {
                    health = maxHealth;
                }
                timerHealth = 0;
            }
        }

        if (mana != maxMana) {
            timerMana++;
            if (timerMana >= 25) {
                mana += 1;
                if (mana >= maxMana) {
                    mana = maxMana;
                }
                timerMana = 0;
            }
        }
    }

    private void drawUI(Graphics g) {
        int uiStartX = width * 20 + 100;
        int uiStartY = 40;

        g.setColor(Color.RED);
        g.drawString("HP: ", uiStartX, uiStartY);
        g.fillRect(uiStartX + 50, uiStartY - 15, health * 2, 20);
        g.setColor(Color.WHITE);
        g.drawRect(uiStartX + 50, uiStartY - 15, 200, 20);

        g.setColor(Color.BLUE);
        g.drawString("MP: ", uiStartX, uiStartY + 40);
        g.fillRect(uiStartX + 50, uiStartY + 25, mana * 2, 20);
        g.setColor(Color.WHITE);
        g.drawRect(uiStartX + 50, uiStartY + 25, 200, 20);

        g.setColor(Color.WHITE);
        g.drawString("|Inventory|", uiStartX + 95, uiStartY + 80);
        for (int i = 0; i < inventory.size(); i++) {
            g.drawString(inventory.get(i), uiStartX , uiStartY + 110 + (i * 20));
        }
        g.drawRect(uiStartX - 5, uiStartY + 60, 335, 200);

        // Display the score
        g.setColor(Color.YELLOW);
        g.drawString("Score: " + score, uiStartX, uiStartY + 300);
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
                Thread.sleep(20); // Increase the sleep time to slow down the update rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (gameOver) {
                break; // Exit game loop if game is over
            }
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (gameOver) return; // Skip key events if game is over

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && playerX > 1) {
                playerX--;
            }
            if (key == KeyEvent.VK_RIGHT && playerX < width - 2) {
                playerX++;
            }
            if (key == KeyEvent.VK_UP && playerY > 1) {
                playerY--;
            }
            if (key == KeyEvent.VK_DOWN && playerY < height - 2) {
                playerY++;
            }

            if (key == KeyEvent.VK_H) {
                if (!isInvincible) {
                    health = Math.max(health - 10, 0);
                    isInvincible = true; // Activate invincibility
                    invincibilityTimer = INVINCIBILITY_DURATION; // Set the duration of invincibility

                    if (health == 0) { // Check if health is 0
                        gameOver = true; // Set game over flag
                    }
                }
            }
            if (key == KeyEvent.VK_M) {
                mana = Math.max(mana - 5, 0);
            }
            if (key == KeyEvent.VK_I) {
                inventory.add("Objet " + (inventory.size() + 1));
            }
            if (key == KeyEvent.VK_A) {
                attackMonster();
            }
        }
    }

    private void attackMonster() {
        for (int i = 0; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            if (Math.abs(monster.x - playerX) <= 1 && Math.abs(monster.y - playerY) <= 1) {
                monsters.remove(i); // Remove the defeated monster
                score += 10; // Increase score
                return; // Exit the method after attacking one monster
            }
        }
    }

    private void moveMonster(Monster monster) {
        int targetX = playerX;
        int targetY = playerY;

        if (monster.x < targetX && grid[monster.y][monster.x + 1] != '#') {
            monster.x++;
        } else if (monster.x > targetX && grid[monster.y][monster.x - 1] != '#') {
            monster.x--;
        }
        if (monster.y < targetY && grid[monster.y + 1][monster.x] != '#') {
            monster.y++;
        } else if (monster.y > targetY && grid[monster.y - 1][monster.x] != '#') {
            monster.y--;
        }
    }

    private class Monster {
        int x, y;
        private int moveTimer = 0;
        private final int moveInterval = 50; // Interval in milliseconds

        Monster(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void moveTowardsPlayer(int playerX, int playerY) {
            moveTimer++;
            if (moveTimer >= moveInterval) {
                moveMonster(this);
                moveTimer = 0;
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
