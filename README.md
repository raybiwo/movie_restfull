# Movie REST API application

This is a assignment test movie rest api of a Xsis

## Requirements

This module requires the following depedencies:

- Java 17
- MySql Database

## Install

    mvn clean install

## Run the app

    java -DDATASOURCE_URL={url-db} -DDATASOURCE_USERNAME={user-db} -DDATASOURCE_PASSWORD={password-db} -jar /target/movie-api-1.0.0.jar

## Run the tests

    mvn test

# REST API

The REST API to the example app is described below.

### API Endpoints
| HTTP Verbs | Endpoints | Action |
| --- | --- | --- |
| POST | /Movies | To create a new Movie |
| GET | /Movies | To retrieve all causes on the platform |
| GET | /Movies/:ID | To retrieve details of a single cause |
| PATCH | /Movies/:ID | To edit the details of a single Movie |
| DELETE | /Movies/:ID | To delete a single Movie |

## Get list of Movie

### Request

`GET /Movies

    curl -i -H 'Accept: application/json' http://localhost:8080/Movies/

### Response

    HTTP/1.1 200 OK
    Date: Sun, 17 Mar 2024 11:42:07 GMT
    Status: 200 OK
    Content-Type: application/json

    {
    "success": true,
    "message": "Movie found",
    "data": [
          {
              "id": 1,
              "title": "Pengabdi Setan 2 Comunion",
              "description": "Film Horor",
              "rating": 7.0,
              "image": "",
              "created_at": "2024-03-17 10:14:51",
              "updated_at": null
          }
      ]
    }

## Create a new Movie

### Request

`POST /Movies/`

    curl -i -H 'Accept: application/json' -d '{"title":"Pengabdi Setan 2 Comunion","description":"Film Horor","rating":7.0,"image":""}' http://localhost:8080/Movies

### Response

    HTTP/1.1 201 Created
    Date: Sun, 17 Mar 2024 11:42:07 GMT
    Status: 200 OK
    Content-Type: application/json

    {
    "success": true,
    "message": "Movie Created Successfully",
    "data": [
          {
              "id": 1,
              "title": "Pengabdi Setan 2 Comunion",
              "description": "Film Horor",
              "rating": 7.0,
              "image": "",
              "created_at": "2024-03-17 10:14:51",
              "updated_at": null
          }
      ]
    }

## Get a detail Movie

### Request

`GET /Movies/:ID`

    curl -i -H 'Accept: application/json' http://localhost:8080/Movies/1

### Response

    HTTP/1.1 200 OK
    Date: Sun, 17 Mar 2024 11:42:07 GMT
    Status: 200 OK
    Content-Type: application/json

    {
    "success": true,
    "message": "Movie Created Successfully",
    "data": [
          {
              "id": 1,
              "title": "Pengabdi Setan 2 Comunion",
              "description": "Film Horor",
              "rating": 7.0,
              "image": "",
              "created_at": "2024-03-17 10:14:51",
              "updated_at": null
          }
      ]
    }

## Get a non-existent Movie

### Request

`GET /Movies/:ID`

    curl -i -H 'Accept: application/json' http://localhost:8080/Movies/9999

### Response

    HTTP/1.1 404 Not Found
    Date: Sun, 17 Mar 2024 11:42:07 GMT
    Status: 404 Not Found
    Content-Type: application/json

    {
      "success": false,
      "message": "Movie not found"
    }

## Change a Movie

### Request

`PATCH /Movies/:ID`

    curl -i -H 'Accept: application/json' -X PUT -d '{"title":"Pengabdi Setan 2 Comunion","description":"Film Seram","rating":7.0,"image":""}' http://localhost:8080/Movies/1

### Response

    HTTP/1.1 200 OK
    Date: Sun, 17 Mar 2024 11:42:07 GMT
    Status: 200 OK
    Content-Type: application/json

    {
    "success": true,
    "message": "Movie Updated Successfully",
    "data": [
          {
              "id": 1,
              "title": "Pengabdi Setan 2 Comunion",
              "description": "Film Seram",
              "rating": 7.0,
              "image": "",
              "created_at": "2024-03-17 10:14:51",
              "updated_at": "2024-03-17 11:14:51"
          }
      ]
    }

## Delete a Movie

### Request

`DELETE /Movies/:ID`

    curl -i -H 'Accept: application/json' -X DELETE http://localhost:8080/Movies/1

### Response

    HTTP/1.1 200 OK
    Date: Sun, 17 Mar 2024 11:42:07 GMT
    Status: 200 OK
    Content-Type: application/json

    {
      "success": true,
      "message": "Movie Deleted Successfully"
    }


## Try to delete same Movie again

### Request

`DELETE /Movies/:ID`

    curl -i -H 'Accept: application/json' -X DELETE http://localhost:8080/Movies/1

### Response

    HTTP/1.1 404 Not Found
    Date: Thu, 24 Feb 2011 12:36:32 GMT
    Status: 404 Not Found
    Content-Type: application/json

    {
      "success": false,
      "message": "Movie not found"
    }
