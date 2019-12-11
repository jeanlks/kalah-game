# Jean Carvalho



## Running the backend

Get inside the spring-backend folder and run the following command:
	`mvn spring-boot:run`

## Running the frontend

Get inside the angular-front and run the following command:
	`ng serve --open`
	
	
## Building the docker image
To build the docker images you can use the following commands:
Inside the spring-backend folder run:
	`mvn clean install dockerfile:build`

Inside the angular-front folder run:
	`docker build -t jeanlks/angular-front .`

## Running the docker image 
To run the docker images built use the following commands:

`docker run -p 8080:8080 jeanlks/bol-test`
`docker run -p 80:80 jeanlks/angular-front`

You can also run without building, the images are pushed to my own registry in docker hub.

