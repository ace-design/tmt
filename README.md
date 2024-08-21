# TMT: The Maroon Theory

- Authors: Sébastien Mosser, Chris Schankula
- Version: 2024.09

## Abstract

This repository is the companion app for **CAS 735 - (Micro-)Service Oriented Architecture**, a graduate course offered by the _Computing And Software_ department at the Faculty of Engineering of McMaster University.

## Overall Description

- `infrastructure`: contains the _technical_ services used to support the TMT system:
  - `message_broker`: A RabbitMQ server to support event-driven architectures using MQTT and AMQP protocols.
  - `service_registry`: An Eureka server (Netflix) to support service discovery
- `services`: The business services used to support TMT business
  - `hrm_receiver`: service to receive data sent by HRM and inject then into the TMT system
- `clients`: External (non-service-based artefacts) consuming the TMT services
  - `hrm_client`: a simulator _pretending_ being an HRM watch sending data to the `hrm_receiver` deployed in a studio 

## Services location

**If you're on a Mac**, you might encounter issues using `localhost` (it might take some time to be available, or be blocked once and foor all). You can always refer to the direct IP address of localhost (`127.0.0.1`) to bypass this situation.

- Infrastructure:
    - RabbitMQ Management web interface: <http://localhost:8080> (login: `admin`, password: `cas735`)
    - Eureka registry: <http://locahost:8761>
- Business Services:
    - HRM Receiver: <http://locahost:9080>
- 

## How to operate?

### Compiling the micro-services

```
tmt $ mvn clean package
```

### Building the turn-key services

```
tmt $ cd deployment
deployment $ docker compose build --no-cache
```

### Starting the complete system

```
deployment $ docker compose up -d
```

### Shutting down the system

```
deployment $ docker compose down
```




## Course Editions

Access to lecture material for McMaster students on Avenue2Learn:

- Fall 2024: <https://avenue.cllmcmaster.ca/d2l/home/630566>
- Fall 2023: <https://avenue.cllmcmaster.ca/d2l/home/549802>
- Fall 2022: <https://avenue.cllmcmaster.ca/d2l/home/485948>