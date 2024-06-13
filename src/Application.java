import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an application with personal information and application details.
 * Inherits from the PersonalInfo class.
 */
public class Application extends PersonalInfo {
    /**
     * Enumeration representing the status of an application.
     */
    public enum ApplicationStatus {
        SUBMITTED,
        APPROVED,
        REJECTED
    }

    private ApplicationStatus applicationStatus;
    private LocalDateTime dateSubmitted;
    private int propertyID;
    private float saving;

    /**
     * Default constructor for the Application class.
     * Initializes the object with default values.
     */
    public Application() {
        super();
    }

    /**
     * Parameterized constructor for the Application class.
     *
     * @param firstName         The first name of the applicant.
     * @param lastName          The last name of the applicant.
     * @param email             The email of the applicant.
     * @param phoneNo           The phone number of the applicant.
     * @param saving            The savings of the applicant.
     * @param propertyID        The ID of the property for the application.
     * @param dateSubmitted     The date and time when the application was submitted.
     * @param applicationStatus The status of the application.
     */
    public Application(String firstName, String lastName, String email, String phoneNo,
                       float saving, int propertyID, LocalDateTime dateSubmitted,
                       ApplicationStatus applicationStatus) {
        super(firstName, lastName, email, phoneNo);
        this.saving = saving;
        this.propertyID = propertyID;
        this.dateSubmitted = dateSubmitted;
        this.applicationStatus = applicationStatus;
    }

    /**
     * Constructor for the Application class that creates an object from CSV data.
     *
     * @param data An array of strings representing the CSV data for creating an Application object.
     *             The expected format of array is in the following order:
     *             [firstName, lastName, email, phoneNo, saving, propertyID, dateSubmitted, applicationStatus]
     *             All fields are mandatory.
     * @throws IllegalArgumentException If the provided data array has an invalid length.
     *                                  Or if the dateSubmitted or applicationStatus values are invalid.
     */
    public Application(String[] data) {
        super(data);

        if (data.length != 8) {
            throw new IllegalArgumentException("Invalid data for creating an Application object.");
        }

        this.saving = Float.parseFloat(data[4]);
        this.propertyID = Integer.parseInt(data[5]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
            this.dateSubmitted = LocalDateTime.parse(data[6], formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date/time format in data for creating a Application object.");
        }
        this.applicationStatus = ApplicationStatus.valueOf(data[7]);
    }

    /**
     * Retrieves the date and time when the application was submitted.
     *
     * @return The date and time when the application was submitted.
     */
    public LocalDateTime getDateSubmitted() {
        return dateSubmitted;
    }

    /**
     * Retrieves the ID of the property for the application.
     *
     * @return The ID of the property for the application.
     */
    public int getPropertyID() {
        return propertyID;
    }

    /**
     * Retrieves the savings of the applicant.
     *
     * @return The savings of the applicant.
     */
    public float getSaving() {
        return saving;
    }

    /**
     * Retrieves the status of the application.
     *
     * @return The status of the application.
     */
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    /**
     * Sets the savings of the applicant.
     *
     * @param saving The savings of the applicant.
     */
    public void setSaving(float saving) {
        this.saving = saving;
    }

    /**
     * Sets the ID of the property for the application.
     *
     * @param propertyID The ID of the property for the application.
     */
    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    /**
     * Sets the date and time when the application was submitted.
     *
     * @param dateSubmitted The date and time when the application was submitted.
     */
    public void setDateSubmitted(LocalDateTime dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    /**
     * Sets the status of the application.
     *
     * @param applicationStatus The status of the application.
     */
    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    /**
     * Converts the Application object to CSV format.
     *
     * @return An array of strings representing the Application object in CSV format.
     */
    @Override
    public String[] toCSVFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String[] personalInfo = super.toCSVFormat();
        return new String[] { personalInfo[0], personalInfo[1], personalInfo[2], personalInfo[3],
                String.valueOf(this.saving), String.valueOf(this.propertyID),
                this.dateSubmitted.format(formatter), this.applicationStatus.name() };
    }

    /**
     * Returns a string representation of the Application object.
     *
     * @return A string representation of the Application object.
     */
    @Override
    public String toString() {
        return super.toString() + ", Application{" +
                "saving=" + saving +
                ", propertyID=" + propertyID +
                ", dateSubmitted=" + dateSubmitted +
                ", applicationStatus=" + applicationStatus +
                '}';
    }
}

