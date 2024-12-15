package com.techelevator.dao;

import com.techelevator.model.Room;

import java.util.List;

public interface RoomDao {
    Room getRoomById(int id);
    List<Room> getListOfRooms();
}
