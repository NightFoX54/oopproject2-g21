import java.sql.*;

class manager extends employee{
    public manager(String employee_id, String username, String role, String name, String surname, String phone_no, String dateOfBirth, String dateOfStart, String email) {
        super(employee_id, username, role, name, surname, phone_no, dateOfBirth, dateOfStart, email);
    }

    /* public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/project2_db", "root", "qwerty");
    */
    public void managerMenu(){
        String operation = "";
        while(!operation.equals("I")){
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
            System.out.println("[I] Log Out.");
            System.out.print("Choose the operation you want to do: ");
            operation = start.scanner.nextLine();
            operation = start.menuInput('I', operation, "Incorrect input! Type again to select the operation: ");
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
                    // log out
                    break;
            }
        }

    }

    public void displayAllEmployees() {
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

    public void managerFire(){
        start.clear();
        String selectQuery = "SELECT name, surname FROM employees WHERE employee_id = ?";  
        String deleteQuery = "DELETE FROM employees WHERE employee_id = ? "; 
        String name = null, surname = null;

        System.out.print("Please enter the employee_id of the employee you want to fire:");
        String employee_id = start.scanner.nextLine();
        employee_id = start.inputControl("number", employee_id, "Incorrect input! Please type again: ");

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
                    System.out.println("No employee matched with Employee ID: " + employee_id);
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
                else{
                    System.out.println("Employee with "+ employee_id + " has not registered in the database!" );
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
        start.scanner.nextLine();
    }

    public void displayByRole(){
        //Getting Input from user may operated in main (?)
        String role = "";

         
        start.clear();
        System.out.print("Enter the role for sorting employees for organized display (Roles: manager, engineer, technician, intern): ");
        role = start.scanner.nextLine().toLowerCase();
        role = start.inputControl("role", role, "The role that you entered is invalid! Please type again (Roles: manager, engineer, technician, intern): ");
            

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

    public void displayByUsername(){
        String username = "";
        boolean valid = false;
        start.clear();
        /*kullanıcının girdiği username database'de var mı yok mu onun kontrolü yapılacak, kısacası username için input control ve database controlü.
        kullanıcıdan input alma işlerini mainde mi yapıcaz fonksiyonlarda herkes kendince yapsın mı?
        */
        System.out.print("Enter the username to see the employee: ");
        username = start.scanner.nextLine().toLowerCase();


        final String displayByUsernameQuery = "SELECT employee_id, username, name, surname, role, phone_no, dateOfBirth, dateofStart, email FROM employees WHERE username = ?";

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

    public boolean hireEmployee() {
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
                    System.out.print("Please type the username of the employee: ");
                else
                    System.out.print("Username is already taken by another employee! Please choose another username: ");
                username = start.scanner.nextLine();
                usernameExists = this.checkUser(username);
                usernameTrial++;
            }
            start.clear();
            System.out.println("Available roles are: manager, engineer, technician, intern");
            System.out.print("Please type the role of the employee: ");
            role = start.scanner.nextLine().toLowerCase();
            role = start.inputControl("role", role, "Incorrect input! Please type again: ");
            start.clear();
            System.out.print("Please type the name of the employee: ");
            name = start.scanner.nextLine().toLowerCase();
            name = start.inputControl("letter", name, "Incorrect input. Please type the name of the employee: ");
            name = start.upperCaseName(name);
            start.clear();
            System.out.print("Please type the surname of the employee: ");
            surname = start.scanner.nextLine().toLowerCase();
            surname = start.inputControl("letter", surname, "Incorrect input. Please type the name of the employee: ");
            surname = start.upperCaseName(surname);
            start.clear();
            System.out.print("Please type the phone number of the employee: ");
            phone_no = start.scanner.nextLine();
            phone_no = start.inputControl("phone", phone_no, "Incorrect input. Please type the phone number of the employee: ");
            start.clear();
            System.out.print("Please type the date of birth of the employee in YYYY-MM-DD format: ");
            dateof_birth = start.scanner.nextLine();
            dateof_birth = start.inputControl("date", dateof_birth, "Incorrect input. Please type the date of birth of the employee in YYYY-MM-DD format: ");
            start.clear();
            System.out.print("Please type the date of start of the employee in YYYY-MM-DD format: ");
            dateof_start = start.scanner.nextLine();
            dateof_start = start.inputControl("date", dateof_start, "Incorrect input. Please type the date of start of the employee in YYYY-MM-DD format: ");
            start.clear();
            System.out.print("Please type the email of the employee: ");
            email = start.scanner.nextLine();
            email = start.inputControl("mail", email, "Incorrect input. Please type the email of the employee: ");
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

            return status > 0;
        } catch (SQLException failed) {
            failed.printStackTrace();
            return false;
        }

    }

    public void employeeUpdate() {
        start.clear();
        String employee_id;
        boolean validID = false;
        String checkEmpID = "SELECT * FROM employees WHERE employee_id = ?";
        String employeeName = "";
        //Checking the employee is exist?:
        do {
            System.out.print("Enter the employee_id of the employee you want to update: ");
            employee_id = start.scanner.nextLine();
            employee_id = start.inputControl("number", employee_id, "Incorrect input! Please type again: ");
            start.clear();
            try (Connection connection = start.connect();
                PreparedStatement checkStatement = connection.prepareStatement(checkEmpID)) {

                checkStatement.setString(1, employee_id);
                ResultSet res = checkStatement.executeQuery();

                if (res.next()) {
                    employeeName = res.getString("name") + " " + res.getString("surname");
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
        String choice = null;

        System.out.println("Fields to update:");
        System.out.println("[A] Name");
        System.out.println("[B] Surname");
        System.out.println("[C] Date of Birth");
        System.out.println("[D] Date of Start");
        System.out.println("[E] Role");
        System.out.print("Select the employee field you want to update: ");
        choice = start.scanner.nextLine();
        choice = start.menuInput('E', choice, "Incorrect input! Please type again: ");
        String newValue = "";
        start.clear();
        switch (choice) {
            case "A":
                updateField = "name";
                System.out.print("Enter the new name for " + employeeName +": ");
                newValue = start.scanner.nextLine().toLowerCase();
                newValue = start.inputControl("letter", newValue, "Incorrect input! Please type again: ");
                newValue = start.upperCaseName(newValue);
                break;
            case "B":
                updateField = "surname";
                System.out.print("Enter the new surnamename for " + employeeName +": ");
                newValue = start.scanner.nextLine().toLowerCase();
                newValue = start.inputControl("letter", newValue, "Incorrect input! Please type again: ");
                newValue = start.upperCaseName(newValue);
                break;
            case "C":
                updateField = "dateOfBirth";
                System.out.print("Enter the new date of birth for " + employeeName +" in 'YYYY-MM-DD' format: ");
                newValue = start.scanner.nextLine().toLowerCase();
                newValue = start.inputControl("date", newValue, "Incorrect input! Please type again: ");
                break;
            case "D":
                updateField = "dateOfStart";
                System.out.print("Enter the new date of start for " + employeeName +" in 'YYYY-MM-DD' format: ");
                newValue = start.scanner.nextLine().toLowerCase();
                newValue = start.inputControl("date", newValue, "Incorrect input! Please type again: ");
                break;
            case "E":
                updateField = "role";
                System.out.println("Available roles are: manager, engineer, technician, intern");
                System.out.print("Enter the new role for " + employeeName +": ");
                newValue = start.scanner.nextLine().toLowerCase();
                newValue = start.inputControl("role", newValue, "Incorrect input! Please type again: ");
                break;
        }
        start.clear();

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
        }
    }


    
}
