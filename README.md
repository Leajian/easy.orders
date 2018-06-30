# Easy Orders
This project was originally developed for the *Software Technology* subject in **University of Macedonia, Department of Applied Informatics**.


## Prerequisites
Make sure that the database (upload the *easyorders_db.sql* file) is up and running locally, or at least on the host you have defined, in the DBConnect class with the correct login credentials.
If you have localization problems and the characters don't display correctly either when you open the project in Eclipse or when you use the application, try using **MySQL 5.7.17** and **UTF-8** encoding.

**AppServ** is recommented for the database, as it was tested during development.

You need at least **JAVA 8.1**.

All users of the application must be connected with their respective application instances in the same local network in order to communicate with the same database. Also, the database must be configured in such way so that the account which is used for the database connection (*root*) allows users to connect from multiple IPs. You may use the **%** symbol in the configuration for that purpose.
