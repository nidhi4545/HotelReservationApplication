package model;
//this class is created to take the user details for the application
import java.util.Objects;
import java.util.regex.*;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    //As you can see, we can also add checks in the constructor and also define states(fields, instances) within the constructor itself.
    //The scope is limited within the constructor
    public Customer(String email, String fName, String lName) {
        this.firstName = fName;
        this.lastName = lName;
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(email).matches()){
                throw new IllegalArgumentException("The email provided is incorrect!");
            }
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    //overriding the toString method of the Object class which will print out the user details when invoked.
    @Override
    public String toString(){
        return "Customer details are: \n"+
                "Name: "+firstName+" "+lastName+"\n"+
                "Email: "+email;
    }

//So before this, I was not aware of this concept of equals and hashcode. I was introduced to it in this course and this project.
//The below method basically helps in checking if the objects are same in any way. There is a contract that says if we change one we need to change both. Need to read more on the topic
//the conditions specified below are: 1. if o refers to this(current) class we return true. 2. If o does not refer to this(current) class or is null, we return false.
//3. We typecast o to Customer(current) class type and return true if the fields match
    @Override
    public boolean equals(Object o){
        if(o == this)return true;

        if(o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email);
    }

//hashcode returns the hash code of the values. At any point the hash and equals results need to return same hash code as long as the tow(or more) objects are equal as is the contract.
    @Override
    public int hashCode(){
        return Objects.hash(firstName, lastName, email);
    }
}
