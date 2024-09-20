package entity;

import main.Main;

public class Player {

	Main m;
	Monster monster;
	
	public Player(Main m) {
		this.m = m;
	}
	
	public void levelUpSystem() {
        if (m.xp >= m.nextXp) {
        	m.xp = 0;
        	m.level++;
        	m.nextXp *= 1.5;
        	m.maxHealth *= 1.2;
        	m.maxMana *= 1.3;
        	m.health = m.maxHealth;
        	m.mana = m.maxMana;
        	m.attributPoint++;
        }
    }

    public void regenerationHpMp() {
        if (m.health != m.maxHealth) {
        	m.timerHealth++;
            if (m.timerHealth >= 50) {
            	m.health += 1 * m.vitality;
                if (m.health >= m.maxHealth) {
                	m.health = m.maxHealth;
                }
                m.timerHealth = 0;
            }
        }

        if (m.mana != m.maxMana) {
        	m.timerMana++;
            if (m.timerMana >= 25) {
            	m.mana += 1 * m.wisdom;
                if (m.mana >= m.maxMana) {
                	m.mana = m.maxMana;
                }
                m.timerMana = 0;
            }
        }
    }
    
    public void attackMonster() {
        for (int i = 0; i < m.monsters.size(); i++) {
            monster = m.monsters.get(i);
            if (Math.abs(monster.x - m.playerX) <= 1 && Math.abs(monster.y - m.playerY) <= 1) {
            	m.monsters.remove(i);
            	m.xp += 10;
                return;
            }
        }
    }
    
    
}
