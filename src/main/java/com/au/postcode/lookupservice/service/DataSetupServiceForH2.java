package com.au.postcode.lookupservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

/*
This is data Setup Service for H2 in Memory DB
Initial run of application with few records only it will create table and insert records from postcodes.sql scripts
Need to enable dependency of H2 in POM and update h2 url
and  disable r2dbc dependency and r2 url in application.properties file
Please enable @service as well if doing locally
 */

//@Service
public class DataSetupServiceForH2 implements CommandLineRunner {

    @Value("classpath:h2/postcodes.sql")
    private Resource postcodes;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;

    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(postcodes.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(query);


        this.entityTemplate.getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();

    }
}
