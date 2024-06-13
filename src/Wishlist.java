import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Represents a wishlist for a tenant.
 */
public class Wishlist {
    private int propertyID;
    private String tenantEmail;
    private LocalDateTime dateAdded;

    /**
     * Default constructor for the Wishlist class.
     */
    public Wishlist() {
        // default constructor
    }

    /**
     * Parameterized constructor for the Wishlist class.
     *
     * @param propertyID  The ID of the property in the wishlist.
     * @param tenantEmail The email of the tenant who added the property to the wishlist.
     * @param dateAdded   The date and time when the property was added to the wishlist.
     */
    public Wishlist(int propertyID, String tenantEmail, LocalDateTime dateAdded) {
        this.propertyID = propertyID;
        this.tenantEmail = tenantEmail;
        this.dateAdded = dateAdded;
    }

    /**
     * Constructor for creating a Wishlist object from an array of data.
     *
     * @param data  The array of data containing wishlist information.
     *              The expected format of the array is [propertyID, tenantEmail, dateAdded].
     *              All fields are mandatory.
     * @throws IllegalArgumentException if the data array is invalid or incomplete.
     */
    public Wishlist(String[] data) {
        this.propertyID = Integer.parseInt(data[0]);
        this.tenantEmail = data[1];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
            this.dateAdded = LocalDateTime.parse(data[2], formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date/time format in data for creating a Wishlist object.");
        }
    }

    /**
     * Retrieves the date and time when the property was added to the wishlist.
     *
     * @return The date and time when the property was added to the wishlist.
     */
    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    /**
     * Retrieves the ID of the property in the wishlist.
     *
     * @return The ID of the property in the wishlist.
     */
    public int getPropertyID() {
        return propertyID;
    }

    /**
     * Retrieves the email of the tenant who added the property to the wishlist.
     *
     * @return The email of the tenant who added the property to the wishlist.
     */
    public String getTenantEmail() {
        return tenantEmail;
    }

    /**
     * Sets the date and time when the property was added to the wishlist.
     *
     * @param dateAdded The date and time when the property was added to the wishlist.
     */
    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Sets the ID of the property in the wishlist.
     *
     * @param propertyID The ID of the property in the wishlist.
     */
    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    /**
     * Sets the email of the tenant who added the property to the wishlist.
     *
     * @param tenantEmail The email of the tenant who added the property to the wishlist.
     */
    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    /**
     * Converts the Wishlist object to an array of strings in CSV format.
     *
     * @return An array of strings representing the Wishlist object in CSV format.
     */
    public String[] toCSVFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return new String[] {
                Integer.toString(this.propertyID),
                this.tenantEmail,
                this.dateAdded.format(formatter)
        };
    }

    /**
     * Returns a string representation of the Wishlist object.
     *
     * @return A string representation of the Wishlist object.
     */
    @Override
    public String toString() {
        return "Wishlist{" +
                "propertyID=" + propertyID +
                ", tenantEmail='" + tenantEmail + '\'' +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
