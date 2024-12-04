import java.sql.*;
/**
 * Represents an employee and its common features.
 * Abstract class defines common features of an employee.
 */
abstract class employee {
    /**
     * The unique id for the employee.
     */
    protected String employee_id;
    /**
     * The username of the employee for login purposes.
     */
    protected String username;
    /**
     * The role of the employee, e.g., manager, engineer, technician, or intern.
     */
    protected String role;
    /**
     * The first name of the employee.
     */
    protected String name;
    /**
     * The last name of the employee.
     */
    protected String surname;
    /**
     * The phone number of the employee, stored as a string.
     */
    protected String phoneNo;
    /**
     * The date of birth of the employee in the format YYYY-MM-DD.
     */
    protected String dateOfBirth;
    /**
     * The date when the employee started working in the format YYYY-MM-DD.
     */
    protected String dateOfStart;
    /**
     * The email address of the employee.
     */
    protected String email;
  /**
   * Constructor for employee class.
   * 
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
                    start.clear();
                    System.out.println("Welcome! Your current password is the default password.");
                    boolean flag = true;
                    String password = "";
                    while(flag){
                        System.out.print("Type your desired password to change your password: ");
                        password = start.scanner.nextLine();
                        boolean flag2 = true;
                        while(flag2){
                            if(password.contains(" ")){
                                System.out.print("Password can't contain space character! Please choose a different password: ");
                                password = start.scanner.nextLine();
                            }
                            else if(password.equals("password123")){
                                System.out.print("Password can't be same as the default password! Please choose a different password: ");
                                password = start.scanner.nextLine();
                            }
                            else
                                flag2 = false;
                        }
                        int passScore = passwordChecker(password);
                        if(passScore <= 2)
                            System.out.print("Your password security is very weak! ");
                        else if(passScore <= 4)
                            System.out.print("Your password security is weak! ");
                        else if(passScore <= 6)
                            System.out.print("Your password security is medium! ");
                        else if(passScore <= 8)
                            System.out.print("Your password security is strong! ");
                        else
                            System.out.print("Your password security is very strong! ");
                        System.out.print("Do you want to select a different password(type yes or no): ");
                        String choice = start.scanner.nextLine();
                        if(choice.equals("no"))
                            flag = false;
                        else{
                            while(!choice.equals("no") && !choice.equals("yes")){
                                System.out.print("Incorrect input! Please type yes or no: ");
                                choice = start.scanner.nextLine();
                            }
                        }
                        start.clear();
                    }
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
     * 
     * Employee ID
     * Username
     * Role
     * Name
     * Surname
     * Phone Number
     * Date of Birth
     * Date of Start
     * Email
     * 
     */
    protected void displayProfile() {
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
     * 
     * Password
     * Phone Number
     * Email
     * Go back
     * 
     * If the user's input is valid, the selected field will be updated.
     * If the user's input is invalid, system will ask another input.
     * 
     * The user can choose to change another field to update or exit the menu.
     * 
     */
    protected void updateProfile() {
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
                    boolean flag = true;
                    while(flag){
                        System.out.print("Enter your new password or type 'X' to go to previous menu: ");
                        newValue = start.scanner.nextLine();
                        boolean flag2 = true;
                        while(flag2){
                            if(newValue.contains(" ")){
                                System.out.print("Password can't contain space character! Please choose a different password: ");
                                newValue = start.scanner.nextLine();
                            }
                            else if(newValue.equals("password123")){
                                System.out.print("Password can't be same as the default password! Please choose a different password: ");
                                newValue = start.scanner.nextLine();
                            }
                            else
                                flag2 = false;
                        }
                        if(newValue.equals("X"))
                            return;
                        int passScore = passwordChecker(newValue);
                        if(passScore <= 2)
                            System.out.print("Your password security is very weak! ");
                        else if(passScore <= 4)
                            System.out.print("Your password security is weak! ");
                        else if(passScore <= 6)
                            System.out.print("Your password security is medium! ");
                        else if(passScore <= 8)
                            System.out.print("Your password security is strong! ");
                        else
                            System.out.print("Your password security is very strong!");
                        System.out.print("Do you want to select a different password(type yes or no): ");
                        String choice = start.scanner.nextLine();
                        if(choice.equals("no"))
                            flag = false;
                        else{
                            while(!choice.equals("no") && !choice.equals("yes")){
                                System.out.print("Incorrect input! Please type yes or no: ");
                                choice = start.scanner.nextLine();
                            }
                        }
                        start.clear();
                    }
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
    }


    /**
     * Checks the security level of the selected password
     * 
     * @param password selected password
     * @return security level of the given password
     */
    protected int passwordChecker(String password){
        int passScore = 0;
        int lowerCase = 0;
        int upperCase = 0;
        int number = 0;
        int special = 0;
        int turkish = 0;
        if(password.length() >= 12)
            passScore += 2;
        for(int i = 0; i < password.length(); i++){
            char current = password.charAt(i);
            if((current >= 'a' && current <= 'z') && lowerCase == 0){
                passScore += 1;
                lowerCase = 1;
            }
            else if((current >= 'A' && current <= 'Z') && upperCase == 0){
                passScore += 1;
                upperCase = 1;
            }
            else if((current >= '0' && current <= '9') && number == 0){
                passScore += 1;
                number = 1;
            }
            else if("çğöşüÇĞÖŞÜİı".indexOf(current) != -1 && turkish == 0){
                passScore += 1;
                turkish = 1;
            }
            if((current < 'a' || current > 'z') && (current < 'A' || current > 'Z') && (current < '0' || current > '9') && "çğöşüÇĞÖŞÜİı".indexOf(current) == -1 && special == 0){
                passScore += 1;
                special = 1;
            }
        }
        return passScore;
    }

    /**
     * Check the user's username if it exist in the database.
     * 
     * @param username The unique username to check in database.
     * @return {@code true} if the username exits on database, {@code false} if the username does not exist.
     */
    public boolean checkUser(String username){
        final String checkUsernameQuery = "SELECT COUNT(*) FROM employees WHERE username COLLATE utf8mb4_bin = ?";
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








