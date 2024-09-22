package utils;

import java.util.Random;

import entity.Mob;
import entity.Player;
import entity.Skeleton;
import entity.Zombie;
import entity.Vampire;
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
        m.floor++;
        m.nextNumMob *= 1.5;
        m.mobDamage *= 1.2;
        m.mobSpawn = (int) m.nextNumMob;
        spawnMobs();
        m.timeLeft += 30;
    }

    // Generate mobs
    public void spawnMobs() {
        Random rand = new Random();
        for (int i = 0; i < m.mobSpawn; i++) {
            int x = rand.nextInt(m.width - 2) + 1;
            int y = rand.nextInt(m.height - 2) + 1;

            // Randomly generate a type of mob
            int pourcentage = rand.nextInt(100);  
            int mobType = 0;
            if(pourcentage <= 55) {
            	mobType = 0;
            } else if (pourcentage >= 55 && pourcentage <= 85) {
            	mobType = 1;
            }else if (pourcentage >= 85 && pourcentage <= 100) {
            	mobType = 2;
            }
            System.out.println(pourcentage);
            switch (mobType) {
                case 0:
                	// Skeleton 55%
                    mob.mobs.add(new Skeleton(x, y, m, player));  
                    break;
                case 1:
                	// Zombie 35%
                    mob.mobs.add(new Zombie(x, y, m, player));  
                    break;
                case 2:
                	// Vampire 15%
                    mob.mobs.add(new Vampire(x, y, m, player));  
                    break;
            }
        }
    }
}
