import java.util.*;


public class algorithms{

    public static void algorithm()
    {  
        start.clear();
        String size_string = "";
        while(!size_string.equals("X")){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Type a number between 1000 and 10000 to declare the size of the array ((X) to go back): ");
            size_string = start.scanner.nextLine();
            int size;
            if(!size_string.equals("X")){
                size_string = start.inputControl("number", size_string, "Invalid input. Please enter a number between 1000 and 10000:");
                size = toInt(size_string);
                while(size < 1000 || size > 10000){
                    System.out.print("Please type a number between 1000 and 10000: ");
                    size_string = start.scanner.nextLine();
                    size_string = start.inputControl("number", size_string, "Invalid input. Please enter a number between 1000 and 10000:");
                    size = toInt(size_string);
                }
            }else{
                start.clear();
                return;
            }
            size_string = "X";
            Random rand = new Random();
            List<Integer> arr = new ArrayList<Integer>();
            for(int i = 0; i < size; i++){
                arr.add(rand.nextInt(20000) - 10000);
            }
            int radix[] = new int[size];
            int shell[] = new int[size];
            int heap[] = new int[size];
            int insertion[] = new int[size];
            copyArray(arr, radix);
            copyArray(arr, shell);
            copyArray(arr, heap);
            copyArray(arr, insertion);
            long startTime = System.nanoTime();
            arr.sort(new IntegerAscendingComparator());
            long endTime = System.nanoTime();
            long defaultExecutionTime = endTime - startTime;
            print(arr,arr.size());
            startTime = System.nanoTime();
            radixsort(radix, radix.length);
            endTime = System.nanoTime();
            print(radix, radix.length);
            System.out.println();
            long radixExecutionTime = endTime - startTime;
            startTime = System.nanoTime();
            radixsort(shell, shell.length);
            endTime = System.nanoTime();
            print(shell, shell.length);
            System.out.println();
            long shellExecutionTime = endTime - startTime;
            startTime = System.nanoTime();
            heapsort(heap, heap.length);
            endTime = System.nanoTime();
            print(heap, heap.length);
            long heapExecutionTime = endTime - startTime;
            System.out.println();
            startTime = System.nanoTime();
            insertion_sort(insertion);
            endTime = System.nanoTime();
            print(insertion, insertion.length);
            long insertionExecutionTime = endTime - startTime;
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Collections sort execution time: " + defaultExecutionTime + " nanoseconds.");
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Radix sort execution time: " + radixExecutionTime + " nanoseconds.");
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Shell sort execution time: " + shellExecutionTime + " nanoseconds.");
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Heap sort execution time: " + heapExecutionTime + " nanoseconds.");
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Insertion sort execution time: " + insertionExecutionTime + " nanoseconds.");
            System.out.println("------------------------------------------------------------------------");
            System.out.println("**************Comparison Results**************");
            if(isArraySame(radix,arr))
                System.out.println("Radix sort and Collections sort outputs are the same");
            if(isArraySame(shell,arr))
                System.out.println("Shell sort and Collections sort outputs are the same");
            if(isArraySame(heap,arr))
                System.out.println("Heap sort and Collections sort outputs are the same");
            if(isArraySame(insertion,arr))
                System.out.println("Insertion sort and Collections sort outputs are the same");
            
            System.out.print("Press enter to continue: ");
            start.scanner.nextLine();
        }
    }

    //General functions
    //-------------------------------------------------------------------------------------

    // Print the array
    static void print(int arr[], int n){
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");

        System.out.println("\n");
    }

    static void print(List<Integer> arr, int n){
        for (int i = 0; i < n; i++)
            System.out.print(arr.get(i) + " ");

        System.out.println("\n");
    }

    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void copyArray(List<Integer> arr1, int[] arr2){
        for(int i = 0; i < arr1.size(); i++){
            arr2[i] = arr1.get(i);
        }
    }

    public static boolean isArraySame(int[] arr, List<Integer> list){
        if(arr.length != list.size())
            return false;
        boolean same = true;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] != list.get(i))
                same = false;
        }
        return same;
    }

    public static int toInt(String input){
        boolean isNegative = false;
        int value = 0;
        for(int i = 0 ; i < input.length(); i++){
            if(input.charAt(i) == '-')
                isNegative = true;
            else{
                int number = input.charAt(i) - '0';
                value += digit(number, input.length() - i - 1);
            }
        }
        if(isNegative)
            value *= -1;
        return value;
    }

    public static double digit(double value, int digit){
        if(digit > 0)
            for(int i = 0; i < digit; i++){
            value *= 10;
            }
        if(digit < 0){
            digit++;
            for(; digit < 0; digit++){
                value /= 10;
            }
        }
        return value;
    }

    //-------------------------------------------------------------------------------------



    //Functions for Radix Sort
    //-------------------------------------------------------------------------------------

    // A function to get the number with maximum amount of digits
    static int getMax(int arr[], int n)
    {
        int high = arr[0];
        int low = arr[0];
        for(int i = 1; i < n; i++){
            if(arr[i] > high)
                high = arr[i];
            if(arr[i] < low)
                low = arr[i];
        }
        low *= -1;
        if(low > high)
            return low;
        return high;
    }

    // A function to sort by digits
    static void countSort(int arr[], int n, int digit)
    {
        int output[] = new int[n];
        int i;
        int count[] = new int[19];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++){
            count[((arr[i] / digit) % 10) + 9]++;
        }

        // Sum with the previous one to have the position of this digit instead of the occurences
        for (i = 1; i < 19; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[count[((arr[i] / digit) % 10) + 9] - 1] = arr[i];
            count[((arr[i] / digit) % 10) + 9]--;
        }

        // Copy back to the original array
        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

    static void radixsort(int arr[], int n)
    {
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);

        // Use countSort to sort for each digit
        for (int digit = 1; m / digit > 0; digit *= 10){
            countSort(arr, n, digit);
        }
    }

    //-------------------------------------------------------------------------------------


    //Functions for Shell Sort
    //-------------------------------------------------------------------------------------

    public static void shellsort(int arr[]) {

        int init_gap = arr.length / 2;
        while (init_gap > 0) {
            for (int i = init_gap; i < arr.length; i++) {
              
                int current = arr[i];
                int j;
     
                for(j = i; j >= init_gap && arr[j - init_gap] > current; j -= init_gap){
                    arr[j] = arr[j - init_gap];
                }

                arr[j] = current;
            }

            init_gap /= 2;
        }
    }

    //-------------------------------------------------------------------------------------




    //Functions for Heap Sort
    //-------------------------------------------------------------------------------------

    public static void heapify(int[] arr, int i, int n){
        int leftChild = i*2 + 1;
        int rightChild = i*2 + 2;

        int largest = i;
        if(leftChild < n && arr[largest] < arr[leftChild])
            largest = leftChild;

        if(rightChild < n && arr[largest] < arr[rightChild])
            largest = rightChild;
        
        if(largest != i){
            swap(arr, i, largest);
            heapify(arr, largest, n);
        }
    }
    public static void heapsort(int[] arr, int n){
        for(int i = n / 2 - 1; i >= 0; i--){
            heapify(arr, i, n);
        }
        for(int i = n; i > 0; i--){
            swap(arr, 0, i-1);
            heapify(arr, 0, i - 1);
        }
    }

    //-------------------------------------------------------------------------------------

    
    //Functions for Insertion Sort
    //-------------------------------------------------------------------------------------

    public static void insertion_sort(int[ ] arr){

        for(int i = 1; i < arr.length; i++){

            int current = arr[i];
            int j;
            for(j =  i- 1; j > 0 && arr[j] > current; j--){
                if(arr[j] > current)
                    arr[j + 1] = arr[j];
                
            }
            arr[j + 1] = current;
            if(arr[0] > arr[1]){
                int temp = arr[0];
                arr[0] = arr[1];
                arr[1] = temp;
            }
        }
    }

    //-------------------------------------------------------------------------------------
    
}
