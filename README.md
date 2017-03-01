timezone app
============

create fat jar

`mvn package`

run app

`java -jar timezone-1.0-SNAPSHOT.jar server ../app.yml`


request

`http://localhost:8080/timezone?timestamp=1488363179&location=40.713956,-75.767577`

response

```json
{

    "timezone": "America/New_York",
    "local_time": {
        "hour": 7,
        "minute": 58,
        "second": 2,
        "nano": 147000000
    },
    "offset": -18000

}
```

update tz data (dst data)

http://www.oracle.com/technetwork/java/javase/tzupdater-readme-136440.html