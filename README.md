# RickandMorty

A simple android application implementing clean architecture principals with Rick And Morty API

## Current ongoing changes
  - Change layouts to compose 
  - Call Api through KTOR
  - Change Sharedpref to Datastore
  - modularize dependencies


## Installation:

Make sure you have the following environment ready on your device.

  -Java Runtime Version 17
  -Android Gradle Plugin 8.1.0
  -Gradle Version 8.2
  -Test Device Used to build and test -> Pixel 6a

## Running the application

clone the github repo in your device and please configure your project as above, the project should
run smoothly.

## Architecture and code structure

### api 
this directory contains all the codes for calling API's

### Cache
    this directory contains logic for disk caching image

### this directory replicates data layer 
    contains Repository as single source of truth and viewmodel alone with other model classes

### db
    this dir contains all the codes for storeing api data locally at app startUP

### di 
    the directory that injects diffrent dependencies into other classes when needed

### UI
    contains the UI poriton of the code
    
### Work
    the code that is running at startUp , saving API response and images as cache.


    
    
### The App has been implemented using MVMM pattern, using Hilt For dependency injection.

## App Start-UP

  ### ->Workmanager Initializes

  ### ->GraphQL to fetch most important datas.

  ### -REST API Was not used, beacuse all datas were not useful to fetch at app startUP, increased time to  fetch data at startup.

### -> Saved Response to Database

  ### -Iterating through all the pages till the end

## Database Insertion->

  ### -> On data inserted into database, a observer database changes, and inflates the UI with the database data.

## Pagination->

  ### It's not a good practice to load a ton of data to UI at once. Might cause ANR. 

