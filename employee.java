import java.sql.*;

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
  /**
 * @param employee_id The identifier for the employee.
 * @param username The unique username of the employee.
 * @param role The role of the employe (manager, engineer, technician, intern)
 * @param name The name of the employee.
 * @param surname The surname of the employee.
 * @param phoneNo The phone number of the employee.
 * @param dateOfBirth The birth date of the employee (YYYY-MM-DD).
 * @param dateOfStart The start date of the employee (YYY-MM-DD).
 * @param email The email adress of the employee.
 */
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
    /**
     * Allows user to change the password after first login.
     * 
     * Checks if the user's password is default password ("password123")
     * if the user's password is default, user will change their password.
     */
    public void defaultPasswordChange(){
        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        try (Connection connection = start.connect(); PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, this.username);
                statement.setString(2, "password123");
                
                ResultSet resultSet = statement.executeQuery();
    
                
                if (resultSet.next()) {
                    System.out.println("Welcome! Your current password is the default password.");
                    System.out.print("Type your desired password to change your password: ");
                    String password = start.scanner.nextLine();
                    query = "UPDATE employees SET password = ? WHERE username = ?";
                    PreparedStatement statement2 = connection.prepareStatement(query);
                    statement2.setString(2, this.username);
                    statement2.setString(1, password);
                    if(statement2.executeUpdate() > 0){
                        System.out.println("Password is updated.");
                        System.out.print("Press enter to continue: ");
                        start.scanner.nextLine();
                    }
                    // Update information
                    
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
    /**
     * Prints the profile information of the user in a table format.
     * 
     * Informations include:
     * <ul>
     * <li>Employee ID</li>
     *<li>Username</li>
     * <li>Role</li>
     * <li>Name</li>
     * <li>Surname</li>
     * <li>Phone Number</li>
     * <li>Date of Birth</li>
     * <li>Date of Start</li>
     * <li>Email</li>
     * <ul>
     */
    public void displayProfile() {
        start.clear();
        // Printing the profile of the employee
        System.out.println("===================================================================================================================================================================================");
        System.out.printf("%-15s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",
            "Employee ID", "Username", "Role", "Name", "Surname", "Phone Number", "Date of Birth", "Date of Start", "Email");
        System.out.println("===================================================================================================================================================================================");
        System.out.printf("%-15s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",
            employee_id, username, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
        System.out.println("===================================================================================================================================================================================");
        System.out.print("Press enter to continue: ");
        start.scanner.nextLine();

    }
    
    /**
     * Allows user to update their profile informations.
     * 
     * The user can choose one field to update or go back on menu:
     * <ul>
     * <li>Password</li>
     * <li>Phone Number</li>
     * <li>Email</li>
     * <li>Go back</li>
     * 
     * If the user's input is valid, the selected field will be updated.
     * If the user's input is invalid, system will ask another input.
     * 
     * The user can choose to change another field to update or exit the menu.
     * 
     * @throws SQLException If a database error ocurs during the update.
     */
    public void updateProfile() {
        boolean updateContinue = true;
        while (updateContinue) {
            start.clear();
            // Update profile menu
            System.out.println("\n    Update Profile ");
            System.out.println("Which information would you like to update?");
            System.out.println("[A] Password");
            System.out.println("[B] Phone Number");
            System.out.println("[C] Email");
            System.out.println("[D] Go Back");
            System.out.print("Choose the operation you want to do: ");
            String input = start.scanner.nextLine();
            input = start.menuInput('D', input, "Incorrect input! Type again to select the operation: ");

            String query = null;
            String newValue = null;
            start.clear();
            // Selection of which part to update
            switch (input) {
                case "A":
                    System.out.print("Enter your new password or type 'X' to go to previous menu: ");
                    newValue = start.scanner.nextLine();
                    if(newValue.equals("X"))
                        return;
                    query = "UPDATE employees SET password = ? WHERE username = ?";
                    break;
                case "B":
                    System.out.print("Enter your new phone number without 0 at the beginning or type 'X' to go to previous menu: ");
                    newValue = start.scanner.nextLine();
                    newValue = start.inputControl("phone", newValue, "Incorrect input! Please type your new phone number without 0 at the beginning or type 'X' to go to previous menu: ", true);
                    if(newValue.equals("X"))
                        return;
                    query = "UPDATE employees SET phone_no = ? WHERE username = ?";
                    break;
                case "C":
                    System.out.print("Enter your new email in 'XXX@khas.firm' format or type 'X' to go to previous menu: ");
                    newValue = start.scanner.nextLine();
                    newValue = start.inputControl("mail", newValue, "Incorrect input! Please type your email in 'XXX@khas.firm' format or type 'X' to go to previous menu: ", true);
                    if(newValue.equals("X"))
                        return;
                    query = "UPDATE employees SET email = ? WHERE username = ?";
                    break;
                case "D":
                    System.out.println("Returning to previous menu.");
                    return;
                default:
                    System.out.println("Your choice is invalid, please enter a number between 1-4.");
                    continue;
            }
            start.clear();
            try (Connection connection = start.connect(); PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newValue);
                statement.setString(2, username);
    
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Profile is updated.");
    
                    // Update information
                    if (input.equals("B")) {
                    this.phoneNo = newValue;
                    }
                    if (input.equals("C")){
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
            String response = start.scanner.nextLine();
            while(!response.equalsIgnoreCase("no") && !response.equalsIgnoreCase("yes")){
                System.out.print("Incorrect input! Please type 'yes' or 'no': ");
                response = start.scanner.nextLine();
            }
            // Turning back to menu
            if (response.equalsIgnoreCase("no")) {
                updateContinue = false;
                System.out.println("Returning to previous menu.");

            }
        }

            /**
     * Check the user's username if it exist in the database.
     * 
     * @param username The unique username to check in database.
     * @return {@code true} if the username exits on database, {@code false} if the username does not exist.
     * @throws SQLException if a database error occurs during the query execution. 
     */
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
