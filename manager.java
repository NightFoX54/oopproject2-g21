import java.util.*;
import java.sql.*;
import java.util.Scanner;

class manager extends employee{
    public manager(String employee_id, String username, String role, String name, String surname, String phoneNo, String dateOfBirth, String dateOfStart, String email) {
        super(employee_id, username, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }

    /* public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/project2_db", "root", "qwerty")
    */
    
    public void managerMenu(){
    }

    public static void displayAllEmployees() {
        final String query = "SELECT employee_id, username, name, surname, role, phoneNo, dateOfBirth, dateOfStart, email FROM employees"; //SQL query for obtaining data from database.
    
        try (Connection connection = start.connect();
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(query);
            ResultSetMetaData allEmps = res.getMetaData();
            int numOfCols = allEmps.getColumnCount();){
            

            //Table view for readibilty.
            System.out.println("=====================================================================");
            System.out.printf("%-15s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Employee ID", "Username", "Name", "Surname", "Role", "Phone Number","Date of Birth", "Date of Start", "Email");
            System.out.println("=====================================================================");

            // To view Query results:
            while (res.next()) {
                for (int i = 1; i <= numOfCols; i++) {
                    System.out.printf("%-20s\t", res.getObject(i));
                }
                System.out.println();
            }
            System.out.println("=====================================================================");
        } catch (SQLException e) {
            System.out.println("Error occurred when trying to retrieve data from database: " + e.getMessage());
            e.printStackTrace();
        } 
    }

    public void managerFire(){

        System.out.println("Please enter the employee_id of the employee you want to fire:");
        
        Scanner scanner = new Scanner(System.in);
        String employee_id = scanner.nextLine();

        if(this.employee_id.equals(employee_id)){
            System.out.println("The manager cannot fire himself/herself !");
            return;
        }

        String selectQuery = "SELECT name, surname FROM employees WHERE employee_id = ?";
        String name = null;
        String surname = null;

        // To obtain 'name' and 'surname' from database by using 'employee_id':
        try {
            PreparedStatement statementForNameSurname = connection.prepareStatement(selectQuery);
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
        String deleteQuery = "DELETE FROM employees WHERE employee_id=? AND role!='manager' ";

        try { 
            PreparedStatement statementForDelete = connection.prepareStatement(deleteQuery);
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
        }
    }

    public static void displayByRole(){
        //Getting Input from user may operated in main (?)
        Scanner scanner = new Scanner(System.in);
        String role = "";
        boolean valid = false;
         
        
        while (!valid){
            System.out.print("Enter the role for sorting employees for organized display (Roles: manager, engineer, technician, intern): ");
            role = scanner.nextLine().toLowerCase();

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

            System.out.println("=====================================================================");
            System.out.printf("%-15s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n","Employee ID", "Username", "Name", "Surname", "Phone Number","Date of Birth","Date of Start", "Email");
            System.out.println("=====================================================================");

            while(res.next()){
                for (int i = 1; i <= numOfCols; i++) {
                    System.out.printf("%-20s\t", res.getObject(i));
                }
                System.out.println();
            }
            System.out.println("=====================================================================");
        }
        catch(SQLException e){
            System.out.println("Error ocurred when retrieving data from database by specified role: "+ e.getMessage());
            e.printStackTrace();
        }
    }   


    
}
