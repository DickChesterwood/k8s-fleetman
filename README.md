# k8s Fleetman

A new version of "Fleetman", my thrilling example microservice architecture.

This version targets Kubernetes as the orchestration system. We're going to use k8s features to achieve this, whilst simplifying the implementation - I hope no Global Config Server will be needed, as we can use k8s for this.

This will become a training course at VirtualPairProgrammers and Udemy, aiming for release in May.

TODO:

* Upgrade the code to use k8s service discovery instead of Docker Swarm
* Get the thing up and running
* Get rid of global config server
* Get the thing standing up locally
* Get running on a cloud platform - definitely AWS but may try Azure also.
* Investigate transforming the front end to a SPA.
