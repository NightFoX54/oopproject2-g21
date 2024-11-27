import java.util.*;
import java.sql.*;

class manager extends employee{
    public manager(String username, String role, String name, String surname, String phoneNo, String dateOfBirth, String dateOfStart, String email) {
        super(username, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }

    public void displayAllEmployees() {
        final String query = "SELECT username, name, surname, role, phoneNo, dateOfBirth, dateOfStart, email FROM employees"; //SQL query for obtaining data from database.
        Statement statement = null; 
        ResultSet res = null;

        try {
            Connection connection = start.connect();
            statement = connection.createStatement();
            res = statement.executeQuery(query);
            ResultSetMetaData allEmps = res.getMetaData();
            int numOfCols = allEmps.getColumnCount();

            // To view Query results:
            while (res.next()) {
                for (int i = 1; i <= numOfCols; i++) {
                    System.out.printf("%-20s\t", res.getObject(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } 
    }
        
    public void displayProfile();

    public void managerFire();

    public void displayProfileByRole(String role);

    public void displayEmployeesByUsername(String username);

    public void updateEmployee(int employee_id, String username,String phoneNo, );



    
}
