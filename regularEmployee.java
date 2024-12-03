/**
 * Represents the regular employee and its features which are uniqe for regular employee class
 * It extends the {@code employee} class inherits certain properties
 */

class regularEmployee extends employee{
    /**
     * Constructor for regularEmployee class.
     * 
     * @param employee_id the ID of the employee
     * @param username the username of the employee
     * @param role the specific role of the employee
     * @param name the first name of the employee
     * @param surname the surname of the employee
     * @param phoneNo the phone number of the employee
     * @param dateOfBirth the birth date of the employee
     * @param dateOfStart the start date of the employee
     * @param email the email of the employee
     */
    public regularEmployee(String employee_id, String username, String role, String name, String surname, String phoneNo, String dateOfBirth, String dateOfStart, String email) {
        super(employee_id, username, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }

    /**
     * The menu interface for regular employee 
     * Menu displays: 
     * Display the profile 
     * Update the current informations
     * Log out options
     * The method loops until the employee selects the "Log Out" option.
     */
    public void regularEmployeeeMenu(){
        String operation = "";
        while(!operation.equals("C")){
            start.clear();
            System.out.println("Welcome " + this.name + " " + this.surname + "\n");
            System.out.println("[A] Display Own Profile");
            System.out.println("[B] Update Own Profile");
            System.out.println("[C] Log Out.");
            System.out.print("Choose the operation you want to do: ");
            operation = start.scanner.nextLine();
            operation = start.menuInput('C', operation, "Incorrect input! Type again to select the operation: ");
            switch(operation){
                case "A":
                    this.displayProfile();
                    break;
                
                case "B":
                    this.updateProfile();
                    break;  

                case "C":
                    break;
            }
        }
    }
}
