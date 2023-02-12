package com.au.postcode.lookupservice.repository;

import com.au.postcode.lookupservice.dto.PostCodeDetailsDto;
import com.au.postcode.lookupservice.entity.PostCodeDetail;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PostCodeRepository extends ReactiveCrudRepository<PostCodeDetail,Integer> {

    // By Suburb
    @Query(
            "select * from postcodedetail  " +
             "where suburb = :suburb"
           )
    Flux<PostCodeDetailsDto> getAllPostCodeDetailsBySuburb(String  suburb );

// By PostCode

    @Query(
            "select  * from postcodedetail " +
                    "where postcode = :postcode"
    )
    Flux<PostCodeDetailsDto> getAllSuburbDetailsByPostcode(Integer postcode ) ;


    // By Post Code and Suburb
    @Query(
            "select  * from postcodedetail " +
                    "where (postcode = :postcode AND suburb = :suburb)"
    )
    Flux<PostCodeDetailsDto> getAllDetailsByPostcodeAndSuburb(Integer postcode , String suburb) ;



}
