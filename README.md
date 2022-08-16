Pay My Buddy
============

The purpose of the project is to build a web app that would allow people to
transfer money or pay their friends with ease.

This application uses the following technologies to achieve its purpose :

-   Spring Security

-   Spring Data JPA

-   Thymeleaf

This version is a prototype intended for a first presentation. All the
functionalities are not present yet and the user interface aims to present the
general operation of the application.

Getting Started
---------------

These instructions will get you a copy of the project up and running on your
local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them

-   Java 11

-   Maven 3.8.2

-   Mysql 8.0.17

### Installing

A step by step series of examples that tell you how to get a development env
running:

1.Install Java:

https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install MySql:

https://dev.mysql.com/downloads/mysql/

After downloading the mysql 8 installer and installing it, you will be asked to
configure the password for the default `root` account. This code uses the
default root account to connect and the password can be set as `rootroot`. If
you add another user/credentials make sure to change the same in the code base.

### Running App

Post installation of MySQL, Java and Maven, you will have to set up the tables
and data in the data base. For this, please run the sql commands present in the
`data.sql` file under the `resources` folder in the code base.

Finally, you will be ready to import the code into an IDE of your choice and run
the DemoApplication.java to launch the application.

### Resources

[UML diagram](pmb-uml-diagram.png)

[Database structure](pmb-mpd.png)
