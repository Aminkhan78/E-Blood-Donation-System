import java.util.ArrayList;
import java.util.Scanner;

// Class to store donor information
class Donor {
    String name;
    String bloodGroup;
    String contactNumber;

    // Constructor
    public Donor(String name, String bloodGroup, String contactNumber) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.contactNumber = contactNumber;
    }

    // Method to display donor information
    public void displayInfo() {
        System.out.println("Name: " + name + ", Blood Group: " + bloodGroup + ", Contact: " + contactNumber);
    }
}

// Main Application Class
public class E_BloodDonationSystem {
    // List to store donor information
    static ArrayList<Donor> donorList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    // Main method
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==== E-Blood Donation System ====");
            System.out.println("1. Register as Donor");
            System.out.println("2. Search Donor by Blood Group");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // To consume the newline character

            switch (choice) {
                case 1:
                    registerDonor();
                    break;
                case 2:
                    searchDonor();
                    break;
                case 3:
                    System.out.println("Exiting the application. Thank you!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to register a new donor
    public static void registerDonor() {
        System.out.print("Enter Donor Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Blood Group (e.g., A+, O-, B+): ");
        String bloodGroup = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();

        // Create a new donor object and add to the list
        Donor newDonor = new Donor(name, bloodGroup, contactNumber);
        donorList.add(newDonor);

        System.out.println("Donor registered successfully!");
    }

    // Method to search for donors by blood group
    public static void searchDonor() {
        System.out.print("Enter Blood Group to Search (e.g., A+, O-, B+): ");
        String bloodGroup = scanner.nextLine();

        boolean found = false;
        System.out.println("=== Search Results ===");
        for (Donor donor : donorList) {
            if (donor.bloodGroup.equalsIgnoreCase(bloodGroup)) {
                donor.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No donors found with blood group " + bloodGroup);
        }
    }
}
