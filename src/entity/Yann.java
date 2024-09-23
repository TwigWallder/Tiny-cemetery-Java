package entity;

import main.Main;

public class Yann extends Mob {
	
	/*
	 *  Caractéristique:
	 *  Vie correct
	 *  Se déplace "normalement"
	 *  Fait peu des dégat importants
	 *  régen ça vie (regagne la moitier des dégat infligé)
	 */

    public Yann(int x, int y, Main m, Player player) {
        super(x, y, m, player);
        this.mobChar = 'Y';  
        this.hp = 3; 
        this.moveInterval = 50;  
        this.mobGiveXp = 50;
    }

    @Override
    public void attacking() {
        int result = rand.nextInt(m.mobDamage + 3) - player.defense; 
        if (result >= 0) {
            player.health -= result;
            this.hp += result / 2;
        } else {
        	// if attack miss
            player.health--;
            this.hp += 1; 
        }
    }
}
