package apatterson.textrpg.main;

import java.util.ArrayList;

public class Player extends Character{

    //integers to store number of upgrades/skills in each path
    public int numAtkUpgrades, numDefUpgrades;

    //additional player stats
    int gold, restsLeft, pots;

    //equipment
    private Item weapon;
    private Item armor;
    private Item accessory;
    public ArrayList<Item> inventory;

    //class specific
    public PlayerClass playerClass;
    public int strength;
    public int intelligence;
    public int dexterity;
    public int constitution;

    //Player specific constructor
    public Player(String name, PlayerClass playerClass) {
        //calling constructor of superclass
        super(name, 100, 0, getBaseMana(playerClass), getBaseManaShield(playerClass));
        this.playerClass = playerClass;
        initializeClassStats();
        learnClassAbilities();

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

    private static int getBaseMana(PlayerClass playerClass) {
        switch (playerClass) {
            case MAGE: return 100;
            case CLERIC: return 80;
            case ROGUE: return 60;
            case WARRIOR: return 40;
            default: return 50;
        }
    }

    private static int getBaseManaShield(PlayerClass playerClass) {
        switch (playerClass) {
            case MAGE: return 100;
            case CLERIC: return 75;
            case ROGUE: return 60;
            case WARRIOR: return 25;
            default: return 50;
        }
    }

    private void initializeClassStats(){
        switch(playerClass){
            case WARRIOR:
                strength = 8;
                intelligence = 2;
                dexterity = 5;
                constitution = 7;
                break;
            case MAGE:
                strength = 2;
                intelligence = 8;
                dexterity = 4;
                constitution = 4;
                break;
            case ROGUE:
                strength = 5;
                intelligence = 3;
                dexterity = 8;
                constitution = 4;
                break;
            case CLERIC:
                strength = 4;
                intelligence = 6;
                dexterity = 3;
                constitution = 7;
                break;
        }
    }

    private void learnClassAbilities(){
        switch(playerClass){
            case WARRIOR:
                abilities.add(new Ability("Heroic Strike", "A powerful physical attack", 20) {
                    @Override
                    public int execute(Character user, Character target) {
                        int damage = ((Player)user).strength * 2 + user.attack();
                        int finalDamage = Math.max(1, damage - target.defend());
                        target.hp -= finalDamage;
                        return finalDamage;
                    }
                });
                break;
            case MAGE:
                abilities.add(new Ability("Fireball", "Flamming ball of fire", 30) {
                    @Override
                    public int execute(Character user, Character target) {
                        int damage = ((Player)user).intelligence * 3;
                        target.hp -= damage;
                        return damage;
                    }
                });
                break;
            case ROGUE:
                abilities.add(new Ability("Backstab", "Deals high damage if enemy HP > 50%", 25) {
                    @Override
                    public int execute(Character user, Character target) {
                        int multiplier = target.hp > target.maxHp/2 ? 3 : 1;
                        int damage = ((Player)user).dexterity * multiplier + user.attack();
                        int finalDamage = Math.max(1, damage - target.defend());
                        target.hp -= finalDamage;
                        return finalDamage;
                    }
                });
                break;
            case CLERIC:
                abilities.add(new Ability("Smite", "Holy damage + small heal", 25) {
                    @Override
                    public int execute(Character user, Character target) {
                        int damage = ((Player)user).intelligence + ((Player)user).strength;
                        user.hp -= Math.min(user.maxHp, user.hp + damage/2);
                        target.hp -= damage;
                        return damage;
                    }
                });
                break;
        }
    }


    //Player specific methods
    @Override
    public int attack() {
        int baseAtk = 5 + (xp/5);
        double classMultiplier = getClassAttackMultiplier();
        int statBonus = getStatAttackBonus();

        //equipment bonus
        int equipmentBonus = 0;
        if(weapon != null)
            equipmentBonus += weapon.atkBonus;
        if(armor != null)
            equipmentBonus += armor.atkBonus;
        if(accessory != null)
            equipmentBonus += accessory.atkBonus;

        double randomFactor = 0.9 + (Math.random() * 0.2);

        return (int) ((baseAtk * classMultiplier + statBonus + equipmentBonus) * randomFactor);
    }

    private double getClassAttackMultiplier(){
        switch(playerClass){
            case WARRIOR: return 1.2;
            case MAGE: return 0.8;
            case ROGUE: return 1.1;
            case CLERIC: return 0.9;
            default: return 1.0;
        }
    }

    private int getStatAttackBonus(){
        switch (playerClass){
            case WARRIOR: return strength * 2;
            case MAGE: return intelligence * 2;
            case ROGUE: return dexterity * 2;
            case CLERIC: return (strength + intelligence) / 2;
            default: return 0;
        }
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
        GameLogic.printHeading("Choose a stat to upgrade:");
        System.out.println("(1) Strength - (Current: " + strength + ")");
        System.out.println("(2) Intelligence - (Current: " + intelligence + ")");
        System.out.println("(3) Dexterity - (Current: " + dexterity + ")");
        System.out.println("(4) Constitution - (Current: " + constitution + ")");


        //get players choice
        int input = GameLogic.readInt("-> ", 4);
        GameLogic.clearConsole();

        GameLogic.printSeparator(20);
        System.out.println("STAT UPGRADED!");
        if(input == 1){
            strength++;
            System.out.println("Strength is now: " + strength);
        }else if(input == 2){
            intelligence++;
            System.out.println("Intelligence is now: " + intelligence);
        }else if(input == 3){
            dexterity++;
            System.out.println("Dexterity is now: " + dexterity);
        }else if(input == 4){
            constitution++;
            System.out.println("Constitution is now: " + constitution);
        }

        GameLogic.printSeparator(20);
        GameLogic.anythingToContinue();
    }
}
