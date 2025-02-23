package apatterson.textrpg.main;

public abstract class Ability {
    protected String name;
    protected String description;
    protected int manaCost;

    public Ability(String name, String description, int manaCost) {
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
    }

    public abstract int execute(Character user, Character target);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getManaCost() {
        return manaCost;
    }
}
