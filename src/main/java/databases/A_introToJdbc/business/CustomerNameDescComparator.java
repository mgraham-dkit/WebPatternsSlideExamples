package databases.A_introToJdbc.business;

import java.util.Comparator;

/**
 *
 * @author michelle
 */
public class CustomerNameDescComparator implements Comparator<Customer> {
    // Compares two Customers and indicates whether their current order (o1 before o2) 
    // is correct relative to each other
    // As it's independent of the class it's comparing data for, 
    // it allows for the creation of MULTIPLE ways to sort/compare

    @Override
    public int compare(Customer o1, Customer o2) {
        // Because it's not part of the Customer class, 
        // we have to use getter methods to access data here
        String o1Name = o1.getCustomerName();
        String o2Name = o2.getCustomerName();

        // Same approach as with compareTo - check each possible situation
        // Are they in the right order? (in this case, does o1's customer name
        // come AFTER o2's customer name in the dictionary, 
        // because we're doing DESCENDING order)
        // Because we're dealing with text, we need to use 
        // the compareTo method from the String class to work that out
        // Remember: We can't use > or < on objects
        if (o1Name.compareTo(o2Name) < 0) {
            //if they're currently in alphabetical order, 
            // then the current ranking is WRONG 
            // because we're doing descending order here
            return 1;
        } else if (o1Name.compareTo(o2Name) > 0) {
            //if they're currently NOT in alphabetical order, 
            // then the current ranking is correct
            return -1;
        } else {
            // If they're the same, return 0
            return 0;
        }
    }

}
