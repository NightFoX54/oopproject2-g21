import java.sql.*;

class manager extends employee{
    public manager(String employee_id, String username, String role, String name, String surname, String phoneNo, String dateOfBirth, String dateOfStart, String email) {
        super(employee_id, username, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }

    /* public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/project2_db", "root", "qwerty");
    */
    public void managerMenu(){

    }

    public void displayAllEmployees() {
        final String query = "SELECT employee_id, username, name, surname, role, phoneNo, dateOfBirth, dateOfStart, email FROM employees"; //SQL query for obtaining data from database.
    
        try (Connection connection = start.connect();
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(query);
            ){
            ResultSetMetaData allEmps = res.getMetaData();
            int numOfCols = allEmps.getColumnCount();

            //Table view for readibilty.
            System.out.println("========================================================================================================================================================================");
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Employee ID", "Username", "Name", "Surname", "Role", "Phone Number","Date of Birth", "Date of Start", "Email");
            System.out.println("========================================================================================================================================================================");

            // To view Query results:
            while (res.next()) {
                for (int i = 1; i <= numOfCols; i++) {
                    System.out.printf("%-20s", res.getObject(i));
                }
                System.out.println();
            }
            System.out.println("========================================================================================================================================================================");
        } catch (SQLException e) {
            System.out.println("Error occurred when trying to retrieve data from database: " + e.getMessage());
            e.printStackTrace();
        } 
    }

    public void managerFire(){

        String selectQuery = "SELECT name, surname FROM employees WHERE employee_id = ?";  
        String deleteQuery = "DELETE FROM employees WHERE employee_id=?"; 
        String name = null, surname = null, role= null;

        System.out.print("Please enter the employee_id of the employee you want to fire:");
        String employee_id = start.scanner.nextLine();

        if(this.employee_id.equals(employee_id)){
            System.out.println("The manager cannot fire himself/herself !");
            return;
        }

        try(Connection connection = start.connect()) {

            // To obtain 'name' and 'surname' from database by using 'employee_id':
            try(PreparedStatement statementForNameSurname = connection.prepareStatement(selectQuery);
            ResultSet res = statementForNameSurname.executeQuery();) {
                statementForNameSurname.setString(1, employee_id);
                if (res.next()) {
                    name = res.getString("name");
                    surname = res.getString("surname");
                    role = res.getString("role");
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
                } 
                else{
                    System.out.println("Employee with "+ employee_id + " has not registered in the database!" );
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

    public void displayByRole() {
        //Getting Input from user may operated in main (?)
        String role = "";
        boolean valid = false;
         
        
        while (!valid){
            System.out.print("Enter the role for sorting employees for organized display (Roles: manager, engineer, technician, intern): ");
            role = start.scanner.nextLine().toLowerCase();

            if(role.equals("manager")||role.equals("engineer")||role.equals("technician")||role.equals("intern")){
                valid=true;
            }
            else{
                System.out.println("The role that you entered is invalid! Please type again (Roles: manager, engineer, technician, intern): ");
            }
        }

        final String displayByRoleQuery = "SELECT employee_id, username, name, surname, phoneNo, dateOfBirth, dateOfStart, email FROM employees WHERE role = ?";

        try(Connection connection = start.connect();
            PreparedStatement statementForRoleSort = connection.prepareStatement(displayByRoleQuery);){
            
            statementForRoleSort.setString(1, role);
            ResultSet res = statementForRoleSort.executeQuery();
            ResultSetMetaData empsByRole = res.getMetaData();
            int numOfCols = empsByRole.getColumnCount();

            System.out.println("========================================================================================================================================================================");
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Employee ID", "Username", "Name", "Surname", "Phone Number", "Date of Birth", "Date of Start", "Email");
            System.out.println("========================================================================================================================================================================");
            while(res.next()){
                
                for (int i = 1; i <= numOfCols; i++) {
                    System.out.printf("%-20s", res.getObject(i));
                }
                System.out.println();
            }
            System.out.println("========================================================================================================================================================================");
        }
        catch(SQLException e){
            System.out.println("Error ocurred when retrieving data from database by specified role: "+ e.getMessage());
            e.printStackTrace();
        }
    }   

    public void displayByUsername(){
        String username = "";
        boolean valid = false;

        /*kullanıcının girdiği username database'de var mı yok mu onun kontrolü yapılacak, kısacası username için input control ve database controlü.
        kullanıcıdan input alma işlerini mainde mi yapıcaz fonksiyonlarda herkes kendince yapsın mı?
        */
        System.out.print("Enter the username to see the employee: ");
        username = start.scanner.nextLine().toLowerCase();


        final String checkUsernameQuery = "SELECT COUNT(*) FROM employees WHERE username = ?";  //Database'de username var mı yok mu kontrol için query.
        final String displayByUsernameQuery = "SELECT employee_id, username, name, surname, phoneNo, dateOfBirth, dateofStart, email FROM employees WHERE username = ?";

        try(Connection connection = start.connect();
        PreparedStatement statementForUsername = connection.prepareStatement(displayByUsernameQuery);){
            statementForUsername.setString(1, username);
            ResultSet res = statementForUsername.executeQuery();
            ResultSetMetaData empsByUsername = res.getMetaData();
            int numOfCols = empsByUsername.getColumnCount();

            System.out.println("========================================================================================================================================================================");
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Employee ID", "Username", "Name", "Surname", "Phone Number", "Date of Birth", "Date of Start", "Email");
            System.out.println("========================================================================================================================================================================");

            while(res.next()){
                for(int i=1; i<=numOfCols;i++){
                    System.out.printf("%-20s", res.getObject(i));
                }
                System.out.println();
            }
            System.out.println("========================================================================================================================================================================");
        }
        catch (SQLException e) {
            System.out.println("Error occurred when retrieving data from database by username: " + e.getMessage());
            e.printStackTrace();
        }
    }

public boolean addUser(String username, String password, String role, String name, String surname, String phone_no, String dateof_birth, String dateof_start, String email) {

        //employees adlı tabloya (database) insert ile dolum yapıyoruz ve gerekli değişkenleri yolluyoruz.
        String user = "INSERT INTO employees (employee_id, username, password, role, name, surname, phone_no, dateof_birth, dateof_start, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String defaultPassword = "NEWEMP345";
        //kullanıcın ilk şifresini sistem belirleyecek kullanıcı sonradan güncelleyebilir.

        try (PreparedStatement statement = connection.prepareStatement(user)) {
            statement.setString(2, username);
            statement.setString(3, defaultPassword);
            statement.setString(4, role);
            statement.setString(5, name);
            statement.setString(6, surname);
            statement.setString(7, phone_no);
            statement.setString(8, dateof_birth);
            statement.setString(9, dateof_start);
            statement.setString(10, email);

            //status ile execute yaptıktan sonra başarılı mı başarısız mı olduğunu takip ediyoruz.
            int status = statement.executeUpdate();

            if (status > 0) {
                System.out.println("Employee added succesfully!");
            }
            //ekrana başarılı mesajı yazdırılıp menüye yönlendirilecek.

            return status > 0;
        } catch (SQLException failed) {
            failed.printStackTrace();
            return false;
        }

    }

    public void userUpdate() {
    
        String employee_id;
        boolean validID = false;
        String checkEmpID = "SELECT * FROM employees WHERE employee_id = ?";

        //Checking the employee is exist?:
        do {
            System.out.print("Enter the employee_id of the user you want to update: ");
            employee_id = start.scanner.nextLine().trim();

            try (Connection connection = start.connect();
                PreparedStatement checkStatement = connection.prepareStatement(checkEmpID)) {

                checkStatement.setString(1, employee_id);
                ResultSet res = checkStatement.executeQuery();

                if (res.next()) {
                    validID = true; 
                    System.out.println("Employee found: "+ employee_id+ " " + res.getString("name") + " " + res.getString("surname"));
                } else {
                    System.out.println("No employee found with EmployeeID: " + employee_id + " Please enter the EmployeeID again!");
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while checking 'employee exists?' in database: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }while(!validID);
        System.out.println("EmployeeID is valid! \t EmployeeId: " + employee_id);


        

        //Field selection for updating employee non-profile fields:
        String[] Fields = {"name", "surname", "dateOfBirth", "dateOfStart", "role"};
        String updateField = null;
        String choice = null;
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println("Fields of employees to update:");
            System.out.println("[1] Name");
            System.out.println("[2] Surname");
            System.out.println("[3] Date of Birth");
            System.out.println("[4] Date of Start");
            System.out.println("[5] Role");
            System.out.print("Select the employee field you want to update (Select between 1-5): ");
            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    updateField = "name";
                    validChoice = true;
                    break;
                case "2":
                    updateField = "surname";
                    validChoice = true;
                    break;
                case "3":
                    updateField = "dateOfBirth";
                    validChoice = true;
                    break;
                case "4":
                    updateField = "dateOfStart";
                    validChoice = true;
                    break;
                case "5":
                    updateField = "role";
                    validChoice = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 5!");
            }
        }
        

        // Inputting new value for employee field to update:
        String newValue;
        boolean validValues = false;
        while (!validValue) {
            System.out.print("Enter the new value for " + updateField + ": ");
            newValue = scanner.nextLine().trim().toLowerCase();

            if (!newValue.isEmpty()) {  //Checking for empty input
                validValue = true;
            } else {
                System.out.println("The new value cannot be empty. Please try again.");
            }
        }

        // Field update with inputted values:
        String updateFieldQuery = "UPDATE employees SET " + updateField + " = ? WHERE employee_id = ?";
        try (Connection connection = start.connect();
            PreparedStatement updateStatement = connection.prepareStatement(updateFieldQuery)) {

            updateStatement.setString(1, newValue);
            updateStatement.setString(2, employee_id);

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Employee details updated successfully!");
            } else {
                System.out.println("Failed to update employee details.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred in while updating employee details in database: " + e.getMessage());
            e.printStackTrace();
        }
    }


    
    
}
