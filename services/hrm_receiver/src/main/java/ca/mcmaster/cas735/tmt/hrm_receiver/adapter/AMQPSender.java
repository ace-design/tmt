package ca.mcmaster.cas735.tmt.hrm_receiver.adapter;

import ca.mcmaster.cas735.tmt.hrm_receiver.dto.Pulse;
import ca.mcmaster.cas735.tmt.hrm_receiver.ports.PulseSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class AMQPSender implements PulseSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public AMQPSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${app.custom.messaging.outbound-exchange-topic}") private String exchange;

    @Override
    public void send(Pulse pulse) {
        log.debug("Sending message to {}: {}", exchange, pulse);
        rabbitTemplate.convertAndSend(exchange, "*", translate(pulse));
    }

    @Bean
    public TopicExchange outbound() {
        // this will create the outbound exchange if it does not exist
        return new TopicExchange(exchange);
    }

    private String translate(Pulse pulse) {
        ObjectMapper mapper= new ObjectMapper();
        try {
            return mapper.writeValueAsString(pulse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
