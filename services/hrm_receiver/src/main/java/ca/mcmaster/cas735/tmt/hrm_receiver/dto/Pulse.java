package ca.mcmaster.cas735.tmt.hrm_receiver.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Data
public class Pulse {

    private String deviceId;
    private Integer timestamp;
    private Float beat;

}
