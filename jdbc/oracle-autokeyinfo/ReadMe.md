## Reproducer for problems with id generation with new Oracle drivers.

To run with ojdbc11, demonstrating the problem:
```
./mvnw clean verify
```
To run with ojdbc8, demonstrating the working variant:
```
./mvnw clean verify -Dojdbc8
```
  