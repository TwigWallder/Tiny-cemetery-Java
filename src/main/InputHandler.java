package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import entity.Player;

public class InputHandler extends KeyAdapter {
    Main m;
    Player player;
    UI ui;

    public InputHandler(Main m, Player player, UI ui) {
        this.m = m;
        this.player = player;
        this.ui = ui;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (m.gameOver) return;

        int gridRight = m.grid[m.playerY][m.playerX + 1];
        int gridLeft = m.grid[m.playerY][m.playerX - 1];
        int gridDown = m.grid[m.playerY + 1][m.playerX];
        int gridUp = m.grid[m.playerY - 1][m.playerX];

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && m.playerX > 1 && gridLeft != '#' && gridLeft != 'M') {
            m.playerX--;
        }
        if (key == KeyEvent.VK_RIGHT && m.playerX < m.width - 2 && gridRight != '#' && gridRight != 'M') {
            m.playerX++;
        }
        if (key == KeyEvent.VK_UP && m.playerY > 1 && gridUp != '#' && gridUp != 'M') {
            m.playerY--;
        }
        if (key == KeyEvent.VK_DOWN && m.playerY < m.height - 2 && gridDown != '#' && gridDown != 'M') {
            m.playerY++;
        }
        if (key == KeyEvent.VK_P && !ui.pause) {
            ui.pause = true;
        } else {
            ui.pause = false;
        }

        // DEBUG INPUT
        if (key == KeyEvent.VK_F1) {
            m.scale++;
        }
        if (key == KeyEvent.VK_F2) {
            m.scale--;
        }
        if (key == KeyEvent.VK_H) {
            if (!m.isInvincible) {
                player.health = Math.max(player.health - 10, 0);
                m.isInvincible = true;
                m.invincibilityTimer = m.INVINCIBILITY_DURATION;

                if (player.health == 0) {
                    m.gameOver = true;
                }
            }
        }
        
        if(key == KeyEvent.VK_R && !m.status) {
        	m.status = true;
        } else {
        	m.status = false;
        }
        // god mode
        if (key == KeyEvent.VK_G) {
            player.maxHealth = 999999;
            player.health = player.maxHealth;
        }
        if (key == KeyEvent.VK_M) {
            player.mana = Math.max(player.mana - 5, 0);
        }
        if (key == KeyEvent.VK_X) {
            m.xp = Math.max(m.xp + 5, 0);
        }

        // fire dance attack
        if (key == KeyEvent.VK_A) {
            if (player.mana >= 5) {
                player.attackMonster(0);
                player.mana -= 5;
            }
            if (player.mana <= 0) {
                player.mana = 0;
            }

            // for fire dance spell
            m.transformCommas = true;
            m.transformStartTime = System.currentTimeMillis();
        }

        if (player.attributPoint > 0) {
            switch (key) {
                case KeyEvent.VK_1:
                    player.attack++;
                    player.attributPoint--;
                    break;
                case KeyEvent.VK_2:
                    player.defense++;
                    player.attributPoint--;
                    break;
                case KeyEvent.VK_3:
                    player.vitality++;
                    player.attributPoint--;
                    break;
                case KeyEvent.VK_4:
                    player.wisdom++;
                    player.attributPoint--;
                    break;
            }
        }
    }
}
