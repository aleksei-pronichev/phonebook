# Start application
You need:  
- java 11
- maven

Open project path with command line
Than you can use spring-boot-plugin (maven)
````
spring-boot:run -f pom.xml
````
Application is ready

You also can do it with standard maven packaging
````
mvn package -f pom.xml
````
After testing and packaging, you can see target folder, go to that folder with command line  
Target include our application - jar file.
Start it with command (jar filename may be different, check it before start)
````
java -jar phonebook-0.0.1-SNAPSHOT.jar
````

** Application use inmemory database  
** For using another, you should edit files:  
settings:
````
    src/main/resources/application.yaml
````
dependencies (jdbc-connectors)  
````
    pom.xml
````
# Rest Api

## Users
The main page for user:  
{hostname}/api/v1/users/

json example for transfer:
````
    {
        "id": 1,
        "login": "Bill"
    }
````
Commands:

Request | Type | address | description | return result
--- | ---  | --- | ---  | ---
get all users | GET | ./api/v1/users/ | - | multi
get user by id | GET | ./api/v1/users/{id} | {id} - user's identifier | single
add user |  POST | ./api/v1/users/ | json, id should be null, but not necessary, the system will assign a new | single
edit user | PUT | ./api/v1/users/ | json | single
delete user | DELETE | ./api/v1/users/{id} | {id} - user's identifier | only http-code
search user | GET | ./api/v1/users/find?login={user} | {user} - user's login or part of it | multi

## Notes
The main page for user:  
{hostname}/api/v1/users/

json example for transfer:
````
    {
        "id": 1,
        "userId": 1,
        "name": "Test Name",
        "phone": "3466"
    }
````
Commands:

Request | Type | address | description | return result
--- | ---  | --- | ---  | ---
get note by id | GET | ./api/v1/notes/{id} | {id} - note's identifier | single
add note |  POST | ./api/v1/notes/ | json, id should be null, but not necessary, the system will assign a new | single
edit note | PUT | ./api/v1/notes/ | json | single
delete note | DELETE | ./api/v1/notes/{id} | {id} - note's identifier | only http-code
get all notes by user | GET | ./api/v1/notes/user/{user_id} | {user_id} - user's identifier  | multi
search note | GET | ./api/v1/notes/user/{user_id}/find?phone={phone} | {user_id} - user's identifier, {phone} - phone or part of it | multi

