package com.au.postcode.lookupservice.IntegrationTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class EndPointInegrationTest {
    @Autowired
    private WebTestClient client;


    @Test
    public void testGetAllEndpoint(){

       this.client
                .get()
                .uri("/api/postcode-detail/all")
                .exchange()
                .expectStatus().is2xxSuccessful()
               .expectBody()
               .consumeWith(res-> Assertions.assertThat(res.getResponseBody()).isNotNull());

    }


    @Test
    public void testDelete(){

        this.client
                .delete()
                .uri("/api/postcode-detail/{id}",2)
                .exchange()
                .expectStatus().is2xxSuccessful();

    }


    @Test
    public void testGetSuburbEndPoint(){

        this.client
                .get()
                .uri("/api/postcode-detail/suburb/{suburb}" ,"xxx")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(res-> Assertions.assertThat(res.getResponseBody()).isNotNull());

    }


}
