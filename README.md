# IoTServer

Backend server application for purporse of providing Rest API through which is user able to manage his own sensor network using LoRaWAN TTN.

## How to run?

First, install JDK 17 or newer. 

After startup, API documentation is available through Swagger. 

### Through console

```
mvn compile
```

```
java -jar IoTServer-1.0-SNAPSHOT.jar
```

### Using docker

```
docker build . -t "name_of_image"
```

```
docker run "name_of_image" -p 9090:9090
```

## Setup SQL database

Server uses its own database for storing information about devices, existing users, measurements and relations. Docker image of this database, together with SQL stamp is available in Docker/database folder. 

```
docker build . -t "name_of_image"
```

```
docker run "name_of_image" -p 3306:3306
```

As second option, is also possible to import .sql file and run it locally within DBMS.

Either way, in case of chaning the password or location of database, set these enviromental variables

```
DB_ADDRESS - address of database server location. Default is localhost
DB_USER - database user. Default is root
DB_PASSWORD - database password. Default is root
```
## Setup TTN credentials

Backend server uses mqtt to communicate with TTN using webhooks. Without this, server is not enable to process incoming measurements nor it is able to centrally control connected irrigation units.

To use own TTN instance, please set these enviromental variables

```
LORA_ADDRESS - address of TTN server
LORA_APPID - ID of TTN application
LORA_KEY - generated TTN token for application
```
It is also possible to set these via application.properties file.










