import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Represents the controller of the MonashRentalSpace application.
 */
public class MonashRentalSpace {
    private static final String APPLICATION_DB = "application.csv";
    private static final String PROPERTY_DB = "property.csv";
    private static final String TENANT_DB = "tenant.csv";
    private static final String WISHLIST_DB = "wishlist.csv";
    private ArrayList<Application> applicationRecord;
    private Tenant curTenant;
    private ArrayList<Property> propertyRecord;
    private ArrayList<Tenant> tenantRecord;
    private UserInterface userInterface;
    private ArrayList<Wishlist> wishlistRecord;


    /**
     * Constructs a new MonashRentalSpace object and initializes the record lists by loading data from CSV files.
     */
    public MonashRentalSpace() {
        this.tenantRecord = loadFromCSV(TENANT_DB, Tenant::new);
        this.propertyRecord = loadFromCSV(PROPERTY_DB, Property::new);
        this.applicationRecord = loadFromCSV(APPLICATION_DB, Application::new);
        this.wishlistRecord = loadFromCSV(WISHLIST_DB, Wishlist::new);
        this.userInterface = new UserInterface();
    }

    /**
     * Constructs a new MonashRentalSpace object with the given record lists.
     *
     * @param tenantRecord       The list of tenants.
     * @param propertyRecord     The list of properties.
     * @param applicationRecord  The list of applications.
     * @param wishlistRecord     The list of wishlist items.
     */
    public MonashRentalSpace(ArrayList<Tenant> tenantRecord, ArrayList<Property> propertyRecord,
                             ArrayList<Application> applicationRecord, ArrayList<Wishlist> wishlistRecord) {
        this.tenantRecord = tenantRecord;
        this.propertyRecord = propertyRecord;
        this.applicationRecord = applicationRecord;
        this.wishlistRecord = wishlistRecord;
    }


    /**
     * Adds or removes a property to/from the tenant's wishlist.
     *
     * @param property The property to add or remove.
     */
    private void addOrRemoveWishlist(Property property) {
        // Check if wishlist item exists
        Optional<Wishlist> existingWishlistItem = wishlistRecord.stream()
                .filter(w -> w.getPropertyID() == property.getPropertyID() && w.getTenantEmail().equals(curTenant.getEmail()))
                .findFirst();

        if (existingWishlistItem.isPresent()) {
            // Wishlist item exists, so remove it
            wishlistRecord.remove(existingWishlistItem.get());
        } else {
            // Add to wishlist
            wishlistRecord.add(new Wishlist(property.getPropertyID(), curTenant.getEmail(), LocalDateTime.now()));
        }
        this.flushToDatabase();
    }


    /**
     * Handles the menu for an authenticated tenant and performs corresponding actions based on user selection.
     */
    private void authenticatedTenantHandler() {
        String[] menuOptions = {"Browse Property", "Wishlist", "Contact", "Logout"};
        userInterface.displayHomeScreen(menuOptions, curTenant.getFirstName(), curTenant.getLastName());
        String userSelection = userInterface.getUserMenuInput("Input (1 - " + menuOptions.length + ")");

        switch (userSelection){
            case "1":
                // Browse Property
                propertiesMenuHandler(() -> this.propertyRecord,
                        () -> userInterface.displayPropertySearchScreen(this.propertyRecord, this.wishlistRecord, curTenant));
                break;
            case "2":
                // Wishlist Property
                propertiesMenuHandler(this::wishlistProperties,
                        () -> userInterface.displayWishlistScreen(wishlistProperties(), this.wishlistRecord, curTenant));
                break;
            case "3":
                userInterface.displayContactScreen();
                userInterface.getUserMenuInput(null);
                break;
            case "4":
                tenantLogout();
                break;
            default:
                userInterface.displayInvalidOptionError(menuOptions.length);
                userInterface.getUserMenuInput(null);
                break;
        }
    }


    /**
     * Flushes the data to the database by writing the records to CSV files.
     */
    private void flushToDatabase() {
        writeToCSV(TENANT_DB, new ArrayList<>(tenantRecord.stream().map(Tenant::toCSVFormat).collect(Collectors.toList())));
        writeToCSV(PROPERTY_DB, new ArrayList<>(propertyRecord.stream().map(Property::toCSVFormat).collect(Collectors.toList())));
        writeToCSV(APPLICATION_DB, new ArrayList<>(applicationRecord.stream().map(Application::toCSVFormat).collect(Collectors.toList())));
        writeToCSV(WISHLIST_DB, new ArrayList<>(wishlistRecord.stream().map(Wishlist::toCSVFormat).collect(Collectors.toList())));
    }

    /**
     * Returns the list of applications.
     *
     * @return The list of applications.
     */
    public ArrayList<Application> getApplicationRecord() {
        return applicationRecord;
    }

    /**
     * Returns the list of properties.
     *
     * @return The list of properties.
     */
    public ArrayList<Property> getPropertyRecord() {
        return propertyRecord;
    }

    /**
     * Returns the list of tenants.
     *
     * @return The list of tenants.
     */
    public ArrayList<Tenant> getTenantRecord() {
        return tenantRecord;
    }

    /**
     * Returns the list of wishlist items.
     *
     * @return The list of wishlist items.
     */
    public ArrayList<Wishlist> getWishlistRecord() {
        return wishlistRecord;
    }


    /**
     * Checks if the tenant has a rejected application for the given property.
     *
     * @param property The property to check.
     * @return True if the tenant has a rejected application for the property, False otherwise.
     */
    private boolean hasRejectedApplication(Property property) {
        return this.applicationRecord.stream()
                .anyMatch(application -> application.getEmail().equals(curTenant.getEmail())
                        && application.getPropertyID() == property.getPropertyID()
                        && application.getApplicationStatus() == Application.ApplicationStatus.REJECTED);
    }


    /**
     * Helper method to handle input validation and confirmation.
     *
     * @param prompt             The prompt message for the input.
     * @param curValue           The current value of the input.
     * @param validationFunction The validation function to check if the input is valid.
     * @param errorMsg           The error message to display if the input is invalid.
     * @return The confirmed and validated input value.
     */
    private String inputConfirmationHandler(String prompt, String curValue,
                                            Function<String, Boolean> validationFunction, String errorMsg) {
        String inputValue = userInterface.getUserMenuInput(prompt + " [" + curValue + "]");
        if (inputValue.isBlank()) {
            return curValue;
        }
        while (!validationFunction.apply(inputValue)) {
            userInterface.printText(errorMsg);
            inputValue = userInterface.getUserMenuInput(prompt + " [" + curValue + "]");
            if (inputValue.isBlank()) {
                return curValue;
            }
        }
        System.out.println();
        return inputValue;
    }

    /**
     * Checks if the given string can be parsed as an integer.
     *
     * @param s The string to check.
     * @return True if the string can be parsed as an integer, False otherwise.
     */
    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Loads data from a CSV file and constructs objects using the provided constructor function.
     *
     * @param db           The name of the CSV file.
     * @param constructor  The constructor function to create objects from CSV data.
     * @param <T>          The type of objects to be created.
     * @return The list of constructed objects.
     */
    private <T> ArrayList<T> loadFromCSV(String db, Function<String[], T> constructor) {
        ArrayList<String[]> data = readFromCSV(db);
        ArrayList<T> record = new ArrayList<>();
        for(String[] item : data) {
            try {
                record.add(constructor.apply(item));
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage() + ". Skipping this record.");
            }
        }
        return record;
    }


    /**
     * Reads data from a CSV file and returns the records as a list of string arrays.
     *
     * @param filename The name of the CSV file.
     * @return The list of string arrays representing the CSV records.
     */
    private ArrayList<String[]> readFromCSV(String filename) {
        ArrayList<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("db/" + filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }



    /**
     * Handles the properties menu and performs corresponding actions based on user selection.
     *
     * @param propertiesSupplier The supplier function to get the list of properties.
     * @param displayScreen      The runnable to display the properties screen.
     */
    private void propertiesMenuHandler(Supplier<List<Property>> propertiesSupplier, Runnable displayScreen) {
        // loop, due to full page error handler
        while(true) {
            List<Property> properties = propertiesSupplier.get();
            displayScreen.run();
            String userChoice = userInterface.getUserMenuInput(null);
            if (userChoice.equalsIgnoreCase("a") || userChoice.equalsIgnoreCase("b")) {
                if (properties.isEmpty()) {
                    userInterface.setTitle("No Properties");
                    userInterface.displayMessage("There are currently no properties available.");
                    userInterface.getUserMenuInput(null);
                    return;
                }

                String propertyID = userInterface.getUserMenuInput("Property no. (1 - " + properties.size() + ")");
                if (isInteger(propertyID)) {
                    int num = Integer.parseInt(propertyID);
                    if (num >= 1 && num <= properties.size()) {
                        if (userChoice.equalsIgnoreCase("a")) {
                            addOrRemoveWishlist(properties.get(num - 1));
                        } else if (userChoice.equalsIgnoreCase("b")) {
                            propertyApplicationFormHandler(properties.get(num - 1));
                        }
                    } else {
                        userInterface.displayInvalidNumberError();
                        userInterface.getUserMenuInput(null);
                    }
                } else if (propertyID.trim().length() != 0) {
                    userInterface.displayInvalidLetterError();
                    userInterface.getUserMenuInput(null);
                }
            } else if (isInteger(userChoice)) {
                int num = Integer.parseInt(userChoice);
                if (num >= 1 && num <= properties.size()) {
                    propertyDetailsHandler(properties.get(num - 1));
                } else {
                    userInterface.displayInvalidNumberError();
                    userInterface.getUserMenuInput(null);
                }
            } else if (userChoice.trim().length() == 0) {
                break;
            } else {
                userInterface.displayInvalidLetterError();
                userInterface.getUserMenuInput(null);
            }
        }
    }

    /**
     * Handles the property application form and submits the application.
     *
     * @param property The property to apply for.
     */
    private void propertyApplicationFormHandler(Property property) {
        if(!hasRejectedApplication(property)) {
            userInterface.displayApplicationFormScreen(property);

            String tenantFirstName = inputConfirmationHandler("First Name", curTenant.getFirstName(),
                    this::validateName, "Invalid first name. Please enter a name with no more than 255 characters.");
            String tenantLastName = inputConfirmationHandler("Last Name", curTenant.getLastName(),
                    this::validateName, "Invalid last name. Please enter a name with no more than 255 characters.");
            String tenantEmail = inputConfirmationHandler("Email", curTenant.getEmail(),
                    this::validateEmail, "Invalid email format. Please use your Monash student email."
            );
            String tenantPhoneNo = inputConfirmationHandler("Phone Number", curTenant.getPhoneNo(),
                    this::validatePhoneNumber, "Invalid phone number. Please enter a valid Australian mobile number.");

            String tenantSaving;
            do {
                tenantSaving = userInterface.getUserMenuInput("Savings (optional)");
                if (!tenantSaving.isBlank() && !validateSavings(tenantSaving)) {
                    userInterface.printText("Invalid savings amount. Please enter a valid number.");
                }
            } while (!tenantSaving.isBlank() && !validateSavings(tenantSaving));

            // Submit the application with validated input
            submitApplication(tenantFirstName, tenantLastName, tenantEmail, tenantPhoneNo, tenantSaving, property);
            userInterface.displayApplicationSubmittedMessage();
        } else {
            userInterface.displayApplicationFailedMessage();
        }
        userInterface.getUserMenuInput(null);
    }



    /**
     * Handles the property details and performs corresponding actions based on user choice.
     *
     * @param property The property to display details for.
     */
    private void propertyDetailsHandler(Property property) {
        while(true) {
            userInterface.displayPropertyDetailsScreen(property, wishlistRecord, curTenant);
            String userChoice = userInterface.getUserMenuInput(null);
            if (userChoice.equals("1")) {
                addOrRemoveWishlist(property);
            }
            else if (userChoice.equals("2")) {
                propertyApplicationFormHandler(property);
            } else {
                if (userChoice.trim().length() != 0) {
                    userInterface.displayInvalidOptionError(2);
                    userInterface.getUserMenuInput(null);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Sets the list of applications.
     *
     * @param applicationRecord The list of applications.
     */
    public void setApplicationRecord(ArrayList<Application> applicationRecord) {
        this.applicationRecord = applicationRecord;
    }

    /**
     * Sets the list of properties.
     *
     * @param propertyRecord The list of properties.
     */
    public void setPropertyRecord(ArrayList<Property> propertyRecord) {
        this.propertyRecord = propertyRecord;
    }

    /**
     * Sets the list of tenants.
     *
     * @param tenantRecord The list of tenants.
     */
    public void setTenantRecord(ArrayList<Tenant> tenantRecord) {
        this.tenantRecord = tenantRecord;
    }

    /**
     * Sets the list of wishlist items.
     *
     * @param wishlistRecord The list of wishlist items.
     */
    public void setWishlistRecord(ArrayList<Wishlist> wishlistRecord) {
        this.wishlistRecord = wishlistRecord;
    }


    /**
     * Starts the program and handles authentication and main program flow.
     */
    private void startProgram() {
        boolean exit = false;
        while (!exit) {
            if (curTenant == null) {
                exit = unauthenticatedTenantHandler();
            } else {
                authenticatedTenantHandler();
            }
        }
    }

    /**
     * Submits an application for a property with the provided tenant and property details.
     *
     * @param tenantFirstName  The first name of the tenant.
     * @param tenantLastName   The last name of the tenant.
     * @param tenantEmail      The email of the tenant.
     * @param tenantPhoneNo    The phone number of the tenant.
     * @param tenantSaving     The savings of the tenant.
     * @param property         The property to apply for.
     */
    private void submitApplication(String tenantFirstName, String tenantLastName, String tenantEmail, String tenantPhoneNo, String tenantSaving, Property property) {
        // The application is automatically submitted and set the date
        Application.ApplicationStatus applicationStatus = Application.ApplicationStatus.SUBMITTED;
        LocalDateTime dateSubmitted = LocalDateTime.now();

        // Convert saving to float if provided
        float tenantSavingFloat = 0.0f;
        if (!tenantSaving.trim().isEmpty())
            tenantSavingFloat = Float.parseFloat(tenantSaving);

        // Add the new application to the application record
        this.applicationRecord.add(new Application(
                tenantFirstName,
                tenantLastName,
                tenantEmail,
                tenantPhoneNo,
                tenantSavingFloat,
                property.getPropertyID(),
                dateSubmitted,
                applicationStatus));

        this.flushToDatabase();
    }

    /**
     * Attempts to log in a tenant with the given email and password.
     *
     * @param email    The email of the tenant.
     * @param password The password of the tenant.
     * @return True if the login is successful, False otherwise.
     */
    private boolean tenantLogin(String email, String password) {
        // Email is unique for each tenant
        for(Tenant tenant : this.tenantRecord) {
            if(tenant.getEmail().equals(email) && tenant.getPassword().equals(password)) {
                curTenant = tenant;
                return true;
            }
        }
        return false;
    }



    /**
     * Handles the login process for a tenant.
     */
    private void tenantLoginHandler() {
        userInterface.displayLoginScreen();
        String tenantEmail, tenantPassword;
        userInterface.printLeaveBlankToReturn();
        while(true) {
            tenantEmail = userInterface.getUserMenuInput("Email");
            if (tenantEmail.isBlank()) {
                return; // Return if the user inputs a blank email.
            } else if (!validateEmail(tenantEmail)) {
                userInterface.printText("Invalid email format. Please use your Monash student email.");
            } else {
                break; // Break the loop when a valid email is input.
            }
        }

        userInterface.printLeaveBlankToReturn();
        while(true) {
            tenantPassword = userInterface.getUserMenuInput("Password");
            if (tenantPassword.isBlank()) {
                return; // Return if the user inputs a blank password.
            } else {
                break; // Break the loop when a non-blank password is input.
            }
        }

        // Pass the collected email and password to tenantLogin for verification
        if (!tenantLogin(tenantEmail, tenantPassword)) {
            userInterface.setTitle("Login failed");
            userInterface.displayMessage("\n" +
                    "Your email address and password are not match.\n" +
                    "\n" +
                    "Please check your credentials and try again.");
            userInterface.getUserMenuInput(null);
            tenantLoginHandler();
        }
    }

    /**
     * Logs out the currently logged-in tenant.
     */
    private void tenantLogout() {
        // Logging out simply set the curTenant to null
        curTenant = null;
    }

    /**
     * Handles the menu for an unauthenticated tenant and performs corresponding actions based on user selection.
     *
     * @return True if the tenant chooses to exit, False otherwise.
     */
    // Handlers
    private boolean unauthenticatedTenantHandler() {
        String[] menuOptions = {"Login", "Exit"};
        userInterface.displayHomeScreen(menuOptions, null, null);
        String userSelection = userInterface.getUserMenuInput("Input (1 - " +menuOptions.length+ ")");

        switch(userSelection) {
            case "1":
                tenantLoginHandler();
                return false;
            case "2":
                return true;
            default:
                userInterface.displayInvalidOptionError(menuOptions.length);
                userInterface.getUserMenuInput(null);
                return false;
        }
    }

    /**
     * Validates if the given email is a valid Monash student email.
     *
     * @param email The email to validate.
     * @return True if the email is valid, False otherwise.
     */
    private boolean validateEmail(String email) {
        // Email validation pattern for Monash student email
        String emailPattern = "^[a-zA-Z0-9._%+-]+@student\\.monash\\.edu$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * Validates if the given name is valid.
     *
     * @param name The name to validate.
     * @return True if the name is valid, False otherwise.
     */
    private boolean validateName(String name) {
        // Check if name length exceeds 255 characters
        return name != null && name.length() <= 255;
    }

    /**
     * Validates if the given phone number is valid.
     *
     * @param phoneNumber The phone number to validate.
     * @return True if the phone number is valid, False otherwise.
     */
    private boolean validatePhoneNumber(String phoneNumber) {
        // Regex pattern for Australian mobile numbers
        String pattern = "^((\\+61|0)4\\d{8})$";
        return phoneNumber.matches(pattern);
    }

    /**
     * Validates if the given savings value is valid.
     *
     * @param savings The savings value to validate.
     * @return True if the savings value is valid, False otherwise.
     */
    private boolean validateSavings(String savings) {
        try {
            Float.parseFloat(savings);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Generates a list of wishlist properties for the current tenant.
     *
     * @return The list of wishlist properties.
     */
    private List<Property> wishlistProperties() {
        // Create a list to hold the properties in the tenant's wishlist
        List<Property> tenantWishlist = new ArrayList<>();

        // Iterate over the wishlist records
        for (Wishlist wishlist : this.wishlistRecord) {
            // Check if the wishlist record belongs to the tenant
            if (wishlist.getTenantEmail().equals(curTenant.getEmail())) {
                // If it does, find the corresponding property and add it to the list
                for (Property property : this.propertyRecord) {
                    if (property.getPropertyID() == wishlist.getPropertyID()) {
                        tenantWishlist.add(property);
                        // We've found the property, so we can stop the search
                        break;
                    }
                }
            }
        }

        // Return the list of properties in the tenant's wishlist
        return tenantWishlist;
    }

    /**
     * Writes records to a CSV file.
     *
     * @param filename The name of the CSV file.
     * @param records  The list of string arrays representing the records.
     */
    private void writeToCSV(String filename, ArrayList<String[]> records) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("db/" + filename))) {
            for (String[] record : records) {
                bw.write(String.join(",", record));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method that initializes the program and starts it.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        MonashRentalSpace monashRentalSpace = new MonashRentalSpace();
        monashRentalSpace.startProgram();
    }
}