package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import entity.Player;

public class InputHandler extends KeyAdapter{
	Main m;
	Player player;
	
	public InputHandler(Main m, Player player) {
		this.m = m;
		this.player = player;
	}
	
	@Override
    public void keyPressed(KeyEvent e) {
        if (m.gameOver) return;

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && m.playerX > 1) {
        	m.playerX--;
        }
        if (key == KeyEvent.VK_RIGHT && m.playerX < m.width - 2) {
        	m.playerX++;
        }
        if (key == KeyEvent.VK_UP && m.playerY > 1) {
        	m.playerY--;
        }
        if (key == KeyEvent.VK_DOWN && m.playerY < m.height - 2) {
        	m.playerY++;
        }

        // DEBUG INPUT
       if (key == KeyEvent.VK_H) {
            if (!m.isInvincible) {
            	m.health = Math.max(m.health - 10, 0);
            	m.isInvincible = true;
            	m.invincibilityTimer = m.INVINCIBILITY_DURATION;

                if (m.health == 0) {
                	m.gameOver = true;
                }
            }
        }
        // god mode
        if (key == KeyEvent.VK_G) {
        	m.maxHealth = 999999;
        	m.health = m.maxHealth;
        }
        if (key == KeyEvent.VK_M) {
        	m.mana = Math.max(m.mana - 5, 0);
        }
        if (key == KeyEvent.VK_X) {
        	m.xp = Math.max(m.xp + 5, 0);
        }
        if (key == KeyEvent.VK_A) {
        	player.attackMonster();
        	m.mana -= 5;
            if (m.mana <= 0) {
            	m.mana = 0;
            }
        }
        if (m.attributPoint > 0) {
            switch (key) {
                case KeyEvent.VK_1:
                	m.attack++;
                	m.attributPoint--;
                    break;
                case KeyEvent.VK_2:
                	m.defense++;
                	m.attributPoint--;
                    break;
                case KeyEvent.VK_3:
                	m.vitality++;
                	m.attributPoint--;
                    break;
                case KeyEvent.VK_4:
                	m.wisdom++;
                	m.attributPoint--;
                    break;
            }

        }
    }
}