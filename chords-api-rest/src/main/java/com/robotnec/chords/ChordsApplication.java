package com.robotnec.chords;

import com.robotnec.chords.config.SwaggerConfiguration;
import com.robotnec.chords.config.WebConfiguration;
import com.robotnec.chords.persistence.PersistenceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Kyrylo Zapylaiev zapylaev@gmail.com
 */
@SpringBootApplication
@EnableSwagger2
@Import({
        PersistenceConfiguration.class,
        SwaggerConfiguration.class,
        WebConfiguration.class
})
public class ChordsApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ChordsApplication.class, args);
    }
}
