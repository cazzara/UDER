# UDER
St. John's University Software Engineering Project - Basic Commerce Android Mobile application<br>
This project is NOT intended for production use, there is no encryption or payment system used. This is merely a proof of concept project.

# Premiss
This application addresses the need for people to have milk delivered to their residence or place of business and leverages crowd sourced labor to handle the deliveries. 

There are two types of users in this application: 
<br>A '<b>Buyer</b>' is the type of user who views a list of available products and submits an order
<br>A '<b>Milker</b>' is a user type who views orders that need to be fulfilled and delivers the products to the delivery address

The app communicates via JSON requests with an API running on an Amazon EC2 instance

# Building the App
To run a demo of the app, simply clone or download the zip file of this Github project and import it into a new Android Studio project

From there you can run an Android emulator and use the app to register a user and login.

# Building the Server API

The server runs on an Amazon Linux image and uses a MySQL database to store order, product, and user information.

The Java server uses the Spark framework to act as a RESTful web service for the app to make database queries.
The following is added to the maven POM.xml file to incorporate Spark into the server:
```
<dependency>
    <groupId>com.sparkjava</groupId>
    <artifactId>spark-core</artifactId>
    <version>2.6.0</version>
</dependency>
```
Spark uses port 4567 so we have to create a firewall rule to allow incoming connections on that port.

To connect Java with the MySQL database we used the MySQL driver "mysql-connector-java-5.1.41-bin.jar"
And added it to our project's classpath.

Then we export the project to a .jar file and FTP it to our server and run it using the command

java -jar our-jar-file.jar
