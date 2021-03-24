# countriesairportsrunways

SpringBoot application - Countries, Airports & Runways

## Implementation
A) Back-end controller calls :

1. List of Runways :
	URL : /countries/runways?country=<<countryCode/countryName>>
	DESCRIPTION : Gets list of runways by country code or country name.
   
2. Top 10 Countries
	URL : /countries/airports
	DESCRIPTION : List of countries with maximum number of airports.

B) Access with UI :
On starting up the application (mvn spring-boot:run), you can visit /home to get the UI of this application.

Eg : http://localhost:8080/home