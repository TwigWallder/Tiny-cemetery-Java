package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entity.Player;
import utils.ColorPerso;

public class UI {

    public boolean pause = false;
    
	Main m;
	Player player;
	ColorPerso cp;
	
	public UI(Main m, Player player, ColorPerso cp) {
		this.m = m;
		this.player = player;
		this.cp = cp;
	}
	
	public void drawUI(Graphics g) {
		g.setFont(new Font("Monospaced", Font.BOLD, 20));
        int uiStartX = m.width * 20 + 100;
        int uiStartY = 40;

        barre(uiStartX, uiStartY, "HP: ", cp.RED_COLOR(255), player.health, player.maxHealth, g);

        barre(uiStartX, uiStartY + 30, "MP: ", cp.CYAN_COLOR(255), player.mana, player.maxMana, g);

        g.setColor(cp.LIGHT_PURPLE_COLOR(255));
        g.drawString("Level: " + player.level, uiStartX, uiStartY + 110);

        barre(uiStartX, uiStartY + 60, "XP: ", cp.YELLOW_COLOR(255), m.xp, player.nextXp, g);

        // Mob information
        int mobInfoOffset = 150;
        g.setColor(cp.PURPLE_COLOR(255));
        g.drawString("Mob:", uiStartX, uiStartY + mobInfoOffset + 30);
        g.drawString("HP: " , uiStartX, uiStartY + mobInfoOffset + 60);
        g.drawString("State: ", uiStartX, uiStartY + mobInfoOffset + 90);
        g.setColor(cp.WHITE_COLOR(255));
        g.drawRect(uiStartX - 5, uiStartY + mobInfoOffset, 300, 105);

        // floor & info
        int infoOffset = 285;
        g.setColor(cp.CYAN_COLOR(255));
        g.drawString("Floor:     " + m.floor, uiStartX, uiStartY + infoOffset + 30);
        g.drawString("Monster:   " + m.mobLeft, uiStartX, uiStartY + infoOffset+ 60);
        g.drawString("Time:      " + m.timeLeft+"s", uiStartX, uiStartY + infoOffset+ 90);
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY + infoOffset, 300, 105);
        
        // log
        int logOffset = 420;
        g.setColor(cp.YELLOW_COLOR(255));
        g.drawString("|Log|" , uiStartX + 100, uiStartY + logOffset + 30);
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY + logOffset, 300, 185);

        // game
        if (m.isInvincible) {
            g.setColor(cp.RED_COLOR(255));
        } else {
            g.setColor(cp.PURPLE_COLOR(255));
        }
        g.drawRect(45, 1 + m.yOffset, 800, 605);
        g.drawRect(46, 2 + m.yOffset, 800, 605);
        g.drawRect(47, 3 + m.yOffset, 800, 605);
        
        
     // stats
        g.setColor(cp.WHITE_COLOR(255));
        g.drawRect(uiStartX - 5, uiStartY - 5, 300, 130);
        if(m.status) { 
        	g.setColor(cp.DARK_PURPLE_COLOR(255));
        	g.fillRect(0, 0, m.width * 50, m.height * 50);
            
           
            g.setColor(cp.WHITE_COLOR(255));
            
            int attributeXOffset = 200;
            g.drawString("| ATTRIBUTES |", (uiStartX /2 + 150)+attributeXOffset, uiStartY);
            g.setColor(cp.CYAN_COLOR(255));
            g.drawString("| NAME |  | LEVEL |   | INPUT |", (uiStartX/2 + 55)+attributeXOffset, uiStartY + 40);
            g.drawString("Attack        " + player.attack +   "          [1]", (uiStartX/2 + 55)+attributeXOffset, uiStartY + 70);
            g.drawString("Defense       " + player.defense +  "          [2]", (uiStartX/2+ 55)+attributeXOffset, uiStartY + 100);
            g.drawString("Vitality      " + player.vitality + "          [3]", (uiStartX/2+ 55)+attributeXOffset, uiStartY + 130);
            g.drawString("Wisdom        " + player.wisdom +   "          [4]", (uiStartX/2+ 55)+attributeXOffset, uiStartY + 160);
            g.drawString("===============================", (uiStartX/2+ 55)+attributeXOffset, uiStartY + 190);

            // metre les informations détailler des attributes
            
            g.setColor(cp.WHITE_COLOR(255));
            g.drawString("| SPELLS |", uiStartX /10 + 135, uiStartY);
            g.setColor(cp.YELLOW_COLOR(255));
            g.drawString("| NAME |    | COST |     | INPUT |", uiStartX/10 + 5, uiStartY + 40);
            g.drawString("Fire Dance    (10)         [A]", uiStartX / 10 + 5, uiStartY + 70);
            g.drawString("FireBall      (20)         [R]", uiStartX / 10 + 5, uiStartY + 100);
            g.drawString("Ice Pick      (25)         [I]", uiStartX / 10 + 5, uiStartY + 130);
            g.drawString("Explosion     (100)        [T]", uiStartX / 10 + 5, uiStartY + 160);
            g.drawString("=================================", (uiStartX/10+ 5), uiStartY + 190);
            
            // INFORMATION
            int infoXOffset = 250;
            g.setColor(cp.WHITE_COLOR(255));
            g.drawString("| INFORMATION |", uiStartX /2 + 55, uiStartY + infoXOffset);
            g.setColor(cp.LIGHT_PURPLE_COLOR(255));
            g.drawString("Class: Wizard", uiStartX /2 + 205, uiStartY + infoXOffset + 50);
            g.drawString("Time Play: ???", uiStartX /2 + 205, uiStartY + infoXOffset + 80);
            g.drawString("Attribute Point: " + player.attributPoint + "", uiStartX /2 + 205, uiStartY + infoXOffset + 110);
            g.drawString("Current Floor: 0", uiStartX /2 + 205, uiStartY + infoXOffset + 140);
            g.drawString("Health: 100/100", uiStartX /2 - 105, uiStartY + infoXOffset + 50);
            g.drawString("Mana: 100/100", uiStartX /2 - 105, uiStartY + infoXOffset + 80);
            g.drawString("Experience: 0/100", uiStartX /2 - 105, uiStartY + infoXOffset + 110);
            g.drawString("Level: 1", uiStartX /2 - 105, uiStartY + infoXOffset + 140);
            g.drawString("=============================================", uiStartX /2 - 105, uiStartY + infoXOffset + 170);
            
            //credits

            g.setColor(cp.PINK_COLOR(255));
            g.drawString("Game made by TwigWallder \\(^o^)/", 20, m.SCREEN_HEIGHT -20);
        }
        // PAUSE
        if(pause) {
        	g.setColor(cp.DARK_PURPLE_COLOR(255));
            g.fillRect(m.getWidth() / 2 - 130, m.getHeight() / 2 - 55, m.width*5 - 5, m.height*3 - 5);
            g.setColor(cp.YELLOW_COLOR(255));

            g.drawString("Tips: Are you winning son?", 50, (m.getHeight() - 90) + m.yOffset);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString(" _____ ", m.getWidth() / 2 - 120, m.getHeight() / 2 - 45);
            g.drawString("|PAUSE|", m.getWidth() / 2 - 120, m.getHeight() / 2);
            g.drawString(" _____ ", m.getWidth() / 2 - 120, m.getHeight() / 2 );
        }
        
        // GAME OVER
        if (m.gameOver) {
            g.setColor(cp.DARK_PURPLE_COLOR(255));
            g.fillRect(0, 0, m.width * 50, m.height * 50);
            g.setColor(cp.RED_COLOR(255));
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString("GAME OVER", m.getWidth() / 2 - 100, m.getHeight() / 2);
        }
    }

    private void barre(int x, int y, String type, Color color, int val, int valMax, Graphics g) {

        g.setColor(color);
        g.drawString(type, x, y + 15);
        g.fillRect(x + 50, y, val * 200 / valMax, 20);
        g.setColor(cp.WHITE_COLOR(255));
        g.drawRect(x + 50, y, 200, 20);

        // background
        g.setColor(cp.DARK_PURPLE_COLOR(255));
        g.drawString(val + "/" + valMax, x + 100, y + 18);

        g.setColor(cp.WHITE_COLOR(255));
        g.drawString(val + "/" + valMax, x + 100, y + 16);
    }
}