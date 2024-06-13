/**
 * Represents a Calculator for rent calculations.
 */
public class Calculator {

    private double weeklyIncome;

    /**
     * Default constructor for Calculator.
     */
    public Calculator() {

    }

    /**
     * Constructor for Calculator with initial weekly income.
     *
     * @param weeklyIncome the initial weekly income
     */
    public Calculator(double weeklyIncome) {
        this.weeklyIncome = weeklyIncome;
    }

    /**
     * Calculate the fortnightly rent based on the weekly rent.
     *
     * @param weeklyRent the weekly rent amount
     * @return the calculated fortnightly rent
     */
    public double calculateFortnightRent(double weeklyRent) {
        return weeklyRent * 2;
    }

    /**
     * Calculate the monthly rent based on the weekly rent.
     *
     * @param weeklyRent the weekly rent amount
     * @return the calculated monthly rent
     */
    public double calculateMonthlyRent(double weeklyRent) {
        return weeklyRent / 7 * 30;
    }

    /**
     * Calculate the recommended rent.
     *
     * @return the recommended rent amount
     */
    public double calculateRecommendedRent() {
        // Feature is unimplemented
        return 0;
    }


    /**
     * Get the weekly income.
     *
     * @return the weekly income
     */
    public double getWeeklyIncome() {
        return weeklyIncome;
    }

    /**
     * Set the weekly income.
     *
     * @param weeklyIncome the weekly income to set
     */
    public void setWeeklyIncome(double weeklyIncome) {
        this.weeklyIncome = weeklyIncome;
    }
}


