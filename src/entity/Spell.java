package entity;


import main.Main;

public class Spell {

	// A TRAVAILLER DESSUS T.T
	public int spellId;
	public int damage;
	public int cost;
	Main m;
	
	public Spell(Main m) {
		this.m = m;
	}
	
	public void spellCard(int spellId, int damage, int cost) {
		
	}
	
	private void charge() {
		spellId = 0;
		damage = 1;
		cost = 5;
	}
}
