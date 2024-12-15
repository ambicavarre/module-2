package com.techelevator.model;

//This will be the parent class for all creatures in the dungeon (Hero, Monster, etc...
//This class cannot be instantiated.
public abstract class Creature {
    private int id;
    private String name; //The name of the creature
    private int health; //How much health does the creature have
    private int defense; //Useless Defense Rating for now until attacks have a chance to miss. At this point all attacks automatically hit.

    public Creature() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
    public int getDefense() {
        return defense;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
}

