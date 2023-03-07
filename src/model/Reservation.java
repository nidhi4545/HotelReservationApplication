package model;

import java.util.Date;
import java.util.Objects;

//This is the class which takes care of the reservation formalities for the customer.
public class Reservation {
    //Creating a parameter of type customer. Java provides us with the functionality to define a user-defined field. Customer and room variables are the example
    private final Customer customer;
    private final IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString(){
        return "Reservation Details: \n" +
                "Name: "+getCustomer().getFirstName()+getCustomer().getLastName()+"\n"+
                "Room: "+getRoom().getRoomNumber()+" - "+getRoom().getRoomType()+"\n "+
                "Price: $"+getRoom().getRoomPrice()+"per night\n"+
                "Check In Date: "+getCheckInDate()+"\n"+
                "Check Out Date: "+getCheckOutDate();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass())return false;

        Reservation reserve = (Reservation) o;
        return Objects.equals(customer, reserve.customer) && Objects.equals(room, reserve.room)
                && Objects.equals(checkInDate, reserve.checkInDate) && Objects.equals(checkOutDate, reserve.checkOutDate);
    }

    @Override
    public int hashCode(){
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
