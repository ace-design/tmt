package ca.mcmaster.cas735.tmt.hrm_receiver.business;

import ca.mcmaster.cas735.tmt.hrm_receiver.ports.FilterManagement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class Filter implements FilterManagement, SenMLFilter {

    private static final Set<String> registered = new HashSet<>();


    @Override
    public boolean keep(String deviceId) {
        return registered.contains(deviceId);
    }

    @Override
    public void register(String deviceId) {
        log.info("Registering device {}", deviceId);
        registered.add(deviceId);

    }

    @Override
    public void unregister(String deviceId) {
        log.info("Unregistering device {}", deviceId);
        registered.remove(deviceId);
    }

    @Override
    public void flush() {
        log.info("Flushing all devices");
        registered.clear();
    }

}
