package ca.mcmaster.cas735.tmt.customers.adapter.rest.hateoas;


import ca.mcmaster.cas735.tmt.customers.business.errors.AlreadyExistingException;
import ca.mcmaster.cas735.tmt.customers.business.errors.NotFoundException;
import ca.mcmaster.cas735.tmt.customers.dto.CustomerAdminData;
import ca.mcmaster.cas735.tmt.customers.ports.provided.CustomerManagement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@Tag(name = "Customer Management (HATEOAS - Richardson Level: 4)")
@RequestMapping(value = "/hateoas/customers")
public class CustomerREST4 {

    private final CustomerManagement manager;

    @Autowired
    public CustomerREST4(CustomerManagement manager) {
        this.manager = manager;
    }

    @GetMapping(value = "/")
    @Operation(description = "Lookup for customers with name starting with a given prefix")
    public CollectionModel<EntityModel<CustomerAdminData>> listCustomers(
            @RequestParam String lastNamePrefix) {
        return CollectionModel.of(
                manager.search(lastNamePrefix).stream().map(this::asEntity).toList()
        );
    }

    @PostMapping(value = "/")
    @Operation(description = "Create a new customer in the system")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CustomerAdminData data)
            throws AlreadyExistingException {
        manager.create(data);
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Lookup for customers with a given member id")
    public EntityModel<CustomerAdminData> read(@PathVariable String id)
            throws NotFoundException {
        return asEntity(manager.findBy(id));
    }


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

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Delete a given customer")
    public void delete(@PathVariable String id) throws NotFoundException {
        manager.delete(id);
    }

    private EntityModel<CustomerAdminData> asEntity(CustomerAdminData data) {
        Link self = linkTo(CustomerREST4.class).slash(data.getMemberId()).withSelfRel();
        Link preview = linkTo(LookupREST4.class).slash(data.getMemberId()).withRel("preview");
        return EntityModel.of(data).add(self, preview);
    }


}
