package com.au.postcode.lookupservice.service;

import com.au.postcode.lookupservice.dto.PostCodeDetailsDto;
import com.au.postcode.lookupservice.entity.PostCodeDetail;
import com.au.postcode.lookupservice.util.CacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostCodeDetailsService {

    @Autowired
    private CacheTemplate<Integer, PostCodeDetail> cacheTemplate;

  // getPostCodeDetails by primary Key id
   public Mono<PostCodeDetail> getPostCodeDetail(int id){
       return this.cacheTemplate.get(id);
   }

   // updatePostCodeDetailBy given id
   public Mono<PostCodeDetail> updatePostCodeDetail(int id, Mono<PostCodeDetail> postCodeDetailMono)
   {
       return postCodeDetailMono.flatMap(p->this.cacheTemplate.update(id,p));
   }

   // delete Post Code record by id
   public Mono<Void> deletePostCodeDetail(int id){
     return this.cacheTemplate.delete(id);
   }


 // get Postcodes from suburb
    public Flux<PostCodeDetailsDto> getAllPostCodeDetailsBySuburb(String suburb) {
        System.out.println("Inside seerice by suburb");
                return this.cacheTemplate.getPostcodeBySuburb(suburb);
    }

// get Suburbs from postcode
    public Flux<PostCodeDetailsDto> getAllSuburbDetailsByPostcode(Integer postcode) {
        return this.cacheTemplate.getSuburbByPostcode(postcode);
    }

    // get ALL details fetch

    public Flux<PostCodeDetailsDto> getALL(){
       return this.cacheTemplate.getAll();
    }
    // get All details by postcode and suburb
    public Flux<PostCodeDetailsDto> getAllDetailsByPostcodeAndSuburb(Integer postcode,String suburb){
       return this.cacheTemplate.getAllDetailsByPostcodeAndSuburb(postcode,suburb);
    }


    // create Post request in request body

    public Mono<PostCodeDetailsDto> createPostCodeDetails(Mono<PostCodeDetailsDto> postCodeDetailsDtoMono) {
       return this.cacheTemplate.createPostCodeDetails(postCodeDetailsDtoMono);

    }





}
