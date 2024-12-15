package com.techelevator.view;

import com.techelevator.model.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;


//This is a utility class that is used for command line input and output.
public class ConsoleService {

    //System Output Methods
    public static void printWelcomeMessage() {
        System.out.println("Welcome to the useless dungeon.");
        System.out.println("You will find this game impossible to beat!");
        System.out.println("Good Luck!");
    }


    public static void printMissionMessage() {
        System.out.println("A local villager named Cletus Van Damm has wandered into this dangerous place. ");
        System.out.println("Your mission is to find Cletus and escape together. Both you and Cletus must survive the dungeon! ");
    }

    public static void printRoomDescription(Room currentRoom){
        System.out.println("Welcome to room number " + currentRoom.getId());
        System.out.println(currentRoom.getDescription());
    }

    public static void printMonsterDescription(Monster currentMonster){
        //Print out the Monster Description
        System.out.println("The room contains the following monster: ");
        System.out.println(currentMonster.getName());
    }

    public static void printHeroAttack(Monster currentMonster, Hero myHero) {
        //Print out the Hero Attack Description
        System.out.println("You Attack!");
        //  System.out.println(currentMonster.getName() + " takes " + myHero.getHeroDamage() + " Damage!");
        System.out.println(currentMonster.getName() + "now has " + (currentMonster.getHealth() - 1) + " Health");
    }

    public static void printMonsterAttack(Monster currentMonster) {
        //Print out the Monster Attack Description
        System.out.println(currentMonster.getName() + " attacks with " + currentMonster.getAttackDescription());
        printDeathMessage();
        printGameOverMessage();
    }

    public static void printGameOverMessage(){
        System.out.println("Game Over.");
    }

    public static void printDeathMessage(){
        System.out.println("You Are Dead.");
    }

    public static void printMissionFailMessage(){
        System.out.println("You Left Cletus!");
    }

    public static void printRunAwayMessage(){
        System.out.println("You run away in fear!");
    }

    public static void printSurrenderMessage(){
        System.out.println("You Surrendered.");
    }

    public static void printTryAnythingMessage(){
        System.out.println(generateRandomMessage());
    }

    //User Input Methods
    public static Profession inputProfessionOptions(List<Profession> professions){
        boolean validInput = false;
        Scanner userInput = new Scanner(System.in);
        int selected = 0;

        while (!validInput) {
            System.out.println("Select from the following options (Numeric Input Only):");
            //Loop Through and Print out the Profession Options
            for (Profession profession : professions) {
                System.out.println(profession.getProfessionId() + ":" + profession.getProfessionName());
            }
            //Make sure they only entered a number
            try {
                selected = Integer.parseInt(userInput.next());
                validInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input");
            }
        }

        return professions.get(selected-1);
    }
    public static Hero inputHeroOptions(List<Profession> professions) {
        Scanner userInput = new Scanner(System.in);
        String characterName;
        Profession characterProfession;

        System.out.println("What is your name?");
        characterName = userInput.nextLine();
        characterProfession = inputProfessionOptions(professions);
        //Default Character Name if no input
        if (characterName.isEmpty()){
            characterName = "Sir Larry";
        }

        //Initialize the My Hero Object
        Hero myHero = new Hero();
        myHero.setName(characterName);
        myHero.setAttack(characterProfession.getStartingAttack());
        myHero.setDefense(characterProfession.getStartingDefense());
        myHero.setHealth(characterProfession.getStartingHealth());
        myHero.setProfession(characterProfession);

        System.out.println("Welcome " + myHero.getName() + " the " + myHero.getProfession().getProfessionName());
        System.out.println("You are armed with: " + myHero.getProfession().getStartingAttackDescription());

        //Return the generated Hero
        return myHero;
    }

    public static int inputCombatOptions() {
        boolean validInput = false;
        Scanner userInput = new Scanner(System.in);
        int selected = 0;

        while (!validInput){
            System.out.println("Please Choose from the following options: ");
            System.out.println("1 -> Attack!");
            System.out.println("2 -> Run Away!");
            System.out.println("3 -> Surrender!");
            System.out.println("4 -> Try Anything! (Choose Randomly)");
            try {
                selected = Integer.parseInt(userInput.next());
                validInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input");
            }

        }
        return selected;
    }

    public static int inputExplorationOptions(){
        boolean validInput = false;
        Scanner userInput = new Scanner(System.in);
        int selected = 0;
        while (!validInput){
            System.out.println("Please Choose from the following options: ");
            System.out.println("1 -> Stay here a while!");
            System.out.println("2 -> Move through the door!");
            System.out.println("3 -> Surrender!");
            try {
                selected = Integer.parseInt(userInput.next());
                validInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input");
            }
        }
        return selected;

    }

    public static int inputNewOrSavedHero(){
        boolean validInput = false;
        Scanner userInput = new Scanner(System.in);
        int selected = 0;
        while (!validInput){
            System.out.println("Please Choose from the following options: ");
            System.out.println("1 -> Load An Existing Hero");
            System.out.println("2 -> Create A New Hero");
            try {
                selected = Integer.parseInt(userInput.next());
                validInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input");
            }
        }
        return selected;
    }

    public static Hero promptForDatabaseHeroes(List<Hero> heroes){
        Hero myHero = new Hero();
        int displayOption=1;
        boolean validInput = false;
        Scanner userInput = new Scanner(System.in);
        int selected = 0;
        while (!validInput){
            System.out.println("Please Choose from the following options: ");
            for (Hero hero : heroes){
                System.out.println(displayOption++ + ") " + hero.getName() + " The " + hero.getProfession().getProfessionName());
            }

            try {
                selected = Integer.parseInt(userInput.next());
                validInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input");
            }
        }
        myHero = heroes.get(selected-1);
        System.out.println("Welcome " + myHero.getName() + " the " + myHero.getProfession().getProfessionName());
        System.out.println("You are armed with: " + myHero.getProfession().getStartingAttackDescription());

        return myHero;
    }

    //Local Utility Methods
    public static String generateRandomMessage(){
        // create instance of Random class
        Random randomNumber = new Random();

        String[] messages =  {"You Try Tap Dancing",
                             "You open an Umbrella",
                             "You Search for your Brown Pants",
                             "Digging a hole sounds fun"};
        // Generate random integers in range 0 to 999
        int messageIndex = randomNumber.nextInt(messages.length);

        return messages[messageIndex];
    }
}
