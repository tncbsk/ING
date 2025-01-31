
# Loan Management System


## Overview
This Loan Management System API is designed for a bank to allow employees to manage loans for customers.
It includes endpoints for creating loans, listing loans, listing installments, and paying off loans.
The system enforces necessary checks and ensures that all financial transactions are processed according to the business rules.

## End Points
* ###### Create Loan:  Create a new loan for a given customer, amount, interest rate and number of installments
  * URL: [Create Loan](http://localhost:8081/api/loan/create)
  * Method: POST
  * Request Body:
    ```
     {
        "customerId": 1,
        "amount": 10000,
        "interestRate": 0.2,
        "numberOfInstallments": 12
     }  
       
  * Response Body:
    ```
    {
    "message": "Loan Created",
    "status": "OK"
    }

  * Validation Rules:
    
    1)Customer credit limit must be enough to cover the loan amount.
    
    2)Allowed installment numbers: 6, 9, 12, 24.
    
    3)Interest rate must be between 0.1 and 0.5.
    
    4)The total loan amount is calculated as amount * (1 + interestRate).
    
    5)Installments are due on the first day of the next month.
    
    
* ###### List Loans:  List loans for a given customer
    * URL: [List Loans](http://localhost:8081/api/loan/list-loan)
    * Method: GET
    * Request Body:
      ```
       {
          "customerId": 1,
          "numberOfInstallments": 12,
          "isPaid":"true"
       }  

    * Response Body:
      ```
         [
          {
          "loanId": 1,
          "amount": 10000,
          "interestRate": 0.2,
          "numberOfInstallments": 12,
          "isPaid": false,
          "customerId": 1
          }
         ]

* ###### List Installments: List installments for a given loan
    * URL: [List Installments](http://localhost:8081/api/loan/list-installment/{id})
    * Method: GET
    * Request Body:
      ```
       {
          "loanId":1
       }  

    * Response Body:
      ```
      [
          {
          "installmentId": 1,
          "amount": 1000,
          "dueDate": "2025-02-01",
          "isPaid": false
          },
          {
          "installmentId": 2,
          "amount": 1000,
          "dueDate": "2025-03-01",
          "isPaid": false
          }
      ]
      

* ###### Pay Loan: Pay installment for a given loan and amount
    * URL: [Pay Loan](http://localhost:8081/api/loan/pay)
        * Method: POST
        * Request Body:
          ```
           {
              "loanId": 1,
              "amount": 2500
           }  
    
        * Response Body:
          ```
          {
            "paidInstallmentCount": 2,
            "totalAmountPaid": 2000,
            "loanPaid": false,
            "status": "OK"
          }

## Technologies

* ###### JDK 17
* ###### H2 DB
* ###### Spring Boot 
* ###### Maven
* ###### Mockito

##  Loan Managment System Backend Application

All endpoints runs with authentication.
Authentication information locate in SecurityConfig.java

#Database Tables

* [Database URL](http://localhost:8081/h2-console/login.jsp)

"customer" : {
    "id": "Long",
    "name": "String",
    "surname": "String"
    "creditLimit" : "BigDecimal"
    "usedCreditLimit" :"BigDecimal"
}

"loan" : {
    "id": "Long",
    "customer": "Customer",
    "loanAmount": "BigDecimal",
    "numberOfInstallment": "BigDecimal"
    "createDate" : "Date"
    "isPaid" : "Boolean"
}

"loan_installment" : {
    "id": "Long",
    "loan": "loan",
    "amount": "BigDecimal",
    "paidAmount":"BigDecimal",
    "dueDate" : "Date",
    "paymentDate" :"Date",
    "isPaid" : "Boolean"
}



## Test Coverage

Controller Coverage % 100 , Service Coverage 81


##Installation & Setup


* Install Dependencies on Maven
  * Execute : 
    ``` 
    mvn clean install
    
* Run Dockerfile
    * There are 3 different profile. Test , Prod and Normal .
      * Normal Profile port : 8081
      * Test Profile port :  8082
      * Prod Profile port :  8083
    * Dockerfile has profile for test. After run the docker file application port will be 8082
    
* Run the Application
  * Execute :
    ```
    mvn spring-boot:run

  * Application port will be 8081
    

* When the application starts, Random Customers in the import.sql file under Resources are automatically added to the Customer table.


* You can check with * [Database URL](http://localhost:8081/h2-console/login.jsp). 
    * There is no any password on Database
    

* Can test all possible cases with "ING.postman_collection.json" file under the resources folder.


 
              

