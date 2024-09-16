package databases.introToJdbc.business;

/*
    CREATE TABLE `employees` (
      `employeeNumber` int(11) NOT NULL,
      `lastName` varchar(50) NOT NULL,
      `firstName` varchar(50) NOT NULL,
      `extension` varchar(10) NOT NULL,
      `email` varchar(100) NOT NULL,
      `officeCode` varchar(10) NOT NULL,
      `reportsTo` int(11) default NULL,
      `jobTitle` varchar(50) NOT NULL,
      PRIMARY KEY  (`employeeNumber`)
    ) 
*/

public class Employee{
    private int employeeNumber;
    private String lastName;
    private String firstName;
    private String extension;
    private String email;
    private String officeCode;
    private int reportsTo;
    private String jobTitle;

    public Employee(int employeeNumber, String lastName, String firstName, String extension, String email, String officeCode, int reportsTo, String jobTitle)
    {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.extension = extension;
        this.email = email;
        this.officeCode = officeCode;
        this.reportsTo = reportsTo;
        this.jobTitle = jobTitle;
    }

    public Employee(int employeeNumber, String lastName, String firstName, String extension, String email, String officeCode, String jobTitle)
    {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.extension = extension;
        this.email = email;
        this.officeCode = officeCode;
        this.jobTitle = jobTitle;
    }

    public Employee()
    {
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getExtension() {
        return extension;
    }

    public String getEmail() {
        return email;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public int getReportsTo() {
        return reportsTo;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;
        return employeeNumber == employee.employeeNumber;
    }

    @Override
    public int hashCode() {
        return employeeNumber;
    }

    @Override
    public String toString()
    {
        return "Employee{" + "employeeNumber=" + employeeNumber + ", lastName=" + lastName + ", firstName=" + firstName + ", extension=" + extension + ", email=" + email + ", officeCode=" + officeCode + ", reportsTo=" + reportsTo + ", jobTitle=" + jobTitle + '}';
    }
}
