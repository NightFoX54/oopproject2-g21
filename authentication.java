import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles authentication and login functionality for employees.
 */
public class authentication {

    /**
     * Default constructor for the authentication class.
     */
    public authentication() {

    }

    /**
     * Provides authentication operations for user according to username and password
     * Queries the database to verify credentials and returns the corresponding employee object 
     * 
     * @param username the username recorded in the database, must match the record in the database
     * @param password the password recorded in the database, must match the record in the database
     * @return it returns a relevant {@code employee} object after succesfull authentication
     */
    public static employee login(String username, String password) {
        //First, we query the database with the provided username and password.
        String query = "SELECT * FROM employees WHERE username COLLATE utf8mb4_bin = ? AND password COLLATE utf8mb4_bin = ?";
        try (Connection connection = start.connect(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
    
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                // Using resultSet to map the database data to variables.
                String employee_id = resultSet.getString("employee_id");
                String _username = resultSet.getString("username");
                String role = resultSet.getString("role");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String phone_no = resultSet.getString("phone_no");
                String dateof_birth = resultSet.getString("dateOfBirth");
                String dateof_start = resultSet.getString("dateOfStart");
                String email = resultSet.getString("email");
                
    
                // Checking the role and create the object according to it.
                if (role.equalsIgnoreCase("Manager")) {
                    return new manager(employee_id ,_username, role, name, surname, phone_no, dateof_birth, dateof_start, email); 
                } else {
                    return new regularEmployee(employee_id, _username, role, name, surname, phone_no, dateof_birth, dateof_start, email); 
                }
            }  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if there is no matched user
    }
}
