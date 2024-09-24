package utils;

import java.util.Random;

import entity.Fungus;
import entity.Mob;
import entity.Player;
import entity.Skeleton;
import entity.Vampire;
import entity.Zombie;
import main.Main;

public class GenerateWorld {
    public int newJ = 7;
    public int newI = 3;
    
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
                    if (i % newI == 0 && j % newJ == 0) {
                    	// tombstone
                        m.grid[i][j] = '#';  
                    } else {
                    	// grass
                        m.grid[i][j] = ',';  
                    }
                }
            }
        }
    }

    public void newFloor() {
        Random rand = new Random();
        newI = rand.nextInt(6)+2;
        newJ = rand.nextInt(6)+2;
        m.floor++;
        m.nextNumMob *= 1.1;
        m.mobDamage *= 1.05;
        m.mobSpawn = (int) m.nextNumMob;
        m.timeLeft += 30;
        spawnMobs();
    }

    // Generate mobs
    public void spawnMobs() {
        Random rand = new Random();
        for (int i = 0; i < m.nextNumMob; i++) {
        	int x = rand.nextInt(m.width - 2) + 1;
            int y = rand.nextInt(m.height - 2) + 1;
            
            // safe zone
        	if(x > 10 && y > 10) {
                 
              // Randomly generate a type of mob
                 int mobType = rand.nextInt(4);  
                 
                 switch (mobType) {
                     case 0:
                         mob.mobs.add(new Skeleton(x, y, m, player));  
                         break;
                     case 1:
                         mob.mobs.add(new Zombie(x, y, m, player));  
                         break;
                     case 2:
                         mob.mobs.add(new Vampire(x, y, m, player));  
                         break;
                     case 3:
                         mob.mobs.add(new Fungus(x, y, m, player));  
                         break;
                 }
        	}           
        }
    }
}
