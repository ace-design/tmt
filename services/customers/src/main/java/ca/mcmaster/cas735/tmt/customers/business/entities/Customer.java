package ca.mcmaster.cas735.tmt.customers.business.entities;

import ca.mcmaster.cas735.tmt.customers.dto.CustomerAdminData;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity @Data @Table(name="customers")
public class Customer {

    @Id
    private String memberId;

    private String lastName;
    private String firstName;
    private String hrmDeviceId;
    private Target target;

    public void updateWith(CustomerAdminData data) {
        // this should ideally be in a Translation/Adapter class to avoid coupling business to DTO
        this.lastName = data.getLastName();
        this.firstName = data.getFirstName();
        this.hrmDeviceId = data.getHrmDeviceId();
        this.target = data.getTarget();
    }

}
