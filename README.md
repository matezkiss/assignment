# assignment
repository for the codebase of the application specidied in the assignment
<br><br><br>

Please make sure that you've given correct values in the application.properties files. There are numerous lines of configurable parameters
giving some flexibility, although it's only been tested using the values seen in this repository. If you choose to stick with the orignal
values, please run the following commands in your local mysql terminal before running the app:
<br>

Create user db user for the app:<br>
CREATE USER 'reporting_app'@'localhost' IDENTIFIED BY 'strong_password_for_app';<br>
<br>

Grant necessary privileges for the new user:<br>
(for simplicity grant all)<br>
GRANT ALL PRIVILEGES ON world_of_books.* TO 'reporting_app'@'localhost';<br>
<br>

Please make sure your maven settings.xml references a repository url from where you can download the necessary dependencies.<br>
(https://repo1.maven.org/maven2 works fine.)<br>
<br>
To build, package and run the application, run the following commands in the directory where the pom.xml is located:<br>
mvn clean install<br>
mvn package spring-boot:repackage -DskipTests<br>
java -jar ./target/assignment-1.0.0-RELEASE.jar
<br><br><br>



Notes:<br>
<br>
1. Eclipse, Maven 3.6.3 and jdk8 were used to develop this project on OSX<br>
2. Datebase: MySQL 8.0.21 Community Server<br>
3. FTP upload was tested with speedtest.tele2.net<br>
