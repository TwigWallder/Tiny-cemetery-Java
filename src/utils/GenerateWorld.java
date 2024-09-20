package utils;

import java.util.Random;

import entity.Monster;
import main.Main;

public class GenerateWorld {
	Main m;
	
	public GenerateWorld(Main m) {
		this.m = m;
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
    	m.nextNumMonster *= 1.5;
    	m.monsterDamage *= 1.2;
    	m.monsterSpawn = m.nextNumMonster;
    	spawnMonsters();
    	m.timeLeft += 30;
    }
    
    public void spawnMonsters() {
        Random rand = new Random();
        for (int i = 0; i < m.monsterSpawn; i++) {
            int x = rand.nextInt(m.width - 2) + 1;
            int y = rand.nextInt(m.height - 2) + 1;
            m.monsters.add(new Monster(x, y, m));
        }
    }
}
