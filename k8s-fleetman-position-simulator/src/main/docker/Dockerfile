FROM openjdk:8u131-jre

MAINTAINER Richard Chesterwood "contact@virtualpairprogrammers.com"

ADD maven/positionsimulator-0.0.1-SNAPSHOT.jar webapp.jar

CMD ["java","-Dspring.profiles.active=docker","-jar","webapp.jar"]
