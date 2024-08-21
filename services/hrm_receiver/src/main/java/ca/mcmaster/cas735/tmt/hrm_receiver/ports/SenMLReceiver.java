package ca.mcmaster.cas735.tmt.hrm_receiver.ports;

import ca.mcmaster.cas735.tmt.hrm_receiver.dto.SenML;

public interface SenMLReceiver {

    void receive(SenML data);

}
