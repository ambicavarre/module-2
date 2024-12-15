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
    private final String MONSTER_SELECT = "SELECT monsters.monster_id, monster_name, monster_health, monster_defense, monster_attack_description, monster_damage FROM public.monsters ";

    public jdbcMonsterDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Monster getMonsterById(int id){
        Monster monster = new Monster();
        String sql = MONSTER_SELECT + "WHERE monster_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()){
                monster = mapRowToMonster(results);
            }

        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to Connect to server or Database.", e);
        }
        return monster;
    }

    @Override
    public List<Monster> getListOfMonsters() {
        List<Monster> monsters = new ArrayList<>();
        String sql = MONSTER_SELECT;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                monsters.add(mapRowToMonster(results));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to Connect to server or Database.", e);
        }

        return monsters;
    }
    public List<Monster> getListOfMonstersForRoom(int roomId){
        List<Monster> monsters = new ArrayList<>();
        String sql = MONSTER_SELECT + "INNER JOIN room_monsters ON monsters.monster_id = room_monsters.monster_id\n" +
                "INNER JOIN rooms ON room_monsters.room_id = rooms.room_id\n" +
                "WHERE room_monsters.room_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, roomId);
            while (results.next()){
                monsters.add(mapRowToMonster(results));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to Connect to server or Database.", e);
        }

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

