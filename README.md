### Default value properties


- modified-names: quantity of N for the modified list of names

  default: (25)

- file.path: path where the file will be read

  default: /batch/coding-test-data.txt

- file.result-name: name of the file with all the results

  default: result.txt

### Run Local

```
mvn clean install exec:java
```
With the wrapper:
```
./mvnw clean install exec:java
```

### Run overriding default arguments

- args[0]: file.path 
- args[1]: modified-names
- args[2]: file.result-name
```
mvn clean install exec:java -Dexec.args="batch/other-coding-test-data.txt 10 other-result.txt"
```