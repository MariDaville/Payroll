// File: src/Employee.java
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public abstract class Employee implements Payable {
    private String firstName;
    private String lastName;
    private String socialSecurityNumber;

    public Employee(String firstName, String lastName, String socialSecurityNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    @Override
    public void writeToFile() {
        try (FileWriter writer = new FileWriter("paystub.txt", true)) {
            writer.write("Name: " + getFirstName() + " " + getLastName() + "\n");
            writer.write("Payment Amount: " + getPaymentAmount() + "\n");
            writer.write("Date of Payment: " + LocalDate.now() + "\n\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}