# spring-boot
## Technical:

1. Framework: Spring Boot v3.0.0 (Spring security 6.0.0)
2. Java 19
3. Thymeleaf
4. Bootstrap v.4.3.1


## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Implement a Feature
1. Create mapping domain class and place in package com.nnk.springboot.domain
2. Create repository class and place in package com.nnk.springboot.repositories
3. Create controller class and place in package com.nnk.springboot.controllers
4. Create view files and place in src/main/resource/templates

## Write Unit Test
1. Create unit test and place in package com.nnk.springboot in folder test > java

## Security
1. Create user service to load user from  database and place in package com.nnk.springboot.services
2. Add configuration class and place in package com.nnk.springboot.config



![](md/Diapositive1.PNG)
![](md/Diapositive2.PNG)
![](md/Diapositive3.PNG)
![](md/Diapositive4.PNG)
![](md/Diapositive5.PNG)
![](md/Diapositive6.PNG)
![](md/Diapositive7.PNG)
![](md/Diapositive8.PNG)
![](md/Diapositive9.PNG)
![](md/Diapositive10.PNG)
![](md/Diapositive11.PNG)
![](md/Diapositive12.PNG)
![](md/Diapositive13.PNG)