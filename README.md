# PG6201 Continuation Exam for Candidate 601

To put it shortly, it did not go well. Lots of time was spent trying to fix problems, without any progress. Given more time, I could have done a better job. At this point, I'm happy if I get a passing grade.

A Postman collection can be found in the root folder of this project. During my testing, only the three GET requests worked.
The intended order of requests was supposed to be:

  1. assignment
  2. booking/MQ-workaround
  3. shipping/MQ-workaround

Last I checked, none of them worked. The happy paths at least show that the services are up and running.

## What works
* Discovery Service
  * Registers all services as expected
* Gateway Service
  * Routes all calls correctly, at least from Postman
* docker-compose.yml
  * Builds and spins up all services, as long as I regularly reboot my computer. `-p`-flag not used
* Dockerfiles for all services
  * No `target` directory needed in project, building is taken care of by Dockerfiles
* Custom Exceptions
  * Used these where I thought it made sense. Kept getting one of them during the normal assignment "pipeline"

## What doesn't work
* Communication between services
  * No matter how much I tinkered with config files, docker-compose, and pom.xml, Processing Service refused to take calls from Booking Service. I have no idea what I did wrong
* RabbitMQ
  * Messages were never serialized properly, so I could never get it to work. On top of that, my computer gave up before I could test simple strings as messages

## What was never tested
* General functionality on the three services
  * CRUD-operations work on Booking/Processing/Shipping, as far as I know. My computer kept shutting down the services during manual testing - 8GB RAM was simply not enough
* Pagination
  * Should work, as far as I know. But then again, with this project, I can't be too sure
* Assignment logic
  * To my knowledge, the code logic is sound. If I got communication and MQ working, I could have tested this

## What I didn't do
* Integration testing
  * This is the big one. I had planned to write tests when I had everything up and working. I never got that far, so I never ended up writing any tests
* Circuit breakers
  * Didn't have time
* Caching
  * Didn't have time