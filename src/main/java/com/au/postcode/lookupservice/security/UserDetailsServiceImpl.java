package com.au.postcode.lookupservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Order(1)
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    @Autowired
    private Map<String, UserDetails> map;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(this.map.get(username));
    }
}
