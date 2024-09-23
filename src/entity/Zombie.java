package entity;

import main.Main;

public class Zombie extends Mob {
	
	/*
	 *  Caractéristique:
	 *  Beaucoup de vie
	 *  Se déplace lentement
	 *  Fait des dégats correct
	 */

    public Zombie(int x, int y, Main m, Player player) {
        super(x, y, m, player);
        this.mobChar = 'Z';  
        this.hp = 4;  
        this.moveInterval = 70; 
        this.mobGiveXp = 30;
    }

    @Override
    public void attacking() {
        int result = rand.nextInt(m.mobDamage + 5) - player.defense;  
        if (result >= 0) {
            player.health -= result;
        } else {
            player.health--;
        }
    }
}
