package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Hero;
import com.techelevator.model.Profession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class jdbcHeroDao implements HeroDao {
    private final JdbcTemplate jdbcTemplate;
    public jdbcHeroDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Hero getHeroById(int id){
        Hero myHero = new Hero();

        return myHero;
    }

    @Override
    public Hero createNewHero (Hero hero){
        Hero newHero = null;

        return newHero;
    }

    @Override
    public Hero updateHero(Hero hero) {
        Hero updatedHero = null;


        return updatedHero;
    }

    @Override
    public List<Profession> getListOfProfessions (){
        List<Profession> professions = new ArrayList<>();


        return professions;
    }

    @Override
    public List<Hero> getListOfHeroes (){
        List<Hero> heroes = new ArrayList<>();

        return heroes;
    }


    public Hero mapRowToHero(SqlRowSet rowSet){
        Hero myHero = new Hero();
        Profession profession = new Profession();
        myHero.setId(rowSet.getInt("hero_id"));
        myHero.setName(rowSet.getString("hero_name"));
        myHero.setHealth(rowSet.getInt("hero_health"));
        myHero.setDefense(rowSet.getInt("hero_defense"));
        profession.setProfessionId(rowSet.getInt("profession_id"));
        profession.setStartingAttack(rowSet.getInt("starting_attack"));
        profession.setStartingDefense(rowSet.getInt("starting_health"));
        profession.setStartingHealth(rowSet.getInt("starting_defense"));
        profession.setProfessionName(rowSet.getString("profession_name"));
        profession.setStartingAttackDescription(rowSet.getString("starting_attack_description"));
        myHero.setProfession(profession);
        myHero.setAttack(profession.getStartingAttack());
        return myHero;
    }
    public Profession mapRowToProfession(SqlRowSet rowSet){
        Profession profession = new Profession();
        profession.setProfessionId(rowSet.getInt("profession_id"));
        profession.setProfessionName(rowSet.getString("profession_name"));
        profession.setStartingHealth(rowSet.getInt("starting_health"));
        profession.setStartingDefense(rowSet.getInt("starting_defense"));
        profession.setStartingAttack(rowSet.getInt("starting_attack"));
        profession.setStartingAttackDescription(rowSet.getString("starting_attack_description"));
        return profession;
    }

}

