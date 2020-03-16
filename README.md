# Software Engineering - Lab Project

## Contextualization

This repository contains the work done by the authors on the lab project for the course in Software Engineering of the MSc. in Informatics Engineering of the University of Aveiro.
The assignment is focused on all the main concepts and technological solutions addressed in the first part of the course: REST (consuming and deploying), JPA (persisting), Kafka Messaging (logging and event alarms).
The deployment is done using docker containers and all code is presented with the proper documentation.

## Authors

Filipe Pires, nmec. 85122

João Alegria, nmec. 85048

## Description

The project is about a live tracking system of the International Space Station (ISS).
The data is taken from the REST API at https://wheretheiss.at/.
We retrieve the data, store it in a H2 database, with the help of JPA, and present the information in a simple web-UI containing a world map.
All requests made by our clients are logged through Kafka.

## Instructions on how to run the code

1. Have installed Docker and Docker Compose.
2. Run the docker-compose to create the application's containers.
3. Make sure your Kafka (either locally installed or in a docker container) is running.
4. Open the project 'iss' in your IDE (only tested with Netbeans).
5. Run the project.
6. Open a browser, enter the address 'http://localhost:8080/' and enjoy the application.


