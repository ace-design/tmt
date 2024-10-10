package ca.mcmaster.cas735.tmt.customers.ports.required;

import ca.mcmaster.cas735.tmt.customers.business.entities.Customer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDataRepository extends JpaRepository<Customer, String> {

    /** Methods are auto-generated by JPA based on name conventions:
     * Source: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
     * **/

    public Optional<Customer> findByHrmDeviceId(String deviceId);

    public List<Customer> findByLastNameStartsWith(String start);


}
