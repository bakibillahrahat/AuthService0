package org.mbr.authservice.controller;

import lombok.AllArgsConstructor;
import org.mbr.authservice.entities.RefreshToken;
import org.mbr.authservice.model.UserInfoDto;
import org.mbr.authservice.response.JwtResponseDTO;
import org.mbr.authservice.service.JwtService;
import org.mbr.authservice.service.RefreshTokenService;
import org.mbr.authservice.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoDto userInfoDto){
        try{
            Boolean isSignUped = userDetailsService.signupUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignUped)){
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken = jwtService.GenerateToken(userInfoDto.getUsername());
            return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken).
                    token(refreshToken.getToken()).build(), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>("Exception is User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
