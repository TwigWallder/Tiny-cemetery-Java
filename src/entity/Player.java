package entity;

import main.Main;
import utils.LogsMessage;

public class Player {

    public int maxHealth = 100;
    public int maxMana = 100;
    public int health = maxHealth;
    public int mana = maxMana;
    public int level = 1;
    public int nextXp = 100;
    public int attack = 1;
    public int defense = 1;
    public int vitality = 1;
    public int wisdom = 1;
    public int attributPoint = 1;

	Main m;
	Mob mob;
	
	public Player(Main m,Mob mob) {
		this.m = m;
		this.mob = mob;
	}
	
	public void update() {
        levelUpSystem();
        regenerationHpMp();
        m.grid[m.playerY][m.playerX] = '@';
        
        if (m.isInvincible) {
            m.invincibilityTimer--;
            if (m.invincibilityTimer <= 0) {
                m.isInvincible = false;
            }
        }
        // a faire (ou pas)
        if (mana <= 0) {
            mana = 0;
        }
	}
	
	public void levelUpSystem() {
        if (m.xp >= nextXp) {
        	m.xp = 0;
        	level++;
        	nextXp *= 1.5;
        	maxHealth *= 1.2;
        	maxMana *= 1.3;
        	health = maxHealth;
        	mana = maxMana;
        	attributPoint++;
        }
    }

    public void regenerationHpMp() {
        if (health != maxHealth && !m.isInvincible) {
        	m.timerHealth++;
            if (m.timerHealth >= 50) {
            	health += 1 * vitality;
                if (health >= maxHealth) {
                	health = maxHealth;
                }
                m.timerHealth = 0;
            }
        }

        if (mana != maxMana) {
        	m.timerMana++;
            if (m.timerMana >= 25) {
            	mana += 1 * wisdom;
                if (mana >= maxMana) {
                	mana = maxMana;
                }
                m.timerMana = 0;
            }
        }
    }
    
    // ATTACK
    public void fireDance(int damage) {
        for (int i = 0; i < mob.mobs.size(); i++) {
            Mob mob1 = mob.mobs.get(i);
            if (Math.abs(mob1.x - m.playerX) <= 1 && Math.abs(mob1.y - m.playerY) <= 1) {
            	mob1.hp -= attack + damage;
            	LogsMessage.log("Le joueur a attaqué un mob avec Fire Dance infligeant " + (attack + damage) + " points de dégâts.");
            }
        }
    }
    
    public void meteor(int damage) {
        for (int i = 0; i < mob.mobs.size(); i++) {
            Mob mob1 = mob.mobs.get(i);
            if (Math.abs(mob1.x - m.playerX) <= 3 && Math.abs(mob1.y - m.playerY) <= 3) {
            	mob1.hp -= attack + damage;
            	LogsMessage.log("Le joueur a attaqué un mob avec Meteor infligeant " + (attack + damage) + " points de dégâts.");
            }
        }
    }
    
    public void fireWall(int damage) {
        for (int i = 0; i < mob.mobs.size(); i++) {
            Mob mob1 = mob.mobs.get(i);
            if (Math.abs(mob1.y - m.playerY) <= 1){
            	mob1.hp -= attack + damage;
            	LogsMessage.log("Le joueur a attaqué un mob avec Fire Wall infligeant " + (attack + damage) + " points de dégâts.");
            }
        }
    }
    
    public void explosion(int damage) {
        for (int i = 0; i < mob.mobs.size(); i++) {
            Mob mob1 = mob.mobs.get(i);
            if (Math.cos(mob1.x - m.playerX) <= 100) {
            	mob1.hp -= attack + damage;
            	LogsMessage.log("Le joueur a attaqué un mob avec Explosion infligeant " + (attack + damage) + " points de dégâts.");
            }
        }
    }
}