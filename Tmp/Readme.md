```sh
docker run --name postgres-container -e POSTGRES_PASSWORD=tmpPass -e POSTGRES_USER=postgres -e POSTGRES_DB=scoutingdb -p 5432:5432 -d postgres:13 
```

```sh
java -jar scouting-service-0.0.1-SNAPSHOT.jar
```
