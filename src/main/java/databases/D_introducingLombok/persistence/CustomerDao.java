package databases.D_introducingLombok.persistence;



import databases.D_introducingLombok.business.Customer;

import java.util.List;

public interface CustomerDao {
    public Customer getById(int id);
    public List<Customer> getAllCustomers();
    public boolean deleteById(int id);
}
