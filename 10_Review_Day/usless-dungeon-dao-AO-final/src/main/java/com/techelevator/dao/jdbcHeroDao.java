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
    private final String HERO_SELECT = "SELECT hero_id, hero_name, hero_health, hero_defense, hero_profession_id,  profession_id, profession_name, starting_health, \n" +
            "starting_defense, starting_attack, starting_attack_description\n" +
            "FROM public.heroes INNER JOIN professions ON heroes.hero_profession_id = professions.profession_id ";
    private final String HERO_INSERT = "INSERT INTO public.heroes(\n" +
            "\thero_name, hero_health, hero_defense, hero_profession_id)\n" +
            "\tVALUES (?, ?, ?, ?) ";
    private final String PROFESSION_SELECT = "SELECT profession_id, profession_name, starting_health, starting_defense, starting_attack, starting_attack_description\n" +
            "\tFROM public.professions ";
    private final String HERO_UPDATE = "UPDATE public.heroes\n" +
            "\tSET hero_name=?, hero_health=?, hero_defense=?, hero_profession_id=?\n" +
            "\tWHERE hero_id=?;"; //Dude: Don't remove the where ever...

    public jdbcHeroDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Hero getHeroById(int id){
        Hero myHero = new Hero();
        String sql = HERO_SELECT + "WHERE hero_id =?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()){
                myHero = mapRowToHero(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to Connect to server or Database.", e);
        }
        return myHero;
    }

    @Override
    public Hero createNewHero (Hero hero){
        Hero newHero = null;
        String sql = HERO_INSERT + " RETURNING hero_id";

        try {
            int newHeroID = jdbcTemplate.queryForObject(sql, int.class,
                    hero.getName(), hero.getHealth(), hero.getDefense(), hero.getProfession().getProfessionId());
            newHero = getHeroById(newHeroID);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newHero;
    }

    @Override
    public Hero updateHero(Hero hero) {
        Hero updatedHero = null;
        String sql = HERO_UPDATE;
        try {
            int rowsAffected = jdbcTemplate.update(sql, hero.getName(), hero.getHealth(), hero.getDefense(), hero.getProfession().getProfessionId(), hero.getId());
            if (rowsAffected != 1){
                throw new DaoException("Three things are certain: Death, Taxes and Loss of Data. Guess which has occurred.");
            } else {
                updatedHero = getHeroById(hero.getId());
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return updatedHero;
    }

    @Override
    public List<Profession> getListOfProfessions (){
        List<Profession> professions = new ArrayList<>();
        String sql = PROFESSION_SELECT;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                professions.add(mapRowToProfession(results));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to Connect to server or Database.", e);
        }

        return professions;
    }

    @Override
    public List<Hero> getListOfHeroes (){
        List<Hero> heroes = new ArrayList<>();
        String sql = HERO_SELECT;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                heroes.add(mapRowToHero(results));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to Connect to server or Database.", e);
        }
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

