package com.au.postcode.lookupservice.service;

import com.au.postcode.lookupservice.entity.PostCodeDetail;
import com.au.postcode.lookupservice.repository.PostCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/*
This is data Setup Service for postgress which is running as container
Initial run of application , it will create table and insert records from postcodes.sql
 */

@Service
public class DataSetupServicePostgres implements CommandLineRunner {
    @Autowired
    private PostCodeRepository repository;


    @Autowired
    private R2dbcEntityTemplate entityTemplate;


    @Value("classpath:postgres/schema.sql")
    private Resource resource;



    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(query);
        this.entityTemplate.getDatabaseClient()
                .sql(query)
                .then()
                .doOnNext(r->System.out.println(r))
                .subscribe();

    }
}
