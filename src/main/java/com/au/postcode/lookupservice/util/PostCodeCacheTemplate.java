package com.au.postcode.lookupservice.util;

import com.au.postcode.lookupservice.dto.PostCodeDetailsDto;
import com.au.postcode.lookupservice.entity.PostCodeDetail;
import com.au.postcode.lookupservice.repository.PostCodeRepository;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostCodeCacheTemplate extends CacheTemplate<Integer, PostCodeDetail> {

    @Autowired
    private PostCodeRepository repository;
    private RMapReactive<Integer,PostCodeDetail> map;

    public PostCodeCacheTemplate(RedissonReactiveClient client){
        this.map = client.getMap("postcode" , new TypedJsonJacksonCodec(Integer.class,PostCodeDetail.class));

    }


   /*  Updating records from DB to Cache  with Hash every 10 second ( It depends on use case if  we have regular update in DB
       then most of the fetch querys can come from cache infact very first request  or we can have cache aside pattern */

    @Scheduled(fixedRate = 10_000)
    public void updatePostCodeDetails() {
         this.repository.findAll()
                .collectList()
                .map(list -> list.stream().collect(Collectors.toMap(PostCodeDetail::getId, Function.identity())))
                .flatMap(this.map::putAll)
                .subscribe();
    }

    @Override
    protected Mono<PostCodeDetail> getFromSource(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    protected Mono<PostCodeDetail> getFromCache(Integer id) {
        return this.map.get(id);
    }

    @Override
    protected Mono<PostCodeDetail> updateSource(Integer id, PostCodeDetail postCodeDetail) {
        return this.repository.findById(id)
                .doOnNext(p -> postCodeDetail.setId(id))
                .flatMap(p -> this.repository.save(postCodeDetail));
    }

    @Override
    protected Mono<PostCodeDetail> updateCache(Integer id, PostCodeDetail postCodeDetail) {
        return this.map.fastPut(id, postCodeDetail).thenReturn(postCodeDetail);
    }

    @Override
    protected Mono<Void> deleteFromSource(Integer id) {
        return this.repository.deleteById(id);
    }

    @Override
    protected Mono<Void> deleteFromCache(Integer id) {
        return this.map.fastRemove(id).then();
    }


    // For Custom can further be extended with caching etc..
    @Override
    public Flux<PostCodeDetailsDto> getPostcodeBySuburb(String suburb) {
        return this.repository
                .getAllPostCodeDetailsBySuburb(suburb);
    }

    @Override
    public Flux<PostCodeDetailsDto> getSuburbByPostcode(Integer postcode){
        return this.repository.getAllSuburbDetailsByPostcode(postcode);
    }

    @Override
    public Flux<PostCodeDetailsDto> getAllDetailsByPostcodeAndSuburb(Integer postcode, String suburb){
        return this.repository.getAllDetailsByPostcodeAndSuburb(postcode,suburb);
    }



    @Override
    public Flux<PostCodeDetailsDto> getAll() {
        return this.repository.findAll().map(EntityDtoUtil::toDto);
    }


    @Override
    public Mono<PostCodeDetailsDto> createPostCodeDetails(Mono<PostCodeDetailsDto> postCodeDetailsDtoMono) {
             return postCodeDetailsDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.repository::save)
                .map(EntityDtoUtil::toDto);
    }


}



