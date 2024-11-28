abstract class employee{
    protected String employee_id;
    protected String username;
    protected String role;
    protected String name;
    protected String surname;
    protected String phoneNo;
    protected String dateOfBirth;
    protected String dateOfStart;
    protected String email;

    public employee(String employee_id, String username, String role, String name, String surname, String phoneNo, String dateOfBirth, String dateOfStart, String email) {
        this.employee_id = employee_id;
        this.username = username;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.phoneNo = phoneNo;
        this.dateOfBirth = dateOfBirth;
        this.dateOfStart = dateOfStart;
        this.email = email;
    }

}

