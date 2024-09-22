package entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import main.Main;

public class Mob {
	public int x, y;
    public int moveTimer = 0;
    public int moveTimer2 = 0;
    public char mobChar = 'M';
    public Color mobColor = Color.orange;
    public final int moveInterval = 50;
    public int hp = 2;
    public final ArrayList<Mob> mobs = new ArrayList<>();

    Random rand = new Random();
    Main m;
    Player player;

    public Mob(int x, int y, Main m, Player player) {
        this.x = x;
        this.y = y;
        this.m = m;
        this.player = player;
    }
    
    public void update() {
    	int nummob = 0;
        // Utilisation de l'iterator pour éviter ConcurrentModificationException 
        Iterator<Mob> iterator = mobs.iterator();
        
        while (iterator.hasNext()) {
            Mob mob = iterator.next();
            nummob++;
            m.mobLeft = nummob;
            mob.moveTowardsPlayer(m.playerX, m.playerY, m.grid);
            
            if (!m.isInvincible && Math.abs(mob.x - m.playerX) <= 1 && Math.abs(mob.y - m.playerY) <= 1) {
                mob.attacking();
                m.isInvincible = true;
                m.invincibilityTimer = m.INVINCIBILITY_DURATION;
            }
            
            m.grid[mob.y][mob.x] = mob.mobChar;
            
            if (mob.hp <= 0) {
                // Utilisation de l'iterator pour la suppression
                iterator.remove();
                m.xp += rand.nextInt(10-5)+5;
            }
        }
    }

    public void moveTowardsPlayer(int playerX, int playerY, char[][] grid) {

        // Calcul de la distance entre le joueur et le mob
        int distanceX = Math.abs(playerX - x);
        int distanceY = Math.abs(playerY - y);
        
        // Si le joueur est dans un rayon de n blocs en x ou y, le mob prend l'aggro
        if (distanceX <= 10 && distanceY <= 10) {
            moveTimer++;
            if (moveTimer >= moveInterval) {
                movement(playerX, playerY, grid);
                moveTimer = 0;
            	mobColor = Color.red;
            }
        } 
    }

    private void movement(int playerX, int playerY, char[][] grid) {
        int gridRight = grid[y][x + 1];
        int gridLeft = grid[y][x - 1];
        int gridDown = grid[y + 1][x];
        int gridUp = grid[y - 1][x];
        
        // Si le mob est à plus d'un bloc du joueur, il se rapproche
        if (x < playerX - 1 && gridRight != '#' && gridRight != 'M') {
            x++;
        } else if (x > playerX + 1 && gridLeft != '#' && gridLeft != 'M') {
            x--;
        } 
        if (y < playerY - 1 && gridDown != '#' && gridDown != 'M') {
            y++;
        } else if (y > playerY + 1 && gridUp != '#' && gridUp != 'M') {
            y--;
        }
    }
    
    public void attacking() {
    	Random rand = new Random();
    	int result = rand.nextInt(m.mobDamage) - player.defense;
    	if(result >= 0) {
    		player.health -= result;
    	} else {
    		player.health --;
    	}
    }
    
}