package ca.mcmaster.cas735.tmt.hrm_receiver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Data
public class FilterRequest {

    private String verb;
    private String parameter;

}
