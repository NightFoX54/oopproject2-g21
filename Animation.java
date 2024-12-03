import java.util.concurrent.TimeUnit; // Import for TimeUnit (or use Thread.sleep instead)

/**
 * A class for the opening animation of the application
 */
public class Animation {
    /**
     * A function for the opening animation of the application
     */
    public static void animation() throws InterruptedException {
        // ANSI escape codes for colors
        final String RESET = "\u001B[0m";
        final String YELLOW = "\u001B[33m";
        final String RED = "\u001B[31m";

        String ascii1 = " .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------. ";
        String ascii2 = "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |";
        String ascii3 = "| | _____  _____ | || |  _________   | || |   _____      | || |     ______   | || |     ____     | || | ____    ____ | || |  _________   | |";
        String ascii4 = "| ||_   _||_   _|| || | |_   ___  |  | || |  |_   _|     | || |   .' ___  |  | || |   .'    `.   | || ||_   \\  /   _|| || | |_   ___  |  | |";
        String ascii5 = "| |  | | /\\ | |  | || |   | |_  \\_|  | || |    | |       | || |  / .'   \\_|  | || |  /  .--.  \\  | || |  |   \\/   |  | || |   | |_  \\_|  | |";
        String ascii6 = "| |  | |/  \\| |  | || |   |  _|  _   | || |    | |   _   | || |  | |         | || |  | |    | |  | || |  | |\\  /| |  | || |   |  _|  _   | |";
        String ascii7 = "| |  |   /\\   |  | || |  _| |___/ |  | || |   _| |__/ |  | || |  \\ `.___.'\\  | || |  \\  `--'  /  | || | _| |_\\ /| |_ | || |  _| |___/ |  | |";
        String ascii8 = "| |  |__/  \\__|  | || | |_________|  | || |  |________|  | || |   `._____.'  | || |   `.____.'   | || ||_____||_____|| || | |_________|  | |";
        String ascii9 = "| |              | || |              | || |              | || |              | || |              | || |              | || |              | |";
        String ascii10 = "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |";
        String ascii11 = " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' ";


        String space = " ";

        for(int i = 0; i < 319; i++){
            int j;
            if(i < 139)
                j = 139 - i;
            else
                j = 0;
            int k = i - 139;
            int e = 318 - i;
            String padding;
            if(k > 0)
                padding = space.repeat(k);
            else
                padding = "";
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if(i < 179){
                System.out.println(padding + YELLOW + ascii1.substring(j));
                System.out.println(padding + YELLOW + ascii2.substring(j));
                System.out.println(padding + RED + ascii3.substring(j));
                System.out.println(padding + RED + ascii4.substring(j));
                System.out.println(padding + RED + ascii5.substring(j));
                System.out.println(padding + RED + ascii6.substring(j));
                System.out.println(padding + RED + ascii7.substring(j));
                System.out.println(padding + RED + ascii8.substring(j));
                System.out.println(padding + RED + ascii9.substring(j));
                System.out.println(padding + YELLOW + ascii10.substring(j));
                System.out.println(padding + YELLOW + ascii11.substring(j));
            }
            else{
                System.out.println(padding + YELLOW + ascii1.substring(j,e));
                System.out.println(padding + YELLOW + ascii2.substring(j,e));
                System.out.println(padding + RED + ascii3.substring(j,e));
                System.out.println(padding + RED + ascii4.substring(j,e));
                System.out.println(padding + RED + ascii5.substring(j,e));
                System.out.println(padding + RED + ascii6.substring(j,e));
                System.out.println(padding + RED + ascii7.substring(j,e));
                System.out.println(padding + RED + ascii8.substring(j,e));
                System.out.println(padding + RED + ascii9.substring(j,e));
                System.out.println(padding + YELLOW + ascii10.substring(j,e));
                System.out.println(padding + YELLOW + ascii11.substring(j,e) + RESET);
            }
            TimeUnit.MILLISECONDS.sleep(20);
        }

    }
}
