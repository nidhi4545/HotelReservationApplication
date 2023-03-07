package model;

//this class is for checking if the room is free of cost
public class FreeRoom extends Room {
    protected double cost;

    public FreeRoom(String rNumber, double cost, RoomType rType) {
        super(rNumber,0, rType);
    }



}
