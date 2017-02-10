Silver Bars code exercise
========================

Prepared by Thang To, <thang.to@outlook.com>


Overview
---------


The solution is designed into several layers:


  * **Model**: provide domain classes, employs Immutables package to create immutable domain models with fluent builder pattern.
  * **Dao**: repository layer that provide CRUD operations, use an in-memory implementation for now and can be replaced by a
   database-backed implementation in the future
  * **Rest**: provide Restful operations

In a typical system there is usually an additional **business service** layer but we don't have enough business logic for one here.

Unit tests are based on JUnit and Mockito.

I also provide a rest UI/documentation based on Swagger/SwaggerUI.



Deployment
------------

Follow these step for IntelliJ:

 1. Create a run configuration for a local Tomcat
 2. On the deployment tab select 'silverbars:Web exploded' and Application context is 'silverbars'
 3. Run the Tomcat configuration, upon the artifact deployment completed a web page is automatically opened to show the
 rest service UI page.



Test using UI
------------

 1. Click on 'Orders' to expands all operations
 2. Expand the 'GET /orders' section then click on 'Try it out!' button to see it returns an empty array in the body
 3. Expand the 'POST /orders' section
 4. Paste the below into the body to add a new order
~~~
{
    "userId": "user1", 
    "quantity": 3.5, 
    "price": 306, 
    "type": "SELL" 
}
~~~ 
 5. Paste the below into the body to add a new order
~~~
{
    "userId": "user2", 
    "quantity": 1.2, 
    "price": 310, 
    "type": "SELL" 
}
~~~ 
 6. Paste the below into the body to add a new order
~~~
{
    "userId": "user3", 
    "quantity": 1.5, 
    "price": 307, 
    "type": "SELL" 
}
~~~
 7. Paste the below into the body to add a new order
~~~
{
    "userId": "user4", 
    "quantity": 2.0, 
    "price": 306, 
    "type": "SELL" 
}
~~~ 
 8. Now go back to the 'GET /orders' operation, select type as 'BUY' and click on 'Try it now!', you would see
~~~
[
  {
    "userId": "[user1,user4]",
    "quantity": 5.5,
    "price": 306,
    "type": "SELL"
  },
  {
    "userId": "user3",
    "quantity": 1.5,
    "price": 307,
    "type": "SELL"
  },
  {
    "userId": "user2",
    "quantity": 1.2,
    "price": 310,
    "type": "SELL"
  }
]
~~~ 
 9. Expand the DELETE /orders and paste the below to the body
~~~
{
    "userId": "user1", 
    "quantity": 3.5, 
    "price": 306, 
    "type": "SELL" 
}
~~~ 
  10. Try 'GET /orders' with type as SELL again, you would see
 ~~~
[
  {
    "userId": "user4",
    "quantity": 2,
    "price": 306,
    "type": "SELL"
  },
  {
    "userId": "user3",
    "quantity": 1.5,
    "price": 307,
    "type": "SELL"
  },
  {
    "userId": "user2",
    "quantity": 1.2,
    "price": 310,
    "type": "SELL"
  }
]
 ~~~ 

 
 
Troubleshooting
---------------

1. Can't make the project

   This probably is caused by the incompatible paths to project dependencies between MacOS and Windows. Try to delele 
   the .idea folder and build again.
   
2. Error message saying 'ClassNotFoundException ...' on deploying the artifact to Tomcat.

   This is probably because some dependencies aren't include in the artifact lib folder, check the artifact configuration
   and add all project dependencies to the artifact.
   

 
### Thank you ###