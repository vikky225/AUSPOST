package com.au.postcode.lookupservice.util;

import com.au.postcode.lookupservice.dto.PostCodeDetailsDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Created Generic CacheTemplate for extendability

public abstract class CacheTemplate<KEY, ENTITY> {

    public Mono<ENTITY> get(KEY key){

        return getFromCache(key)
                .switchIfEmpty(
                        getFromSource(key)
                                .flatMap(e->updateCache(key,e))
                );
    }

    public Mono<ENTITY> update(KEY key , ENTITY entity){
        return updateSource(key,entity)
                .flatMap(e->deleteFromCache(key)
                        .thenReturn(e));
    }

    public Mono<Void> delete(KEY key){
        return deleteFromSource(key)
                .then(deleteFromCache(key));
    }


    abstract protected Mono<ENTITY> getFromSource(KEY key);
    abstract protected Mono<ENTITY> getFromCache(KEY key);
    abstract protected Mono<ENTITY> updateSource(KEY key, ENTITY entity);
    abstract protected Mono<ENTITY> updateCache(KEY key, ENTITY entity);
    abstract protected Mono<Void>  deleteFromSource(KEY key);
    abstract protected Mono<Void>  deleteFromCache(KEY key);

    // For Custom Implementation
    public abstract Flux<PostCodeDetailsDto> getPostcodeBySuburb(String suburb);
    public abstract Flux<PostCodeDetailsDto> getSuburbByPostcode(Integer postcode);
    public abstract Flux<PostCodeDetailsDto> getAllDetailsByPostcodeAndSuburb(Integer postcode, String suburb);
    public abstract Flux<PostCodeDetailsDto> getAll();
    public abstract Mono<PostCodeDetailsDto> createPostCodeDetails(Mono<PostCodeDetailsDto> postCodeDetailsDtoMono);
}
