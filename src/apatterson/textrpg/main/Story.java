package apatterson.textrpg.main;

//class does nothing but store methods to print out parts of the story
public class Story {

    public static void printIntro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("STORY");
        GameLogic.printSeparator(30);
        System.out.println("You wake up in an unknown place with a haze in your mind");
        System.out.println("In the sky there is what can only be described as a RIFT in the sky");
        System.out.println("People seem to be falling out the RIFT one by one");
        System.out.println("Are you the only lost soul? What mysteries does this land hold?");
        GameLogic.anythingToContinue();
    }

    public static void printFirstActIntro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT I - INTRO");
        GameLogic.printSeparator(30);
        //Story Here
        GameLogic.anythingToContinue();
    }

    public static void printFirstActOutro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT I - Outro");
        GameLogic.printSeparator(30);
        //Story Here
        GameLogic.anythingToContinue();
    }

    public static void printSecondActIntro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT II - INTRO");
        GameLogic.printSeparator(30);
        //Story Here
        GameLogic.anythingToContinue();
    }

    public static void printSecondActOutro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT II - OUTRO");
        GameLogic.printSeparator(30);
        //Story Here
        GameLogic.anythingToContinue();
    }

    public static void printThirdActIntro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT III - INTRO");
        GameLogic.printSeparator(30);
        //Story Here
        GameLogic.anythingToContinue();
    }

    public static void printThirdActOutro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT III - OUTRO");
        GameLogic.printSeparator(30);
        //Story Here
        GameLogic.anythingToContinue();
    }

    public static void printFourthActIntro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT IV - INTRO");
        GameLogic.printSeparator(30);
        //Story Here
        GameLogic.anythingToContinue();
    }

    public static void printEnd(Player player){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("Congratulations, " + player.name + "! You have solved the mysteries of the RIFT and this land!");
        GameLogic.printSeparator(30);
        System.out.println("This game was developed by HYPNOTIC");
    }
}
