/**
 * Represents a tenant, inheriting from the PersonalInfo class.
 */
public class Tenant extends PersonalInfo {
    /**
     * Enumeration representing the gender of a tenant.
     */
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    /**
     * Default constructor for the Tenant class.
     */
    private Gender gender;
    private String password;
    private float preferredPrice;
    private String preferredSuburb;

    /**
     * Default constructor for the Tenant class.
     */
    public Tenant(){
        super();
    }

    /**
     * Parameterized constructor for the Tenant class.
     *
     * @param firstName        The first name of the tenant.
     * @param lastName         The last name of the tenant.
     * @param email            The email of the tenant.
     * @param phoneNo          The phone number of the tenant.
     * @param password         The password of the tenant.
     * @param gender           The gender of the tenant.
     * @param preferredPrice   The preferred price range of the tenant.
     * @param preferredSuburb  The preferred suburb of the tenant.
     */
    public Tenant(String firstName, String lastName, String email, String phoneNo, String password,
                  Gender gender, float preferredPrice, String preferredSuburb) {
        super(firstName, lastName, email, phoneNo);
        this.gender = gender;
        this.preferredPrice = preferredPrice;
        this.preferredSuburb = preferredSuburb;
    }

    /**
     * Constructor for creating a Tenant object from an array of data.
     *
     * @param data  The array of data containing tenant information.
     *              The expected format of the array is as the following order:
     *              [firstName, lastName, email, phoneNo, password, gender, preferredPrice, preferredSuburb].
     *              All fields are mandatory.
     * @throws IllegalArgumentException if the data array is invalid or incomplete.
     */
    public Tenant(String[] data) {
        super(data);

        if (data.length != 8) {
            throw new IllegalArgumentException("Invalid data for creating a Tenant object.");
        }

        this.password = data[4];
        this.gender = Gender.valueOf(data[5].toUpperCase());

        try {
            this.preferredPrice = Float.parseFloat(data[6]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid preferred price format in data for creating a Tenant object.");
        }

        this.preferredSuburb = data[7];
    }

    /**
     * Retrieves the gender of the tenant.
     *
     * @return The gender of the tenant.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Retrieves the password of the tenant.
     *
     * @return The password of the tenant.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the preferred price range of the tenant.
     *
     * @return The preferred price range of the tenant.
     */
    public double getPreferredPrice() {
        return preferredPrice;
    }

    /**
     * Retrieves the preferred suburb of the tenant.
     *
     * @return The preferred suburb of the tenant.
     */
    public String getPreferredSuburb() {
        return preferredSuburb;
    }

    /**
     * Sets the gender of the tenant.
     *
     * @param gender The gender of the tenant.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Sets the password of the tenant.
     *
     * @param password The password of the tenant.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the preferred price range of the tenant.
     *
     * @param preferredPrice The preferred price range of the tenant.
     */
    public void setPreferredPrice(float preferredPrice) {
        this.preferredPrice = preferredPrice;
    }

    /**
     * Sets the preferred suburb of the tenant.
     *
     * @param preferredSuburb The preferred suburb of the tenant.
     */
    public void setPreferredSuburb(String preferredSuburb) {
        this.preferredSuburb = preferredSuburb;
    }

    /**
     * Converts the Tenant object to an array of strings in CSV format.
     *
     * @return An array of strings representing the Tenant object in CSV format.
     */
    @Override
    public String[] toCSVFormat() {
        String[] personalInfo = super.toCSVFormat();
        return new String[] { personalInfo[0], personalInfo[1], personalInfo[2], personalInfo[3],
                this.password, this.gender.toString(), String.valueOf(this.preferredPrice), this.preferredSuburb };
    }

    /**
     * Returns a string representation of the Tenant object.
     *
     * @return A string representation of the Tenant object.
     */
    @Override
    public String toString() {
        return "Tenant {" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNo='" + getPhoneNo() + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", preferredPrice=" + preferredPrice +
                ", preferredSuburb='" + preferredSuburb + '\'' +
                '}';
    }
}
