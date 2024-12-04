import java.sql.*;

/**
 * The Manager class extends the Employee class and provides functionality for
 * managing employees in a company, such as hiring, firing, and updating profiles.
 * It extends the {@code employee} class inherits certain properties
 */
class manager extends employee{
    /**
     * Constructor for manager class.
     * 
    *@param employee_id  The unique ID of the employee.
    *@param username     The individual and unique username of the employees.
    *@param role         The role of the employee.
    *@param name         The first name of the employee.
    *@param surname      The surname of the employee.
    *@param phone_no     The phone number of the employee.
    *@param dateOfBirth  The date of birth of the employee.
    *@param dateOfStart  The date that employee hired to company.
    *@param email        The email adress od the employee.
    **/
    public manager(String employee_id, String username, String role, String name, String surname, String phone_no, String dateOfBirth, String dateOfStart, String email) {
        super(employee_id, username, role, name, surname, phone_no, dateOfBirth, dateOfStart, email);
    }
    
    /**
        *Prints out the manager menu for user to choose which operation will be operated.
        *Includes: 
        *- Displaying and updating manager's own profile.
        *- Managing Employees (Hiring a new employee, firing an existing employee, updating specific fields of employees.)
        *- Running algorithms for obtaining and comparing their runtime for analytical calculations.
        *- Log out option.
    **/
    public void managerMenu(){
    
        String operation = "";
        while(!operation.equals("J")){
            start.clear();
            System.out.println("Welcome " + this.name + " " + this.surname + "\n");
            System.out.println("[A] Display Own Profile");
            System.out.println("[B] Update Own Profile");
            System.out.println("[C] Display All Employees");
            System.out.println("[D] Display Employees With The Role");
            System.out.println("[E] Display Employee With Username");
            System.out.println("[F] Update Employee");
            System.out.println("[G] Hire Employee");
            System.out.println("[H] Fire Employee");
            System.out.println("[I] Algorithms");
            System.out.println("[J] Log Out.");
            System.out.print("Choose the operation you want to do: ");
            operation = start.scanner.nextLine();
            operation = start.menuInput('J', operation, "Incorrect input! Type again to select the operation: ");
            switch(operation){
                case "A":
                    this.displayProfile();
                    break;
                
                case "B":
                    this.updateProfile();
                    break;
                
                case "C":
                    this.displayAllEmployees();
                    break;
                
                case "D":
                    this.displayByRole();
                    break;

                case "E":
                    this.displayByUsername();
                    break;
                
                case "F":
                    this.employeeUpdate();
                    break;
                
                case "G":
                    this.hireEmployee();
                    break;
                
                case "H":
                    this.managerFire();
                    break;

                case "I":
                    algorithms.algorithm();
                    break;
                case "J":
                    // log out
                    break;
            }
        }

    }

    /**
        *Displays all employee records from the database in a table format for managerFire and employeeUpdate functions.

        *Records include:
        *- Employee ID
        *- Username
        *- Name and surname
        *- Role
    **/
    private void displayHelper() {
    
        final String query = "SELECT employee_id, username, name, surname, role FROM employees"; //SQL query for obtaining data from database.
        start.clear();
        try (Connection connection = start.connect();
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(query);
            ){
            ResultSetMetaData allEmps = res.getMetaData();
            int numOfCols = allEmps.getColumnCount();

            //Table view for readibilty.
            System.out.println("===================================================================================================================================================================================");
            System.out.printf("%-15s%-20s%-20s%-20s%-20s\n", "Employee ID", "Username", "Name", "Surname", "Role");
            System.out.println("===================================================================================================================================================================================");

            // To view Query results:
            while (res.next()) {
                for (int i = 1; i <= numOfCols; i++) {
                    if(i == 1)
                        System.out.printf("%-15s", res.getObject(i));
                    else
                        System.out.printf("%-20s", res.getObject(i));
                }
                System.out.println();
            }
            System.out.println("===================================================================================================================================================================================");
        } catch (SQLException e) {
            System.out.println("Error occurred when trying to retrieve data from database: " + e.getMessage());
            e.printStackTrace();
        } 
    }

    
    /**
        *Displays all employee records from the database in a table format.

        *Records include:
        *- Employee ID
        *- Username
        *- Name and surname
        *- Role
        *- Contact information
        *- Date of birth
        *- Date of start
    **/
    private void displayAllEmployees() {
    
        final String query = "SELECT employee_id, username, name, surname, role, phone_no, dateOfBirth, dateOfStart, email FROM employees"; //SQL query for obtaining data from database.
        start.clear();
        try (Connection connection = start.connect();
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(query);
            ){
            ResultSetMetaData allEmps = res.getMetaData();
            int numOfCols = allEmps.getColumnCount();

            //Table view for readibilty.
            System.out.println("===================================================================================================================================================================================");
            System.out.printf("%-15s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Employee ID", "Username", "Name", "Surname", "Role", "Phone Number","Date of Birth", "Date of Start", "Email");
            System.out.println("===================================================================================================================================================================================");

            // To view Query results:
            while (res.next()) {
                for (int i = 1; i <= numOfCols; i++) {
                    if(i == 1)
                        System.out.printf("%-15s", res.getObject(i));
                    else
                        System.out.printf("%-20s", res.getObject(i));
                }
                System.out.println();
            }
            System.out.println("===================================================================================================================================================================================");
            System.out.print("Press enter to continue: ");
            start.scanner.nextLine();
        } catch (SQLException e) {
            System.out.println("Error occurred when trying to retrieve data from database: " + e.getMessage());
            e.printStackTrace();
        } 
    }
    
    /**
        *Removes an employee record from the database based on the specified employee ID.
        *The function checks:
        *- Managers cannot fire themselve.
        *- The employee which manager wants to fire must be in the database.
    **/
    private void managerFire(){
    
        start.clear();
        String selectQuery = "SELECT name, surname FROM employees WHERE employee_id = ?";  
        String deleteQuery = "DELETE FROM employees WHERE employee_id = ? "; 
        String name = null, surname = null;

        displayHelper();
        System.out.print("Please enter the employee_id of the employee you want to fire or type 'X' to go back to previous menu: ");
        String employee_id = start.scanner.nextLine();
        employee_id = start.inputControl("number", employee_id, "Incorrect input! Please type again or type 'X' to go back to previous menu: ", true);
        if(employee_id.equals("X"))
            return;
        if(this.employee_id.equals(employee_id)){
            System.out.println("The manager cannot fire himself/herself !");
            System.out.print("Press enter to continue: ");
            start.scanner.nextLine();
            return;
        }

        try(Connection connection = start.connect()) {

            // To obtain 'name' and 'surname' from database by using 'employee_id':
            try(PreparedStatement statementForNameSurname = connection.prepareStatement(selectQuery)) {
                statementForNameSurname.setString(1, employee_id);
                ResultSet res = statementForNameSurname.executeQuery();

                if (res.next()) {
                    name = res.getString("name");
                    surname = res.getString("surname");
                } 
                else {
                    System.out.println("Employee with "+ employee_id + " has not registered in the database!" );
                    System.out.print("Press enter to continue: ");
                    start.scanner.nextLine();
                    return;
                }
            } 
            catch (SQLException e) {
                System.out.println("Error occured while obtaining employee details from database: " + e.getMessage());
                e.printStackTrace();

                return;
            }
    
            //Deleting employee from database depending on Managers' decision:
            try (PreparedStatement statementForDelete = connection.prepareStatement(deleteQuery);){ 
                statementForDelete.setString(1,employee_id);
                int deletedRows = statementForDelete.executeUpdate();

                if(deletedRows > 0){
                    System.out.println("Employee "+ name +" "+ surname + " has been fired !");
                    System.out.print("Press enter to continue: ");
                    start.scanner.nextLine();
                } 
            }
            catch (SQLException e) {
                System.out.println("Some error ocureed when firing wanted employee: " + e.getMessage());
                e.printStackTrace();
            }
        }catch(SQLException e){
            System.out.println("Error ocurred when connecting to database!"+ e.getMessage());
            e.printStackTrace();
        }

    }

    /**
        *Displays employees by specified role depending on the managers input.
    **/
    private void displayByRole(){
    
        String role = "";

         
        start.clear();
        System.out.print("Enter the role for sorting employees for organized display or type 'X' to go back to previous menu (Roles: manager, engineer, technician, intern): ");
        role = start.scanner.nextLine();
        role = start.inputControl("role", role, "The role that you entered is invalid! Please type again or type 'X' to go back to previous menu (Roles: manager, engineer, technician, intern): ", true);
        if(role.equals("X"))
            return;
            

        final String displayByRoleQuery = "SELECT employee_id, username, name, surname, phone_no, dateOfBirth, dateOfStart, email FROM employees WHERE role = ?";

        try(Connection connection = start.connect();
            PreparedStatement statementForRoleSort = connection.prepareStatement(displayByRoleQuery);){
            
            statementForRoleSort.setString(1, role);
            ResultSet res = statementForRoleSort.executeQuery();
            ResultSetMetaData empsByRole = res.getMetaData();
            int numOfCols = empsByRole.getColumnCount();

            System.out.println("========================================================================================================================================================================");
            System.out.printf("%-15s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Employee ID", "Username", "Name", "Surname", "Phone Number", "Date of Birth", "Date of Start", "Email");
            System.out.println("========================================================================================================================================================================");
            while(res.next()){
                
                for (int i = 1; i <= numOfCols; i++) {
                    if(i == 1)
                        System.out.printf("%-15s", res.getObject(i));
                    else
                        System.out.printf("%-20s", res.getObject(i));
                }
                System.out.println();
            }
            System.out.println("========================================================================================================================================================================");
            System.out.print("Press enter to continue: ");
            start.scanner.nextLine();
        }
        catch(SQLException e){
            System.out.println("Error ocurred when retrieving data from database by specified role: "+ e.getMessage());
            e.printStackTrace();
        }
    }   
    
    /**
        *Displays employee by username which manager inputted.
    **/
    private void displayByUsername(){
        String username = "";
        boolean valid = false;
        start.clear();
        while(valid == false){
            valid = true;
            System.out.print("Enter the username to see the employee or type 'X' to go back to previous menu: ");
            username = start.scanner.nextLine();
            if(username.equals("X"))
                return;
            if(!checkUser(username)){
                System.out.println("Username " + username + " doesn't exist in the database!");
                valid = false;
            }
        }

        final String displayByUsernameQuery = "SELECT employee_id, username, name, surname, role, phone_no, dateOfBirth, dateofStart, email FROM employees WHERE username COLLATE utf8mb4_bin = ?";

        try(Connection connection = start.connect();
        PreparedStatement statementForUsername = connection.prepareStatement(displayByUsernameQuery);){
            statementForUsername.setString(1, username);
            ResultSet res = statementForUsername.executeQuery();
            ResultSetMetaData empsByUsername = res.getMetaData();
            int numOfCols = empsByUsername.getColumnCount();

            System.out.println("===================================================================================================================================================================================");
            System.out.printf("%-15s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Employee ID", "Username", "Name", "Surname", "Role", "Phone Number", "Date of Birth", "Date of Start", "Email");
            System.out.println("===================================================================================================================================================================================");

            while(res.next()){
                for(int i=1; i<=numOfCols;i++){
                    if(i == 1)
                        System.out.printf("%-15s", res.getObject(i));
                    else
                        System.out.printf("%-20s", res.getObject(i));
                }
                System.out.println();
            }
            System.out.println("===================================================================================================================================================================================");
            System.out.print("Press enter to continue: ");
            start.scanner.nextLine();
        }
        catch (SQLException e) {
            System.out.println("Error occurred when retrieving data from database by username: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
        *Adds a new employee to the database with details provided by the manager.
        *Checks for:
        *- Username uniqueness.
        *- Role, phone number, and email formatting.
        
        *Default password that system assesses : "password123".
    **/
    private void hireEmployee() {
    
        start.clear();
        String username = "";
        String role = "";
        String name = "";
        String surname = "";
        String phone_no = "";
        String dateof_birth = "";
        String dateof_start = "";
        String email = "";
        final String query = "SELECT employee_id FROM employees";
        String user = "INSERT INTO employees (employee_id, username, password, role, name, surname, phone_no, dateofbirth, dateofstart, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        
        String defaultPassword = "password123";

        try (Connection connection = start.connect(); PreparedStatement statement = connection.prepareStatement(user)) {
            boolean usernameExists = true;
            Statement rowCheck = connection.createStatement();
            ResultSet res = rowCheck.executeQuery(query);

            String maxId = "";
            int id = 0;
            while(res.next()){
                if(res.getInt(1) > id)
                    id = res.getInt(1);
            }
            id++;
            maxId = String.valueOf(id);
            int usernameTrial = 0;
            while(usernameExists){
                start.clear();
                if(usernameTrial == 0)
                    System.out.print("Please type the username of the employee (username needs to be within the range of 2-50 characters, and can only contain alphanumerical characters): ");
                else
                    System.out.print("Username is already taken by another employee! Please choose another username (username needs to be within the range of 2-50 characters, and can only contain alphanumerical characters): ");
                username = start.scanner.nextLine();
                username = start.inputControl("username", username, "Incorrect input! Please type again (username needs to be within the range of 2-50 characters, and can only contain alphanumerical characters): ", false);
                usernameExists = this.checkUser(username);
                usernameTrial++;
            }
            start.clear();
            System.out.println("Available roles are: manager, engineer, technician, intern");
            System.out.print("Please type the role of the employee: ");
            role = start.scanner.nextLine().toLowerCase();
            role = start.inputControl("role", role, "Incorrect input! Please type again: ", false);
            start.clear();
            System.out.print("Please type the name of the employee: ");
            name = start.scanner.nextLine().toLowerCase();
            name = start.inputControl("letter", name, "Incorrect input. Please type the name of the employee: ", false);
            name = start.upperCaseName(name);
            start.clear();
            System.out.print("Please type the surname of the employee: ");
            surname = start.scanner.nextLine().toLowerCase();
            surname = start.inputControl("letter", surname, "Incorrect input. Please type the name of the employee: ", false);
            surname = start.upperCaseName(surname);
            start.clear();
            System.out.print("Please type the phone number of the employee in '+XXX XXXXXXXXXX' format(accepted country codes: +90, +1, +44, +49, +91) : ");
            phone_no = start.scanner.nextLine();
            phone_no = start.inputControl("phone", phone_no, "Incorrect input. Please type the phone number of the employee in '+XXX XXXXXXXXXX' format(accepted country codes: +90, +1, +44, +49, +91) : ", false);
            start.clear();
            System.out.print("Please type the date of birth of the employee in YYYY-MM-DD format: ");
            dateof_birth = start.scanner.nextLine();
            dateof_birth = start.inputControl("date", dateof_birth, "Incorrect input. Please type the date of birth of the employee in YYYY-MM-DD format: ", false);
            start.clear();
            if(dateof_birth.equals("X"))
                return;
            System.out.print("Please type the date of start of the employee in YYYY-MM-DD format: ");
            dateof_start = start.scanner.nextLine();
            dateof_start = start.inputControl("date", dateof_start, "Incorrect input. Please type the date of start of the employee in YYYY-MM-DD format: ", false);
            start.clear();
            if(dateof_start.equals("X"))
                return;
            System.out.print("Please type the email of the employee: ");
            email = start.scanner.nextLine();
            email = start.inputControl("mail", email, "Incorrect input. Please type the email of the employee: ", false);
            statement.setString(1, maxId);
            statement.setString(2, username);
            statement.setString(3, defaultPassword);
            statement.setString(4, role);
            statement.setString(5, name);
            statement.setString(6, surname);
            statement.setString(7, phone_no);
            statement.setString(8, dateof_birth);
            statement.setString(9, dateof_start);
            statement.setString(10, email);

            int status = statement.executeUpdate();

            if (status > 0) {
                System.out.println("Employee added succesfully!");
                System.out.print("Press enter to continue: ");
                start.scanner.nextLine();
            }

        } catch (SQLException failed) {
            failed.printStackTrace();
        }

    }

    /**
     *Updates specific fields of an employee depending on the manager's input.
     *Editable fields include:
     *- Name
     *- Surname
     *- Role
     *- Username
     *- Date of birth
     *- Date of start
     **/
    private void employeeUpdate() {
        start.clear();
        String employee_id;
        boolean validID = false;
        String checkEmpID = "SELECT * FROM employees WHERE employee_id = ?";
        String employeeName = "";
        String empName = "";
        String empSurname = "";
        String empDateOfBirth = "";
        String empDateOfStart = "";
        String employeeRole = "";
        String employeeUserName = "";
        //Checking the employee is exist?:
        do {
            displayHelper();
            System.out.print("Enter the employee_id of the employee you want to update or type 'X' to go back to previous menu: ");
            employee_id = start.scanner.nextLine();
            employee_id = start.inputControl("number", employee_id, "Incorrect input! Please type again or type 'X' to go back to previous menu: ", true);
            if(employee_id.equals("X"))
            return;
            start.clear();
            try (Connection connection = start.connect();
                PreparedStatement checkStatement = connection.prepareStatement(checkEmpID)) {

                checkStatement.setString(1, employee_id);
                ResultSet res = checkStatement.executeQuery();
                
                if (res.next()) {
                    employeeName = res.getString("name") + " " + res.getString("surname");
                    empName = res.getString("name");
                    empSurname = res.getString("surname");
                    empDateOfBirth = res.getString("dateOfBirth");
                    empDateOfStart = res.getString("dateOfStart");
                    employeeRole = res.getString("role");
                    employeeUserName = res.getString("username");
                    validID = true; 
                    System.out.println("Employee found: "+ employee_id+ " " + employeeName);
                } else {
                    System.out.println("No employee found with EmployeeID: " + employee_id + " Please enter the EmployeeID again!");
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while checking 'employee exists?' in database: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }while(!validID);


        
        //Field selection for updating employee non-profile fields:
        String updateField = null;
        String choice = "";
        boolean flag = true;

        while(!choice.equals("G")){
            start.clear();
            System.out.println("Employee found: "+ employee_id+ " " + employeeName);
            System.out.println("Fields to update:");
            System.out.println("[A] Name");
            System.out.println("[B] Surname");
            System.out.println("[C] Date of Birth");
            System.out.println("[D] Date of Start");
            System.out.println("[E] Role");
            System.out.println("[F] Username");
            System.out.println("[G] Go Back To Previous Menu");
            System.out.print("Select the employee field you want to update: ");
            choice = start.scanner.nextLine();
            choice = start.menuInput('G', choice, "Incorrect input! Please type again: ");
            String newValue = "";
            start.clear();
            switch (choice) {
                case "A":
                    updateField = "name";
                    flag = true;
                    while (flag){
                        System.out.print("Enter the new name for " + employeeName +" or type 'X' to go back to main menu: ");
                        newValue = start.scanner.nextLine();
                        newValue = start.inputControl("letter", newValue, "Incorrect input! Please type again or type 'X' to go back to main menu: ", true);
                        if(newValue.equals("X"))
                            return;
                        newValue = start.upperCaseName(newValue);
                        if(newValue.equals(empName)){
                            start.clear();
                            System.out.println("The new name cannot be same! Please type again: ");
                        }
                        else{
                            flag = false;
                            employeeName = employeeName.replace(empName, newValue);
                            empName = newValue;
                        }
                    }
                    break;
                case "B":
                    updateField = "surname";
                    flag = true;
                    while (flag){
                        System.out.print("Enter the new surname for " + employeeName +" or type 'X' to go back to main menu: ");
                        newValue = start.scanner.nextLine();
                        newValue = start.inputControl("letter", newValue, "Incorrect input! Please type again or type 'X' to go back to main menu: ", true);
                        if(newValue.equals("X"))
                            return;
                        newValue = start.upperCaseName(newValue);
                        if(newValue.equals(empSurname)){
                            start.clear();
                            System.out.println("The new surname cannot be same! Please type again: ");
                        }
                        else{
                            flag = false;
                            employeeName = employeeName.replace(empSurname, newValue);
                        }
                    }
                    break;
                case "C":
                    updateField = "dateOfBirth";
                    flag = true;
                    while (flag){
                        System.out.print("Enter the new date of birth for " + employeeName +" in 'YYYY-MM-DD' format or type 'X' to go back to main menu: ");
                        newValue = start.scanner.nextLine();
                        newValue = start.inputControl("birth", newValue, "Incorrect input! Please type again or type 'X' to go back to main menu: ", true);
                        if(newValue.equals("X"))
                            return;
                        if(newValue.equals(empDateOfBirth)){
                            start.clear();
                            System.out.println("The new date of birth cannot be same! Please type again: ");
                        }
                        else{
                            flag = false;
                            empDateOfBirth = newValue;
                        }
                    }
                    break;
                case "D":
                    updateField = "dateOfStart";
                    flag = true;
                    while (flag){
                        System.out.print("Enter the new date of start for " + employeeName +" in 'YYYY-MM-DD' format or type 'X' to go back to main menu: ");
                        newValue = start.scanner.nextLine();
                        newValue = start.inputControl("date", newValue, "Incorrect input! Please type again or type 'X' to go back to main menu: ", true);
                        if(newValue.equals("X"))
                            return;
                        if(newValue.equals(empDateOfStart)){
                            start.clear();
                            System.out.println("The new date of start cannot be same! Please type again: ");
                        }
                        else{
                            flag = false;
                            empDateOfStart = newValue;
                        }
                    }
                    break;
                case "E":
                    updateField = "role";
                    while(flag){
                        switch(employeeRole){
                            case "manager":
                                System.out.println("Available roles are: engineer, technician, intern");
                                break;
                            case "engineer":
                                System.out.println("Available roles are: manager, technician, intern");
                                break;
                            case "technician":
                                System.out.println("Available roles are: manager, engineer, intern");
                                break;
                            case "intern":
                                System.out.println("Available roles are: manager, engineer, technician");
                                break;
                        }

                        System.out.print("Enter the new role for " + employeeName +" or type 'X' to go back to main menu: ");
                        newValue = start.scanner.nextLine();
                        newValue = start.inputControl("role", newValue, "Incorrect input! Please type again or type 'X' to go back to main menu: ", true);
                        if(newValue.equals("X"))
                            return;
                        newValue = newValue.toLowerCase();
                        if(employeeRole.equals(newValue)){
                            start.clear();
                            System.out.println("Employee's previous and new role can't be the same!");
                        }
                        else
                            flag = false;
                            
                    }
                    break;
                case "F":
                    updateField = "username";
                    boolean control = true;
                    while (control){
                        boolean usernameExists = false;
                        System.out.print("Enter the new username for " + employeeName +" or type 'X' to go back to main menu (username needs to be within the range of 2-50 characters, and can only contain alphanumerical characters): ");
                        newValue = start.scanner.nextLine();
                        newValue = start.inputControl("username", newValue, "Incorrect input! Please type again or type 'X' to go back to main menu (username needs to be within the range of 2-50 characters, and can only contain alphanumerical characters): ", true);
                        usernameExists = this.checkUser(newValue);
                        if(newValue.equals("X"))
                            return;
                        if(newValue.equals(employeeUserName)){
                            start.clear();
                            System.out.println("The new username cannot be same!");
                        }
                        else if(usernameExists){
                            start.clear();
                            System.out.println("Username is already taken by another employee!");
                        }
                        else{
                            control = false;
                            employeeUserName = newValue;
                        }
                    }
                break;
            }
            start.clear();

            if(!choice.equals("G")){
                // Field update with inputted values:
                String updateFieldQuery = "UPDATE employees SET " + updateField + " = ? WHERE employee_id = ?";
                try (Connection connection = start.connect();
                    PreparedStatement updateStatement = connection.prepareStatement(updateFieldQuery)) {

                    updateStatement.setString(1, newValue);
                    updateStatement.setString(2, employee_id);

                    int updatedRows = updateStatement.executeUpdate();
                    if (updatedRows > 0) {
                        System.out.println("Employee details updated successfully!");
                        System.out.print("Press enter to continue: ");
                        start.scanner.nextLine();
                    } else {
                        System.out.println("Failed to update employee details.");
                        System.out.print("Press enter to continue: ");
                        start.scanner.nextLine();
                    }
                } catch (SQLException e) {
                    System.out.println("Error occurred in while updating employee details in database: " + e.getMessage());
                    e.printStackTrace();
                    System.out.print("Press enter to continue: ");
                    start.scanner.nextLine();
                }
            }
        }
    }


    
}
