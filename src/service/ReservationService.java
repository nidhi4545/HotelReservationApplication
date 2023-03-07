package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static final ReservationService reservationServiceInstance = new ReservationService();

    private ReservationService(){}

    public static ReservationService getInstance(){
        return reservationServiceInstance;
    }

    private final Map<String, IRoom> roomMap = new HashMap<>();

    private final Map<String, Collection<Reservation>> reservationDetails = new HashMap<>();

    public void addRoom(IRoom room){
        roomMap.put(room.getRoomNumber(),room);
    }

    public IRoom getARoom(String roomID){
        return roomMap.get(roomID);
    }

    public Collection<IRoom> getAllRooms(){
        return roomMap.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer,room,checkInDate,checkOutDate);

        Collection<Reservation> clientReservations = reservationDetails.get(customer.getEmail());
        if(clientReservations == null){
            clientReservations = new LinkedList<>();
        }

        clientReservations.add(reservation);
        reservationDetails.put(customer.getEmail(), clientReservations);

        return reservation;
    }

    public Collection<IRoom> findARooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new LinkedList<>();
        Collection<IRoom> reservedRooms = searchRooms(checkInDate, checkOutDate);
        for (IRoom rooms : roomMap.values()) {
            if (!reservedRooms.contains(rooms)) {
                availableRooms.add(rooms);
            }
        }
        return availableRooms;
    }

    public Collection<IRoom> searchRooms(Date checkInDate, Date checkOutDate){
        Collection<Reservation> allReservations = getAllReservations();
        Collection<IRoom> reservedRooms = new LinkedList<>();
        for(Reservation reservation : allReservations){
            if(checkInDate.after(reservation.getCheckInDate())|| checkInDate.equals(reservation.getCheckInDate())
                && checkOutDate.before(reservation.getCheckOutDate()) || checkOutDate.equals(reservation.getCheckOutDate())){
                reservedRooms.add(reservation.getRoom());
            }
        }
        return reservedRooms;
    }

    private Collection<Reservation> getAllReservations() {
        Collection<Reservation> allCustomerReservations = new LinkedList<>();
        for(Collection<Reservation> reservations : reservationDetails.values()){
            allCustomerReservations.addAll(reservations);
        }
        return allCustomerReservations;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        return reservationDetails.get(customer.getEmail());
    }

    public void printAllReservations(){
        Collection<Reservation> reservationCollection = getAllReservations();
        if(reservationCollection.isEmpty()){
            System.out.println("No rooms have been reserved yet!");
        }
        else{
            for(Reservation reservation : reservationCollection){
                System.out.print("Reservations: \n"+reservation);
            }
        }

    }
}
