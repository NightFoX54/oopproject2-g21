abstract class employee{
    protected String username;
    protected String role;
    protected String name;
    protected String surname;
    protected String phoneNo;
    protected String dateOfBirth;
    protected String dateOfStart;
    protected String email;

    public employee(String username, String role, String name, String surname, String phoneNo, String dateOfBirth, String dateOfStart, String email) {
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

