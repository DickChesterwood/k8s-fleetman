FROM amazoncorretto:17.0.5-al2022-RC-headless    
 
MAINTAINER Richard Chesterwood "richard@inceptiontraining.co.uk"
   
RUN yum install -y which wget gzip tar && wget -O activemq.tar.gz http://archive.apache.org/dist/activemq/5.17.3/apache-activemq-5.17.3-bin.tar.gz
     
RUN tar -xzf activemq.tar.gz
    
ENV JAVA_HOME=/usr/bin/
     
CMD ["apache-activemq-5.17.3/bin/activemq", "console"]
