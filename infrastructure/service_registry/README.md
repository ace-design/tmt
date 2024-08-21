# Service Registry

- Author: Sébastien Mosser
- Version 2024.09

## Rationale

This service uses Eureka to implement a service registry for the TMT system.


## Technologies

- Java 21 (LTS)
- Spring Boot 3.3.2
- Eureka Server

## How to use locally

To compile the service registry on your computer:

```
service_registry $ mvn clean package
```

To start the service registry locally:

```
service_registry $ mvn spring-boot:run
```

You can then access the registry web manegement interface at the following address:

- [http://localhost:8761](http://localhost:8761)

**If you're on a Mac**, you might encounter issues using localhost (it might take some time to be available, or be blocked once and foor all). You can always refer to the direct IP address of localhost to by pass this situation.

  - [http://127.0.0.1:8761](http://127.0.0.1:8761)

## Build the container image

To build an image containing the service (assuming you have compiled locally):

```
service_registry $ docker build -t cas735/service-registry:1.0
```

This will create locally an image named `service-registry` in the `cas735` namespace, version `1.0`.