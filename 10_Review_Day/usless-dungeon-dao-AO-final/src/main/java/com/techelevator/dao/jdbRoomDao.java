package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Room;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class jdbRoomDao implements RoomDao {
    private final JdbcTemplate jdbcTemplate;
    private MonsterDao monsterDao;
    private final String ROOM_SELECT = "SELECT room_id, room_name, room_description FROM public.rooms ";
    public jdbRoomDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        monsterDao = new jdbcMonsterDao(dataSource);
    }
    @Override
    public Room getRoomById(int id){
        Room room = new Room();
        String sql = ROOM_SELECT + "WHERE room_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()){
                room = mapRowToRoom(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to Database or Whatever...");
        }

        return room;
    }

    @Override
    public List<Room> getListOfRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = ROOM_SELECT;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                rooms.add(mapRowToRoom(results));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to Database or Whatever...");
        }
        return rooms;
    }

    public Room mapRowToRoom(SqlRowSet rowSet){
        Room room = new Room();
        room.setId(rowSet.getInt("room_id"));
        room.setName(rowSet.getString("room_name"));
        room.setDescription(rowSet.getString("room_description"));
        room.setMonsterList(monsterDao.getListOfMonstersForRoom(room.getId()));
        return room;
    }



}
