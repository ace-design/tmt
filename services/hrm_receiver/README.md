# HRM Receiver

- Author: Sébastien Mosser
- Version 2024.09

## Rationale

This service acts as the radio receiver deployed in a given studio to collect the messages sent by the HRMs held by participants. It receives the raw data sent by registered heart rates monitors (using MQTT), and pushes them into the TMT system (using a regular AMQP queue).


## Technologies

- Java 21 (LTS)
- Spring Boot 3.3.2
- RabbitMQ (AMQP and MQTT)

## Architecture

### Service Description

```mermaid
flowchart LR

    MQTT([fa:fa-circle-right MQTT fa:fa-envelope\n tmt_hrm])
    AMQP_IN([fa:fa-circle-right AMQP fa:fa-envelope\n hrm.filter])

    AMQP_OUT([fa:fa-envelope AMQP fa:fa-circle-right\nheartbeat])


    subgraph "<< service >>"
        SENML_RECEIVER("<< InputPort >>\nSenML Receiver")
        FILTRABLE("<< InputPort >>\n Filter\nManagement")
        SERVICE{{"\nHRM\nReceiver\n<br>"}}
        HRM_SENDER("<< OutputPort >>\nPulse Sender")
    end

    MQTT -- SenML--> SENML_RECEIVER
    AMQP_IN -- FilterRequest--> FILTRABLE

    FILTRABLE -. offered .- SERVICE
    SENML_RECEIVER -. offered .- SERVICE

    SERVICE -. required .- HRM_SENDER
    HRM_SENDER -- Pulse --> AMQP_OUT
```

### Service Model

```mermaid
classDiagram


    namespace InboundPorts {
        class SenMLReceiver {
            <<Interface>>
            + receive(data: SenML)
        }

        class FilterManagement {
            <<Interface>>
            + register(deviceId: String)
            + unregister(deviceId: String)
        }
    }

    namespace InboundAdapters {
        class MQTTReceiver {
            + handler(): MessageHandler
            + mqttInputChannel(): MessageChannel
            + inbound(): MessageProducer
        }

        class AMQPListener {
            +listen(data: String)
        }
    }

    namespace OutboundPorts {
        class PulseSender {
            <<Interface>>
            + send(pulse: Pulse)
        }
    }

    namespace OutboundAdapters {
        class AMQPSender {

        }
    }

    namespace Business {
        class Translator {
        }

        class Filter {
        }

        class SenMLFilter {
            <<Interface>>
            + keep(deviceId: String): Boolean
        }

    }


    class SenML {
        <<DTO>>
        + n: String
        + u: String
        + t: Int
        + v: Float
    }

    class FilterRequest {
        <<DTO>>
        + verb: String
        + parameter: String
    }

    class Pulse {
        <<DTO>>
        + deviceId: String
        + timestamp: Int
        + beat: Float
    }



    SenMLReceiver <|.. Translator
    Translator --> SenMLFilter
    FilterManagement <|.. Filter
    SenMLFilter <|.. Filter
    MQTTReceiver --> SenMLReceiver
    AMQPListener --> FilterManagement
    PulseSender <|.. AMQPSender
    Translator --> PulseSender
    SenML <.. SenMLReceiver
    Pulse <.. PulseSender
    FilterRequest <.. AMQPListener
```

## How to use locally

### Operating the service 

To compile the service registry on your computer:

```
hrm_receiver $ mvn clean package
```

To start the service registry locally:

```
hrm_receiver $ mvn spring-boot:run -Dspring-boot.run.profiles=local
```

When ran locally, the service assumes the infrastructure is up and running on `localhost`.

### Example of messages to send

- Use the `hrm_client` program to simulate multiple HRM sending datas
- Filtering requests to be sent to the `hrm.filter` exchange:
  - To register a device identified as `foo`: `{ "verb": "register", "parameter": "foo" }`   
  - To unregister a device identified as `foo`: `{ "verb": "unregister", "parameter": "foo" }`   
  - To flush all registrations: `{ "verb": "flush", "parameter": "" }`   