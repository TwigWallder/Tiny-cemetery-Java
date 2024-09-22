package entity;

import main.Main;

public class Skeleton extends Mob {
	
	/*
	 *  Caractéristique:
	 *  Peu de vie
	 *  Se déplace rapidement
	 *  Fait peu de dégat
	 */

    public Skeleton(int x, int y, Main m, Player player) {
        super(x, y, m, player);
        this.mobChar = 'S';  
        this.hp = 1;  
        this.moveInterval = 30; 
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
