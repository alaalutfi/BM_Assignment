# BM_Assignment

This is Rest API for the Account Statement
when you want to access it login page will be shown to log in either using admin/admin or user/user.

The Database file (accountsdb) should be located in the following path: E:\BM-JavaTest\

to run the application make sure you have java 15 installed and latest maven, then navigate to the directory of the application and run mvn spring-boot:run

THe link to access account statement is
http://localhost:8080/accountStatement/{id} where id the account id

ex:http://localhost:8080/accountStatement/3

you can add request parameters fromDate , toDate , fromAmnt,toAmnt to do filteration . when you add all the parameters the link will look like this ex:

localhost:8080/accountStatement/3?fromDate=09.05.2020&toDate=01.01.2021&fromAmnt=100&toAmnt=500

please note that when you need admin previliges to add request parameters or you will have unauthrozed exception 401 error.

the below link for logout
localhost:8080/logout

if you didn't logout , the session will timeout after 5 minutes.


