# docker-only-fleetman
The full set of microservices for Fleetman without needing a Euerka Registry.

Created as a working example for the Docker training course at https://www.virtualpairprogrammers.com/training-courses/Docker-Module-2-for-Java-Developers-training.html?agent=github

Chapter 9 of the course shows how Docker's Overlay Network can exploit Round Robin DNS to support a Microservice Registry - hence making external registries such as Netflix/Spring Cloud Eureka potentially redundant. 

A full extract from the course can be seen here: https://www.virtualpairprogrammers.com/tutorials/0eb05093-8b5a-4d4a-9949-5023ceb64f7f?agent=github

For this version I haven't bothered creating separate repos - each subfolder is a separate Java microservice.
