class regularEmployee extends employee{
    public regularEmployee(String employee_id, String username, String role, String name, String surname, String phoneNo, String dateOfBirth, String dateOfStart, String email) {
        super(employee_id, username, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }



    public void regularEmployeeeMenu(){
        start.clear();
        System.out.println("Welcome " + this.name + " " + this.surname + "\n");
        String operation = "";
        while(!operation.equals("C")){
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
