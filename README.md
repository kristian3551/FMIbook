# FMIbook

## Summary

RESTful API for managing university administration, providing course resources and information for
currently conducting courses.

## Postman collection

Go to this [link](https://www.postman.com/solar-star-822370/workspace/fmibook/collection/29473314-f868fde0-2e99-40f8-9ff3-2c8ddf5c0043?action=share&creator=29473314&active-environment=29473314-e21328a0-dde9-4042-a11f-0bf43e845f39).
There are urls for different environments. Render is used for hosting
dev environment.

## How to run locally
*Prequisites: You need to have Java 17 or newer installed on your machine*

1. Clone GitHub repo:
```
git clone https://github.com/kristian3551/FMIbook.git
```
2. Open project folder with your favourite IDE and download the required artifacts.
3. Open `application.properties` file located in `src/main/resources/` and make sure
you are running Spring boot with environment variables listed there
(export Firebase config from your Firebase project). Here is a list.
**Make sure you replace "{...}" on each row with your personal data!**
```
PG_DATABASE={name of database, e.g. fmibook}
PG_HOST=dpg-cor884fsc6pc73dl0620-a.frankfurt-postgres.render.com
PG_PORT={your PostgreSQL server port}
PG_USERNAME={your database username}
PG_PASSWORD={your database password}
PORT={your server port}
SECRET_KEY={your secret key for JWT token}
FIREBASE_BUCKET_NAME={your Firebase storage bucket name}
FIREBASE_CLIENT_ID=109388434257282247844
FIREBASE_CLIENT_EMAIL={firebase client email}
FIREBASE_CERT_URL={firebase cert url}
FIREBASE_KEY_ID={firebase private key id}
FIREBASE_PRIVATE_KEY={your private key to Firebase storage}
```
*Before running make sure you have configured database properly and
there is an existing database with this name.*
4. You are all set!


## Technologies

<div style="display: flex; justify-content: center; gap: 5%;margin: 20px 0;">
    <img src="https://www.vectorlogo.zone/logos/springio/springio-ar21.svg"
    alt="Spring Boot"
    width="100"
    style="background-color: white"/>
    <img src="https://www.vectorlogo.zone/logos/firebase/firebase-ar21.svg"
    alt="Firebase"
    width="100"
    style="background-color: white"/>
    <img src="https://www.vectorlogo.zone/logos/postgresql/postgresql-ar21.svg"
    alt="Postgres"
    width="100"
    style="background-color: white"/>
    <img src="https://intellyx.com/wp-content/uploads/2019/08/Render-cloud-intellyx-BC-logo.png"
        alt="Java"
        width="100"
    style="background-color: white"/>
</div>