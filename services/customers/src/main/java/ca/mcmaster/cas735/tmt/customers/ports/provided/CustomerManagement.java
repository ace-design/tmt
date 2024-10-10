package ca.mcmaster.cas735.tmt.customers.ports.provided;

import ca.mcmaster.cas735.tmt.customers.business.entities.Customer;
import ca.mcmaster.cas735.tmt.customers.business.errors.AlreadyExistingException;
import ca.mcmaster.cas735.tmt.customers.business.errors.NotFoundException;
import ca.mcmaster.cas735.tmt.customers.dto.CustomerAdminData;
import java.util.List;

public interface CustomerManagement {

    void create(CustomerAdminData request) throws AlreadyExistingException;

    void update(CustomerAdminData request) throws NotFoundException, AlreadyExistingException;

    void delete(String memberId) throws NotFoundException;

    Customer read(String memberId) throws NotFoundException;

    List<CustomerAdminData> search(String lastName);

    CustomerAdminData findBy(String memberId) throws NotFoundException;;

}
