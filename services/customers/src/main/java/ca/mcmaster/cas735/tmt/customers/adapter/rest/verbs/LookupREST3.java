package ca.mcmaster.cas735.tmt.customers.adapter.rest.verbs;

import ca.mcmaster.cas735.tmt.customers.business.errors.NotFoundException;
import ca.mcmaster.cas735.tmt.customers.dto.LookupInfo;
import ca.mcmaster.cas735.tmt.customers.ports.provided.MemberLookup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Lookup for Members (subset of customer) Info (VERBS - Richardson Level: 3)")
@RequestMapping(value = "/verbs/members")

public class LookupREST3 {

    private final MemberLookup lookup;

    @Autowired
    public LookupREST3(MemberLookup manager) {
        this.lookup = manager;
    }

    // GET /verbs/members?hrmId={HRM_ID}
    @GetMapping(value = "/")
    @Operation(description = "Lookup for customers with a given hrm device")
    public LookupInfo lookupByHRM(@RequestParam String hrmId) throws NotFoundException {
        return lookup.lookupByHRMId(hrmId);
    }

    // GET /verbs/members/{ID}
    @GetMapping(value = "/{id}")
    @Operation(description = "Find an identified customer")
    public LookupInfo lookup(@PathVariable String id) throws NotFoundException {
        return lookup.lookupByMemberId(id);
    }

}
