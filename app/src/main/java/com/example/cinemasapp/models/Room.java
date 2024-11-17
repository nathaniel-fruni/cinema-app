package com.example.cinemasapp.models;

public class Room {
    private long ID;
    private long room_number;
    private long capacity;

    public Room(long ID, long room_number, long capacity) {
        this.ID = ID;
        this.room_number = room_number;
        this.capacity = capacity;
    }

    public long getID() { return ID; }
    public void setID(long ID) { this.ID = ID; }
    public long getRoomNumber() { return room_number; }
    public void setRoomNumber(long room_number) { this.room_number = room_number; }
    public long getCapacity() { return capacity; }
    public void setCapacity(long capacity) { this.capacity = capacity; }
}
