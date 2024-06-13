import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a property with various attributes.
 */
public class Property {
    /**
     * Enum representing the type of the property.
     * The types include HOUSE, UNIT, TOWNHOUSE, and APARTMENT.
     */
    public enum PropertyType {
        HOUSE,
        UNIT,
        TOWNHOUSE,
        APARTMENT;

        /**
         * Returns a lowercase string representation of the property type.
         *
         * @return Lowercase string representation of the property type.
         */
        @Override
        public String toString() {
            String s = super.toString();
            return s.charAt(0) + s.substring(1).toLowerCase();
        }
    }

    private int propertyID;
    private String appFormURL;
    private String address;
    private LocalDateTime dateAdded;
    private String description;
    private LocalDateTime inspectionTime;
    private boolean isFurnished;
    private boolean isOffMarket;
    private float price;
    private PropertyType propertyType;
    private String state;
    private String suburb;
    private String zipCode;


    /**
     * Default constructor for the Property class.
     * Initializes the object with default values.
     */
    public Property()
    {
    }

    /**
     * Parameterized constructor for the Property class.
     *
     * @param propertyID     The ID of the property.
     * @param address        The address of the property.
     * @param suburb         The suburb of the property.
     * @param state          The state of the property.
     * @param zipCode        The zip code of the property.
     * @param isFurnished    Indicates whether the property is furnished or not.
     * @param propertyType   The type of the property.
     * @param price          The price of the property.
     * @param appFormURL     The URL of the application form for the property.
     * @param inspectionTime The time of the property inspection.
     * @param description    The description of the property.
     * @param isOffMarket    Indicates whether the property is off the market or not.
     * @param dateAdded      The date when the property was added.
     */
    public Property(int propertyID, String address, String suburb, String state, String zipCode, boolean isFurnished,
                    PropertyType propertyType, float price, String appFormURL, LocalDateTime inspectionTime,
                    String description, boolean isOffMarket, LocalDateTime dateAdded) {
        this.propertyID = propertyID;
        this.address = address;
        this.suburb = suburb;
        this.state = state;
        this.zipCode = zipCode;
        this.isFurnished = isFurnished;
        this.propertyType = propertyType;
        this.price = price;
        this.appFormURL = appFormURL;
        this.inspectionTime = inspectionTime;
        this.description = description;
        this.isOffMarket = isOffMarket;
        this.dateAdded = dateAdded;
    }

    /**
     * Constructor for the Property class that creates an object from data array.
     *
     * @param data An array of strings representing the property data.
     *             The array must contain 13 elements in the following order:
     *             [propertyID, address, suburb, state, zipCode, isFurnished, propertyType, price, appFormURL,
     *             inspectionTime, description, isOffMarket, dateAdded]
     * @throws IllegalArgumentException If the provided data array has an invalid length.
     * @throws DateTimeParseException    If the date/time format in the data is invalid.
     */
    public Property(String[] data) {
        if (data.length != 13) {
            throw new IllegalArgumentException("Invalid data for creating a Property object.");
        }

        this.propertyID = Integer.parseInt(data[0]);
        this.address = data[1];
        this.suburb = data[2];
        this.state = data[3];
        this.zipCode = data[4];
        this.isFurnished = Boolean.parseBoolean(data[5]);
        this.propertyType = PropertyType.valueOf(data[6].toUpperCase());
        this.price = Float.parseFloat(data[7]);
        this.appFormURL = data[8];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
            this.inspectionTime = LocalDateTime.parse(data[9], formatter);
            this.dateAdded = LocalDateTime.parse(data[12], formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date/time format in data for creating a Property object.");
        }

        this.description = data[10];
        this.isOffMarket = Boolean.parseBoolean(data[11]);
    }
    /**
     * Checks if the property is furnished.
     *
     * @return True if the property is furnished, false otherwise.
     */
    public boolean isFurnished() {
        return isFurnished;
    }

    /**
     * Checks if the property is off the market.
     *
     * @return True if the property is off the market, false otherwise.
     */
    public boolean isOffMarket() {
        return isOffMarket;
    }

    /**
     * Returns the address of the property.
     *
     * @return The address of the property.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the appFormURL of the property.
     *
     * @return The appFormURL of the property.
     */
    public String getAppFormURL() {
        return appFormURL;
    }

    /**
     * Returns the dateAdded of the property.
     *
     * @return The dateAdded of the property.
     */
    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    /**
     * Returns the description of the property.
     *
     * @return The description of the property.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the ID of the property.
     *
     * @return The ID of the property.
     */
    public int getPropertyID() {
        return propertyID;
    }

    /**
     * Returns the price of the property.
     *
     * @return The price of the property.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Returns the propertyType of the property.
     *
     * @return The propertyType of the property.
     */
    public PropertyType getPropertyType() {
        return propertyType;
    }

    /**
     * Returns the state of the property.
     *
     * @return The state of the property.
     */
    public String getState() {
        return state;
    }

    /**
     * Returns the suburb of the property.
     *
     * @return The suburb of the property.
     */
    public String getSuburb() {
        return suburb;
    }

    /**
     * Returns the zipCode of the property.
     *
     * @return The zipCode of the property.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Returns the inspection time of the property.
     *
     * @return The inspection time of the property.
     */
    public LocalDateTime getInspectionTime() {
        return inspectionTime;
    }

    /**
     * Sets the address of the property.
     *
     * @param address The address of the property.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the appFormURL of the property.
     *
     * @param appFormURL The appFormURL of the property.
     */
    public void setAppFormURL(String appFormURL) {
        this.appFormURL = appFormURL;
    }

    /**
     * Sets the dateAdded of the property.
     *
     * @param dateAdded The dateAdded of the property.
     */
    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Sets the description of the property.
     *
     * @param description The description of the property.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets whether the property is furnished or not.
     *
     * @param furnished True if the property is furnished, false otherwise.
     */
    public void setFurnished(boolean furnished) {
        isFurnished = furnished;
    }

    /**
     * Sets whether the property is off the market or not.
     *
     * @param offMarket True if the property is off the market, false otherwise.
     */
    public void setOffMarket(boolean offMarket) {
        isOffMarket = offMarket;
    }

    /**
     * Sets the property ID.
     *
     * @param propertyID The property ID.
     */
    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    /**
     * Sets the price of the property.
     *
     * @param price The price of the property.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Sets the property type.
     *
     * @param propertyType The property type.
     */
    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    /**
     * Sets the state of the property.
     *
     * @param state The state of the property.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Sets the suburb of the property.
     *
     * @param suburb The suburb of the property.
     */
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    /**
     * Sets the zip code of the property.
     *
     * @param zipCode The zip code of the property.
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Converts the Property object to an array of strings in CSV format.
     *
     * @return An array of strings representing the Property object in CSV format.
     */
    public String[] toCSVFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return new String[] {
                Integer.toString(this.propertyID), this.address, this.suburb, this.state, this.zipCode,
                Boolean.toString(this.isFurnished), this.propertyType.toString().toUpperCase(),
                Float.toString(this.price), this.appFormURL, this.inspectionTime.format(formatter),
                this.description, Boolean.toString(this.isOffMarket), this.dateAdded.format(formatter)
        };
    }

    /**
     * Returns a string representation of the Property object.
     *
     * @return A string representation of the Property object.
     */
    @Override
    public String toString() {
        return "Property{" +
                "propertyID=" + propertyID +
                ", address='" + address + '\'' +
                ", suburb='" + suburb +
                ", state='" + state +
                ", zipCode='" + zipCode +
                ", isFurnished=" + isFurnished +
                ", propertyType=" + propertyType +
                ", price=" + price +
                ", appFormURL='" + appFormURL +
                ", inspectionTime=" + inspectionTime +
                ", description='" + description +
                ", isOffMarket=" + isOffMarket +
                ", dateAdded=" + dateAdded +
                '}';
    }
}