package com.silva.industries.nameprocessor;

import com.silva.industries.nameprocessor.batch.Processor;
import com.silva.industries.nameprocessor.batch.Reader;
import com.silva.industries.nameprocessor.batch.Writer;

import java.util.stream.Collectors;

public class NameProcessor {

    public static void main(String[] args) {
        var filePath = "batch/coding-test-data.txt";
        var modifiedNamesSize = 25;
        var resultFile = "result.txt";
        if (args.length == 3) {
            filePath = args[0];
            modifiedNamesSize = Integer.parseInt(args[1]);
            resultFile = args[2];
        } else {
            return;
        }
        final var reader = new Reader(filePath);
        final var processor = new Processor(modifiedNamesSize);
        final var writer = new Writer(resultFile);
        nameProcessorFlow(reader, processor, writer);
    }

    public static void nameProcessorFlow(Reader reader, Processor processor, Writer writer) {
        reader.readFile()
                .map(processor::process)
                .collect(Collectors.toList());
        writer.write();
    }
}