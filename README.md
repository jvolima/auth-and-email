# Auth and email

## Features
- [x] Sign up
- [x] Sign in
- [x] Email verification to sign up
- [] Change password by sending email

## Stack
- Java 17
- Spring Boot 3.0.1
- JPA and Hibernate
- PostgreSQL
- Spring Security
- JWT
- Lombok
- JavaMail

## Extra miles
- [] Flyway
- [] Docker
- [] Integration tests

## Routes

### POST `/api/v1/users/sign-up` - Sign up
**REQUEST** <br/>
HEADER: none <br/>
BODY (JSON):

```json
{
  "firstname": "John",
  "lastname": "Doe",
  "email": "johndoe@gmail.com",
  "password": "123456"
}
```
**RESPONSES** <br/>
STATUS: 201 CREATED <br/>

STATUS: 400 BAD REQUEST <br/>
DESCRIPTION: users cannot have the same email <br/>
BODY (JSON):
```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "There is already a user with this email.",
  "instance": "/api/v1/users/sign-up"
}
```

### GET `/api/v1/users/verify` - Verify user account
**REQUEST** <br/>
HEADER: none <br/>
QUERY PARAM: `/api/v1/users/verify?code=example` <br/>
**RESPONSES** <br/>
STATUS: 200 OK <br/>
BODY (JSON):
```json
{
  "message": "Account verified successfully."
}
```
STATUS: 404 NOT FOUND <br/>
DESCRIPTION: the code provided does not belong to any user <br/>
BODY (JSON):
```json
{
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "User not found.",
  "instance": "/api/v1/users/verify"
}
```

### POST `/api/v1/auth/sign-in` - Sign in
**REQUEST** <br/>
HEADER: none <br/>
BODY (JSON):
```json
{
	"email": "johndoe@gmail.com",
	"password": "123456"
}
```

**RESPONSES** <br/>
STATUS: 200 OK<br/>
BODY (JSON):
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdmRvcGFyYWd1YWlAZ21haWwuY29tIiwiaWF0IjoxNjk4NTI5MDMzLCJleHAiOjE2OTg2MTU0MzN9.Siww5ClIEIelSCDVSEGMCLXAUfe-ed7UykZVbxvlAiY"
}
```
STATUS: 400 BAD REQUEST<br/>
DESCRIPTION: account not verified <br/>
BODY (JSON):
```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "Account not verified",
  "instance": "/api/v1/auth/sign-in"
}
```
STATUS: 401 UNAUTHORIZED<br/>
DESCRIPTION: incorrect credentials <br/>
BODY (JSON):
```json
{
  "type": "about:blank",
  "title": "Unauthorized",
  "status": 401,
  "detail": "Bad credentials",
  "instance": "/api/v1/auth/sign-in",
  "access_denied_reason": "Authentication Failure."
}
```