
package com.au.postcode.lookupservice.controller;

import com.au.postcode.lookupservice.dto.PostCodeDetailsDto;
import com.au.postcode.lookupservice.entity.PostCodeDetail;
import com.au.postcode.lookupservice.service.PostCodeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/api/postcode-detail")
public class PostCodeDetailController {

    @Autowired
    private PostCodeDetailsService postCodeDetailsService;



   // Assuming no constraints in DB for postcode and suburb ,
   // if it is their than we need to check if records does exist before insert.
   @PreAuthorize("hasRole('ADMIN')")
   @PostMapping(value="post" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PostCodeDetailsDto> createPostCodeDetails(@RequestBody Mono<PostCodeDetailsDto> postCodeDetailsDtoMono){

       return this.postCodeDetailsService.createPostCodeDetails(postCodeDetailsDtoMono);
   }



    @PreAuthorize("hasRole('USER')")
    @GetMapping( "/suburb/{suburb}")
    public Flux<PostCodeDetailsDto> getAllPostCodeDetailsBySuburb(@PathVariable("suburb") String suburb) {
        return this.postCodeDetailsService.getAllPostCodeDetailsBySuburb(suburb);


    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping( "/postcode/{postcode}")
    public Flux<PostCodeDetailsDto> getAllSuburbDetailsByPostcode(@PathVariable("postcode") Integer postcode) {
        return this.postCodeDetailsService.getAllSuburbDetailsByPostcode(postcode);


    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping( "/postcode/{postcode}/{suburb}")
    public Flux<PostCodeDetailsDto> getAllSuburbDetailsByPostcode(@PathVariable("postcode") Integer postcode,@PathVariable("suburb") String suburb) {
        return this.postCodeDetailsService.getAllDetailsByPostcodeAndSuburb(postcode,suburb);


    }

    @PreAuthorize("hasRole('USER')")
    // using stream to load faster in browser as compare to json
    @GetMapping( value="all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PostCodeDetailsDto> getALL() {
        return this.postCodeDetailsService.getALL();

   }

    @PreAuthorize("hasRole('USER')")
   @GetMapping(value ="{id}" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<PostCodeDetail> getPostCodeDetailsById(@PathVariable int id) {
        return this.postCodeDetailsService.getPostCodeDetail(id);
   }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping( value = "{id}" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<PostCodeDetail> updatePostCodeDetails(@PathVariable int id,@RequestBody Mono<PostCodeDetail> postCodeDetailsDtoMono) {
        return this.postCodeDetailsService.updatePostCodeDetail(id,postCodeDetailsDtoMono);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping( value = "{id}" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Void> deletePostCodeDetails(@PathVariable int id) {
        return this.postCodeDetailsService.deletePostCodeDetail(id);
    }




}

