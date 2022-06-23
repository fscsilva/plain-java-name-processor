package com.silva.industries.nameprocessor.batch;


import com.silva.industries.nameprocessor.domain.NameStructures;
import com.silva.industries.nameprocessor.domain.Person;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class Processor {

    private final int modifiedNamesSize;

    public Person process(Person person) {
        validateName(person);
        validateLastName(person);
        validateFullName(person);
        validateModifiedName(person);
        return person;
    }

    private void validateName(Person person) {
        var nameCount = NameStructures.names.get(person.getName());
        if (Objects.nonNull(nameCount)) {
            NameStructures.names.put(person.getName(), nameCount + 1);
        } else {
            NameStructures.names.put(person.getName(), 1);
        }
    }

    private void validateFullName(Person person) {
        var fullName = person.getName() + person.getLastName();
        var nameCount = NameStructures.fullNames.get(fullName);
        if (Objects.nonNull(nameCount)) {
            NameStructures.fullNames.put(fullName, nameCount + 1);
        } else {
            NameStructures.fullNames.put(fullName, 1);
        }
    }

    private void validateLastName(Person person) {
        var lastNameCount = NameStructures.lastNames.get(person.getLastName());
        if (Objects.nonNull(lastNameCount)) {
            NameStructures.lastNames.put(person.getLastName(), lastNameCount + 1);
        } else {
            NameStructures.lastNames.put(person.getLastName(), 1);
        }
    }

    private void validateModifiedName(Person person) {
        if (NameStructures.modifiedNames.size() < modifiedNamesSize) {
            if (!NameStructures.modifiedNames.containsKey(person.getName()) &&
                    !NameStructures.modifiedNames.containsValue(person.getLastName())) {
                NameStructures.modifiedNames.put(person.getName(), person.getLastName());
            }
        }
    }
}
