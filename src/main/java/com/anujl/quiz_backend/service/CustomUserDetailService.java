package com.anujl.quiz_backend.service;


import com.anujl.quiz_backend.entity.UserEntity;
import com.anujl.quiz_backend.repository.UserRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public @NotNull UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        Optional<UserEntity> Optuser=userRepo.findByUsername(username);
        if(Optuser.isPresent()){
            UserEntity userEntity =Optuser.get();
            System.out.println("login");
return userEntity;
        }else
throw new UsernameNotFoundException("Username not present");
    }
}
