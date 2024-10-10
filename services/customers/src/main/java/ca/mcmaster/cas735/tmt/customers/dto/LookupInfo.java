package ca.mcmaster.cas735.tmt.customers.dto;

import ca.mcmaster.cas735.tmt.customers.business.entities.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class LookupInfo {

    private String memberId;
    private String displayName;
    private String hrmDeviceId;

    public LookupInfo(Customer c) {
        this.memberId = c.getMemberId();
        this.displayName = c.getFirstName() + " " + c.getLastName().charAt(0) + ".";
        this.hrmDeviceId = c.getHrmDeviceId();
    }

}
