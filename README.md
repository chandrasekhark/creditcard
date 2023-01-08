## Credit Card Application
## Environment:
- Java version: 11
- Maven version: 3.*
- Spring Boot version: 2.7.7.RELEASE

## Read-Only Files:
- src/test/*

## Data:
Description of an credit card data JSON object:

```json
{
   "id": "the unique ID of the event (Integer)",
   "name": "the name of the card holder (String)",
   "cardNumber": "credit card numnber (Long)",
   "balance": "credit card balance (BigDecimal)",
   "limit": "Credit card limit (BigDecimal)"
}
```

Example of an credit card data JSON object:
```json
{
    "type": "Allice",
    "cardNumber": 1111222233334444,
    "balance": 0,
    "limit": 1000
}
```

## Requirements:

`POST` request to `/creditcard` :
* creates a new credit card
* expects a JSON credit card object without an id property as the body payload. You can assume that the given object is always valid.
* adds the given credit card object to the collection of events and assigns a unique integer id to it. The first created event must have id 1, the second one 2, and so on.
* Card numbers should be validated using Luhn 10
* New cards starts with £0 balance
* the response code is 201 and the response body is the created credit card object, including its id

`GET` request to `/creditcards`:
* returns a collection of all credit cards
* the response code is 200, and the response body is an array of all credit cards ordered by their ids in increasing order

The project by default supports the use of the H2 database.

## Commands
- run: 
```bash
mvn clean package; java -jar target/creditcard-0.0.1-SNAPSHOT.jar
```
- install: 
```bash
mvn clean install
```
- test: 
```bash
mvn clean test
```
