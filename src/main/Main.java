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
import utils.ColorPerso;
import utils.GenerateWorld;
import utils.TimeLeft;

public class Main extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    public final int width = 40;
    public final int height = 30;
    public final int SCREEN_WIDTH = 1280;
    public final int SCREEN_HEIGHT = 720;
    public char[][] grid;

    Random randPlayer = new Random();
    // Stats player
    public int playerX = randPlayer.nextInt(width-5)+1;
    public int playerY = randPlayer.nextInt(height-5)+1;
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

    // animation
    public boolean animationFD = false;
    public long animationFDTime = 0;
    public static final long ANIMATION_FD = 500; 
    
    public boolean animationM = false;
    public long animationMTime = 0;
    public static final long ANIMATION_M = 500; 
    
    public boolean animationFW = false;
    public long animationFWTime = 0;
    public static final long ANIMATION_FW = 500; 
    
    public boolean animationE = false;
    public long animationETime = 0;
    public static final long ANIMATION_E = 500; 

    // Game state
    private boolean running = true;
    public boolean gameOver = false;
    public boolean status = false;

    public int yOffset = 35;

    private UI ui;
    private TimeLeft timeL;
    private GenerateWorld gw;
    private Player player;
    private Mob mob;
    private ColorPerso cp;

    public Main() {
        init();
        setFocusable(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        addKeyListener(new InputHandler(this, player, ui));

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void init() {
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

        gw.fillEmptyGrid();
        timeL.getTimer();
        player.update();
        mob.update();

        // for fire dance timer
        if (animationFD && System.currentTimeMillis() - animationFDTime > ANIMATION_FD) {
        	animationFD = false;
        }
        
        // Meteor timer
        if (animationM && System.currentTimeMillis() - animationMTime > ANIMATION_M) {
        	animationM = false;
        }
        
        // fire wall timer
        if (animationFW && System.currentTimeMillis() - animationFWTime > ANIMATION_FW) {
        	animationFW = false;
        }
        
        // explosion timer
        if (animationE && System.currentTimeMillis() - animationETime > ANIMATION_E) {
        	animationE = false;
        }

        // Game over triggers
        if (player.health <= 0) {
            gameOver = true;
        }
        if (timeL.getTimer() == 0) {
            gameOver = true;
        }

        // Next floor
        if (mob.mobs.size() == 0) {
            gw.newFloor();  
        }
    }

    // Debug
    public int scale = 2;
    

    public void render(Graphics g) {
        g.setFont(new Font("Monospaced", Font.PLAIN, 20));
        
        g.setColor(cp.DARK_PURPLE_COLOR(255));
        g.fillRect(0, 0, getWidth(), getHeight());

        int gridXOffset = 50;
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char currentChar = grid[i][j];
                
                // LIGHT EFFECT
                int radius = 8;
                boolean darknessArea = ((playerX - j) * (playerX - j) + (playerY - i) * (playerY - i) <= radius * radius);
                radius = 3;
                boolean lightArea = ((playerX - j) * (playerX - j) + (playerY - i) * (playerY - i) <= radius * radius);
                int levelLight = 15;
                
                if(darknessArea) {
                	levelLight = 100;
                }
                if(lightArea) {
                	levelLight = 255;
                }
                	
                // for fire dance spell
                boolean patternFD = Math.abs(playerX - j) <= 1 && Math.abs(playerY - i) <= 1;
                boolean patternM = Math.abs(playerX - j) <= 3 && Math.abs(playerY - i) <= 3;
                boolean patternFW = Math.abs(playerY - i) <= 1;
                boolean patternE = Math.abs(playerX - j) <= 100;
                
                // FD animation
                if (currentChar == ',' && patternFD && animationFD) {
                    g.setColor(cp.RED_COLOR(255));
                    currentChar = '~'; 
                } else if (currentChar == ',' && patternM && animationM){
                    // meteo animation
                	g.setColor(cp.RED_COLOR(255));
                    currentChar = '¤'; 
                } else if (currentChar == ',' && patternFW && animationFW){
                	 // FW animation
                	g.setColor(cp.RED_COLOR(255));
                	currentChar = '-'; 
                } else if (currentChar == ',' && patternE && animationE) {
                	g.setColor(cp.RED_COLOR(255));
                	currentChar = '¤';
                }else {
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
                        case 'M':
                            g.setColor(Color.RED);
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
                g.drawString(String.valueOf(currentChar), gridXOffset + j * 20 + xOffset, ((i + 1) * 20) + yOffset);
            }
        }

        ui.drawUI(g);
        for (int i = 0; i < SCREEN_HEIGHT; i += 2) {
            // Ligne balayage
        	if (isInvincible && status && !gameOver) {
        		g.setColor(cp.RED_COLOR(100));

                g.setFont(new Font("Monospaced", Font.BOLD, 32));
        		g.drawString("You are being attacked /!\\", SCREEN_WIDTH/2  - 300, SCREEN_HEIGHT/2 + 200);
            } else {
            	g.setColor(new Color(0,0,0,50));
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
