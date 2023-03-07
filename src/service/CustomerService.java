package service;
//So, this was the most challenging class that I have worked on creating as there were many new concepts involved. Added comments below.
import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
/*
    the below line helps in creating the only global entry point to the CustomerService class. As can be seen below, it is declared private meaning that no outsider can directly mess with the
    variable. The declaration and instantiation is done together
*/
    private static final CustomerService customerServiceInstance = new CustomerService();

//The default constructor of the class is also declared private so that no new object/instance of the class can be created outside the class
    private CustomerService(){}

//The below method declared public helps in returning the instance of the class which was declared above. This is the only way in which the instance of the class can be called/accessed
//As you can also see that there are 2 lines of code commented in the below block, that signifies another way of instantiating the class. This method is known as the LAZY INITIALIZATION
    public static CustomerService getInstance(){
//        if(customerServiceInstance == null){
//            customerServiceInstance = new CustomerService();
//        }
       return  customerServiceInstance;
    }

    private final Map<String, Customer> clients = new HashMap<String, Customer>();

    //Adding the clients to the hashmap
    public void addCustomer(String email, String firstName, String lastName){
        clients.put(email,new Customer(email,firstName,lastName));
    }

    public Customer getCustomer(String customerEmail){
        return clients.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers(){
        return clients.values();
    }

}
