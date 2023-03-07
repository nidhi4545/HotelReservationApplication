package menu;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    public static boolean flag = true;
    public static final String mainMenu = """
            Welcome to Hotel Reservation Application
            ------------------------------------------
            1. Find and Reserve a room
            2. See my reservation
            3. Create an Account
            4. Admin menu
            5. Exit\040
            ------------------------------------------
             Please select a number for the menu option\040
            """;

    public static void displayMainMenu() {
        try (Scanner scan = new Scanner(System.in)) {
            while (flag) {
                System.out.println(mainMenu);
                while (scan.hasNext()) {
                    while (!scan.hasNextInt()) {
                        System.out.println("Please select number between 1 and 5.");
                        scan.next();
                    }
                    int temp = scan.nextInt();
                    if (temp > 0 && temp < 6) {
                        scan.nextLine();
                        menuChoice(scan, temp);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    protected static void menuChoice(Scanner scan, int numChosen) {
        switch (numChosen) {
            case 1 -> findAndReserveARoom(scan);
            case 2 -> seeMyReservation(scan);
            case 3 -> createAnAccount(scan);
            case 4 -> AdminMenu.displayAdminMenu(scan);
            case 5 -> flag = false;
            default -> throw new IllegalStateException("Number out of main menu options. Please choose a number from 1 to 5.");
        }
    }

    public static void findAndReserveARoom(Scanner scan) {
        SimpleDateFormat bookingDate = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("Enter Check-In date: (Date format: MM/dd/yyyy): ");
        Date checkInDate = null;
        try {
            checkInDate = bookingDate.parse(scan.nextLine());
        } catch (ParseException e) {
            e.getLocalizedMessage();
        }

        System.out.println("Enter Check-Out date: (Date format: MM/dd/yyyy): ");
        Date checkOutDate = null;
        try {
            checkOutDate = bookingDate.parse(scan.nextLine());
        } catch (ParseException e) {
            e.getLocalizedMessage();
        }
        System.out.println();

        Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);

        if (availableRooms.isEmpty()) {
            System.out.println("There are no rooms available during the given dates. Below are our recommendations: ");
            //int daysForward = scan.nextInt();
            //calculatingDays(daysForward,checkInDate,checkOutDate);
            //try {
                Calendar calendar = new GregorianCalendar();
                assert checkInDate != null;
                calendar.setTime(checkInDate);
                calendar.add(Calendar.DATE, 7);
                checkInDate = calendar.getTime();
                assert checkOutDate != null;
                calendar.setTime(checkOutDate);
                calendar.add(Calendar.DATE, 7);
                checkOutDate = calendar.getTime();
//            } catch (Exception e) {
//                System.out.println(e.getLocalizedMessage());
//            }
            availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);
            System.out.println("Showing recommendations between dates: " + checkInDate + " and " + checkOutDate);
        }
        for (IRoom room : availableRooms) {
            System.out.println(room);
        }
        System.out.println();

        String customerEmail = "abc@domain.com";
        System.out.println("Do you have an account? (y/n): ");
        char userChoice = scan.next().charAt(0);
        if (userChoice == 'y') {
            System.out.println("Please enter your registered email: (Email format: abc@domain.com)");
            String userEmail = scan.nextLine();
            Customer customer = AdminResource.getCustomer(userEmail);
            if (customer == null) {
                customerEmail = createAnAccount(scan);
            } else {
                customerEmail = userEmail;
            }
        } else if (userChoice == 'n') {
            customerEmail = createAnAccount(scan);
        } else {
            System.out.println("Please provide a valid email address!");
        }

        System.out.println("Please enter the room number that you would like to reserve: ");
        while (!scan.hasNextInt()) {
            System.out.println("Please enter your choice of room number: ");
            scan.next();
            while (true) {
                IRoom reservingRoom = HotelResource.getRoom(scan.nextLine());
                if (availableRooms.contains(reservingRoom)) {
                    Reservation customerReservation = HotelResource.reserveARoom(customerEmail, reservingRoom, checkInDate, checkOutDate);
                    System.out.println(customerReservation);
                    break;
                } else {
                    System.out.println("Please provide a valid room number");
                }
            }
        }
    }

//    private static String calculatingDays(int days, Date checkInDate, Date checkOutDate){
//        Calendar calendar = new GregorianCalendar();
//        assert checkInDate != null;
//        calendar.setTime(checkInDate);
//        calendar.add(Calendar.DATE, days);
//        checkInDate = calendar.getTime();
//        assert checkOutDate != null;
//        calendar.setTime(checkOutDate);
//        calendar.add(Calendar.DATE, days);
//        checkOutDate = calendar.getTime();
//        return (checkInDate+" "+checkOutDate);
//    }

    public static void seeMyReservation(Scanner scan) {
        System.out.println("Enter your email: ");
        String email = scan.next();
//        scan.next();
        if (HotelResource.getCustomer(email) != null) {
            for (Reservation reservation : HotelResource.getCustomersReservations(email)) {
                System.out.println(reservation);
            }
        } else {
            System.out.println("No reservations found! Please check if you have entered the correct email address registered on our website.");
        }
    }

    public static String createAnAccount(Scanner scan) {
        System.out.println("Please enter your first name: ");
        String firstName = scan.nextLine();
        scan.next();

        System.out.println("Please enter your last Name: ");
        String lastName = scan.nextLine();
        scan.next();

        System.out.println("Please enter your email address (Format: abc@domain.com): ");
        String emailAddress = scan.nextLine();
        //scan.next();

        HotelResource.createACustomer(emailAddress, firstName, lastName);
        //System.out.println();
        System.out.println("Name: " + firstName + " " + lastName + ", \n" +
                "Email: " + emailAddress);
        return emailAddress;

    }

}
