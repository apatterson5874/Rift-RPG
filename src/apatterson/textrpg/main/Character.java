package apatterson.textrpg.main;

public abstract class Character {

    //Variables / Attributes
    public String name;
    public int maxHp, hp, xp;

    //constructor for character
    public Character(String name, int maxHp, int xp){
        this.name = name;
        this.maxHp = maxHp;
        this.xp = xp;
        this.hp = maxHp;
    }

    //methods for all characters
    public abstract int attack();
    public abstract int defend();
}
