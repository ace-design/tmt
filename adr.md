# Architectural Decision Record(s)

## Security

- In the context of _developping a microservice architecture for a graduate course_,
- facing _the need for securing such architecture in real life_
- we decided for _not covering this topic in the course_
  - and neglected _introducing distributed security concerns theory/labs_,
- to achieve _depth of knowledge in how to design/implement microservices_,
  - accepting _that security is a topic on its own which deserves a dedicated grad course_,
  - because _it is easy to "think" a system is secure when it's not, and we don't want to provide a false sense of security expertise as a learning outcome_.

## Message Brokering

- In the context of _deploying a message broker to support the TMT architecture_,
- facing _the need to chose between different deployment technologies_
- we decided for _RabbitMQ_ 
  - and neglected other options like _Kafka_,
- to achieve _simpler ramp-up for students_,
  - accepting _that RabbitMQ is more limited in terms of features and scalability capacity_
  - because _heavier solution might not run on a student laptop easily_


## Deployment

- In the context of _deploying the system into its runtime environment_,
- facing _the need to chose between different deployment technologies_
- we decided for _using Docker Compose_ 
  - and neglected other options like _Kubernetes_,
- to achieve _simpler ramp-up for students_,
  - accepting _that docker compose is not really recommended for production environments_
  - because _it is easier for students to only interact with Docker for both image building and deployment_


---

## Template

- In the context of _use case/user story u_,
- facing _concern c_
- we decided for _option o_
  - and neglected _other options_,
- to achieve _system qualities/desired consequences_,
  - accepting _downside d/undesired consequences_,
  - because _additional rationale_.