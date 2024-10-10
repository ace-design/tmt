package ca.mcmaster.cas735.tmt.customers.ports.provided;

import ca.mcmaster.cas735.tmt.customers.business.errors.NotFoundException;
import ca.mcmaster.cas735.tmt.customers.dto.LookupInfo;

public interface MemberLookup {

    LookupInfo lookupByMemberId(String memberId) throws NotFoundException;

    LookupInfo lookupByHRMId(String hrmId) throws NotFoundException;




}
