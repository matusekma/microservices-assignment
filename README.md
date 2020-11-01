# Kotlin blog API

##Requirements to run MongoDB:
- Docker

##Launching MongoDB
Run the following command in the root folder containing docker-compose.yml:  
``docker-compose up``

## Launching the app
Run the following command in the blog-api folder:  
``./gradlew bootRun``

## Running unit tests
Run the following command in the blog-api folder:  
``./gradlew clean test``  
JaCoCo test coverage can be inspected in the following folder:  
``kotlin-blog-api\blog-api\build\reports\jacoco\test\html``
