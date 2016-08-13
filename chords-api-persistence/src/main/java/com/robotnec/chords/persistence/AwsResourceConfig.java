package com.robotnec.chords.persistence;

import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/awsconfig.xml")
@EnableRdsInstance(databaseName = "chordsdb",
                   dbInstanceIdentifier = "rds-chords",
                   password = "chords-api-375")
public class AwsResourceConfig {
}