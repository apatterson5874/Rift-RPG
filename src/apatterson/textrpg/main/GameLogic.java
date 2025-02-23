package apatterson.textrpg.main;
import java.util.Scanner;

public class GameLogic {
    static Scanner scanner = new Scanner(System.in);

    static Player player;

    public static boolean isRunning;

    //random encounters
    public static String[] encounters = {"Battle", "Battle", "Battle", "Battle", "Battle"};

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

        //class selection
        boolean classSelected = false;
        PlayerClass playerClass = null;

        do {
            clearConsole();
            printHeading("Choose your class:");
            System.out.println("(1) Warrior - Strong physical attacks and high defense");
            System.out.println("(2) Mage - Powerful spells and high mana");
            System.out.println("(3) Rogue - Critical strikes and evasion");
            System.out.println("(4) Cleric - Healing and holy magic");

            int input = readInt("-> ", 4);

            switch (input) {
                case 1:
                    playerClass = PlayerClass.WARRIOR;
                    break;
                case 2:
                    playerClass = PlayerClass.MAGE;
                    break;
                case 3:
                    playerClass = PlayerClass.ROGUE;
                    break;
                case 4:
                    playerClass = PlayerClass.CLERIC;
                    break;
            }

            clearConsole();
            printHeading("You selected " + playerClass + ". Is that correct?");
            printSeparator(20);
            System.out.println("(1) Yes");
            System.out.println("(2) No I want to choose again");
            input = readInt("-> ", 2);
            if (input == 1)
                classSelected = true;
        } while(!classSelected);

        //print story intro
        Story.printIntro();

        //create new player object with the name
        player = new Player(name, playerClass);

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
            encounters[3] = "Battle";
            encounters[4] = "Battle";
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
            encounters[3] = "Battle";
            encounters[4] = "Battle";

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
        System.out.println(player.name + "\tHP " + player.hp + "/" + player.maxHp + "\tMana Shield " + player.manaShield + "/" + player.maxManaShield +
                "\nMana " + player.mana + "/" + player.maxMana);
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
        System.out.println("CLASS: " + player.playerClass);
        printSeparator(20);
        //printing stats
        System.out.println("Strength: " + player.strength);
        System.out.println("Intelligence: " + player.intelligence);
        System.out.println("Dexterity: " + player.dexterity);
        System.out.println("Constitution: " + player.constitution);
        printSeparator(20);


        anythingToContinue();
    }

    //shopping / encountering a travelling trader
    public static void shop(){
        clearConsole();
        printHeading("SHOP");
        printSeparator(20);
        System.out.println("Gold: " + player.gold);
        printSeparator(20);

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

        System.out.println((numItems + 1) + ") EXIT");

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
            printHeading("Do you want to take a rest? (" + player.restsLeft + ") rest(s) left");
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

            if(enemy.hp <= 0){
                printHeading("You defeated the " + enemy.name + "!");
                int xpEarned = enemy.xp/4 + 1;
                int goldEarned = enemy.xp/6 + 3;

                System.out.println("You earned " + goldEarned + " gold");
                System.out.println("You earned " + xpEarned + " xp");
                player.xp += xpEarned;
                player.gold += goldEarned;

                anythingToContinue();
                break;
            }
            if(player.hp <= 0){
                playerDied();
                break;
            }

            printHeading(enemy.name + "\nHP: " + enemy.hp + "/" + enemy.maxHp);
            printHeading(player.name + "\nHP: " + player.hp + "/" + player.maxHp + "\nMana: " + player.mana + "/" + player.maxMana +
                    "\nMana Shield: " + player.manaShield + "/" + player.maxManaShield);
            System.out.println("Choose an action:");
            printSeparator(20);

            // Calculate the total number of options
            int totalOptions = 2; // Start with basic attack + abilities
            totalOptions += player.abilities.size(); // Add number of abilities

            // Display options
            System.out.println("(1) Basic Attack");

            // Show class abilities
            for(int i = 0; i < player.abilities.size(); i++){
                Ability ability = player.abilities.get(i);
                System.out.println("(" + (i + 2) + ") " + ability.getName() +
                        " (Mana: " + ability.getManaCost() + ") - " +
                        ability.getDescription());
            }

            // Add potion and run options
            System.out.println("(" + (totalOptions) + ") Use Potion (" + player.pots + " remaining)");
            System.out.println("(" + (totalOptions + 1) + ") Run Away");

            int input = readInt("-> ", totalOptions + 1);

            //react to player input
            if(input == 1){
                //basic attack
                int dmg = player.attack() - enemy.defend();
                int dmgTook = enemy.attack() - player.defend();

                //apply damage
                enemy.hp -= Math.max(1, dmg);
                if(player.manaShield > 0) {
                    player.manaShield -= Math.max(1, dmgTook);
                } else {
                    player.hp -= Math.max(1, dmgTook);
                }

                //display results
                clearConsole();
                printHeading("BATTLE");
                System.out.println("You dealt " + dmg + " damage!");
                System.out.println("You took " + dmgTook + " damage!");
                anythingToContinue();

            }else if(input <= player.abilities.size() + 1){
                // Use ability
                Ability selectedAbility = player.abilities.get(input - 2);

                if(player.mana >= selectedAbility.getManaCost()) {
                    player.mana -= selectedAbility.getManaCost();

                    // Execute ability and get damage dealt
                    int damageDealt = selectedAbility.execute(player, enemy);

                    // Enemy still gets their attack
                    int dmgTook = enemy.attack() - player.defend();

                    if(player.manaShield > 0) {
                        player.manaShield -= Math.max(1, dmgTook);
                    } else {
                        player.hp -= Math.max(1, dmgTook);
                    }
                    clearConsole();
                    printHeading("BATTLE");
                    System.out.println("You used " + selectedAbility.getName() + "!");
                    System.out.println("You dealt " + damageDealt + " damage!");

                    // Special message for Cleric's healing
                    if(player.playerClass == PlayerClass.CLERIC && selectedAbility.getName().equals("Smite")) {
                        System.out.println("You healed for " + (damageDealt/2) + " HP!");
                    }

                    System.out.println("You took " + dmgTook + " damage!");
                    anythingToContinue();
                } else {
                    clearConsole();
                    printHeading("Not enough mana!");
                    anythingToContinue();
                }
            }else if(input == totalOptions){
                // Use potion
                if(player.pots > 0){
                    player.hp += 30; // Heal amount
                    if(player.hp > player.maxHp)
                        player.hp = player.maxHp;
                    player.pots--;

                    // Enemy still gets their attack
                    int dmgTook = enemy.attack() - player.defend();
                    player.hp -= Math.max(1, dmgTook);

                    clearConsole();
                    printHeading("You used a potion and restored health!");
                    System.out.println("You took " + dmgTook + " damage!");
                    anythingToContinue();
                }else{
                    clearConsole();
                    printHeading("No potions remaining!");
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
                        player.hp -= dmgTook;
                        System.out.println("You took " + dmgTook + " damage");
                        anythingToContinue();
                        //check if player is still alive
                        if(player.hp <= 0){
                            playerDied();
                            break;
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
        System.out.println("(4) Shop");
        System.out.println("(5) Rest");
        System.out.println("(6) Exit Game");
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
            int input = readInt("-> ", 6);
            if(input == 1)
                continueJourney();
            else if(input == 2)
                characterInfo();
            else if (input == 3)
                manageInventory();
            else if (input == 4)
                shop();
            else if (input == 5)
                takeRest();
            else
                isRunning = false;
        }
    }
}
