timezone app
============

Our timezone app turns your location and timestamp into timezone and local time. Thus, if you need local time in your application, just ask GraphHopper timezone. It is microservice you can run wherever you like.

###Build and run app

create fat jar

`mvn package`

run app

`java -jar timezone-1.0-SNAPSHOT.jar server ../app.yml`

####Request app

You need to specify two parameters, (Unix) timestamp and location (lat,lon), and you will be provided with the according timezone, local time and offset to UTC. Local time and offset consider daylight saving time (dst).

Input:

Parameter | Description
:------|:-----
timestamp | Unix timestamp
location | latitude, longitude

Output:

Name | Description
:------|:-----
timezone | time zone id as defined here: http://efele.net/maps/tz/world/
local_time | local time considering daylight saving time in hour, minute, second and nano
offset | offset in relation to UTC considering daylight saving time (in seconds)

###Example 

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

###TZ data
Here it is described how you can update tz data (especially dst data) for your java environment.

http://www.oracle.com/technetwork/java/javase/tzupdater-readme-136440.html
