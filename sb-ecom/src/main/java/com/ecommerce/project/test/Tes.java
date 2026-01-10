package com.ecommerce.project.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class Tes {
    @Bean
    CommandLineRunner h2Check(DataSource dataSource){
        return args -> {
            System.out.println("DB URL ="+dataSource.getConnection().getMetaData().getURL());
        };
    }
}
