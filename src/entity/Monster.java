package entity;

import main.Main;

public class Monster {
	public int x, y;
    public int moveTimer = 0;
    public final int moveInterval = 50;
    Main m;

    public Monster(int x, int y, Main m) {
        this.x = x;
        this.y = y;
        this.m = m;
    }

    public void moveTowardsPlayer(int playerX, int playerY, char[][] grid) {
        moveTimer++;
        if (moveTimer >= moveInterval) {
            moveMonster(playerX, playerY, grid);
            moveTimer = 0;
        }
    }

    private void moveMonster(int playerX, int playerY, char[][] grid) {
        if (x < playerX-1 && grid[y][x + 1] != '#') {
            x++;
        } else if (x > playerX+1 && grid[y][x - 1] != '#') {
            x--;
        }
        if (y < playerY-1 && grid[y + 1][x] != '#') {
            y++;
        } else if (y > playerY+1 && grid[y - 1][x] != '#') {
            y--;
        }
    }
    
    public void damagePlayer() {
    	int result = m.monsterDamage - m.defense;
    	if(result > 0) {
    		m.health = Math.max(m.health - (result), 0);
    	}
    }
}