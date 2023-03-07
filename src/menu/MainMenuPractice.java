package menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import api.HotelResource;
import model.Customer;
import model.IRoom;

import model.Reservation;

public class MainMenuPractice {

    private static final String mainMenu = """
            Welcome to Hotel Reservation Application
            ------------------------------------------
            1. Find and Reserve a Room
            2. See my reservation
            3. Create an account
            4. Admin Menu
            5. Exit
            -------------------------------------------
            Please select the desired number from the above menu\040
            """;
    //to repeatedly display the menu, I have 3 loop choices. For, while, do...while. Other one is also, switch case.

    static final Scanner scan = new Scanner(System.in);
    public static void initialize() {
        int choice = 1;
//what if I move while loop inside try catch block? not working. still shows error
        while(choice > 0 && choice <6){
            System.out.println(mainMenu);
            choice = scan.nextInt();
            menuChoice(choice);
        }
    }

    public static void menuChoice(int selectedMenuNumber){
        switch (selectedMenuNumber) {
            case 1 -> findAndReserveARoom(scan);
            case 2 -> seeMyReservation( scan);
            case 3 -> createAnAccount(scan);
            case 4 -> AdminMenu.displayAdminMenu(scan);
            case 5 -> exitApplication();
            default -> System.out.println("Number selected is out of range. Please select from 1 to 5.");
        }
    }

    public static void findAndReserveARoom(Scanner scan){
/*        in any hotel booking app you browse, the first is that they ask for the location you are travelling to.
        the second is the dates that you are visiting on
        the third is the available rooms
        fourth if the room you need is not available on the desired dates, other rooms will be recommended.
        fifth becomes checking if you have an account and if not then creating one
        now, code everything :)

 */

        System.out.println("Please enter the location that you would like to travel to: ");
        String location = scan.nextLine();
        SimpleDateFormat Default_Date_Format = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("Please enter the Check-In date: (format: mm/dd/yyyy");
        Date[] checkInCheckOutDates = new Date[2];
        try {
            checkInCheckOutDates[0] = Default_Date_Format.parse(scan.nextLine());
        }catch(ParseException e){
            e.getLocalizedMessage();
        }
        System.out.println("Please enter the Check-Out date: (format: mm/dd/yyyy");
        try {
            checkInCheckOutDates[1] = Default_Date_Format.parse(scan.nextLine());
        }catch(ParseException e){
            e.getLocalizedMessage();
        }

        Collection<IRoom> availableRooms = null;
        if(checkInCheckOutDates[0] != null && checkInCheckOutDates[1] != null){
            availableRooms = HotelResource.findARoom(checkInCheckOutDates[0],checkInCheckOutDates[1]);

            if(availableRooms.isEmpty()) {
                System.out.println("No rooms are available during the desired dates.");
                checkInCheckOutDates = updateDate(checkInCheckOutDates[0], checkInCheckOutDates[1]);
                Collection<IRoom> alternativeRooms = HotelResource.findARoom(checkInCheckOutDates[0], checkInCheckOutDates[1]);
                if (alternativeRooms.isEmpty()) {
                    System.out.println("No rooms found between alternative dates " + checkInCheckOutDates[0] + " and " + checkInCheckOutDates[1]);
                }else{
                    System.out.println("We have found below rooms for alternative dates: "+checkInCheckOutDates[0]+" - "+checkInCheckOutDates[1]);
                    printRooms(alternativeRooms);
                }
            }else{
                System.out.println("Below are the available rooms for your desired dates");
                printRooms(availableRooms);
            }
        }

        System.out.println("Would you like to book a room with us? (y/n): ");
        char userResponse = scan.next().charAt(0);
        String userRegisteredEmail = "";
        Customer customer;
        if(userResponse == 'y'){
            System.out.println("Please enter the registered email:");
            userRegisteredEmail = scan.nextLine();
           if(HotelResource.getCustomer(userRegisteredEmail) == null){
               System.out.println("The entered email is not registered with us. Please create an account to proceed further");
           }else{
               customer = HotelResource.getCustomer(userRegisteredEmail);
           }
        }else if(userResponse == 'n'){
            System.out.println("Please create an account before proceeding.");
            userRegisteredEmail = createAnAccount(scan);
            }

        System.out.println("Please let us know the room number of your choice: ");
        assert availableRooms != null;
        printRooms(availableRooms);
        String userRoomNumberChoice = scan.nextLine();
            if(availableRooms.stream().allMatch(room -> room.getRoomNumber().equals(userRoomNumberChoice))) {
                IRoom room = HotelResource.getRoom(userRoomNumberChoice);
                Reservation reservation = HotelResource.reserveARoom(userRegisteredEmail, room, checkInCheckOutDates[0], checkInCheckOutDates[1]);
                System.out.println("Your room has been reserved! Please see the details: ");
                System.out.println(reservation);
            }else{
                System.out.println("No desired rooms are available. Please check for other dates or see alternative options");
            }

        System.out.println("A final check on your reservation details:\n"+
                "Destination: "+location+"\n"+
                "Stay duration: "+checkInCheckOutDates[0]+" "+checkInCheckOutDates[1]+"\n"+
                "Email address registered: "+userRegisteredEmail);
        }

    private static Date[] updateDate(Date checkinDate, Date checkoutDate){
        Date[] updatedDates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        assert checkinDate != null;
        calendar.setTime(checkinDate);
        calendar.add(Calendar.DATE,7);
        updatedDates[0] = calendar.getTime();

        calendar.setTime(checkoutDate);
        calendar.add(Calendar.DATE,7);
        updatedDates[1] = calendar.getTime();

        return updatedDates;
    }

    public static void seeMyReservation(Scanner scan){
        System.out.println("To check your reservation, please enter the email registered with us: ");
        String userEnteredEmail = scan.nextLine();
        if(HotelResource.getCustomer(userEnteredEmail) != null){
            for(Reservation reservation : HotelResource.getCustomersReservations(userEnteredEmail)){
                System.out.println(reservation);
            }
        }else{
            System.out.println("No reservations found for the entered email address");
        }
    }

    public static String createAnAccount(Scanner scan){
        System.out.println("Please enter your first name: ");
        String firstName = scan.nextLine();
        System.out.println("Please enter your last name: ");
        String lastName = scan.nextLine();
        System.out.println("Please enter the email id you would like to register with us: ");
        String email = scan.nextLine();
        System.out.println("Please confirm your details: \n"+
                "Name: "+firstName+" "+lastName+" \n"+
                "Email Address: "+email+
                " \n Please choose y/n: ");
        char userChoice = scan.nextLine().charAt(0);

        if(userChoice == 'y') {
            HotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Name: " + firstName + " " + lastName + " \n" +
                    "Email Address: " + email);
        }else if(userChoice == 'n'){
            //switch case might be better
            System.out.println("Please enter the field to be changed: ");
            String userAnswer = scan.nextLine();
            if(userAnswer.equals(firstName)){
                System.out.println("Please enter the corrected first name");
                firstName = scan.nextLine();
            }else if(userAnswer.equals(lastName)){
                lastName = scan.nextLine();
            }else if(userAnswer.equals(email)){
                email = scan.nextLine();
            }
            HotelResource.createACustomer(email,firstName,lastName);
            System.out.println("Name: "+firstName+" "+lastName+" \n"+
                    "Email Address: "+email);
        }
        return email;
    }

    public static void exitApplication(){
        System.exit(0);
    }

    private static void printRooms(Collection<IRoom> room){
        if(room.isEmpty()){
            System.out.println("There is no room data available");
        }else{
            room.forEach(System.out::println);
        }
    }
}
