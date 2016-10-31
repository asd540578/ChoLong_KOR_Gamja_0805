@echo off & color 0C
@java -server -Xms4G -Xmx8G -XX:SurvivorRatio=32 -XX:+UseG1GC -cp l1jserver.jar;lib\xmlapi;lib\c3p0-0.9.1.2.jar;lib\netty-all-4.0.29.Final.jar;lib\commons-dbcp-1.4.jar;lib\commons-pool-1.6.jar;lib\mysql-connector-java-5.1.21-bin.jar;lib\javolution.jar;lib\JTattoo.jar;lib\netty-all-4.0.29.Final.jar server.manager.eva
@pause