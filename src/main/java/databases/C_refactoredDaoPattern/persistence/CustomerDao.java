package databases.C_refactoredDaoPattern.persistence;


import databases.C_refactoredDaoPattern.business.Customer;

import java.util.List;

public interface CustomerDao {
    public Customer getById(int id);
    public List<Customer> getAllCustomers();
    public boolean deleteById(int id);
}
