# apitrary.jar [![Build Status](https://travis-ci.org/denisneuling/apitrary.jar.png?branch=master)](https://travis-ci.org/denisneuling/apitrary.jar)

Maven parent project, which contains the Java [apitrary Backend as a Service](http://apitrary.com/) client and orm modules.


### Getting Started
---

The apitrary.jar is broken down into 3 modules: apitrary-api, apitrary-api-client and apitrary-orm. 

* The apitrary-api is a plain pojo implementation of the apitrary api schema.
* The apitrary-api-client uses the apitrary-api to fire requests against the apitrary API. 
* The apitrary-orm module is devided into 2 submodules. 
    * The apitrary-orm-core module brings everything to you to CRUD your entities, map your business POJOs to JSON and vice versa and fire higher cascading operations. 
    * The apitrary-orm-codec module provides en- and decoding of your entity's properties (ex. images to base64 and vice versa).

The latest apitrary artifacts are published to maven central. Bringing apitrary into your project should be as simple as adding the following to your maven pom.xml file:

```xml
  <dependencies>
    <dependency>
      <groupId>com.apitrary</groupId>
      <artifactId>apitrary-api-client</artifactId>
      <version>1.0</version> <!-- or whatever the latest version is -->
    </dependency>
  </dependencies>
```


### Requirements
---

You will need following:

- [Java JRE 1.6+](http://www.oracle.com/technetwork/java/javase/downloads) ([Download page](http://www.oracle.com/technetwork/java/javase/downloads/jre6-downloads-1637595.html))
- [Java JDK 1.6+](http://www.oracle.com/technetwork/java/javase/downloads) ([Download page](http://www.oracle.com/technetwork/java/javase/downloads/jdk6-downloads-1637591.html))
- [Apache Maven 2.0+](http://maven.apache.org/)

Install via your package manager, otherwise get the libraries.

Descriptions can be found here:

For Maven: [Building a Project with Maven](http://maven.apache.org/run-maven/index.html)


### How to setup this project?
---

1. Clone the [apitrary.jar](https://github.com/denisneuling/apitrary.jar.git) project

        $ git clone https://github.com/denisneuling/apitrary.jar.git

2. Change into the directory:

        $ cd apitrary.jar

3. Run maven:

        $ mvn install # fetch all dependencies and build the project

4. Start hacking.


### Which modules does this project contain?
---

* [apitrary-api](https://github.com/denisneuling/apitrary.jar/tree/master/apitrary-api/)
* [apitrary-api-client](https://github.com/denisneuling/apitrary.jar/tree/master/apitrary-api-client/)
* [apitrary-orm](https://github.com/denisneuling/apitrary.jar/tree/master/apitrary-orm/)


### Interested into somewhat like build stability?
---

* Have a look at [Apitrary.jar at Travis CI](https://travis-ci.org/denisneuling/apitrary.jar)


### What about interface documentation? - Javadoc.
---

* [apitrary-api Javadoc](http://denisneuling.github.com/apitrary.jar/apitrary-api/)
* [apitrary-api-client Javadoc](http://denisneuling.github.com/apitrary.jar/apitrary-api-client/)
* [apitrary-orm-core Javadoc](http://denisneuling.github.com/apitrary.jar/apitrary-orm/apitrary-orm-core/)
* [apitrary-orm-codec Javadoc](http://denisneuling.github.com/apitrary.jar/apitrary-orm/apitrary-orm-codec/)

_Note: The Javadoc will be exported once a release, so it might differ from the current master branch implementation._