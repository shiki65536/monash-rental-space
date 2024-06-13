import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.DecimalFormat;

/**
 * Represents the User Interface of the Monash Rental Space application.
 */
public class UserInterface {
    private final int MAX_WIDTH = 48;
    private String title;


    /**
     * Constructs a UserInterface object with no title.
     */
    public UserInterface() {

    }

    /**
     * Constructs a UserInterface object with the specified title.
     *
     * @param title The title to be displayed in the user interface.
     */
    public UserInterface(String title) {
        this.title = title;
    }

    /**
     * Centers a string within a fixed width.
     *
     * @param s The string to be centered.
     * @return The centered string.
     */
    private String centre(String s) {
        int width = 15;
        int padSize = width - s.length();
        int padStart = s.length() + padSize / 2;

        s = String.format("%-" + padStart + "s", s);
        s = String.format("%"  + width    + "s", s);
        return s;
    }

    /**
     * Displays a message indicating that the application has failed.
     */
    public void displayApplicationFailedMessage() {
        title = "Application failed";
        displayMessage("You have rejected application for this property.\n" +
                "\n" +
                "Re-applying is not possible at this moment.");
    }

    /**
     * Displays the application form screen for a specific property.
     *
     * @param property The property to display the application form for.
     */
    public void displayApplicationFormScreen(Property property) {
        title = "Application Form";
        displayHeader("for " + property.getAddress());
        printText("Please enter the following information:");
    }

    /**
     * Displays a message indicating that the application has been successfully submitted.
     */
    public void displayApplicationSubmittedMessage() {
        title = "Application submitted";
        displayMessage("Your rental application has been successfully submitted!\n" +
                "\n" +
                "Kindly email your proof of income/funds to landlord@mproperty.com.au.\n" +
                "\n" +
                "Please check out for important information from RTBA at https://rentalbonds.vic.gov.au/Bond/Lodgment/Begin.");
    }

    /**
     * Displays the contact screen with contact information.
     */
    public void displayContactScreen() {
        title = "Contact Us";
        displayHeader(null);
        printText("For any inquiries or issues with your property application, please send an email to:");
        printText("support@mproperty.com.au");
        printText("\n<- Press enter to return.");
    }

    /**
     * Displays the header with the specified subtitle.
     *
     * @param subtitle The subtitle to be displayed.
     */
    public void displayHeader(String subtitle) {
        String appName = "Monash Rental Space";
        String titleString = "*** " + title + " ***";

        int totalLength = MAX_WIDTH - appName.length() - 2;
        int totalLengthTitle = MAX_WIDTH - titleString.length();

        int paddingSizeLeft = totalLength / 2;
        int paddingSizeRight = totalLength / 2;
        int paddingSizeLeftTitle = totalLengthTitle / 2;
        int paddingSizeRightTitle = totalLengthTitle / 2;

        paddingSizeRight += 1;

        if (totalLengthTitle % 2 != 0) {
            paddingSizeRightTitle += 1;
        }

        paddingSizeLeft = Math.max(paddingSizeLeft, 0);
        paddingSizeRight = Math.max(paddingSizeRight, 0);
        paddingSizeLeftTitle = Math.max(paddingSizeLeftTitle, 0);
        paddingSizeRightTitle = Math.max(paddingSizeRightTitle, 0);

        String paddingLeft = String.join("", Collections.nCopies(paddingSizeLeft, " "));
        String paddingRight = String.join("", Collections.nCopies(paddingSizeRight, " "));
        String paddingLeftTitle = String.join("", Collections.nCopies(paddingSizeLeftTitle, " "));
        String paddingRightTitle = String.join("", Collections.nCopies(paddingSizeRightTitle, " "));

        String line = String.join("", Collections.nCopies(MAX_WIDTH, "-"));

        System.out.println("\n" + line);
        System.out.println("|" + paddingLeft + appName + paddingRight + "|");
        System.out.println(line + "\n");
        System.out.println(paddingLeftTitle + titleString + paddingRightTitle);
        if(subtitle != null) {
            int totalLengthSubtitle = MAX_WIDTH - subtitle.length();
            int paddingSizeLeftSubtitle = totalLengthSubtitle / 2;
            int paddingSizeRightSubtitle = totalLengthSubtitle / 2;

            if (totalLengthSubtitle % 2 != 0) {
                paddingSizeRightSubtitle += 1;
            }

            paddingSizeLeftSubtitle = Math.max(paddingSizeLeftSubtitle, 0);
            paddingSizeRightSubtitle = Math.max(paddingSizeRightSubtitle, 0);

            String paddingLeftSubtitle = String.join("", Collections.nCopies(paddingSizeLeftSubtitle, " "));
            String paddingRightSubtitle = String.join("", Collections.nCopies(paddingSizeRightSubtitle, " "));

            System.out.println(paddingLeftSubtitle + subtitle + paddingRightSubtitle);
        }
        System.out.println();
    }


    /**
     * Displays the home screen with menu options for the user.
     *
     * @param options    The array of menu options to display.
     * @param firstName  The first name of the user.
     * @param lastName   The last name of the user.
     */
    public void displayHomeScreen(String[] options, String firstName, String lastName) {
        title = (firstName != null && lastName != null) ? "Hi, " + firstName + " " + lastName + "!" : "Welcome to MRS!";
        displayHeader(null);
        String message = (firstName != null && lastName != null) ? "How can we assist you today?" : "You must be logged in to use the system";
        printText(message + "\n\n");
        printText("Please enter the number of your choice." + "\n\n");
        printMenuOptions(options);
    }

    /**
     * Displays an error message for an invalid letter input.
     */
    public void displayInvalidLetterError() {
        title = "Invalid Letter";
        displayMessage("Your option entered is invalid.\nPlease enter a correct letter");
    }

    /**
     * Displays an error message for an invalid number input.
     */
    public void displayInvalidNumberError() {
        title = "Invalid Number";
        displayMessage("Your option entered is invalid.\nPlease enter a correct number");
    }


    /**
     * Displays an error message for an invalid menu option.
     *
     * @param optionsLength The length of the menu options.
     */
    public void displayInvalidOptionError(int optionsLength) {
        title = "Invalid Option";
        displayMessage("Your option entered is invalid.\nPlease enter a number between 1 - " + optionsLength + ".");
    }

    /**
     * Displays the login screen.
     */
    public void displayLoginScreen() {
        title = "Login";
        displayHeader(null);
        printText("Please enter your login information below:\n");
    }


    /**
     * Displays a general message with a header.
     *
     * @param msg The message to be displayed.
     */
    public void displayMessage(String msg) {
        displayHeader(null);
        printText(msg);
        printText("\n<- Press enter to return.");
    }

    /**
     * Displays the property details screen for a specific property.
     *
     * @param property       The property to display the details for.
     * @param wishlistRecord The list of wishlist items.
     * @param curTenant      The current tenant.
     */
    public void displayPropertyDetailsScreen(Property property, List<Wishlist> wishlistRecord, Tenant curTenant) {
        boolean isOnWishlist = wishlistRecord.stream().anyMatch(w ->
                w.getPropertyID() == property.getPropertyID() &&
                        w.getTenantEmail().equals(curTenant.getEmail())
        );

        List<String> detailMenuOptions = new ArrayList<>();
        detailMenuOptions.add(isOnWishlist ? "Remove" : "Add");
        detailMenuOptions.add("Apply");

        List<String> detailOptionDescription = new ArrayList<>();
        detailOptionDescription.add(isOnWishlist ? "Remove from Wishlist" : "Add to Wishlist");
        detailOptionDescription.add("Apply to rent a property");
        title =  property.getAddress();
        displayHeader(null);
        String address = property.getAddress() + ", " + property.getSuburb() + ", " + property.getState() + " " + property.getZipCode();
        printText("Address: " + address);
        printText("Type: " + property.getPropertyType());
        printText("Status: " + (property.isOffMarket() ? "Off market" : "Leasing"));
        printText("Furnished: " + (property.isFurnished() ? "Furnished" : "Not Furnished"));
        DecimalFormat df = new DecimalFormat("#,##0.00");
        printText("Price: A$" + df.format(property.getPrice()) + " per week");

        Calculator calculator = new Calculator();
        System.out.printf("%7s%s", "",  "A$" + df.format(calculator.calculateFortnightRent(property.getPrice())) + " per fortnight\n");
        System.out.printf("%7s%s", "",  "A$" + df.format(calculator.calculateMonthlyRent(property.getPrice())) + " per month\n");
        DateTimeFormatter formatterFull = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        printText("Inspection: " + property.getInspectionTime().format(formatterFull) + "\n");
        printText("Application URL: " + property.getAppFormURL());
        printText("Description: " + property.getDescription());

        printText("\nPlease enter the letter of your choice.");
        System.out.println();
        printSubMenuOptions(detailMenuOptions, detailOptionDescription, false);
        printLeaveBlankToReturn();
    }


    /**
     * Displays the list of properties and related information.
     *
     * @param propertyRecord      The list of properties items.
     * @param wishlistRecord      The list of wishlist items.
     * @param curTenant           The current tenant.
     * @param propMenuOptions     The array of menu options for each property.
     * @param propOptionDescription The array of menu option descriptions for each property.
     */
    public void displayPropertyListView(List<Property> propertyRecord, List<Wishlist> wishlistRecord,
                                        Tenant curTenant, String[] propMenuOptions, String[] propOptionDescription) {
        // Check if property list is empty
        if (propertyRecord.size() == 0) {
            printText("There are currently no properties available.\n");
        } else {
            for (int i = 0; i < propertyRecord.size(); i++) {
                Property currentProperty = propertyRecord.get(i);

                String wishlistStar = "☆";
                String wishlistAdded = null;
                DateTimeFormatter formatterFull = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                // iterate over the wishlistRecord
                for (Wishlist item : wishlistRecord) {
                    // Check if the wishlist record belongs to the tenant
                    if (item.getPropertyID() == currentProperty.getPropertyID() && item.getTenantEmail().equals(curTenant.getEmail())) {
                        wishlistStar = "★";
                        wishlistAdded = "Wishlisted on " + item.getDateAdded().format(formatterFull);
                        break;
                    }
                }

                String furnishStatus = currentProperty.isFurnished() ? "Furnished" : "Not Furnished";
                String propertyStatus = currentProperty.isOffMarket() ? "Off market" : "Leasing";
                printText("[" + (i + 1) + "] " + currentProperty.getAddress() + " " + wishlistStar);
                System.out.printf("%-15s|%s|%15s\n", currentProperty.getSuburb(), centre(currentProperty.getPropertyType().toString()), furnishStatus);

                DecimalFormat df = new DecimalFormat("#,###");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.printf("%-15s|%s|%15s\n", propertyStatus, centre("A$" + df.format(currentProperty.getPrice())), currentProperty.getDateAdded().format(formatter));

                if(wishlistAdded != null)
                    printText(wishlistAdded);

                System.out.println();
            }

            printText("Please enter the letter of your choice." + "\n");
            printSubMenuOptions(List.of(propMenuOptions), List.of(propOptionDescription), true);
        }
        printLeaveBlankToReturn();
    }

    /**
     * Displays the property search screen.
     *
     * @param propertyRecord The list of properties.
     * @param wishlistRecord The list of wishlist items.
     * @param curTenant      The current tenant.
     */
    public void displayPropertySearchScreen(List<Property> propertyRecord, List<Wishlist> wishlistRecord,
                                            Tenant curTenant) {
        String[] propMenuOptions = {"Add to Wishlist", "Apply"};
        String[] propOptionDescription = {"Save a property", "Apply to rent a property"};
        title = "Properties";
        displayHeader(null);
        displayPropertyListView(propertyRecord, wishlistRecord,
                curTenant, propMenuOptions, propOptionDescription);
    }

    /**
     * Displays the wishlist screen.
     *
     * @param propertyRecord The list of properties.
     * @param wishlistRecord The list of wishlist items.
     * @param curTenant      The current tenant.
     */
    public void displayWishlistScreen(List<Property> propertyRecord, List<Wishlist> wishlistRecord,
                                      Tenant curTenant) {
        String[] propMenuOptions = {"Remove from Wishlist", "Apply"};
        String[] propOptionDescription = {"Remove a property", "Apply to rent a property"};
        title = "Wishlist";
        displayHeader(null);
        displayPropertyListView(propertyRecord, wishlistRecord,
                curTenant, propMenuOptions, propOptionDescription);
    }

    /**
     * Prompts the user for input and returns the entered value as a string.
     *
     * @param label The label to display before the input prompt.
     * @return The user's input as a string.
     */
    public String getUserMenuInput(String label) {
        Scanner scanner = new Scanner(System.in);
        System.out.print((label != null ? label : "Input") + ": ");
        if (scanner.hasNextLine())
            return scanner.nextLine();

        return "";
    }

    /**
     * Retrieves the title of the UserInterface.
     *
     * @return The title of the UserInterface.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Prints the leave blank to return message.
     */
    public void printLeaveBlankToReturn() {
        printText("\n<- Leave blank to return.");
    }

    /**
     * Prints the menu options with their corresponding indices.
     *
     * @param options The array of menu options to be displayed.
     */
    public void printMenuOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            printText("[" + (i + 1) + "]   " + options[i]);
        }
        System.out.println();
    }

    /**
     * Prints the sub-menu options with their corresponding letters or indices.
     *
     * @param options     The list of sub-menu options to be displayed.
     * @param desc        The list of descriptions for the sub-menu options.
     * @param isLetter    Specifies whether the sub-menu options should be displayed as letters (true) or indices (false).
     */
    public void printSubMenuOptions(List<String> options, List<String> desc, boolean isLetter) {
        int optionWidth = 22;

        for (int i = 0; i < options.size(); i++) {
            String info, option;

            if (isLetter) {
                char optionChar = (char) (97 + i);
                option = String.valueOf(optionChar);
            } else {
                option = String.valueOf(i + 1);
            }

            info = "[" + option + "] " + options.get(i);
            String description = desc.get(i);
            int padding = optionWidth - info.length();
            printText(String.format("%s%" + padding + "s%s", info, "", description));
        }
        System.out.println();
    }

    /**
     * Prints a text message to the console with word wrapping.
     *
     * @param text The text message to be printed.
     */
    public void printText(String text) {
        StringBuilder line = new StringBuilder();
        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '\n') {
                // If character is newline, print current line and start a new line
                System.out.println(line);
                line = new StringBuilder();
            } else {
                line.append(c);
                // If appending current character causes the line length to reach MAX_WIDTH, print the line with hyphen (if next character isn't a whitespace) and start a new line
                if (line.length() == MAX_WIDTH) {
                    if (c != ' ' && i + 1 < chars.length && chars[i + 1] != ' ') {
                        System.out.println(line.append('-'));
                    } else {
                        System.out.println(line);
                    }
                    line = new StringBuilder();
                }
            }
        }

        // Print any remaining text
        if (line.length() > 0) {
            System.out.println(line);
        }
    }

    /**
     * Sets the title of the UserInterface.
     *
     * @param title The title to be set for the UserInterface.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
