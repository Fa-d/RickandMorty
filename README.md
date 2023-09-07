# RickandMorty

A simple android application implementing clean architecture principals with Rick And Morty API

Installation:
Make sure you have the following environment ready on your device.

-Java Runtime Version 17
-Android Gradle Plugin 8.1.0
-Gradle Version 8.2
-Test Device Used to build and test -> Pixel 6a





The App has been implemented using MVMM pattern, using Hilt For dependency injection.

App Start-UP
->Workmanager Initializes
->GraphQL to fetch most important datas.
-REST API Was not used, beacuse all datas were not useful to fetch at app startUP, increased time to
fetch data at startup.
-> Saved Response to Database
-Iterating through all the pages till the end

Database Insertion->
-> On data inserted into database, a observer database changes, and inflates the UI with the
database data.

Pagination->
It's not a good practice to load a ton of data to UI at once. Might cause ANR. 

