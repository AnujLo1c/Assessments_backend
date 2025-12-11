package com.anujl.quiz_backend.service;


import com.anujl.quiz_backend.dto.login.UserLoginDto;
import com.anujl.quiz_backend.dto.login.UserRegisterDTO;
import com.anujl.quiz_backend.dto.login.UserResponseDTO;
import com.anujl.quiz_backend.entity.UserEntity;
import com.anujl.quiz_backend.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
@Autowired
    PasswordEncoder passwordEncoder;
@Autowired
    AuthenticationManager authenticationManager;
@Autowired
JwtService jwtService;


@Autowired
    ModelMapper modelMapper;

@Transactional
    public boolean saveUser(UserRegisterDTO userDTO){
    System.out.println("userDTO before encryption: " + userDTO.getPassword());
    String encryptedPassword=passwordEncoder.encode(userDTO.getPassword());
    System.out.println("Encrypted Password: " + encryptedPassword);
        userDTO.setPassword(encryptedPassword);
    if (userRepo.findByUsername(userDTO.getUsername()).isPresent()) {
        throw new RuntimeException("Username already exists!");
    }
    UserEntity userEntity=modelMapper.map(userDTO, UserEntity.class);
    userEntity.setLastLoginTime(null);
        userRepo.save(userEntity);
return true;
}

    public List<UserResponseDTO> getAllUsers() {
return userRepo.findAll().stream().map(m->new UserResponseDTO(m.getUsername(),m.getEmail(),m.getLastLoginTime())).toList();
    }


    public String verify(UserLoginDto userLoginDto)  {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));
  if(authentication.isAuthenticated()){
      String token= jwtService.generateToken(userLoginDto.getUsername());
UserEntity userEntity= userRepo.findByUsername(userLoginDto.getUsername())
              .orElseThrow(() -> new RuntimeException("User not found"));
if(userEntity==null){
    throw new RuntimeException("User not found");
}
      userEntity.setLastLoginTime(LocalDateTime.now().plusSeconds(5));
      userRepo.save(userEntity);
      return token;
  }
  else{
      throw new RuntimeException("Invalid Login Credentials");
  }
    }

    public void logout(String token) {
        UserEntity user = userRepo.findByUsername(jwtService.extractUsername(token))
                .orElseThrow(() -> new RuntimeException("Invalid token or user not found"));

        System.out.println("User " + user.getUsername() + " logged out.");
        user.setLastLoginTime(LocalDateTime.now());
        userRepo.save(user);

    }


}
