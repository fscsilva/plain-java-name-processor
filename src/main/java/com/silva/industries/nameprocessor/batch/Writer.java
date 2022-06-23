package com.silva.industries.nameprocessor.batch;

import com.silva.industries.nameprocessor.domain.NameStructures;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class Writer {

    private final String filename;

    public void write() {
        resultStep();
    }

    @SneakyThrows
    public void write(String fileName, String formattedString) {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(formattedString);
        writer.close();
    }

    public void resultStep() {
        buildNameResults();
        var changedNames = changeNames();
        var result = String.format("\nThe names cardinality for full, last, and first names:" +
                        "\nFull names : %s \nLast names : %s \nFirst names : %s \nThe most common last names are: \n%s\n" +
                        "The most common first names are: \n%s \nModified names are: \n%s\n", NameStructures.fullNames.size(),
                NameStructures.lastNames.size(), NameStructures.names.size(), NameStructures.lastNames.entrySet().stream().limit(10)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new)),
                NameStructures.names.entrySet().stream().limit(10)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new)),
                changedNames);
        write(filename, result);
    }

    private List<String> changeNames() {
        var newValues = Arrays.asList(NameStructures.modifiedNames.values().toArray());
        var newKeys = NameStructures.modifiedNames.keySet().toArray();
        Collections.rotate(newValues, 1);
        return IntStream.range(0, newKeys.length).boxed()
                .collect(Collectors.toMap(i -> newKeys[i], newValues::get))
                .entrySet()
                .stream()
                .map(entry -> entry.getValue() + " " + entry.getKey())
                .collect(Collectors.toList());
    }

    private void buildNameResults() {
        NameStructures.names = NameStructures.names.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
        NameStructures.lastNames = NameStructures.lastNames.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
        NameStructures.fullNames = NameStructures.fullNames.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
    }
}
