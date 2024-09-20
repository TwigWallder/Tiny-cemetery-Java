package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UI {
	
	Main m;
	
	public UI(Main m) {
		this.m = m;
	}
	
	public void drawUI(Graphics g) {
		g.setFont(new Font("Monospaced", Font.BOLD, 20));
        int uiStartX = m.width * 20 + 100;
        int uiStartY = 40;

        barre(uiStartX, uiStartY, "HP: ", Color.RED, m.health, m.maxHealth, g);

        barre(uiStartX, uiStartY + 30, "MP: ", Color.cyan, m.mana, m.maxMana, g);

        g.setColor(Color.GREEN);
        g.drawString("Level: " + m.level, uiStartX, uiStartY + 110);

        barre(uiStartX, uiStartY + 60, "XP: ", Color.yellow, m.xp, m.nextXp, g);

        // Display stats       
        g.setColor(Color.getHSBColor(100, 100, 100));
        g.drawString("Status:  Lvl:  Input:", uiStartX, uiStartY + 170);
        g.drawString("Attack:   " + m.attack + "     [1]", uiStartX, uiStartY + 200);
        g.drawString("Defense:  " + m.defense + "     [2]", uiStartX, uiStartY + 220);
        g.drawString("Vitality: " + m.vitality + "     [3]", uiStartX, uiStartY + 240);
        g.drawString("Wisdom:   " + m.wisdom + "     [4]", uiStartX, uiStartY + 260);
        g.drawString("========================", uiStartX, uiStartY + 285);
        g.drawString("Attribut Point: " + m.attributPoint + "", uiStartX, uiStartY + 300);
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY + 150, 300, 165);
        // stats
        g.drawRect(uiStartX - 5, uiStartY - 5, 300, 130);

        // floor & info
        g.setColor(Color.getHSBColor(0, 290, 100));
        g.drawString("Info:    Count:", uiStartX, uiStartY + 520);
        g.drawString("Floor:     " + m.floor, uiStartX, uiStartY + 550);
        g.drawString("Monster:   " + m.monsterLeft, uiStartX, uiStartY + 575);
        g.drawString("Time:      " + m.timeLeft+"s", uiStartX, uiStartY + 600);
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY + 500, 300, 105);

        // game
        if (m.isInvincible) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.DARK_GRAY);
        }
        g.drawRect(45, 1, 800, 605);
        g.drawRect(46, 2, 800, 605);
        g.drawRect(47, 3, 800, 605);

        // Display Spell
        g.setColor(Color.lightGray);
        g.drawString("Spell:   Cost:  Input:", uiStartX, uiStartY + 370);
        g.drawString("Charge    (5)    [A]", uiStartX, uiStartY + 400);
        g.drawString("FireBall  (20)   [R]", uiStartX, uiStartY + 420);
        g.drawString("Ice Pick  (25)   [I]", uiStartX, uiStartY + 440);
        g.drawString("Explosion (100)  [T]", uiStartX, uiStartY + 460);
        g.setColor(Color.white);
        g.drawRect(uiStartX - 5, uiStartY + 350, 300, 120);
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
