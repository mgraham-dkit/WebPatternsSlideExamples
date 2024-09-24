package databases.B_daoPattern.business;

/**
 *
 * @author michelle
 */
public class Customer implements Comparable<Customer> {

    /*
     * CREATE TABLE customers ( customerNumber int(11) NOT NULL, customerName
     * varchar(50) NOT NULL, contactLastName varchar(50) NOT NULL,
     * contactFirstName varchar(50) NOT NULL, phone varchar(50) NOT NULL,
     * addressLine1 varchar(50) NOT NULL, addressLine2 varchar(50) default NULL,
     * city varchar(50) NOT NULL, `state` varchar(50) default NULL, postalCode
     * varchar(15) default NULL, country varchar(50) NOT NULL,
     * salesRepEmployeeNumber int(11) default NULL, creditLimit double default
     * NULL, PRIMARY KEY (customerNumber), FOREIGN KEY (salesRepEmployeeNumber)
     * REFERENCES employees(employeeNumber) );
     */

    private int customerNumber;
    private String customerName;
    private String contactLastName;
    private String contactFirstName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private int salesRepEmployeeNumber;
    private double creditLimit;

    public Customer() {
        this.salesRepEmployeeNumber = 0;
    }

    // Constructor that does not take in a sales rep - this is for where there
    // is no sales rep, i.e. it's null
    public Customer(int customerNumber, String customerName, String contactLastName, String contactFirstName, String phone, String addressLine1, String addressLine2, String city, String state, String postalCode, String country, double creditLimit) {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.contactLastName = contactLastName;
        this.contactFirstName = contactFirstName;
        this.phone = phone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.salesRepEmployeeNumber = 0;
        this.creditLimit = creditLimit;
    }

    public Customer(int customerNumber, String customerName, String contactLastName, String contactFirstName, String phone, String addressLine1, String addressLine2, String city, String state, String postalCode, String country, int salesRepEmployeeNumber, double creditLimit) {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.contactLastName = contactLastName;
        this.contactFirstName = contactFirstName;
        this.phone = phone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.salesRepEmployeeNumber = salesRepEmployeeNumber;
        this.creditLimit = creditLimit;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public int getSalesRepEmployeeNumber() {
        return salesRepEmployeeNumber;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return customerNumber == customer.customerNumber;
    }

    @Override
    public int hashCode() {
        return customerNumber;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerNumber=" + customerNumber + ", customerName=" + customerName + ", contactLastName=" + contactLastName + ", contactFirstName=" + contactFirstName + ", phone=" + phone + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city + ", state=" + state + ", postalCode=" + postalCode + ", country=" + country + ", salesRepEmployeeNumber=" + salesRepEmployeeNumber + ", creditLimit=" + creditLimit + '}';
    }

    public String format() {
        String formattedText = customerNumber + ": " + customerName
                + "\n\t" + contactLastName + ", " + contactFirstName
                + "\n\tPhone: " + phone
                + "\n\t\t" + addressLine1
                + "\n\t\t" + addressLine2
                + "\n\t\t" + city
                + "\n\t\t" + state
                + "\n\t\t" + postalCode
                + "\n\t\t" + country;
        if (salesRepEmployeeNumber != 0) {
            formattedText = formattedText + "\n\tSales Rep: " + salesRepEmployeeNumber;
        } else {
            formattedText = formattedText + "\n\tSales Rep: None";
        }
        formattedText = formattedText + "\n\tCredit limit: " + creditLimit;

        return formattedText;
    }

    // As we are implementing the Comparable interface, we need to include the
    // compareTo method (and since it's a parameterized version of the interface,
    // i.e. Comparable<Customer> instead of just Comparable,
    // we should take in a Customer object, not just an Object
    @Override
    public int compareTo(Customer c) {
        // If we define the "natural order" of a Customer as its id number,
        // then the compareTo method should base its ranking (of this object vs
        // the one supplied as a parameter) on the id number in ASCENDING order

        // Therefore we check our customer number against the parameter object's one
        // If ours is smaller, it should come first.
        //  (i.e. it's currently in the RIGHT order relative to the parameter, 
        //  and the result should be < 0)
        if (customerNumber < c.customerNumber) {
            return -1;
        } // If ours is bigger, it should come second 
        //  (i.e. it's currently in the WRONG order relative to the parameter,
        //  and the result should be > 0)
        else if (customerNumber > c.customerNumber) {
            return 1;
        } else {
            // If they're the same, then there's no change to be made, 
            // and the result should be 0
            return 0;
        }
    }
}
