package com.techelevator.dao;

import com.techelevator.model.Hero;
import com.techelevator.model.Profession;

import java.util.List;

public interface HeroDao {

    Hero getHeroById(int id);
    Hero createNewHero (Hero hero);
    Hero updateHero(Hero hero);
    List<Profession> getListOfProfessions ();
    List<Hero> getListOfHeroes ();

}
