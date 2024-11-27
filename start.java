import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class start{
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        employee user = null;
        String username;
        String password;
        while(user == null){
            System.out.print("Please type your username to login: ");
            username = scanner.nextLine();
            System.out.print("Please type your password to login: ");
            password = scanner.nextLine();
            user = authentication.login(username,password);
        }
        manager mUser;
        regularEmployee eUser;
        if(user.role.equals("manager")){
            mUser = (manager) user;
            //manager menu
        }
        else{
            eUser = (regularEmployee) user;
            //employee menu
        }
    }
    
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/deneme", "root", "179492");
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
                            if(input.charAt(i) < 'a' || input.charAt(i) > 'z')
                                correctInput = false;
                            if(input.charAt(i) < 'A' || input.charAt(i) > 'Z')
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
