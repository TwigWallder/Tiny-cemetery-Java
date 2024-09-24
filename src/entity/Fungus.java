package entity;

import main.Main;

public class Fungus extends Mob {
	
	/*
	 *  Caractéristique:
	 *  Peu de vie
	 *  Se déplace lentement
	 *  Fait peu de dégat
	 */

    public Fungus(int x, int y, Main m, Player player) {
        super(x, y, m, player);
        this.mobChar = 'F';  
        this.hp = 1;  
        this.moveInterval = 70;
        this.mobGiveXp = 5; 
    }

    @Override
    public void attacking() {
        int result = rand.nextInt(m.mobDamage / 2) - player.defense;  
        if (result >= 0) {
            player.health -= result;
        } else {
            player.health--;
        }
    }
}
