# Message Broker

- Author: Sébastien Mosser
- Version: 2024.09


## Rationale

This message broker is used for event-driven messaging between services, as well as MQTT reception from IoT devices. We use an off-the-shelf image hosted on the docker hub, simply customized to our need.


## Technologies

- Docker Compose
- RabbitMQ 3

## Configuration:

We use the following two files to override default configuration of RabbitMQ:

- `_login.env`: Define 2 environment variable used as master admin password.
  - default account login is `admin`
  - default password is `cas735`

- `_rabbitmq.conf`: override default confiog options
  - MQTT boiler plate configuration
  - Allowing `management_metrics_collection` even if deprecated (to be fixed in next release of RabbitMQ)


## How to use locally

To start the broker:

```
message_broker $ docker compose up -d
```

To access the broker web interface:

  - [http://localhost:8080](http://localhost:8080)

To stop the broker:

```
message_broker $ docker compose down
```