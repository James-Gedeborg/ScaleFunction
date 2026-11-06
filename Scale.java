public class Scale {
    // Attributes (Fields)
    private double maxWeightLbs = 400.0;
    private double currentWeightLbs = 0.0;
    private boolean isDigital = true;

    // Constructor to easily initialize new scale objects
    public Scale(double maxWeightLbs, boolean isDigital) {
        this.maxWeightLbs = maxWeightLbs;
        this.isDigital = isDigital;
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

    public static void main(String[] args) {
        System.out.println("=== Starting Extended Custom Object Project ===\n");

        // 1. Instantiating and modifying Base Class (Scale)
        System.out.println("--- 1. Testing Parent: Base Scale ---");
        Scale baseScale = new Scale(300.0, false);
        baseScale.stepOn(145.4); // Will round because isDigital = false

        // 2. Instantiating and modifying Child Class 1 (SmartScale)
        System.out.println("\n--- 2. Testing Child 1: SmartScale ---");
        SmartScale fitnessScale = new SmartScale(400.0, true, "Alex");
        fitnessScale.stepOn(180.5);       // Custom stepOn handles user logging
        fitnessScale.printCurrentBMI(70); // Calculates BMI using tracked weight and 70" height
        fitnessScale.stepOn(179.2);       // Modifying state with a second weigh-in
        fitnessScale.printWeightHistory(); // Verifying primitive array data structure changes

        // 3. Instantiating and modifying Child Class 2 (LuggageScale)
        System.out.println("\n--- 3. Testing Child 2: LuggageScale ---");
        LuggageScale airportScale = new LuggageScale(150.0, 50.0); // 50lb typical airline cap
        
        // Modifying state by testing a safe bag
        airportScale.checkBagCapacity(42.3);
        System.out.printf("International Readout: %.2f kg\n\n", airportScale.getCurrentWeightKg());
        
        // Modifying state by testing an overweight bag
        airportScale.checkBagCapacity(54.8);
    }
}

class SmartScale extends Scale {
    // Data structures specific to a high-tech smart scale (Primitive elements only, no imports)
    private String syncedAppUser;
    private double[] weightHistory; 
    private int logCount;

    public SmartScale(double maxWeightLbs, boolean isDigital, String appUser) {
        super(maxWeightLbs, isDigital); // Call parent constructor
        this.syncedAppUser = appUser;
        this.weightHistory = new double[10]; // Can store up to 10 historical weigh-ins
        this.logCount = 0;
    }

    // Overriding behavior to sync with history automatically
    @Override
    public void stepOn(double weight) {
        super.stepOn(weight); // Use parent logic to safely calculate weight boundaries
        if (weight >= 0 && weight <= 400.0 && logCount < weightHistory.length) { 
            weightHistory[logCount] = getCurrentWeightLbs();
            logCount++;
            System.out.println("-> [Sync] Logged " + getCurrentWeightLbs() + " lbs to " + syncedAppUser + "'s app profile.");
        }
    }

    // Unique method calculating biological metrics
    public void printCurrentBMI(double heightInches) {
        if (getCurrentWeightLbs() == 0) {
            System.out.println("Cannot calculate BMI. Please weigh yourself first.");
            return;
        }
        double bmi = (getCurrentWeightLbs() / Math.pow(heightInches, 2)) * 703;
        System.out.printf("-> [BMI Calculator] Current BMI for %s: %.1f\n", syncedAppUser, bmi);
    }

    // Prints the native array contents manually
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
    // Data structures specific to travel limits
    private double maxLuggageLimitLbs; 
    private int bagCount = 0; 

    public LuggageScale(double maxWeightLbs, double maxLuggageLimitLbs) {
        super(maxWeightLbs, true); // Luggage scales are digital
        this.maxLuggageLimitLbs = maxLuggageLimitLbs;
    }

    // Unique method validating weights against trip boundaries
    public void checkBagCapacity(double bagWeight) {
        System.out.println("Placing luggage on scale...");
        stepOn(bagWeight); // Explicitly calls inherited stepOn logic
        
        if (getCurrentWeightLbs() > maxLuggageLimitLbs) {
            System.out.println("⚠️ Warning: This bag is HEAVY! It exceeds airline limit of " + maxLuggageLimitLbs + " lbs.");
        } else if (getCurrentWeightLbs() > 0) {
            bagCount++;
            System.out.println("✅ Bag accepted! Total bags checked so far: " + bagCount);
        }
    }

    // Conversion capability
    public double getCurrentWeightKg() {
        return getCurrentWeightLbs() * 0.453592;
    }
}
