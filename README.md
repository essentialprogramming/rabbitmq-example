Spring AMQP (RabbitMQ) sample project  
=====================================
This guide walks you through the process of setting up a RabbitMQ AMQP server that
publishes and subscribes to messages and creating sample Spring Boot applications to interact
with that RabbitMQ server ( publish/subscribe to messages ).


Set up RabbitMQ
===============

Before you can build your messaging application, you need to set up a server to handle
receiving and sending messages.


RabbitMQ is an AMQP server. The server is freely available at https://www.rabbitmq.com/download.html. 

There are two options for using rabbit locally: install or use a docker container. For convenience use [Docker Compose](https://docs.docker.com/compose/) to quickly launch a
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


Prerequisites:
---------------

* [java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [maven](https://maven.apache.org/)
* [docker](https://www.docker.com/products/docker-desktop)

## Additional Resources
* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
* [Spring-AMQP](http://projects.spring.io/spring-amqp/)
* [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
