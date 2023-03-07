package model;

//this class implements the IRoom interface. You can also see the definitions/class bodies for all the abstract methods defined in the IRoom interface.
public class Room implements IRoom {
    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;

    public Room(String rNumber, double cost, RoomType rType){
        this.roomNumber = rNumber;
        this.price = cost;
        this.roomType = rType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return (price == 0);
    }

    public String toString(){
        return "Below are the details of the room: " +
                " Room Number:"+getRoomNumber()+
                " Price:"+getRoomPrice()+
                " Room Type:"+getRoomType();
    }
}
