# Credorax - Payment Gateway

Take home assignment for a Java/Spring developer.

The detailed requirements are found in [ASSIGNMENT.md](ASSIGNMENT.md).

**Validation**
chain to responsibilities for the validation
    all fields should be present;
    amount should be a positive integer;
    email should have a valid format;
    PAN should be 16 digits long and pass a Luhn check;
    expiry date should be valid and not in the past.
    will be manage by the TransactionWriteProcessor service

**Processor**
    chain to responsibilities for encode sensitive data
    will be manage by the TransactionWriteValidator service before saving the transaction

**PCI Restrictions**
    chain to responsibilities for decode sensitive data
    will be manage by the TransactionReadValidator service while reading the transaction from the persistence layer

**Persistence**
    spring with H2 database can be used by decided to use a HashMap

**Audit**
    When saving a transaction, a message will be published and Listener (in AuditService) will catch it and will write to a file.
    (will be easy to add more action after save)

To run the project run the next command from your "java" bin direcory:
_**java -Dserver.port=8000 -jar {Jar file location full name}**_
for example:
_java -Dserver.port=8000 -jar C:\GitHub\Credorax\java-paymentgateway-avishayzamir\payment-gateway\target\payment-gateway-1.0-SNAPSHOT.jar_

then, go to:
http://localhost:8000/swagger-ui.html

Under 'payment-gateway-rest-controller' you may find the API's:
1. submit
2. retrive
