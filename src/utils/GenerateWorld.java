package utils;

import java.util.Random;

import entity.Mob;
import entity.Player;
import main.Main;

public class GenerateWorld {
	Main m;
	Player player;
	Mob mob;
	
	public GenerateWorld(Main m, Player player, Mob mob) {
		this.m = m;
		this.player = player;
		this.mob = mob;
	}
	
	public void fillEmptyGrid() {
        for (int i = 0; i < m.height; i++) {
            for (int j = 0; j < m.width; j++) {
                if (i == 0 || i == m.height - 1 || j == 0 || j == m.width - 1) {
                    m.grid[i][j] = '|';
                } else {
                    if (i % 3 == 0 && j % 7 == 0) {
                        m.grid[i][j] = '#';
                    } else {
                        m.grid[i][j] = ',';
                    }
                }
            }
        }
    }

    
    public void newFloor() {
    	m.floor++;
    	m.nextNumMob *= 1.5;
    	m.mobDamage *= 1.2;
    	m.mobSpawn = m.nextNumMob;
    	spawnMobs();
    	m.timeLeft += 30;
    }
    
    public void spawnMobs() {
        Random rand = new Random();
        for (int i = 0; i < m.mobSpawn; i++) {
            int x = rand.nextInt(m.width - 2) + 1;
            int y = rand.nextInt(m.height - 2) + 1;
            mob.mobs.add(new Mob(x, y, m, player));
        }
    }
}