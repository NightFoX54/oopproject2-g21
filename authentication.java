import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class authentication {

    //There will be a connection variable to connect database

    public static employee login(String username, String password) {
        //First, we query the database with the provided username and password.
        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
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
                String phone_no = resultSet.getString("phoneNo");
                String dateof_birth = resultSet.getString("dateOfBirth");
                String dateof_start = resultSet.getString("dateOfStart");
                String email = resultSet.getString("email");
                
    
                // Checking the role and create the object according to it.
                if (role.equalsIgnoreCase("Manager")) {
                    return new manager(employee_id ,_username, role, name, surname, phone_no, dateof_birth, dateof_start, email); 
                } else if (role.equalsIgnoreCase("RegularEmployee")) {
                    return new regularEmployee(employee_id, _username, role, name, surname, phone_no, dateof_birth, dateof_start, email); 
                }
            }  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if there is no matched user
    }


}
