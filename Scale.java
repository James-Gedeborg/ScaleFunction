import java.util.ArrayList;
import java.util.Collections;

// Implementing the Comparable interface with a Scale generic type 
public class Scale implements Comparable<Scale> { 
    // Attributes (Fields) 
    private double maxWeightLbs = 400.0; 
    private double currentWeightLbs = 0.0; 
    private boolean isDigital = true; 

    // Constructor to easily initialize new scale objects 
    public Scale(double maxWeightLbs, boolean isDigital) { 
        this.maxWeightLbs = maxWeightLbs; 
        this.isDigital = isDigital; 
    } 

    // Implementation of the compareTo method required by the Comparable interface 
    @Override 
    public int compareTo(Scale other) { 
        // First, compare by maximum weight capacity 
        if (this.maxWeightLbs != other.maxWeightLbs) { 
            return Double.compare(this.maxWeightLbs, other.maxWeightLbs); 
        } 
        // If max capacities are identical, break the tie using current weight 
        return Double.compare(this.currentWeightLbs, other.currentWeightLbs); 
    } 

    // Behavior (Method) that changes and uses the attributes 
    public void stepOn(double weight) { 
        if (weight > this.maxWeightLbs) { 
            System.out.println("Error: Weight exceeds maximum capacity!"); 
            this.currentWeightLbs = this.maxWeightLbs; 
        } else if (weight < 0) { 
            System.out.println("Error: Weight cannot be negative."); 
        } else { 
            // Using the isDigital attribute to change how the scale behaves 
            if (this.isDigital) { 
                this.currentWeightLbs = weight; // Digital shows exact decimal 
            } else { 
                this.currentWeightLbs = Math.round(weight); // Mechanical rounds to whole numbers 
            } 
            System.out.println("Weighing... Current weight is now " + this.currentWeightLbs + " lbs."); 
        } 
    } 

    // Getters and Setters (Needed because fields are private) 
    public double getCurrentWeightLbs() { return this.currentWeightLbs; } 
    public void setDigital(boolean isDigital) { this.isDigital = isDigital; } 
    public void setMaxWeightLbs(double maxWeightLbs) { this.maxWeightLbs = maxWeightLbs; } 
    public double getMaxWeightLbs() { return this.maxWeightLbs; } 

    public static void main(String[] args) { 
        System.out.println("=== Starting Extended Custom Object Project ===\n"); 

        // --- Instantiating Exactly 10 Scale Objects ---
        System.out.println("--- Instantiating 10 Scale Objects ---"); 
        
        // Base Scales (Objects 1-4)
        Scale scale1 = new Scale(350.0, true); 
        Scale scale2 = new Scale(350.0, false); 
        Scale scale3 = new Scale(200.0, true); 
        Scale scale4 = new Scale(300.0, true); 

        // Smart Scales (Objects 5-7)
        SmartScale scale5 = new SmartScale(450.0, true, "Taylor"); 
        SmartScale scale6 = new SmartScale(500.0, true, "Jordan"); 
        SmartScale scale7 = new SmartScale(400.0, true, "Morgan"); 

        // Luggage Scales (Objects 8-10)
        LuggageScale scale8 = new LuggageScale(120.0, 50.0); 
        LuggageScale scale9 = new LuggageScale(100.0, 40.0); 
        LuggageScale scale10 = new LuggageScale(150.0, 70.0); 
        System.out.println("Successfully created 10 distinct scale instances.\n");

        // --- Testing Individual Scales ---
        System.out.println("--- Testing Base Scale (Mechanical) ---"); 
        scale2.stepOn(145.4); // Tests rounding functionality

        System.out.println("\n--- Testing SmartScale Features ---"); 
        scale5.stepOn(180.5); 
        scale5.printCurrentBMI(70); 
        scale5.stepOn(179.2); 
        scale5.printWeightHistory(); 

        System.out.println("\n--- Testing LuggageScale Features ---"); 
        scale8.checkBagCapacity(42.3); 
        System.out.printf("International Readout: %.2f kg\n", scale8.getCurrentWeightKg()); 
        scale8.checkBagCapacity(54.8); 

        // --- Simulating active weights for sorting test tie-breakers ---
        scale1.stepOn(150.0); 
        scale2.stepOn(200.0); 
        scale3.stepOn(110.0); 
        scale4.stepOn(165.0); 

        // --- Testing the Comparable Interface using an ArrayList ---
        System.out.println("\n--- Testing ArrayList Sorting with Collections.sort() ---"); 
        ArrayList<Scale> scaleList = new ArrayList<>(); 
        
        // Adding the 10 pre-defined objects to the list
        scaleList.add(scale1);
        scaleList.add(scale2);
        scaleList.add(scale3);
        scaleList.add(scale4);
        scaleList.add(scale5);
        scaleList.add(scale6);
        scaleList.add(scale7);
        scaleList.add(scale8);
        scaleList.add(scale9);
        scaleList.add(scale10);

        // Print list before sorting 
        System.out.println("\nScales BEFORE sorting (Max Capacity / Current Weight):"); 
        for (Scale s : scaleList) { 
            System.out.println("- Max: " + s.getMaxWeightLbs() + " lbs, Current: " + s.getCurrentWeightLbs() + " lbs"); 
        } 

        // Sort using our custom compareTo method
        Collections.sort(scaleList); 

        // Print list after sorting to confirm changes 
        System.out.println("\nScales AFTER sorting (Ascending order by Max Capacity -> Current Weight):"); 
        for (Scale s : scaleList) { 
            System.out.println("- Max: " + s.getMaxWeightLbs() + " lbs, Current: " + s.getCurrentWeightLbs() + " lbs"); 
        } 
    } 
} 

class SmartScale extends Scale { 
    private String syncedAppUser; 
    private double[] weightHistory; 
    private int logCount; 

    public SmartScale(double maxWeightLbs, boolean isDigital, String appUser) { 
        super(maxWeightLbs, isDigital); 
        this.syncedAppUser = appUser; 
        this.weightHistory = new double[10]; 
        this.logCount = 0; 
    } 

    @Override 
    public void stepOn(double weight) { 
        super.stepOn(weight); 
        if (weight >= 0 && weight <= getMaxWeightLbs() && logCount < weightHistory.length) { 
            weightHistory[logCount] = getCurrentWeightLbs(); 
            logCount++; 
            System.out.println("-> [Sync] Logged " + getCurrentWeightLbs() + " lbs to " + syncedAppUser + "'s app profile."); 
        } 
    } 

    public void printCurrentBMI(double heightInches) { 
        if (getCurrentWeightLbs() == 0) { 
            System.out.println("Cannot calculate BMI. Please weigh yourself first."); 
            return; 
        } 
        double bmi = (getCurrentWeightLbs() / Math.pow(heightInches, 2)) * 703; 
        System.out.printf("-> [BMI Calculator] Current BMI for %s: %.1f\n", syncedAppUser, bmi); 
    } 

    public void printWeightHistory() { 
        System.out.print("-> [History Log] " + syncedAppUser + "'s history: ["); 
        for (int i = 0; i < logCount; i++) { 
            System.out.print(weightHistory[i]); 
            if (i < logCount - 1) { 
                System.out.print(", "); 
            } 
        } 
        System.out.println("]"); 
    } 
} 

class LuggageScale extends Scale { 
    private double maxLuggageLimitLbs; 
    private int bagCount = 0; 

    public LuggageScale(double maxWeightLbs, double maxLuggageLimitLbs) { 
        super(maxWeightLbs, true); 
        this.maxLuggageLimitLbs = maxLuggageLimitLbs; 
    } 

    public void checkBagCapacity(double bagWeight) { 
        System.out.println("Placing luggage on scale..."); 
        stepOn(bagWeight); 
        if (getCurrentWeightLbs() > maxLuggageLimitLbs) { 
            System.out.println("⚠️ Warning: This bag is HEAVY! It exceeds airline limit of " + maxLuggageLimitLbs + " lbs."); 
        } else if (getCurrentWeightLbs() > 0) { 
            bagCount++; 
            System.out.println("✅ Bag accepted! Total bags checked so far: " + bagCount); 
        } 
    } 

    public double getCurrentWeightKg() { 
        return getCurrentWeightLbs() * 0.453592; 
    } 
}
