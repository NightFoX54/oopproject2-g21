class manager extends employee{
    public manager(String employee_id, String username, String role, String name, String surname, String phoneNo, String dateOfBirth, String dateOfStart, String email) {
        super(employee_id, username, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }

    public void displayAllEmployees() {
        final String query = "SELECT employee_id, username, name, surname, role, phoneNo, email FROM employees"; //SQL query for obtaining data from database.
        Statement statement = null; 
        ResultSet res = null;

        try {
            statement = connection.createStatement();
            res = statement.executeQuery(query);
            ResultSetMetaData allEmps = res.getMetaData();
            int numOfCols = allEmps.getColumnCount();

            //Table view for readibilty.
            System.out.println("=====================================================================");
            System.out.printf("%-15s%-20s%-20s%-20s%-20s%-20s%-20s\n");
            System.out.printf("Employee ID", "Username", "Name", "Surname", "Role", "PhoneNo", "Email");
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
            e.printStackTrace(); 
        } 
    }
