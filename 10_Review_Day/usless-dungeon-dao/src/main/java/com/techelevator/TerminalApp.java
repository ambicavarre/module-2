package com.techelevator;
import java.util.List;

import com.techelevator.dao.*;
import com.techelevator.model.Hero;
import com.techelevator.model.Monster;
import com.techelevator.model.Room;
import com.techelevator.view.ConsoleService;
import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;

public class TerminalApp {
	private HeroDao heroDao;
	private RoomDao roomDao;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/useless_dungeon");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		TerminalApp application = new TerminalApp(dataSource);
		application.run();
	}
	
	public TerminalApp(DataSource dataSource) {
		heroDao = new jdbcHeroDao(dataSource);
		roomDao = new jdbRoomDao(dataSource);
	}

	private void run() {
		//Collection of Room Objects (I wouldn't normally use a map for this but I really wanted to review maps)
		List<Room> rooms = roomDao.getListOfRooms();
		//Main hero Object
		Hero myHero;
		//Current Monster
		Monster currentMonster;
		int currentRoom = 0;
		boolean gameOver = false; //Used to control tha main game loop

		//Print Welcome Messages
		ConsoleService.printWelcomeMessage();
		//Prompt for New or Certified Pre Owned Heroes
		int newOrUsedHero = ConsoleService.inputNewOrSavedHero();
		if (newOrUsedHero == 1){
			//Select Heroes From the Database
			myHero = ConsoleService.promptForDatabaseHeroes(heroDao.getListOfHeroes());
		}else {
			//Hero Creation Prompts
			myHero = ConsoleService.inputHeroOptions(heroDao.getListOfProfessions());
		}

		//Print out the Mission Briefing
		ConsoleService.printMissionMessage();

		//Begin the game
		while (!gameOver){
			//Ensure the Room Counter Does not exceed 4 rooms
			Room thisRoom = rooms.get(currentRoom);

			//Print out the Room Description
			ConsoleService.printRoomDescription(thisRoom);
			if (currentRoom > rooms.size()){
				currentRoom =0;
			}
			//This is the Current Monster (Based on the Integer Key of the Map)
			if (currentRoom >0) {
				currentMonster = thisRoom.getMonsterList().get(0);
			} else {
				currentMonster = null;
			}
			//If there is a currently a monster
			if(currentMonster != null){
				//Print out the Monster Description
				ConsoleService.printMonsterDescription(currentMonster);
				//Prompt for The Hero's Combat Action and resolve the hero's turn in the switch
				switch (ConsoleService.inputCombatOptions()){
					case 1: //Attack
						ConsoleService.printHeroAttack(currentMonster, myHero);
						break; //Break the Switch to go to the Monster Attack
					case 2: //Run Away
						ConsoleService.printRunAwayMessage();
						currentRoom++; //Advance to the Next Room
						if(currentRoom == 3){
							ConsoleService.printMissionFailMessage();
							ConsoleService.printGameOverMessage();
							gameOver = true;//Game over switch
						}
						continue; // Go Back to the top of the loop

					case 3: //Surrender
						ConsoleService.printSurrenderMessage();
						ConsoleService.printGameOverMessage(); //Let them know the game is over
						gameOver = true;//Game over switch
						continue;// Go Back to the top of the loop (But not for long..The condition is flipped)
					case 4: //Try Anything
						ConsoleService.printTryAnythingMessage();
						break; //Break the Switch to go to the Monster Attack
				}
				//Print out the Monster Attack
				ConsoleService.printMonsterAttack(currentMonster);
				gameOver = true;
			} else {
				switch (ConsoleService.inputExplorationOptions()) {
					case 1: //Stay There
						break; //Break the Switch
					case 2: //Leave the Room
						System.out.println("Moving Out!");
						currentRoom++; //Advance to the Next Room
						continue; // Go Back to the top of the loop
					case 3: //Surrender
						ConsoleService.printSurrenderMessage();
						ConsoleService.printGameOverMessage(); //Let them know the game is over
						gameOver = true;//Game over switch
				}
			}
		}
	}
}
