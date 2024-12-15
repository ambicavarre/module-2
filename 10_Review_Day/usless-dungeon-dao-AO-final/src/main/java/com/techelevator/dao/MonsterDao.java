package com.techelevator.dao;

import com.techelevator.model.Monster;

import java.util.List;

public interface MonsterDao {
    Monster getMonsterById(int id);
    List<Monster> getListOfMonsters();
    List<Monster> getListOfMonstersForRoom(int roomId);

}