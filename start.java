import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;



public class start{
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        clear();
        System.setProperty("file.encoding", "UTF-8");
        try {
            Animation.animation();
        } catch (InterruptedException ex) {
        }
        clear();
        employee user = null;
        String username;
        String password = "";
        int loginAttemp = 0;
        String close = "";
        while(user == null){
            if(loginAttemp != 0){
                System.out.print("Incorrect credentials! Press enter to try again, type X to close the program: ");
                close = scanner.nextLine();
            }
            if(close.equals("X"))
                break;
            System.out.print("Please type your username to login: ");
            username = scanner.nextLine();
            System.out.print("Please type your password to login: ");
            password = scanner.nextLine();
            user = authentication.login(username,password);
            loginAttemp++;
        }
        if(!close.equals("X")){
            user.defaultPasswordChange();
            manager mUser;
            regularEmployee eUser;
            if(user.role.equals("manager")){
                mUser = (manager) user;
                mUser.managerMenu();
            }
            else{
                eUser = (regularEmployee) user;
                eUser.regularEmployeeeMenu();
            }
        }
    }

    public static void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String upperCaseName(String name){
        int spaceIndex = 0;
        int currentIndex = 0;
        String tempName = "";
        String tempPartial = "";
        while(spaceIndex != -1){
            spaceIndex = name.indexOf(' ');
            if(spaceIndex == -1){
                tempPartial = name.substring(currentIndex);
            }
            else{
                tempPartial = name.substring(currentIndex, spaceIndex);
                currentIndex = spaceIndex + 1;
            }
            tempPartial = tempPartial.substring(0,1).toUpperCase() + tempPartial.substring(1);
            tempName += tempPartial;
        }
        return tempName;
    }
    
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/project2_db?useUnicode=true&characterEncoding=utf8", "root", "179492");
    }

    public static String menuInput(char max, String input, String message){
        boolean correctInput = false;
        while(!correctInput){
            correctInput = true;
            if(input.length() == 1){
                if(input.charAt(0) < 'A' || input.charAt(0) > max)
                    correctInput = false;
            }
            else{
                correctInput = false;
            }
            if(!correctInput){
                System.out.print(message);
                input = scanner.nextLine();
            }
        }
        return input;
    }

    public static String inputControl(String selection, String input, String message){

        switch(selection){
            case "mail":
                boolean correctInput = false;
                while(!correctInput){
                    correctInput = true;
                    if(input.length() > 10){
                        if(!input.substring(input.length() - 10).equals("@khas.firm")){
                            correctInput = false;
                        }
                        if(input.contains(" "))
                            correctInput = false;
                    }
                    else{
                        correctInput = false;
                    }
                    if(!correctInput){
                        System.out.print(message);
                        input = scanner.nextLine();
                    }
                }
                break;
            
            case "number":
                correctInput = false;
                while(!correctInput){
                    correctInput = true;
                    if(input.length() != 0){
                        for(int i = 0; i < input.length(); i++){
                            if(input.charAt(i) < '0' || input.charAt(i) > '9')
                                correctInput = false;
                        }
                    }
                    else{
                        correctInput = false;
                    }
                    if(!correctInput){
                        System.out.print(message);
                        input = scanner.nextLine();
                    }
                }
                break;
            
            case "letter":
                correctInput = false;
                while(!correctInput){
                    correctInput = true;
                    if(input.length() != 0){
                        for(int i = 0; i < input.length(); i++){
                            if((input.charAt(i) < 'a' || input.charAt(i) > 'z') && input.charAt(i) != ' ' && (input.charAt(i) < 'A' || input.charAt(i) > 'Z') && "çğöşüÇĞÖŞÜİı".indexOf(input.charAt(i)) == -1)
                                correctInput = false;
                        }
                    }
                    else{
                        correctInput = false;
                    }
                    if(!correctInput){
                        System.out.print(message);
                        input = scanner.nextLine();
                    }
                }
                break;
            
            case "date":
                correctInput = false;
                LocalDate currentDate = LocalDate.now();
                while(!correctInput){
                    correctInput = true;
                    if(input.length() == 10){
                        for(int i = 0; i < input.length(); i++){
                            if(input.charAt(i) < '0' || input.charAt(i) > '9'){
                                if((i != 4 || i != 7) && input.charAt(i) == '-');
                                else
                                    correctInput = false;
                            }
                        }
                        if(correctInput){
                            int year = algorithms.toInt(input.substring(0,4));
                            int month = algorithms.toInt(input.substring(5,7));
                            int day = algorithms.toInt(input.substring(8));

                            if(day > 31)
                                correctInput = false;
                            if((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
                                correctInput = false;
                            if(month == 2){
                                if(year % 4 == 0 && day > 29)
                                    correctInput = false;
                                if(year % 4 != 0 && day > 28)
                                    correctInput = false;
                            }
                            if(month > 12)
                                correctInput = false;
                            if(correctInput){
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate inputDate = LocalDate.parse(input, formatter);
                                if(inputDate.isAfter(currentDate))
                                    correctInput = false;
                            }
                        }
                    }
                    else{
                        correctInput = false;
                    }
                    if(!correctInput){
                        System.out.print(message);
                        input = scanner.nextLine();
                    }
                }
                break;
            case "birth":
                correctInput = false;
                currentDate = LocalDate.now();
                while(!correctInput){
                    correctInput = true;
                    inputControl("date", input, message);
                    int currYear = currentDate.getYear();
                    int currMonth = currentDate.getMonthValue(); // Numeric value of the month (1-12)
                    int currDay = currentDate.getDayOfMonth();
                    int year = algorithms.toInt(input.substring(0,4));
                    int month = algorithms.toInt(input.substring(5,7));
                    int day = algorithms.toInt(input.substring(8));
                    boolean is18 = true;
                    if(currMonth < month || (currMonth == month && currDay < day))
                        is18 = false;
                    if(currYear - year < 18 || (currYear - year == 18 && is18 == false))
                        correctInput = false;
                    
                    if(correctInput == false){
                        System.out.println("Employee can't be younger than 18 years old!");
                        System.out.print("Please type again: ");
                        input = scanner.nextLine();
                    }
                }
                break;

            case "phone":
                correctInput = false;
                while(!correctInput){
                    correctInput = true;
                    if(input.length() == 10){
                        input = inputControl("number", input, message);
                    }
                    else{
                        correctInput = false;
                        System.out.print(message);
                        input = scanner.nextLine();
                    }
                }
                break;
            case "role":
                while(!input.equals("manager") && !input.equals("engineer") && !input.equals("technician") && !input.equals("intern")){
                    System.out.print(message);
                    input = scanner.nextLine();
                }
        }
        return input;
    }
}
