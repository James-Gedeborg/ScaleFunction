public class Exceptions {
    public static void main(String[] args) {
        System.out.println("Starting the program...\n");

        // ==========================================
        // PART 1: Array Out of Bounds Exception
        // ==========================================
        int[] numbers = {10, 20, 30}; // Valid indexes are 0, 1, and 2

        try {
            System.out.println("--- Testing Array Access ---");
            System.out.println("First number: " + numbers[0]);
            
            // Intentionally accessing index 5, which does not exist
            System.out.println("Sixth number: " + numbers[5]); 
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: You tried to access a spot in the array that does not exist!");
            System.out.println("Simple Explanation: The array only has 3 items (indexes 0 to 2), so index 5 is out of bounds.\n");
        }

        // ==========================================
        // PART 2: Divide by Zero Exception
        // ==========================================
        int num1 = 50;
        int num2 = 0;

        try {
            System.out.println("--- Testing Math Problems ---");
            System.out.println("Addition: " + num1 + " + " + num2 + " = " + (num1 + num2));
            
            // Intentionally dividing by zero to trigger an exception
            int result = num1 / num2; 
            System.out.println("Division result: " + result);
            
        } catch (ArithmeticException e) {
            System.out.println("Error: You cannot divide a number by zero!");
            System.out.println("Simple Explanation: In math, dividing by zero is undefined, so Java stopped the calculation.\n");
        }

        // ==========================================
        // PART 3: Program Completion
        // ==========================================
        System.out.println("All errors were safely caught. The program has closed without crashing!");
    }
}
