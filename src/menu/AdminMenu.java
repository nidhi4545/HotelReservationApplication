package menu;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static boolean flag = true;
    public static final String adminMenu = """
            Admin Menu Content
            ------------------------------------------
            1. See all Customers
            2. See all Rooms
            3. See all Reservations
            4. Add a Room
            5. Back to main Menu\040
            ------------------------------------------
             Please select a number for the menu option\040
            """;

    public static void displayAdminMenu(Scanner scan) {
        try {
            while (flag) {
                System.out.println(adminMenu);
                while (scan.hasNext()) {
                    while (!scan.hasNextInt()) {
                        System.out.println("Please select number between 1 and 5.");
                        scan.next();
                    }
                    int temp = scan.nextInt();
                    if (temp > 0 && temp < 6) {
                        scan.nextLine();
                        userInput(scan, temp);
                        break;
                    }
                }
                System.out.println("Please select a number between 1 and 5");
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    protected static void userInput(Scanner scan, int userInput) {
        switch (userInput) {
            case 1 -> seeAllCustomers();
            case 2 -> seeAllRooms();
            case 3 -> AdminResource.displayAllReservations();
            case 4 -> addARoom(scan);
            case 5 -> flag = false;
            default -> throw new IllegalStateException("Number out of main menu options. Please choose a number from 1 to 5.");
        }
    }

    public static void seeAllCustomers(){
        Collection<Customer> customerCollection = AdminResource.getAllCustomers();
        if(!customerCollection.isEmpty()){
            for(Customer customer : customerCollection){
                System.out.println(customer);
            }
        }else{
            System.out.println("No customers have been added.\n");
        }

    }

    public static void seeAllRooms(){
        Collection<IRoom> allRooms = AdminResource.getAllRooms();
        if(!allRooms.isEmpty()){
            for(IRoom rooms : allRooms){
                System.out.println(rooms);
            }
        }else{
            System.out.println("No rooms found.\n");
        }

    }

    public static void addARoom(Scanner scan){
        List<IRoom> rooms = new ArrayList<>();
        RoomType roomType = RoomType.SINGLE;

        String userChoice = "y";
        while (userChoice.equals("y")) {
            System.out.println("Enter room number");
            String roomNumber = scan.next();

            System.out.println("Enter price per night");
            while (!scan.hasNextDouble()) {
                System.out.println("Please input the price (Format: 00.00): ");
                scan.next();
            }
            double price = scan.nextDouble();

            System.out.println("Enter room type: 1 for SINGLE bed,  2 for DOUBLE bed");
            while (scan.hasNext()) {
                int temp = scan.nextInt();
                if (temp == 1 || temp == 2) {
                    roomType = temp == 1 ? RoomType.SINGLE : RoomType.DOUBLE;
                    break;
                }
                System.out.println("Please input 1 or 2...");
            }

            Room room = new Room(roomNumber, price, roomType);
            System.out.println("Room Number: " + roomNumber + " |"
                    + "Room-Price: " + price + " |"
                    + "Room-Type: " + roomType);
            rooms.add(room);

            System.out.println("Add more rooms? y/n");
            userChoice = scan.next();
        }
        AdminResource.addRoom(rooms);
    }
}
