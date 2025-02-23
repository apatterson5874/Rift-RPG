package apatterson.textrpg.main;

import java.util.ArrayList;

public abstract class Character {

    //Variables / Attributes
    public String name;
    public int maxHp, hp, xp;

    //attributes for class system
    protected int maxMana, mana, maxManaShield, manaShield;
    protected ArrayList<Ability> abilities;

    //constructor for character
    public Character(String name, int maxHp, int xp, int maxMana, int maxManaShield){
        this.name = name;
        this.maxHp = maxHp;
        this.xp = xp;
        this.hp = maxHp;
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.maxManaShield = maxManaShield;
        this.manaShield = maxManaShield;
        this.abilities = new ArrayList<>();
    }

    //methods for all characters
    public abstract int attack();
    public abstract int defend();

    public void restoreMana(int amount){
        mana = Math.min(maxMana, mana + amount);
    }
}
