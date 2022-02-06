Spring AMQP (RabbitMQ) sample project  
=====================================
This guide walks you through the process of setting up a RabbitMQ AMQP server that
publishes and subscribes to messages and creating a Spring Boot application to interact
with that RabbitMQ server ( publish messages to RabbitMQ ) and two Spring applications to 
consume messages from the RabbitMQ server. 


Set up RabbitMQ
=========================

Before you can build your messaging application, you need to set up a server to handle
receiving and sending messages.


RabbitMQ is an AMQP server. The server is freely available at https://www.rabbitmq.com/download.html. 

For convenience use [Docker Compose](https://docs.docker.com/compose/) to quickly launch a
RabbitMQ server.

```
services:
 rabbitmq:
  image: rabbitmq:management
  ports:
    - "5672:5672"
    - "15672:15672"
```

With this file in the current directory, you can run `docker-compose up` to get RabbitMQ
running in a container.

## Additional Resources
