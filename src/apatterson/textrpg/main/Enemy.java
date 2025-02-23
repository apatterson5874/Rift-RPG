package apatterson.textrpg.main;

public class Enemy extends Character{

    //variable to store the players current xp
    int playerXp;

    //enemy specific constructor
    public Enemy(String name, int playerXp){
        super(name, (int) (Math.random()*playerXp + playerXp/3 + 5), (int) (Math.random()*(playerXp/4 + 2) + 1), 0, 0);

        //assigning variable
        this.playerXp = playerXp;
    }


    //Enemy specific attack and defense calculations
    @Override
    public int attack() {
        int baseAtk = (int) (Math.random()*(playerXp/4 + 1) + playerXp/4 + 3);

        double actMultiplier = switch(GameLogic.act){
            case 1 -> 1.0;
            case 2 -> 1.2;
            case 3 -> 1.4;
            case 4 -> 1.6;
            default -> 1.0;
        };

        double randomFactor = 0.9 + (Math.random() * 0.2);

        return (int) (baseAtk * actMultiplier * randomFactor);
    }

    @Override
    public int defend() {
        int baseDef = (int) (Math.random() * (playerXp/5 + 1) + playerXp/5 + 2);

        double actMultiplier = switch(GameLogic.act) {
            case 1 -> 1.0;
            case 2 -> 1.15;
            case 3 -> 1.3;
            case 4 -> 1.5;
            default -> 1.0;
        };

        double randomFactor = 0.8 + (Math.random() * 0.2);

        return (int) (baseDef * actMultiplier * randomFactor);
    }
}
