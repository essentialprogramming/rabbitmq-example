Spring AMQP (RabbitMQ) sample project  
=====================================
This guide walks you through the process of setting up a RabbitMQ AMQP server that
publishes and subscribes to messages and creating sample Spring Boot applications to interact
with that RabbitMQ server ( publish/subscribe to messages ).


## :rocket: Set up RabbitMQ

Before you can build your messaging application, you need to set up a server to handle
receiving and sending messages.


RabbitMQ is an AMQP server. The server is freely available at https://www.rabbitmq.com/download.html. 

There are two options for using Rabbit locally: install or use a docker container. For convenience use [Docker Compose](https://docs.docker.com/compose/) to quickly launch a
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


Spring AMQP Core Concepts
=========================
You need to create a publisher to send messages. To send a message, you'll need a Rabbit template.

You need to create a receiver that responds to published messages.

Spring AMQP's `RabbitTemplate` provides everything you need to send and receive messages
with RabbitMQ. Before you can send and receive messages, you need to: 

- Declare the queue, the exchange, and the binding between them. ( Or create a durable queue and a durable exchange ) 
- The `queue()` method creates an AMQP queue.
- The `exchange()` method creates a topic exchange.
- The `binding()` method binds these two together, defining the behavior that
occurs when `RabbitTemplate` publishes to an exchange. ( Send messages to the exchange to be routed to the queue based on a routing key ) 

NOTE: Spring Boot automatically creates a connection factory and a RabbitTemplate,
reducing the amount of code you have to write.

## Maven Dependency ( Spring Boot ) 
```xml
<dependency>  
 <groupId>org.springframework.boot</groupId>  
 <artifactId>spring-boot-starter-amqp</artifactId>  
</dependency>
```

RabbitMQ Management
===================
To manage the RabbitMQ server, you can use the [rabbitmq-management plugin](https://www.rabbitmq.com/management.html). This plugin allows you to manage and monitor your RabbitMQ server in a variety of ways, such as listing and deleting exchanges, queues, bindings and users. You can send and receive messages, and monitor activity on specific queues.
Once the plugin is installed, you are able to access it via the following url:

http://[your-server]:15672/

> The default username and password are [guest] and [guest]. Login, and you will be greeted with the RabbitMQ dashboard.


Summary
=======

Congratulations! You have just developed a simple publish-and-subscribe application with
Spring and RabbitMQ. You can do more with RabbitMQ, but this guide should provide a good start.

## Additional Resources
* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
* [Spring AMQP](http://projects.spring.io/spring-amqp/)
* [First steps with RabbitMQ and Spring Boot](https://medium.com/javarevisited/first-steps-with-rabbitmq-and-spring-boot-81d293554703)
* [Detailed explanation of Spring Boot Integration RabbitMQ](https://www.codestudyblog.com/cs2112bga/1205234445.html)
* [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
