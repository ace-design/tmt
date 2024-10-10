package ca.mcmaster.cas735.tmt.customers.dto;

import ca.mcmaster.cas735.tmt.customers.business.entities.Customer;
import ca.mcmaster.cas735.tmt.customers.business.entities.Target;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class CustomerAdminData {

    private String memberId;
    private String lastName;
    private String firstName;
    private String hrmDeviceId;
    private Target target;

    public Customer asCustomer() {
        Customer result = new Customer();
        result.setMemberId(this.memberId);
        result.setLastName(this.lastName);
        result.setFirstName(this.firstName);
        result.setHrmDeviceId(this.hrmDeviceId);
        result.setTarget(target);
        return result;
    }

    public CustomerAdminData(Customer customer) {
        this.memberId = customer.getMemberId();
        this.lastName = customer.getLastName();
        this.firstName = customer.getFirstName();
        this.hrmDeviceId = customer.getHrmDeviceId();
        this.target = customer.getTarget();
    }

}
