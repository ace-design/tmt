# Technical Infrastructure

- Author: Sébastien Mosser
- Version: 2024.09

## Rationale

This artefact describes the technical infrastructure (message broker and service registry) used by TMT

## Technologies

- Docker compose

## How to use?

To build all the containers:

```
deployment $ docker compose build --no-cache
```

To deploy the system:

```
deployment $ docker compose up -d
```

To shut down the system:

```
deployment $ docker compose stop
```
