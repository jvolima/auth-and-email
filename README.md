# Auth and email

## Features

- [x] Sign up
- [x] Sign in
- [x] Email verification to sign up
- [x] Change password by sending email

## Stack

- Java 17
- Spring Boot 3.0.1
- JPA and Hibernate
- PostgreSQL
- Spring Security
- JWT
- Lombok
- JavaMail
- JUnit
- Flyway
- Docker

## Extra miles

- [x] Token and expiration date in the user table to change the password
- [x] Flyway
- [x] Docker
- [x] Integration tests

## Problem with credentials

As I initiated integration tests for my API, I initially possessed a .env-dev file but realized the absence of a .env-tests file. Promptly, I created the file, populated it with essential data, executed the tests, and seemingly committed the changes, assuming all was well. It was only during the push to the origin that the gravity of my oversight dawned on me—I had neglected to include .env-tests in the .gitignore, inadvertently exposing sensitive credentials in the commit history.

Faced with this predicament, I felt at a loss until I delved into potential solutions and discovered the BFG Repo-Cleaner. This tool became my salvation; it enabled me to retrospectively alter the commit history, rectifying the inadvertent exposure of credentials. This experience served as a valuable lesson, impressing upon me the importance of meticulous version control practices.

Moving forward, I've gained a profound understanding from this mistake. Should a similar scenario arise in a production environment, I am now equipped with the knowledge to calmly and effectively address such issues, ensuring the security and integrity of sensitive information.

### How did I solve this problem

1 - I downloaded the .jar file from [BFG Repo-Cleaner](https://rtyley.github.io/bfg-repo-cleaner/) and placed it in the same folder as the project

2 - I deleted the file that had the commit history credentials with the command:

```bash
  java -jar bfg-1.14.0.jar --delete-files env-tests.properties
```

3 - I ran this command that is in the BFG documentation:

```bash
  git reflog expire --expire=now --all && git gc --prune=now --aggressive
```

4 - and finally I was able to upload to the origin without the leaked credentials

```bash
  git push
```

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

### GET `/api/v1/users/verify/{verificationToken}` - Verify user account

**REQUEST** <br/>
HEADER: none <br/>
ROUTE PARAM: `/api/v1/users/verify/example` <br/>
**RESPONSES** <br/>
STATUS: 200 OK <br/>
BODY (JSON):

```json
{
  "message": "Account verified successfully."
}
```

STATUS: 404 NOT FOUND <br/>
DESCRIPTION: the token provided does not belong to any user <br/>
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

### GET `/api/v1/users/forgot-password` - Send email to user change its password

**REQUEST** <br/>
HEADER: none <br/>
BODY (JSON):

```json
{
  "email": "johndoe@gmail.com"
}
```

**RESPONSES** <br/>
STATUS: 200 OK <br/>
BODY (JSON):

```json
{
  "message": "Email to change password has been sent."
}
```

STATUS: 400 BAD REQUEST <br/>
DESCRIPTION: account not verified <br/>
BODY (JSON):

```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "Account not verified, check your email.",
  "instance": "/api/v1/users/forgot-password"
}
```

STATUS: 404 NOT FOUND <br/>
DESCRIPTION: the email provided does not belong to any user <br/>
BODY (JSON):

```json
{
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "User not found.",
  "instance": "/api/v1/users/forgot-password"
}
```

### PATCH `/api/v1/users/change-password` - Change user password

**REQUEST** <br/>
HEADER: none <br/>
BODY (JSON):

```json
{
  "changePasswordToken": "token_example",
  "newPassword": "654321"
}
```

**RESPONSES** <br/>
STATUS: 200 OK <br/>
BODY (JSON):

```json
{
  "message": "Password changed successfully."
}
```

STATUS: 400 BAD REQUEST <br/>
DESCRIPTION: token expired <br/>
BODY (JSON):

```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "Change password token has expired..",
  "instance": "/api/v1/users/change-password"
}
```

STATUS: 404 NOT FOUND <br/>
DESCRIPTION: the token provided does not belong to any user <br/>
BODY (JSON):

```json
{
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "User not found.",
  "instance": "/api/v1/users/change-password"
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
