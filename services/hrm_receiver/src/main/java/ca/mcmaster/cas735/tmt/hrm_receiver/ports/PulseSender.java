package ca.mcmaster.cas735.tmt.hrm_receiver.ports;

import ca.mcmaster.cas735.tmt.hrm_receiver.dto.Pulse;

public interface PulseSender {

    void send(Pulse pulse);

}
