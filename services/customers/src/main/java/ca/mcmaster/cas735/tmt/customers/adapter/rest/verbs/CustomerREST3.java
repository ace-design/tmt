package ca.mcmaster.cas735.tmt.customers.adapter.rest.verbs;


import ca.mcmaster.cas735.tmt.customers.business.errors.AlreadyExistingException;
import ca.mcmaster.cas735.tmt.customers.business.errors.NotFoundException;
import ca.mcmaster.cas735.tmt.customers.dto.CustomerAdminData;
import ca.mcmaster.cas735.tmt.customers.ports.provided.CustomerManagement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Customer Management (VERBS - Richardson Level: 3)")
@RequestMapping(value = "/verbs/customers")
public class CustomerREST3 {

    private final CustomerManagement manager;

    @Autowired
    public CustomerREST3(CustomerManagement manager) {
        this.manager = manager;
    }

    // GET /verbs/customers
    @GetMapping(value = "/")
    @Operation(description = "Lookup for customers with name starting with a given prefix")
    public List<CustomerAdminData> listCustomers(@RequestParam String lastNamePrefix) {
        return manager.search(lastNamePrefix);
    }

    // POST /verbs/customers
    @PostMapping(value = "/")
    @Operation(description = "Create a new customer in the system")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CustomerAdminData data)
            throws AlreadyExistingException {
        manager.create(data);
    }

    // GET /verbs/customers/{ID}
    @GetMapping(value = "/{id}")
    @Operation(description = "Lookup for customers with a given member id")
    public CustomerAdminData read(@PathVariable String id)
            throws NotFoundException {
        return manager.findBy(id);
    }


    // PUT /verbs/customers/{ID}
    @PutMapping(value = "/{id}")
    @Operation(description = "Update an existing customer in the system")
    public void update(@PathVariable String id, @RequestBody CustomerAdminData data)
            throws NotFoundException, AlreadyExistingException {
        if (! id.equals(data.getMemberId())) {
            throw new IllegalArgumentException("Resource "
                    + id + "cannot be updated with a different member Id: "
                    + data.getMemberId());
        }
        manager.update(data);
    }

    // DEL /verbs/customers/{ID}
    @DeleteMapping(value = "/{id}")
    @Operation(description = "Delete a given customer")
    public void delete(@PathVariable String id) throws NotFoundException {
        manager.delete(id);
    }

}
