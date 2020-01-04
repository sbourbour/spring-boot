# myretail web service
 
myretail web service is a Spring Boot application. Once started it will be available at localhost:8090

## To run

You can run the application in your IDE (e.g. Eclipse or InteliJ) or on the command line:

Windows example:

C:\Users\Owner\workspace\myretailweb>java -Dspring.profiles.active=local -jar target\myretailweb-1.0.0-SNAPSHOT.jar

## Usage

The web service provides two end points. A GET and a PUT.

The GET end point can be accessed via a browser or a tool like Postman.

Example:

http://localhost:8090/myretailweb/products/13860428

The PUT endpoint is accessible via the same end point, and it requires a payload in the following format:

{"name":"The Big Lebowski (Blu-ray)","id":13860428,"current_price":{"value":9.99,"currency_code":"USD"}}

Upon success, the service will have updated the price of the item to the value specified in the "value" field.

## Requirements

This service requires Apache Cassandra. 
The Cassandra database will need to hold a table containing the items and the prices.

You may install Java, Python, and Cassandra by following the instructions provided here:

https://www.how2shout.com/how-to/install-apache-cassandra-on-windows-10-8-7-without-datastax.html

(make sure you download Cassandra 3.11.4)
http://www.apache.org/dyn/closer.lua/cassandra/3.11.4/apache-cassandra-3.11.4-bin.tar.gz
 

## Cassandra keyspace and table

Once Cassandra is running, you may create the keyspace and table using the cqlsh command line interface:

CREATE KEYSPACE shahriar WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

CREATE TABLE shahriar.product (
   id int,
   price double,
   currency text,
   PRIMARY KEY (id)
   );

Insert into shahriar.product (id, price, currency) values (13860428, 13.49, 'USD');
 

