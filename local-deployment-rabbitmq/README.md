# How to execute the local deployment

## Prerequisite

Docker must be installed and running.

## Steps

1. Make sure you execute `mvn clean install` on root pom level at least once.
2. Go into "local-deployment-rabbitmq" and execute `docker-compose up`. As an alternative you can
   execute `docker-compose -f localdeployment/docker-compose.yml up` from the root pom level.
3. The RabbitMQ management interface is accessible through port 15672 by default. So accessing http://localhost:15672/ will get you to the login page.
4. You can log in as guest, by using `guest` as username and password.

![RabbitMqLogin](local-deployment-rabbitmq/img/login.png)


### ❄ Overview
The RabbitMQ management interface groups the information into 6 tabs, starting with Overview where you find details about: queued messages, messages rate, nodes, port and contexts, global count and import/export definitions.

![RabbitMqOverview](local-deployment-rabbitmq/img/overview.png)


### ❄ Queue
The queue tab shows the queues for all or one selected vhost. Queues have different parameters and arguments depending on how they were created. You can also create a queue from this view.

![RabbitMqQueue](local-deployment-rabbitmq/img/queue.png)

### ❄ Channels
The channel tab shows information about all current channels. If you click on one of the channels, you get a detailed overview of that specific channel. From here you can see the message rate on the number of logical consumers retrieving messages via the channel.

![RabbitMqChannels](local-deployment-rabbitmq/img/channels.png)

### ❄ Connection
The connection tab shows the connections established to the RabbitMQ server. You can view channels in the connection and data rates, client properties, and also you can close the connection.

![RabbitMqConnections](local-deployment-rabbitmq/img/connections.png)

### ❄ Exchange
All exchanges can be listed from the exchange tab. An exchange receives messages from producers and pushes them to queues. The exchange must know exactly what to do with a message it receives.

![RabbitMqExchanges](local-deployment-rabbitmq/img/exchanges.png)

### ❄ Admin
From the Admin view, it is possible to add users and change user permissions. You can set up vhosts, policies, federation, and shovels.

![RabbitMqAdmin](local-deployment-rabbitmq/img/admin.png)