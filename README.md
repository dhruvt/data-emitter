# data-emitter
A simple to use data emitter to populate kinesis streams to test a kinesis consumer for gaming workloads
This emitter can also be configured to emit any custom event that you define to test your custom workloads

Usage:

-Create a new Sample<EventType>.java class and populate it with the data you want to emit
-Implement a toJson() method for each custom class
-You can use the Java Faker library to populate fake data. Look at the Faker Documentation for more details
-Modify the DataEmitter.java file to support your new event and then run the sample.


