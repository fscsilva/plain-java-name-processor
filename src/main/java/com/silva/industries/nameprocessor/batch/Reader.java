package com.silva.industries.nameprocessor.batch;

import com.silva.industries.nameprocessor.domain.Person;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.xml.validation.Validator;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;


@RequiredArgsConstructor
public class Reader {

    private final String filePath;
    private final Pattern pattern = Pattern.compile("([A-z]+[, ]+[A-z]+[ -- ]+)[A-z]+");

    public Stream<Person> readFile() {
        var classLoader = getClass().getClassLoader();
        return readLine(classLoader.getResourceAsStream(filePath));
    }

    @SneakyThrows
    private Stream<Person> readLine(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .map(this::createPerson)
                .filter(Objects::nonNull);
    }

    private Person createPerson(String line) {
        if (pattern.matcher(line).matches()) {
            var splitFullName = line.split(" -- ");
            var splitNameLastName = splitFullName[0].split(", ");
            return new Person(splitNameLastName[1], splitNameLastName[0]);
        }
       return null;
    }
}