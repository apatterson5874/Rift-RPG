package apatterson.textrpg.main;
import java.util.Scanner;

public class GameLogic {
    static Scanner scanner = new Scanner(System.in);

    static Player player;

    public static boolean isRunning;

    //random encounters
    public static String[] encounters = {"Battle", "Battle", "Battle", "Rest", "Rest"};

    //enemy names
    public static String[] enemies = {"Savage", "Savage", "Corrupted", "Corrupted", "Elemental", "Elemental"};

    //story elements
    public static int place = 0, act = 1;
    public static String[] places = {"Lost Woods", "The Scorched Plains", "The RIFT", "The Lands Between"};

    //method to get user input from console
    public static int readInt(String prompt, int userChoice){
        int input;

        do{
            System.out.println(prompt);
            try{
                input = Integer.parseInt(scanner.next());
            }catch(Exception e){
                input = -1;
                System.out.println("Please enter an Integer!");
            }
        }while(input < 1 || input > userChoice);
        return input;
    }

    //method to clear console
    public static void clearConsole(){
        for(int i = 0; i < 100; i++)
            System.out.println();
    }

    //method to print separator of length n
    public static void printSeparator(int n){
        for(int i = 0; i < n; i++)
            System.out.print("-");
        System.out.println();
    }

    //method to print heading
    public static void printHeading(String title){
        printSeparator(30);
        System.out.println(title);
        printSeparator(30);
    }

    //method to stop the game until input
    public static void anythingToContinue(){
        System.out.println("\nEnter anything to continue...");
        scanner.next();
    }

    //method to start game
    public static void startGame(){
        boolean nameSet = false;
        String name;

        //print title screen
        clearConsole();
        printSeparator(40);
        printSeparator(30);
        System.out.println("THE RIFT RPG");
        System.out.println("TEXT RPG BY HYPNOTIC");
        printSeparator(30);
        printSeparator(40);
        anythingToContinue();

        //getting player name
        do{
            clearConsole();
            printHeading("What's your name?");
            name = scanner.next();

            //ask player if this is correct
            clearConsole();
            printHeading("Your name is " + name + ".\nIs that correct?");
            System.out.println("(1) Yes");
            System.out.println("(2) No I want to change my name.");
            int input = readInt("-> ", 2);
            if(input == 1)
                nameSet = true;
        }while(!nameSet);

        //print story intro
        Story.printIntro();

        //create new player object with the name
        player = new Player(name);

        //print first story act intro
        Story.printFirstActIntro();

        //setting isRunning to true, so the game loop can continue
        isRunning = true;

        //start main game loop
        gameLoop();
    }

    //method that changes the game's values based on player xp
    public static void checkAct(){
        //change act based on xp
        if(player.xp >= 10 && act == 1){
            //increment act and place
            act = 2;
            place = 1;
            //story
            Story.printFirstActOutro();
            //let player level up
            player.chooseTrait();
            //story
            Story.printSecondActIntro();
            //assign new values to enemies
            enemies[0] = "Scorched";
            enemies[1] = "Savage";
            enemies[2] = "Sand Worm";
            enemies[3] = "Sand Snake";
            enemies[4] = "Sand Elemental";
            //assign new values to encounters
            encounters[0] = "Battle";
            encounters[1] = "Battle";
            encounters[2] = "Battle";
            encounters[3] = "Rest";
            encounters[4] = "Shop";
            //fully heal player
            player.hp = player.maxHp;
        }else if(player.xp >= 50 && act == 2){
            act = 3;
            place = 2;
            Story.printSecondActOutro();
            player.chooseTrait();
            Story.printThirdActIntro();

            enemies[0] = "Corrupted";
            enemies[1] = "Corrupted";
            enemies[2] = "Corrupted";
            enemies[3] = "Rift Beast";
            enemies[4] = "Rift Beast";

            encounters[0] = "Battle";
            encounters[1] = "Battle";
            encounters[2] = "Battle";
            encounters[3] = "Rest";
            encounters[4] = "Shop";

            player.hp = player.maxHp;
        }else if(player.xp >= 100 && act == 3){
            act = 4;
            place = 3;
            Story.printThirdActOutro();
            player.chooseTrait();
            Story.printFourthActIntro();

            player.hp = player.maxHp;

            //calling final battle
            finalBattle();
        }
    }

    //method to calculate a random encounter
    public static void randomEncounter(){
        //random number between 0 and length of encounters array
        int encounter = (int) (Math.random()*encounters.length);

        //calling respective methods
        if(encounters[encounter].equals("Battle")) {
            randomBattle();
        }else if(encounters[encounter].equals("Rest")) {
            takeRest();
        }else{
            shop();
        }
    }

    //method to continue journey
    public static void continueJourney(){
        //check if act must be increased
        checkAct();
        //check if game isn't in last act
        if(act != 4)
            randomEncounter();
    }

    //printing out important info about player character
    public static void characterInfo(){
        clearConsole();
        printHeading("CHARACTER INFO");
        System.out.println(player.name + "\tHP " + player.hp + "/" + player.maxHp);
        printSeparator(20);
        //Player xp and gold
        System.out.println("XP: " + player.xp + "\tGold: " + player.gold);
        printSeparator(20);
        //equipment
        System.out.println("EQUIPMENT:");
        System.out.println(player.getEquipmentString());
        printSeparator(20);
        //# of pots
        System.out.println("# of Potions: " + player.pots);
        printSeparator(20);

        //printing chosen traits
        if(player.numAtkUpgrades > 0){
            System.out.println("Offensive trait: " + player.atkUpgrades[player.numAtkUpgrades - 1]);
        }
        if(player.numDefUpgrades > 0){
            System.out.println("Defensive trait: " + player.defUpgrades[player.numAtkUpgrades - 1]);
        }

        anythingToContinue();
    }

    //shopping / encountering a travelling trader
    public static void shop(){
        clearConsole();
        printHeading("SHOP");

        //Get selection of items to sell
        Item[] shopItems = Item.createCommonItems();
        //Show only a few random items
        int numItems = Math.min(30, shopItems.length);

        for(int i = 0; i < numItems; i++){
            Item item = shopItems[i];
            System.out.println((i+1) + ") " + item.name + " - " + item.value + " gold");
            //print item stats
            if(item.atkBonus > 0)
                System.out.println("    Attack: +" + item.atkBonus);
            if(item.defBonus > 0)
                System.out.println("    Defense: +" + item.defBonus);
            if(item.hpBonus > 0)
                System.out.println("    HP: +" + item.hpBonus);
        }

        System.out.println((numItems + 1) + ") ");

        int input = readInt("-> ", numItems + 1);
        if(input != numItems + 1) {
            Item selectedItem = shopItems[input - 1];
            if(player.gold >= selectedItem.value){
                player.gold -= selectedItem.value;
                player.inventory.add(selectedItem);
                printHeading("You bought " + selectedItem.value);
            }else{
                printHeading("Not enough gold");
            }
        }
    }

    //taking a rest
    public static void takeRest(){
        clearConsole();
        if(player.restsLeft >= 1){
            printHeading("Do you want to take a rest? (" + player.restsLeft + " rest(s) left");
            System.out.println("(1) Yes\n(2) No");
            int input = readInt("-> ", 2);
            if(input == 1){
                clearConsole();
                if(player.hp < player.maxHp){
                    int hpRestored = (int) (Math.random()*(player.xp/4 + 1) + 10);
                    player.hp += hpRestored;
                    if(player.hp > player.maxHp)
                        player.hp = player.maxHp;
                    System.out.println("You took a rest and restored " + hpRestored + " health");
                    System.out.println("You are now at " + player.hp + "/" + player.maxHp + " health");
                    player.restsLeft--;
                }
            }else
                System.out.println("You are at full health. You do not need to rest");
            anythingToContinue();
        }
    }

    //creating a random battle
    public static void randomBattle(){
        clearConsole();
        printHeading("You have encountered an enemy!");
        anythingToContinue();

        //creating new enemy with random name
        battle(new Enemy(enemies[(int)(Math.random()*enemies.length)], player.xp));
    }

    //main battle method
    public static void battle(Enemy enemy){
        //main battle loop
        while(true){
            clearConsole();
            printHeading(enemy.name + "\nHP: " + enemy.hp + "/" + enemy.maxHp);
            printHeading(player.name + "\nHP: " + player.hp + "/" + player.maxHp);
            System.out.println("Choose an action:");
            printSeparator(20);
            System.out.println("(1) Fight\n(2) Use Potion\n(3) Run Away");
            int input = readInt("-> ", 3);

            //react to player input
            if(input == 1){
                //fight
                //calculate dmg and dmgTook (dmg enemy deals to player)
                int dmg = player.attack() - enemy.defend();
                int dmgTook = enemy.attack() - player.defend();
                //check that dmg and dmgTook isn't negative
                if(dmgTook < 0){
                    dmg -= dmgTook/2;
                    dmgTook = 0;
                }
                if(dmg < 0)
                    dmg = 0;
                //deal dmg to both parties
                player.hp -= dmgTook;
                enemy.hp -= dmg;

                //print info of this battle round
                clearConsole();
                printHeading("BATTLE");
                System.out.println("You dealt " + dmg + " damage to the " + enemy.name);
                printSeparator(15);
                System.out.println("The " + enemy.name + " dealt " + dmgTook + " damage to you");
                anythingToContinue();
                //check if player is still alive or dead
                if(player.hp <= 0){
                    playerDied();
                    break;
                }else if(enemy.hp <= 0){
                    //tell the player they won
                    clearConsole();
                    printHeading("You defeated the " + enemy.name);
                    //increase player xp
                    player.xp += enemy.xp;
                    System.out.println("You earned " + enemy.xp + " XP");
                    //random drops
                    boolean addRest = (Math.random()*5 + 1 <= 2.25);
                    int goldEarned = (int) ((Math.random()*enemy.xp) + (Math.random()*5));
                    if(addRest){
                        player.restsLeft++;
                        System.out.println("You earned a chance to rest");
                    }
                    if(goldEarned > 0){
                        player.gold += goldEarned;
                        System.out.println("You collected " + goldEarned + " gold from the " + enemy.name);
                    }
                    anythingToContinue();
                    break;
                }

            }else if(input == 2){
                //use potion
                clearConsole();
                if(player.pots > 0 && player.hp < player.maxHp){
                    //make sure player wants to take a potion
                    printHeading("Do you want to drink a potion? (" + player.pots + "left)");
                    System.out.println("(1) Yest\n(2) No");
                    input = readInt("-> ", 2);
                    if(input == 1){
                        player.hp = player.maxHp;
                        clearConsole();
                        printHeading("You took a potion. HP restored to " + player.maxHp);
                        anythingToContinue();
                    }
                }else{
                    printHeading("You don't have any potions or you're at full health");
                    anythingToContinue();
                }
            }else{
                //run away
                clearConsole();
                //check that player is not in final act
                if(act != 4){
                    //35% chance to escape
                    if(Math.random()*10 + 1 <= 3.5){
                        printHeading("You ran away from the " + enemy.name);
                        anythingToContinue();
                        break;
                    }else{
                        printHeading("You didn't manage to escape");
                        //calculate damage player takes
                        int dmgTook = enemy.attack();
                        System.out.println("You took " + dmgTook + " damage");
                        anythingToContinue();
                        //check if player is still alive
                        if(player.hp <= 0){
                            playerDied();
                        }
                    }
                }else{
                    printHeading("YOU CANNOT ESCAPE!");
                    anythingToContinue();
                }
            }
        }
    }

    //printing main menu
    public static void printMenu(){
        clearConsole();
        printHeading(places[place]);
        System.out.println("Choose an action:");
        printSeparator(20);
        System.out.println("(1) Continue on your journey");
        System.out.println("(2) Character Info");
        System.out.println("(3) Inventory");
        System.out.println("(4) Exit Game");
    }

    public static void manageInventory(){
        clearConsole();
        printHeading("INVENTORY");

        if(player.inventory.isEmpty()){
            System.out.println("Your inventory is empty");
            anythingToContinue();
            return;
        }

        for(int i = 0; i < player.inventory.size(); i++){
            Item item = player.inventory.get(i);
            System.out.println((i+1) + ") " + item.name);
            if(item.atkBonus > 0)
                System.out.println("    Attack: +" + item.atkBonus);
            if(item.defBonus > 0)
                System.out.println("    Defense: +" + item.defBonus);
            if(item.hpBonus > 0)
                System.out.println("    HP: +" + item.hpBonus);
        }

        System.out.println((player.inventory.size() + 1) + ") ");

        int input = readInt("-> ", player.inventory.size() + 1);
        if(input != player.inventory.size() + 1){
            Item selectedItem = player.inventory.get(input - 1);
            player.equipItem(selectedItem);
            player.inventory.remove(input - 1);
            printHeading("You equipped " + selectedItem.name);
            anythingToContinue();
        }
    }

    //final battle of the game
    public static void finalBattle(){
        //creating final boss and letting player fight it
        battle(new Enemy("GOD OF THE LANDS BETWEEN", 300));
        //printing proper ending
        Story.printEnd(player);
        isRunning = false;
    }

    //method called when player is dead
    public static void playerDied(){
        clearConsole();
        printHeading("You died...");
        printHeading("You earned " + player.xp + " XP on your journey");
        System.out.println("Thanks for playing");
        isRunning = false;
    }

    //main game loop
    public static void gameLoop(){
        while(isRunning){
            printMenu();
            int input = readInt("-> ", 4);
            if(input == 1)
                continueJourney();
            else if(input == 2)
                characterInfo();
            else if (input == 3)
                manageInventory();
            else
                isRunning = false;
        }
    }
}
