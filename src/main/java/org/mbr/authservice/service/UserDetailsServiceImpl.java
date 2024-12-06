package org.mbr.authservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mbr.authservice.entities.UserInfo;
import org.mbr.authservice.eventProducer.UserInfoEvent;
import org.mbr.authservice.eventProducer.UserInfoProducer;
import org.mbr.authservice.model.UserInfoDto;
import org.mbr.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserInfoProducer userInfoProducer;

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username);
        if(user == null){
            log.info("User not found" + username);
            throw new UsernameNotFoundException("User not found");
        }
        log.info("User Authenticatd Successfully");
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserExists(UserInfoDto userInfoDto){
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signupUser(UserInfoDto userInfoDto){
        // Define a function to check if userEmail, password, and username are valid
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserExists(userInfoDto))){
            return false;
        }
        String userId = UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>()));
        // pushEventToKafka(userInfoDto);
        userInfoProducer.sendEventToKafka(userInfoEventToPublish(userInfoDto, userId));
        return true;
    }

    private UserInfoEvent userInfoEventToPublish(UserInfoDto userInfoDto, String userId){
        return UserInfoEvent.builder()
                .userId(userId)
                .firstName(userInfoDto.getFirstname())
                .lastName(userInfoDto.getLastname())
                .email(userInfoDto.getEmail())
                .phoneNumber(userInfoDto.getPhoneNumber())
                .build();
    }

}
