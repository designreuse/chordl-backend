package com.robotnec.chords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Kyrylo Zapylaiev zapylaev@gmail.com
 */
@SpringBootApplication
@EnableSwagger2
public class ChordsApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ChordsApplication.class, args);
    }
}
