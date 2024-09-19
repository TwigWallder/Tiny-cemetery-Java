package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter{
	Main m;
	
	public InputHandler(Main m) {
		this.m = m;
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
       /* if (key == KeyEvent.VK_H) {
            if (!isInvincible) {
                health = Math.max(health - 10, 0);
                isInvincible = true;
                invincibilityTimer = INVINCIBILITY_DURATION;

                if (health == 0) {
                    gameOver = true;
                }
            }
        }
        // god mode
        if (key == KeyEvent.VK_G) {
            maxHealth = 999999;
            health = maxHealth;
        }
        if (key == KeyEvent.VK_M) {
            mana = Math.max(mana - 5, 0);
        }
        if (key == KeyEvent.VK_X) {
            xp = Math.max(xp + 5, 0);
        }*/
        if (key == KeyEvent.VK_A) {
        	m.attackMonster();
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
