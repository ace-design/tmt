package ca.mcmaster.cas735.tmt.hrm_receiver.business;

import ca.mcmaster.cas735.tmt.hrm_receiver.dto.Pulse;
import ca.mcmaster.cas735.tmt.hrm_receiver.dto.SenML;
import ca.mcmaster.cas735.tmt.hrm_receiver.ports.PulseSender;
import ca.mcmaster.cas735.tmt.hrm_receiver.ports.SenMLReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class Translator implements SenMLReceiver {

    private final SenMLFilter filter;
    private final PulseSender sender;

    @Autowired
    public Translator(SenMLFilter filter, PulseSender sender) {
        this.filter = filter;
        this.sender = sender;
    }

    @Override
    public void receive(SenML data) {
        if (filter.keep(data.getN())) {
            sender.send(senML2Pulse(data));
        }
    }

    private Pulse senML2Pulse(SenML senML) {
        Pulse result = new Pulse();
        result.setBeat(senML.getV());
        result.setDeviceId(senML.getN());
        result.setTimestamp(senML.getT());
        return result;
    }

}
