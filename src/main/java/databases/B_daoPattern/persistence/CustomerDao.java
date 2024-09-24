package databases.B_daoPattern.persistence;

import databases.B_daoPattern.business.Customer;

import java.util.List;

public interface CustomerDao {
    public Customer getById(int id);
    public List<Customer> getAllCustomers();
    public boolean deleteById(int id);
}
