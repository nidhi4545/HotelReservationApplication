package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

//    private static final HotelResource hotelResourceInstance = new HotelResource();
//
//    private HotelResource(){}
//
//    public static HotelResource getInstance(){
//        return hotelResourceInstance;
//    }

    public static Customer getCustomer(String email){
        CustomerService customerService = CustomerService.getInstance();
        return customerService.getCustomer(email);
    }

    public static void createACustomer(String email, String firstName, String lastName){
        CustomerService customerService = CustomerService.getInstance();
        customerService.addCustomer(email,firstName,lastName);
    }

    public static IRoom getRoom(String roomNumber){
        ReservationService reservationService = ReservationService.getInstance();
        return reservationService.getARoom(roomNumber);
    }

    public static Reservation reserveARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        ReservationService reserveRoom = ReservationService.getInstance();
        return reserveRoom.reserveARoom(getCustomer(customerEmail),room,checkInDate,checkOutDate);
    }

    public static Collection<Reservation> getCustomersReservations(String customerEmail){
        ReservationService reservations = ReservationService.getInstance();
        return reservations.getCustomersReservation(getCustomer(customerEmail));
    }

    public static Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        ReservationService roomReservations = ReservationService.getInstance();
        return roomReservations.findARooms(checkInDate,checkOutDate);
    }
}
