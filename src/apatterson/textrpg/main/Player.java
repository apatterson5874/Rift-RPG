package apatterson.textrpg.main;

import java.util.ArrayList;

public class Player extends Character{

    //integers to store number of upgrades/skills in each path
    public int numAtkUpgrades, numDefUpgrades;

    //additional player stats
    int gold, restsLeft, pots;

    //Arrays to store skill names
    public String[] atkUpgrades = {"Strength", "Power", "Might", "Godlike Strength"};
    public String[] defUpgrades = {"Heavy Bones", "Stoneskin", "Scale Armor", "Holy Aura"};

    //equipment
    private Item weapon;
    private Item armor;
    private Item accessory;
    public ArrayList<Item> inventory;

    //Player specific constructor
    public Player(String name){
        //calling constructor of superclass
        super(name, 100, 0);

        //setting # of upgrades to 0
        this.numAtkUpgrades = 0;
        this.numDefUpgrades = 0;

        //set additional stats
        this.gold = 5;
        this.restsLeft = 1;
        this.pots = 0;

        //initial starting equipment and inventory initialization
        this.inventory = new ArrayList<>();
        this.weapon = new Item("Wooden Sword", "weapon", 2, 0, 0, 0);
        this.armor = new Item("Cloth Armor", "armor", 0, 1, 5, 0);
        this.accessory = null;

        //let the player choose a trait when creating a character
        chooseTrait();

    }


    //Player specific methods
    @Override
    public int attack() {
        int equipmentBonus = 0;
        if(weapon != null)
            equipmentBonus += weapon.atkBonus;
        if(armor != null)
            equipmentBonus += armor.atkBonus;
        if(accessory != null)
            equipmentBonus += accessory.atkBonus;
        return (int) (Math.random()*(xp/5 + numAtkUpgrades*2 + 3) + xp/10 + equipmentBonus);
    }

    @Override
    public int defend() {
        int equipmentBonus = 0;
        if(weapon != null)
            equipmentBonus += weapon.defBonus;
        if(armor != null)
            equipmentBonus += armor.defBonus;
        if(accessory != null)
            equipmentBonus += accessory.defBonus;
        return (int) (Math.random()*(xp/4 + numDefUpgrades*3) + xp/10  + equipmentBonus);
    }

    //get total hp bonus from equipment
    public int getEquipmentHpBonus() {
        int bonus = 0;
        if(weapon != null)
            bonus += weapon.hpBonus;
        if(armor != null)
            bonus += armor.hpBonus;
        if(accessory != null)
            bonus += accessory.hpBonus;
        return bonus;
    }

    //method to equip item
    public void equipItem(Item item){
        if(item == null)
            return;

        //remove old item's HP bonus before equipping new one
        int oldHpBonus = getEquipmentHpBonus();

        switch (item.type){
            case "weapon":
                if(weapon != null)
                    inventory.add(weapon);
                weapon = item;
                break;
            case "armor":
                if(armor != null)
                    inventory.add(armor);
                armor = item;
                break;
            case "accessory":
                if(accessory != null)
                    inventory.add(accessory);
                accessory = item;
                break;
        }

        //adjust current HP proportionally when max HP changes
        int newHpBonus = getEquipmentHpBonus();
        maxHp = 100 + newHpBonus; //Base HP (100) + equipment bonus
        hp = (int)((hp / (100.0 + oldHpBonus)) * maxHp);
    }

    //method to get currently equipped items for display
    public String getEquipmentString(){
        return "Weapon: " + (weapon != null ? weapon.name : "None") + "\n" +
                "Armor: " + (armor != null ? armor.name : "None") + "\n" +
                "Accessory: " + (accessory != null ? accessory.name : "None") + "\n";
    }

    //let the player choose a trait of either skill path
    public void chooseTrait(){
        GameLogic.clearConsole();
        GameLogic.printHeading("Choose a trait:");
        System.out.println("(1) " + atkUpgrades[numAtkUpgrades]);
        System.out.println("(2) " + defUpgrades[numDefUpgrades]);

        //get players choice
        int input = GameLogic.readInt("-> ", 2);
        GameLogic.clearConsole();

        //deal with both cases
        if(input == 1){
            GameLogic.printHeading("You chose " + atkUpgrades[numAtkUpgrades]);
            numAtkUpgrades++;
        }else{
            GameLogic.printHeading("You chose " + defUpgrades[numDefUpgrades]);
            numDefUpgrades++;
        }
        GameLogic.anythingToContinue();
    }
}
