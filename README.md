# Andre Brito Fonseca

## Skills in Spring, Data manipulation and JAX RS
So, this is the branch for the test proposed.
I upgraded Spring to version 2 >, so it ended up breaking a lot of things. I changed a little bit the package strucutre and the name of the class that tests the endpoints (now there's a CityResourceTest and a CountryResourceTest). I believe that this is a better way because you can tell what the class aims to test simply by reading its name.

## Exercise
- Entities were created
- Used SpringData (only the repository I believe) to the Data manipulation layer
- There's a GET method to retrieve the list of cities (there's a screenshot of a Postman GET request)
- Ended up creating a simple rest service to load data into the database. 
Feel free to modify the files included, upgrade frameworks, add or remove packages, in every aspect you want. Just check the note regarding JUnit tests below.

## Results
You can boot the application using 

    mvn spring-boot:run
    
When typed in the browser the URI, open a browser and type :

    http://localhost:8090/rest/cities?country=Uni

We get a JSON containing all the cities in the database (above screenshot).
[![JSON in Browser](https://raw.githubusercontent.com/andrebrito/spring-jpa-jersey/master/opened-in-browser.png)](https://raw.githubusercontent.com/andrebrito/spring-jpa-jersey/master/opened-in-browser.png)]


## Unit tests
The lines requested were uncommented and a lot of tests were added.

## Challenge

According to JaCoCo, got 90% of coverage.

[![Test Coverage in Eclipse](https://raw.githubusercontent.com/andrebrito/spring-jpa-jersey/master/coverage.png)](https://raw.githubusercontent.com/andrebrito/spring-jpa-jersey/master/coverage.png)]

[![Test Coverage in HTML](https://raw.githubusercontent.com/andrebrito/spring-jpa-jersey/master/coverage-html.png)](https://raw.githubusercontent.com/andrebrito/spring-jpa-jersey/master/coverage-html.png)]

## Feedback
If possible, I would like some feedback about my code. Gotta say that a lot of things that I've tried were unsuccessful, but I believe that the code is ok. Thanks.
**If you guys see some commits using 'andrebritofonseca' and other 'andrebrito', please, never mind, it's other computer that I was using.**
