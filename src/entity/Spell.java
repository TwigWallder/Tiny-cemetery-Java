package entity;

import java.util.ArrayList;
import java.util.List;
import main.Main;

public class Spell {

    public int spellId;
    public String name;
    public int damage;
    public int cost;
    Main m;

    public Spell(Main m, int spellId, String name, int damage, int cost) {
        this.m = m;
        this.spellId = spellId;
        this.name = name;
        this.damage = damage;
        this.cost = cost;
    }

    public static List<Spell> getSpellList(Main m) {
        List<Spell> spells = new ArrayList<>();

        spells.add(new Spell(m, 1, "Charge", 10, 5));      
        spells.add(new Spell(m, 2, "Fireball", 50, 20));    
        spells.add(new Spell(m, 3, "Ice Pick", 35, 25));   
        spells.add(new Spell(m, 4, "Explosion", 200, 100)); 

        return spells;
    }

   /* DEBUG 
    @Override
    public String toString() {
        return "Spell: " + name + " | Damage: " + damage + " | Mana Cost: " + cost;
    }

    public static void main(String[] args) {
        Main mainInstance = new Main(); 
        List<Spell> spells = getSpellList(mainInstance);

        // Display all spells
        for (Spell spell : spells) {
            System.out.println(spell);
        }
    }*/
}