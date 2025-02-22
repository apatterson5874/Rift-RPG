package apatterson.textrpg.main;

public class Enemy extends Character{

    //variable to store the players current xp
    int playerXp;

    //enemy specific constructor
    public Enemy(String name, int playerXp){
        super(name, (int) (Math.random()*playerXp + playerXp/3 + 5), (int) (Math.random()*(playerXp/4 + 2) + 1));

        //assigning variable
        this.playerXp = playerXp;
    }


    //Enemy specific attack and defense calculations
    @Override
    public int attack() {
        if(GameLogic.act == 1)
            return (int) (Math.random()*(playerXp/4 + 1) + xp/4 + 3);
        if(GameLogic.act == 2)
            return (int) (Math.random()*(playerXp/4 + 1) + xp/2 + 3);
        if(GameLogic.act == 3)
            return (int) (Math.random()*(playerXp/2 + 1) + xp + 3);
        else {
            return (int) (Math.random()*(playerXp/2 + 1) + xp);
        }
    }

    @Override
    public int defend() {
        if(GameLogic.act == 1)
            return (int) (Math.random()*(playerXp/4 + 1));
        if(GameLogic.act == 2)
            return (int) (Math.random()*(playerXp/2 + 3));
        if(GameLogic.act == 3)
            return (int) (Math.random()*(playerXp/2 + 5));
        else {
            return (int) (Math.random()*(playerXp + 9));
        }
    }
}
