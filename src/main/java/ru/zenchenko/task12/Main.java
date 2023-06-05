package ru.zenchenko.task12;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        String line = "mybean.args="+String.join(",", args);
        Files.write(Path.of("src/main/resources/mybean.properties"),line.getBytes("UTF-8"));

        //берем бин
        MyBean bean = context.getBean("myBean", MyBean.class);

        context.close();
    }
}
