package ca.mcmaster.cas735.tmt.hrm_receiver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Data
public class SenML {
    // https://www.rfc-editor.org/rfc/rfc8428.html

    private String n;
    private String u;
    private Integer t;
    private Float v;

}
