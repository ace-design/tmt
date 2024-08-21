package ca.mcmaster.cas735.tmt.hrm_receiver.ports;

public interface FilterManagement {

    void register(String deviceId);
    void unregister(String deviceId);
    void flush();
}
