package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entity.Player;

public class UI {

    public boolean pause = false;
    
	Main m;
	Player player;
	
	public UI(Main m, Player player) {
		this.m = m;
		this.player = player;
	}
	
	public void drawUI(Graphics g) {
		g.setFont(new Font("Monospaced", Font.BOLD, 20));
        int uiStartX = m.width * 20 + 100;
        int uiStartY = 40;

        barre(uiStartX, uiStartY, "HP: ", Color.RED, player.health, player.maxHealth, g);

        barre(uiStartX, uiStartY + 30, "MP: ", Color.cyan, player.mana, player.maxMana, g);

        g.setColor(Color.GREEN);
        g.drawString("Level: " + player.level, uiStartX, uiStartY + 110);

        barre(uiStartX, uiStartY + 60, "XP: ", Color.yellow, m.xp, player.nextXp, g);

        // Mob information
        g.setColor(Color.getHSBColor(0, 290, 100));
        g.drawString("Mob:", uiStartX, uiStartY + 330);
        g.drawString("HP: " , uiStartX, uiStartY + 360);
        g.drawString("State: ", uiStartX, uiStartY + 390);
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY + 300, 300, 105);

        // floor & info
        g.setColor(Color.getHSBColor(0, 290, 100));
        g.drawString("Info:    Count:", uiStartX, uiStartY + 520);
        g.drawString("Floor:     " + m.floor, uiStartX, uiStartY + 550);
        g.drawString("Monster:   " + m.mobLeft, uiStartX, uiStartY + 575);
        g.drawString("Time:      " + m.timeLeft+"s", uiStartX, uiStartY + 600);
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY + 500, 300, 105);

        // game
        if (m.isInvincible) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.DARK_GRAY);
        }
        g.drawRect(45, 1 + m.yOffset, 800, 605);
        g.drawRect(46, 2 + m.yOffset, 800, 605);
        g.drawRect(47, 3 + m.yOffset, 800, 605);
        
        
     // stats
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY - 5, 300, 130);
        if(m.status) { 
        	g.setColor(Color.black);
        	g.fillRect(0, 0, m.width * 50, m.height * 50);
            
           
            g.setColor(Color.white);
            
            int attributeXOffset = 200;
            g.drawString("| ATTRIBUTES |", (uiStartX /2 + 150)+attributeXOffset, uiStartY);
            g.setColor(new Color(100, 100, 150, 255));
            g.drawString("| NAME |  | LEVEL |   | INPUT |", (uiStartX/2 + 55)+attributeXOffset, uiStartY + 40);
            g.drawString("Attack        " + player.attack +   "          [1]", (uiStartX/2 + 55)+attributeXOffset, uiStartY + 70);
            g.drawString("Defense       " + player.defense +  "          [2]", (uiStartX/2+ 55)+attributeXOffset, uiStartY + 100);
            g.drawString("Vitality      " + player.vitality + "          [3]", (uiStartX/2+ 55)+attributeXOffset, uiStartY + 130);
            g.drawString("Wisdom        " + player.wisdom +   "          [4]", (uiStartX/2+ 55)+attributeXOffset, uiStartY + 160);
            g.drawString("===============================", (uiStartX/2+ 55)+attributeXOffset, uiStartY + 190);
            g.setColor(new Color(150, 100, 100, 255));
            g.drawString("Attribute Point: " + player.attributPoint + "", (uiStartX/2+130)+attributeXOffset, uiStartY + 210);

            // metre les informations détailler des attributes
            
            g.setColor(Color.white);
            g.drawString("| SPELLS |", uiStartX /10 + 135, uiStartY);
            g.setColor(new Color(150, 150, 50, 255));
            g.drawString("| NAME |    | COST |     | INPUT |", uiStartX/10 + 5, uiStartY + 40);
            g.drawString("Fire Dance    (10)         [A]", uiStartX / 10 + 5, uiStartY + 70);
            g.drawString("FireBall      (20)         [R]", uiStartX / 10 + 5, uiStartY + 100);
            g.drawString("Ice Pick      (25)         [I]", uiStartX / 10 + 5, uiStartY + 130);
            g.drawString("Explosion     (100)        [T]", uiStartX / 10 + 5, uiStartY + 160);
            g.drawString("=================================", (uiStartX/10+ 5), uiStartY + 190);
            
            // metre les informations détailler des spells
        }
        // pause
        if(pause) {
        	g.setColor(Color.BLACK);
            g.fillRect(m.getWidth() / 2 - 130, m.getHeight() / 2 - 55, m.width*5 - 5, m.height*3 - 5);
            g.setColor(Color.YELLOW);

            g.drawString("Tips: Are you winning son?", 50, (m.getHeight() - 90) + m.yOffset);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString(" _____ ", m.getWidth() / 2 - 120, m.getHeight() / 2 - 45);
            g.drawString("|PAUSE|", m.getWidth() / 2 - 120, m.getHeight() / 2);
            g.drawString(" _____ ", m.getWidth() / 2 - 120, m.getHeight() / 2 );
        }
        
        // game over
        if (m.gameOver) {
            g.setColor(Color.black);
            g.fillRect(0, 0, m.width * 50, m.height * 50);
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString("GAME OVER", m.getWidth() / 2 - 100, m.getHeight() / 2);
        }
    }

    private void barre(int x, int y, String type, Color color, int val, int valMax, Graphics g) {

        g.setColor(color);
        g.drawString(type, x, y + 15);
        g.fillRect(x + 50, y, val * 200 / valMax, 20);
        g.setColor(Color.WHITE);
        g.drawRect(x + 50, y, 200, 20);

        // background
        g.setColor(Color.black);
        g.drawString(val + "/" + valMax, x + 100, y + 18);

        g.setColor(Color.lightGray);
        g.drawString(val + "/" + valMax, x + 100, y + 16);
    }
}