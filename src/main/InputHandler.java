package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import entity.Player;
import spell.AnimationSpell;
import utils.LogsMessage;

public class InputHandler extends KeyAdapter {
    Main m;
    Player player;
    UI ui;
    AnimationSpell as;

    public InputHandler(Main m, Player player, UI ui, AnimationSpell as) {
        this.m = m;
        this.player = player;
        this.ui = ui;
        this.as = as;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (m.gameOver) return;
        // A LAIDE C TROP POUR MOI AAAAAAh
        int gridRight = m.grid[m.playerY][m.playerX + 1];
        int gridLeft = m.grid[m.playerY][m.playerX - 1];
        int gridDown = m.grid[m.playerY + 1][m.playerX];
        int gridUp = m.grid[m.playerY - 1][m.playerX];
        LogsMessage lm = new LogsMessage();
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && m.playerX > 1 && gridLeft != '#') {
            m.playerX--;
        }
        if (key == KeyEvent.VK_RIGHT && m.playerX < m.width - 2 && gridRight != '#' ) {
            m.playerX++;
        }
        if (key == KeyEvent.VK_UP && m.playerY > 1 && gridUp != '#') {
            m.playerY--;
        }
        if (key == KeyEvent.VK_DOWN && m.playerY < m.height - 2 && gridDown != '#') {
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
        
        if(key == KeyEvent.VK_ESCAPE && !m.status) {
        	m.status = true;
        } else {
        	m.status = false;
        }
        // god mode
        if (key == KeyEvent.VK_G) {
            player.maxHealth = 999999;
            player.health = player.maxHealth;
            player.maxMana = 999999;
            player.mana = player.maxMana;
        }
        if (key == KeyEvent.VK_M) {
            player.mana = Math.max(player.mana - 5, 0);
        }
        if (key == KeyEvent.VK_X) {
            m.xp = Math.max(m.xp + 5, 0);
        }

        // SPELL 
        
        // fire dance attack
        if(player.mana > 10) {
        	if (key == KeyEvent.VK_A) {
                    player.fireDance(0);
                    player.mana -= 10;
                // for fire dance spell
                as.animationFD = true;
                as.animationFDTime = System.currentTimeMillis();
            }
        }
              
        // meteor attack
        if(player.mana > 20) {
        	if (key == KeyEvent.VK_Z) {
            		player.meteor(5);
            		player.mana -= 20;
                as.animationM = true;
                as.animationMTime = System.currentTimeMillis();
            }
        }
               
        // FireWall attack
        if(player.mana > 35) {
        	if (key == KeyEvent.VK_E) {
            		player.fireWall(3);
            		player.mana -= 35;
                as.animationFW = true;
                as.animationFWTime = System.currentTimeMillis();
            }
        }
        
        // explosion attack
        if(player.mana > 100) {
            if (key == KeyEvent.VK_R) {
            		player.explosion(10);
            		player.mana -= 100;
            		player.health /= 2;
                as.animationE = true;
                as.animationETime = System.currentTimeMillis();
            }
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
