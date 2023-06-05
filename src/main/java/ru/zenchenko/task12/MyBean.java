package ru.zenchenko.task12;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//Создаем бин
@Component
@PropertySource("classpath:mybean.properties")
public class MyBean {
    List<String> lines;
    Path inPath;
    Path outPath;
    String[] args;

    public MyBean(@Value("${mybean.args}") String[] args) {
        this.args = args;
    }

    @PostConstruct
    public void init() throws IOException {
        System.out.println("reading file...");
        inPath = Paths.get(args[0]);
        if (Files.exists(inPath))
            lines = Files.readAllLines(inPath);
        else
            lines = null;
    }

    @PreDestroy
    public void destroy() throws IOException {
        outPath = Paths.get(args[1]);
        System.out.println("writing file...");
        if (Files.exists(inPath))
            Files.delete(inPath);
        if (!Files.exists(outPath))
            Files.createFile(outPath);
        if (lines != null)
            for (String line : lines) {
                Files.write(outPath, String.valueOf(line.hashCode()).getBytes());
            }
        else
            Files.write(outPath, "null".getBytes());
    }
}
