# SpaceApi
SpaceDelta private API for getting stuff when it cannot be accessed directly

Uses GraphQL for query processing

#### Current endpoints and their values
* stat
    * onlinePlayers - Last recorded online player count
    * uniquePlayers - Last recorded unique player count
     
##### Example
*Request (GraphQL query)*
```mysql based
{
    stat {
        onlinePlayers
        uniquePlayers
    }
}
```
*Response (JSON)*
```json
{
    "data": {
        "stat": {
            "onlinePlayers": 57,
            "uniquePlayers": 17280
        }
    }
}
```