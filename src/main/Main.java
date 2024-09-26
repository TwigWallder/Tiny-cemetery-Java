package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Mob;
import entity.Player;
import spell.AnimationSpell;
import utils.ColorPerso;
import utils.GenerateWorld;
import utils.TimeLeft;

public class Main extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    
    // Taille de la carte agrandie
    public final int width = 100;   // Anciennement 40
    public final int height = 100;  // Anciennement 30
    
    // Taille de l'écran
    public final int SCREEN_WIDTH = 1280;
    public final int SCREEN_HEIGHT = 720;
    
    public char[][] grid;

    Random randPlayer = new Random();
    // Stats player
    public int playerX = randPlayer.nextInt(width - 5) + 1;
    public int playerY = randPlayer.nextInt(height - 5) + 1;
    public int xp = 0;

    // Floor & info
    public int floor = 0;
    public int mobLeft = 0;
    public int mobSpawn = 17;
    public int nextNumMob = 17;
    public int mobDamage = 10;
    public int timeLeft = 360;
    public int counterTime = 0;

    public int timerHealth = 0;
    public int timerMana = 0;

    // Invincibility variables
    public boolean isInvincible = false;
    public int invincibilityTimer = 0;
    public static final int INVINCIBILITY_DURATION = 30;

    // Game state
    private boolean running = true;
    public boolean gameOver = false;
    public boolean status = false;

    // Debug
    public int scale = 2;
    public int yOffset = 35;

    // Variables de caméra
    public int camX = 0;
    public int camY = 0;
    public final int camWidth = 40;   // Largeur de la caméra (nombre de cellules)
    public final int camHeight = 30;  // Hauteur de la caméra (nombre de cellules)

    private UI ui;
    private TimeLeft timeL;
    private GenerateWorld gw;
    private Player player;
    public Mob mob;
    private ColorPerso cp;
    private AnimationSpell as;

    public Main() {
        init();
        setFocusable(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        addKeyListener(new InputHandler(this, player, ui, as));

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void init() {
        as = new AnimationSpell(this);
        cp = new ColorPerso();
        grid = new char[height][width];
        mob = new Mob(0, 0, this, player);
        player = new Player(this, mob);
        gw = new GenerateWorld(this, player, mob);
        gw.fillEmptyGrid();
        gw.spawnMobs();
        ui = new UI(this, player, cp);
        timeL = new TimeLeft(this);
    }

    public void update() {
        if (gameOver) return;

        // Mise à jour de la position de la caméra pour suivre le joueur
        camX = Math.max(0, Math.min(playerX - camWidth / 2, width - camWidth));
        camY = Math.max(0, Math.min(playerY - camHeight / 2, height - camHeight));

        gw.fillEmptyGrid();
        timeL.getTimer();
        player.update();
        mob.update();
        as.update();

        if (player.health <= 0) {
            player.health = 0;
            gameOver = true;
        }

        // Game over triggers
        if (timeL.getTimer() == 0) gameOver = true;

        // Next floor
        if (mob.mobs.size() == 0) gw.newFloor();
    }

    public void render(Graphics g) {
        g.setFont(new Font("Monospaced", Font.PLAIN, 20));
        
        g.setColor(cp.DARK_PURPLE_COLOR(255));
        g.fillRect(0, 0, getWidth(), getHeight());

        int gridXOffset = 50;

        // Dessiner seulement la portion visible par la caméra
        for (int i = camY; i < camY + camHeight; i++) {
            for (int j = camX; j < camX + camWidth; j++) {
                char currentChar = grid[i][j];

                // Effets de lumière
                int radius = 8;
                boolean darknessArea = ((playerX - j) * (playerX - j) + (playerY - i) * (playerY - i) <= radius * radius);
                radius = 3;
                boolean lightArea = ((playerX - j) * (playerX - j) + (playerY - i) * (playerY - i) <= radius * radius);
                int levelLight = 15;

                if (darknessArea) {
                    levelLight = 100;
                }
                if (lightArea) {
                    levelLight = 255;
                }

                // Animation des cases spécifiques
                if (currentChar == ',' && as.getAnimation(0, i, j) && as.animationFD) {
                    g.setColor(cp.RED_COLOR(255));
                    currentChar = '~'; 
                } else if (currentChar == ',' && as.getAnimation(1, i, j) && as.animationM) {
                    g.setColor(cp.RED_COLOR(255));
                    currentChar = '¤'; 
                } else if (currentChar == ',' && as.getAnimation(2, i, j) && as.animationFW) {
                    g.setColor(cp.RED_COLOR(255));
                    currentChar = '-'; 
                } else if (currentChar == ',' && as.getAnimation(3, i, j) && as.animationE) {
                    g.setColor(cp.RED_COLOR(255));
                    currentChar = '¤';
                } else {
                    switch (currentChar) {
                        case '|':
                            g.setColor(cp.PURPLE_COLOR(levelLight));
                            break;
                        case '#':
                            g.setColor(cp.WHITE_COLOR(levelLight));
                            break;
                        case ',':
                            g.setColor(cp.TURQUOISE_COLOR(levelLight));
                            break;
                        case '@':
                            g.setColor(isInvincible ? cp.WHITE_COLOR(levelLight) : cp.YELLOW_COLOR(levelLight));
                            break;
                        case 'S':
                            g.setColor(cp.PINK_COLOR(levelLight));
                            break;
                        case 'Z':
                            g.setColor(cp.RED_COLOR(levelLight));
                            break;
                        case 'V':
                            g.setColor(cp.LIGHT_PURPLE_COLOR(levelLight));
                            break;
                        case 'F':
                            g.setColor(cp.CYAN_COLOR(levelLight));
                            break;
                    }
                }

                // Appliquer l'effet de décalage
                int xOffset = (int) (Math.sin(System.currentTimeMillis() * 0.001 + j * 0.1) * scale);
                g.drawString(String.valueOf(currentChar), gridXOffset + (j - camX) * 20 + xOffset, ((i - camY + 1) * 20) + yOffset);
            }
        }

        ui.drawUI(g);

        for (int i = 0; i < SCREEN_HEIGHT; i += 2) {
            // Ligne balayage
            if (isInvincible && status && !gameOver) {
                g.setColor(cp.RED_COLOR(100));
                g.setFont(new Font("Monospaced", Font.BOLD, 32));
                g.drawString("You are being attacked /!\\", SCREEN_WIDTH / 2 - 300, SCREEN_HEIGHT / 2 + 200);
            } else {
                g.setColor(new Color(0, 0, 0, 50));
            }
            
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
            if (!ui.pause) {
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
