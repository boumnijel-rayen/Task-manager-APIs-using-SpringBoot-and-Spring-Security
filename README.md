
# task manager API


Task manager APIs using SpringBoot and Spring Security


## Tech Stack

**Server:** Spring Boot, MySql


## Server Informations

`server` : `localhost`

`port` : `8080`


## Requirements
 
 #### **.** JDK 17
 #### **.** maven 3.8.6+


## API Reference

#### Login

```http
  GET /login
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. from urlencoded |
| `password` | `string` | **Required**. from urlencoded |

#### Get one User
```http
  GET /user/{id}
```
**You must be logged in as a admin or as the user desired in the link**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |

#### Get users

```http
  GET /user
```
**you must be logged in as a admin**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |

#### Add User
```http
  POST /user
```
**you must be logged in as a admin**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |
| `image` | `File jpg or png` | from data |
| `name` | `string` | from data |
| `email` | `string` | from data |
| `username` | `string` | from data |
| `password` | `string` | from data |

#### Delete User
```http
  DELETE /user/{id}
```
**you must be logged in**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |

#### Add Role
```http
  POST /user/role
```
**you must be logged in as a admin**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |
| `name`      | `string` | from raw |

#### Add Role to User
```http
  POST /user/role/addtouser
```
**you must be logged in as a admin**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |
| `username`      | `string` | from raw |
| `name`      | `string` | from raw |

#### Get Roles
```http
  GET /user/role
```
**you must be logged in as a admin**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |

#### Add Task
```http
  POST /task
```
**You must be logged in as a admin**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |
| `title`      | `string` | from raw |
| `description`      | `string` | from raw |
| `start`      | `date` | from raw |
| `end`      | `date` | from raw |

#### Get Task
```http
  GET /task/{id}
```
**Only the user concerned with this task**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |

#### Delete Task
```http
  DELETE /task/{id}
```
**You must be logged in as a admin**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |

#### Get Tasks by User
```http
  Get /task/user/{id}
```
**You must be logged in as a admin or as the user desired in the link**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |

#### Assign Task to User
```http
  POST /task/{idT}/user/{idU}
```
**You must be logged in as a admin**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |

#### Assign Done to a Task
```http
  PUT /task/{id}
```
**Only the user concerned with this task**

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization`      | `string` | **Required**. should be 'Bearer *token'* |


