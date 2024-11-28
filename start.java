import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;



public class start{
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        employee user = null;
        String username;
        String password = "";
        int loginAttemp = 0;
        String close = "";
        while(user == null){
            if(loginAttemp != 0){
                System.out.println("Incorrect credentials! Press enter to try again, type X to close the program: ");
                close = scanner.nextLine();
            }
            System.out.print("Please type your username to login: ");
            username = scanner.nextLine();
            System.out.print("Please type your password to login: ");
            password = scanner.nextLine();
            user = authentication.login(username,password);
            loginAttemp++;
            if(close.equals("X"))
                break;
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
                //employee menu
            }
        }
    }

    public static void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
                    if(input.length() != 0){
                        if(!input.contains("@khas.firm")){
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
                            if((input.charAt(i) < 'a' || input.charAt(i) > 'z') && input.charAt(i) != ' ' && (input.charAt(i) < 'A' || input.charAt(i) > 'Z'))
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
        }
        return input;
    }
}
