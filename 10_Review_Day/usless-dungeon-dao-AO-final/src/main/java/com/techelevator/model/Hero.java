package com.techelevator.model;


public class Hero extends Creature {


    private Profession profession;
    private int attack;

    public Hero() {
    }


    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }


    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
