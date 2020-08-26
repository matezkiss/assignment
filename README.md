# wob_assignment
repository for the codebase of the application specidied in the assignment
<br><br><br>

Please make sure that you've given correct values in the application.properties files. There are numerous lines of configurable parameters
giving some flexibility, although it's only been tested using the values seen in this repository. If you choose to stick with the orignal
values, please run the following commands in your local mysql terminal before running the app:
<br>

Create database for the app:<br>
CREATE DATABASE world_of_books;<br>
<br>

Create user db user for the app:<br>
CREATE USER 'reporting_app'@'localhost' IDENTIFIED BY 'strong_password_for_app';<br>
<br>

Grant necessary privileges for the new user:<br>
GRANT SELECT, INSERT, CREATE, REFERENCES ON world_of_books.* TO 'reporting_app'@'localhost';<br>

GRANT CREATE ON world_of_books.DATABASECHANGELOG TO 'reporting_app'@'localhost';<br>

GRANT UPDATE, DELETE ON world_of_books.DATABASECHANGELOGLOCK TO 'reporting_app'@'localhost';<br>
<br>

Please make sure your maven settings.xml references a repository url from where you can download the necessary dependencies.<br>
(https://repo1.maven.org/maven2 works fine.)<br>
<br>
Run the following command from the directory where the pom.xml is located:<br>
mvn clean install<br>
<br>

Then you can run the app with the following command:<br>
mvn spring-boot:run<br>
<br><br><br>



Notes:<br>
<br>
1. Eclipse, Maven and jdk8 were used to develop this project on OSX<br>
2. Email address length is limited to 254 characters (please refer to: https://www.rfc-editor.org/errata_search.php?rfc=3696&eid=1690)<br>
