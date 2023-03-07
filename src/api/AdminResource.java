package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    public static Customer getCustomer(String email){
        CustomerService customers = CustomerService.getInstance();
        return customers.getCustomer(email);
    }

    public static void addRoom(List<IRoom> room){
        ReservationService roomReservationService = ReservationService.getInstance();
        for(IRoom room1 : room) {
            roomReservationService.addRoom(room1);
        }
    }

    public static  Collection<IRoom> getAllRooms(){
        ReservationService retrieveRooms = ReservationService.getInstance();
        return retrieveRooms.getAllRooms();
    }

    public static Collection<Customer> getAllCustomers(){
        CustomerService allCustomers = CustomerService.getInstance();
        return allCustomers.getAllCustomers();
    }

    public static void displayAllReservations(){
        ReservationService allReservations = ReservationService.getInstance();
        allReservations.printAllReservations();
    }
}
