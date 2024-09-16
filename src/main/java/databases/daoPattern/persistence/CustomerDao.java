package databases.daoPattern.persistence;

import databases.daoPattern.business.Customer;

import java.util.List;

public interface CustomerDao {
    public Customer getById(int id);
    public List<Customer> getAllCustomers();
    public boolean deleteById(int id);
}
