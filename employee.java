import java.sql.*;
import java.util.Scanner;

abstract class employee {
    protected String employee_id;
    protected String username;
    protected String role;
    protected String name;
    protected String surname;
    protected String phoneNo;
    protected String dateOfBirth;
    protected String dateOfStart;
    protected String email;

    // Constructor
    public employee(String employee_id, String username, String role, String name, String surname,
                    String phoneNo, String dateOfBirth, String dateOfStart, String email) {
        this.employee_id = employee_id;                
        this.username = username;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.phoneNo = phoneNo;
        this.dateOfBirth = dateOfBirth;
        this.dateOfStart = dateOfStart;
        this.email = email;
    }

    public void displayProfile() {
    // Printing the profile of the employee
    System.out.println("======================================================================================================================================================================================");
    System.out.printf("%-15s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",
        "Employee ID", "Username", "Role", "Name", "Surname", "Phone Number", "Date of Birth", "Date of Start", "Email");
    System.out.println("======================================================================================================================================================================================");
    System.out.printf("%-15s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",
        employee_id, username, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    System.out.println("======================================================================================================================================================================================");

    }
    
    // Updating the user's profile
    public void updateProfile() {
        Scanner scanner = new Scanner(System.in);
        boolean updateContinue = true;
    
        while (updateContinue) {
            // Update profile menu
            System.out.println("\n    Update Profile ");
            System.out.println("Which information would you like to update?");
            System.out.println("[1] Password");
            System.out.println("[2] Phone Number");
            System.out.println("[3] Email");
            System.out.println("[4] Go Back");
            System.out.print("Choose an option between 1-4: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            String query = null;
            String newValue = null;
    
            // Selection of which part to update
            switch (input) {
                case 1:
                    System.out.print("Enter your new password: ");
                    newValue = scanner.nextLine();
                    query = "UPDATE employees SET password = ? WHERE username = ?";
                    break;
                case 2:
                    System.out.print("Enter your new phone number: ");
                    newValue = scanner.nextLine();
                    query = "UPDATE employees SET phone_no = ? WHERE username = ?";
                    break;
                case 3:
                    System.out.print("Enter your new email: ");
                    newValue = scanner.nextLine();
                    query = "UPDATE employees SET email = ? WHERE username = ?";
                    break;
                case 4:
                    System.out.println("Returning to previous menu.");
                    return;
                default:
                    System.out.println("Your choice is invalid, please enter a number between 1-4.");
                    continue;
            }
            
            try (Connection connection = start.connect(); PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newValue);
                statement.setString(2, username);
    
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Profile is updated.");
    
                    // Update information
                    if (input == 2) {
                    this.phoneNo = newValue;
                    }
                    if (input == 3){
                    this.email = newValue;
                    }

                } else {
                    System.out.println("Error occured while updating profile.");
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            // Ask the user if they want to update again
            System.out.print("Would you like to update another information? (yes/no): ");
            String response = scanner.nextLine();
            // Turning back to menu
            if (response.equalsIgnoreCase("no")) {
                updateContinue = false;
                System.out.println("Returning to previous menu.");

            }
        }
    }
    public boolean checkUser(String username){
        final String checkUsernameQuery = "SELECT COUNT(*) FROM employees WHERE username = ?";
        try(Connection connection = start.connect();
        PreparedStatement statement = connection.prepareStatement(checkUsernameQuery);){
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt(1) > 0;
            }
        }
        catch (SQLException e) {
            System.out.println("Error occurred when retrieving data from database by username: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}   
