package com.robotnec.chords;

import com.robotnec.chords.persistence.PersistenceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author Kyrylo Zapylaiev zapylaev@gmail.com
 */
@SpringBootApplication
@Import(PersistenceConfiguration.class)
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ChordsApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ChordsApplication.class, args);
    }
}
