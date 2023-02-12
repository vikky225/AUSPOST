package com.au.postcode.lookupservice.ComponentTest;

import com.au.postcode.lookupservice.controller.PostCodeDetailController;
import com.au.postcode.lookupservice.dto.PostCodeDetailsDto;
import com.au.postcode.lookupservice.service.PostCodeDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(PostCodeDetailController.class)
public class ControllerTest{

    private WebTestClient client;

    @MockBean
    private PostCodeDetailsService service;

    @Test
    public void postTest(){

        Mockito.when(service.createPostCodeDetails(Mockito.any())).thenReturn(Mono.just(new PostCodeDetailsDto()));
        this.client
                .post()
                .uri("/api/postcode-detail")
                .accept(MediaType.APPLICATION_JSON)
                .headers(h->h.setBasicAuth("admin","admin"))
                        .bodyValue(new PostCodeDetailsDto())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

    }

    @Test
    public void getTest2(){

        Mockito.when(service.getAllPostCodeDetailsBySuburb(Mockito.any())).thenReturn(Flux.just(new PostCodeDetailsDto()));
        this.client
                .post()
                .uri("/api/postcode-detail/suburb/{suburb}","stkilda")
                .accept(MediaType.APPLICATION_JSON)
                .headers(h->h.setBasicAuth("user","user"))
                .bodyValue(new PostCodeDetailsDto())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }


    @Test
    public void postTest3(){

        Mockito.when(service.getAllSuburbDetailsByPostcode(Mockito.any())).thenReturn(Flux.just(new PostCodeDetailsDto()));
        this.client
                .post()
                .uri("/api/postcode-detail/postcode/{postcode}",42020)
                .accept(MediaType.APPLICATION_JSON)
                .headers(h->h.setBasicAuth("admin","admin"))
                .bodyValue(new PostCodeDetailsDto())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    public void postTest4(){

         Mockito.when(service.getPostCodeDetail(Mockito.any()).thenReturn(Mono.just(new PostCodeDetailsDto())));
        this.client
                .post()
                .uri("/api/postcode-detail/{id}",2)
                .accept(MediaType.APPLICATION_JSON)
                .headers(h->h.setBasicAuth("admin","admin"))
                .bodyValue(new PostCodeDetailsDto())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }


    @Test
    public void testDelete(){
        Mockito.when(service.deletePostCodeDetail(Mockito.any()).thenReturn(Mono.empty()));
        this.client
                .delete()
                .uri("/api/postcode-detail/{id}",1)
                .accept(MediaType.APPLICATION_JSON)
                .headers(h->h.setBasicAuth("admin","admin"))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }


}
