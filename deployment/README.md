# TMT architecture deployment

- Author: Sébastien Mosser
- Version: 2024.09

## Rationale

This artefacts models how the complete TMT system is deployed in production.

## Technologies

- Docker Compose

## How to use?

To build all the containers:

```
deployment $ docker compose build --no-cache
```

The images built by the previous command start each service and acvtivate their `docker` profile (specified in `application-docker.yml` in each service). This allows us to reference partners services using their DNS inside the docker environment rather than using `localhost` (which is used when we are using the `local` profile, defined in `application-local.yml`).

To deploy the system:

```
deployment $ docker compose up -d
```

To shutdown the system:

```
deployment $ docker compose stop
```

