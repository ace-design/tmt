package ca.mcmaster.cas735.tmt.customers.business;

import ca.mcmaster.cas735.tmt.customers.business.entities.Customer;
import ca.mcmaster.cas735.tmt.customers.business.errors.AlreadyExistingException;
import ca.mcmaster.cas735.tmt.customers.business.errors.NotFoundException;
import ca.mcmaster.cas735.tmt.customers.dto.CustomerAdminData;
import ca.mcmaster.cas735.tmt.customers.dto.LookupInfo;
import ca.mcmaster.cas735.tmt.customers.ports.required.CustomerDataRepository;
import ca.mcmaster.cas735.tmt.customers.ports.provided.MemberLookup;
import ca.mcmaster.cas735.tmt.customers.ports.provided.CustomerManagement;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistry implements MemberLookup, CustomerManagement {


    private final CustomerDataRepository database;

    @Autowired
    public CustomerRegistry(CustomerDataRepository database) {
        this.database = database;
    }

    @Override
    public LookupInfo lookupByMemberId(String memberId) throws NotFoundException {
        return new LookupInfo(database.findById(memberId)
                .orElseThrow( () -> new NotFoundException("Customer", memberId, "MemberId")));
    }

    @Override
    public LookupInfo lookupByHRMId(String hrmId) throws NotFoundException {
        return new LookupInfo(database.findByHrmDeviceId(hrmId)
                .orElseThrow( () -> new NotFoundException("Customer", hrmId, "HRMId")));
    }


    @Override
    public void create(CustomerAdminData request) throws AlreadyExistingException {
        Customer customer = request.asCustomer();
        if (database.existsById(customer.getMemberId())) {
            throw new AlreadyExistingException("Customer", customer.getMemberId(), "memberId");
        }
        if (database.findByHrmDeviceId(customer.getHrmDeviceId()).isPresent()) {
            throw new AlreadyExistingException("Customer", customer.getMemberId(), "hrmDeviceId");
        }
        database.saveAndFlush(customer);
    }

    @Override
    public void update(CustomerAdminData request)
            throws NotFoundException, AlreadyExistingException {

        Customer stored = database.findById(request.getMemberId()).orElseThrow(
                () ->  new NotFoundException("Customer", request.getMemberId(), "memberId"));

        Optional<Customer> sameHrmUser = database.findByHrmDeviceId(request.getHrmDeviceId());
        if (sameHrmUser.isPresent()
                && !sameHrmUser.get().getMemberId().equals(stored.getMemberId())) {
            // someone is already using that HRM device, and it's not the member to update
            throw new AlreadyExistingException("Customer", stored.getHrmDeviceId(), "hrmDeviceId");
        }
        stored.updateWith(request);
        database.saveAndFlush(stored); // synchronizing with database
    }

    @Override
    public void delete(String memberId) throws NotFoundException {
        Customer stored = database.findById(memberId).orElseThrow(
                () ->  new NotFoundException("Customer", memberId, "memberId"));
        database.delete(stored);
    }

    @Override
    public Customer read(String memberId) throws NotFoundException {
        return database.findById(memberId).orElseThrow(
                () ->  new NotFoundException("Customer", memberId, "memberId"));
    }

    @Override
    public List<CustomerAdminData> search(String lastName) {
        List<Customer> customers = database.findByLastNameStartsWith(lastName);
        return customers.stream()
                .map(CustomerAdminData::new) // Translating to DTO to avoid abstraction leak
                .toList();
    }

    @Override
    public CustomerAdminData findBy(String memberId) throws NotFoundException {
        Customer stored = database.findById(memberId).orElseThrow(
                () ->  new NotFoundException("Customer", memberId, "memberId"));
        return new CustomerAdminData(stored);
    }
}
