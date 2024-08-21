package ca.mcmaster.cas735.tmt.hrm_receiver.adapter;

import ca.mcmaster.cas735.tmt.hrm_receiver.dto.SenML;
import ca.mcmaster.cas735.tmt.hrm_receiver.ports.SenMLReceiver;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class MQTTReceiver {

    private final SenMLReceiver processor;

    @Autowired
    public MQTTReceiver(SenMLReceiver receiver) {
        this.processor = receiver;
    }

    @Value("${app.custom.mqtt.host}")   private String host;
    @Value("${app.custom.mqtt.port}")   private Integer port;
    @Value("${app.custom.mqtt.topic}")  private String topic;

    @Bean @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            log.debug("Receiving MQTT data {}", message.getPayload());
            SenML payload = translate((String) message.getPayload());
            processor.receive(payload);
        };
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound(@Qualifier("mqttInputChannel") @Lazy MessageChannel channel) {
        String url = "tcp://" + host + ":" + port;
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(url, "hrm_receiver", topic);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(channel);
        return adapter;
    }

    private SenML translate(String raw) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(raw, SenML.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
