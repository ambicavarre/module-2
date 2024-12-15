package com.techelevator.dao;


import com.techelevator.exception.DaoException;
import com.techelevator.model.Monster;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class jdbcMonsterDao implements MonsterDao{
    private final JdbcTemplate jdbcTemplate;
    public jdbcMonsterDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Monster getMonsterById(int id){
        Monster monster = new Monster();

        return monster;
    }

    @Override
    public List<Monster> getListOfMonsters() {
        List<Monster> monsters = new ArrayList<>();


        return monsters;
    }
    public List<Monster> getListOfMonstersForRoom(int roomId){
        List<Monster> monsters = new ArrayList<>();

        return monsters;
    }
    public Monster mapRowToMonster(SqlRowSet rowSet){
        Monster monster = new Monster();
        monster.setId(rowSet.getInt("monster_id"));
        monster.setName(rowSet.getString("monster_name"));
        monster.setHealth(rowSet.getInt("monster_health"));
        monster.setDefense(rowSet.getInt("monster_defense"));
        monster.setAttack(rowSet.getInt("monster_damage"));
        monster.setAttackDescription(rowSet.getString("monster_attack_description"));
        return monster;
    }
}

