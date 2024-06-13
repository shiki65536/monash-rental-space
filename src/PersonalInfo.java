/**
 * Abstract class representing personal information.
 * Provides methods to get and set personal information attributes.
 */
public abstract class PersonalInfo {
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String phoneNo;

    /**
     * Default constructor for the PersonalInfo class.
     * Initializes the object with default values.
     */
    public PersonalInfo() {
    }

    /**
     * Parameterized constructor for the PersonalInfo class.
     *
     * @param firstName The first name of the person.
     * @param lastName  The last name of the person.
     * @param email     The email address of the person.
     * @param phoneNo   The phone number of the person.
     */
    public PersonalInfo(String firstName, String lastName, String email, String phoneNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    /**
     * Constructor for the PersonalInfo class that creates an object from data array.
     *
     * @param data An array of strings representing the personal information data.
     *             The expected format of array is as the following order:
     *             [firstName, lastName, email, phoneNo]
     *             All fields are mandatory.
     * @throws IllegalArgumentException If the provided data array has an invalid length.
     */
    public PersonalInfo(String[] data) {
        if (data.length < 4) {
            throw new IllegalArgumentException("Invalid data for creating a PersonalInfo object.");
        }

        this.firstName = data[0];
        this.lastName = data[1];
        this.email = data[2];
        this.phoneNo = data[3];
    }

    /**
     * Retrieves the email address of the person.
     *
     * @return The email address of the person.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the first name of the person.
     *
     * @return The first name of the person.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the last name of the person.
     *
     * @return The last name of the person.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the phone number of the person.
     *
     * @return The phone number of the person.
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets the email address of the person.
     *
     * @param email The email address of the person.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the first name of the person.
     *
     * @param firstName The first name of the person.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the person.
     *
     * @param lastName The last name of the person.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the phone number of the person.
     *
     * @param phoneNo The phone number of the person.
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Converts the personal information to CSV format.
     *
     * @return An array of strings representing the personal information in CSV format.
     */
    public String[] toCSVFormat() {
        return new String[] { this.firstName, this.lastName, this.email, this.phoneNo };
    }
}
