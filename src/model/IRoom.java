package model;

//this is an interface which gives the layout of the abstract methods that will be inherited and defined in the child class
public interface IRoom {

    public String getRoomNumber();

    public Double getRoomPrice();

    public RoomType getRoomType();

    public boolean isFree();
}
