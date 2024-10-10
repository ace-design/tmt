package ca.mcmaster.cas735.tmt.customers.adapter.rest.hateoas;

import ca.mcmaster.cas735.tmt.customers.business.errors.NotFoundException;
import ca.mcmaster.cas735.tmt.customers.dto.LookupInfo;
import ca.mcmaster.cas735.tmt.customers.ports.provided.MemberLookup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@Tag(name = "Lookup for Members (subset of customer) (HATEOAS - Richardson Level: 4)")
@RequestMapping(value = "/hateoas/members")
public class LookupREST4 {

    private final MemberLookup lookup;

    @Autowired
    public LookupREST4(MemberLookup manager) {
        this.lookup = manager;
    }

    @GetMapping(value = "/")
    @Operation(description = "Lookup for customers with a given hrm device")
    public EntityModel<LookupInfo> lookupByHRM(@RequestParam String hrmId)
            throws NotFoundException {
        return asEntity(lookup.lookupByHRMId(hrmId));
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Find an identified customer")
    public EntityModel<LookupInfo> lookup(@PathVariable String id) throws NotFoundException {
        return asEntity(lookup.lookupByMemberId(id));
    }

    private EntityModel<LookupInfo> asEntity(LookupInfo info) {
        // link taxonomy: https://www.iana.org/assignments/link-relations/link-relations.xhtml
        Link self = linkTo(LookupREST4.class).slash(info.getMemberId()).withSelfRel();
        Link edit = linkTo(CustomerREST4.class).slash(info.getMemberId()).withRel("edit");
        return EntityModel.of(info).add(self, edit);
    }


}
