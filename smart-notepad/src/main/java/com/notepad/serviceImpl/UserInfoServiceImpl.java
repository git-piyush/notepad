package com.notepad.serviceImpl;

import com.notepad.dao.UserInfoRepository;
import com.notepad.entity.UserInfo;
import com.notepad.filter.JwtAuthFilter;
import com.notepad.jwtService.JwtService;
import com.notepad.restImpl.UserInfoRestImpl;
import com.notepad.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    Logger log = LoggerFactory.getLogger(UserInfoRestImpl.class);

    Map<String, String> map = new HashMap<>();

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Override
    public ResponseEntity<?> addNewAppUser(UserInfo userInfo) {
        try {
            if(!validateUserInfo(userInfo)){
                return new ResponseEntity<>("{\"message\":\"Missing Required Data.\"}", HttpStatus.BAD_REQUEST);
            }
            Optional<UserInfo> dbUser = userInfoRepository.findByEmail(userInfo.getEmail());
            if(dbUser.isPresent()){
                return new ResponseEntity<>("{\"message\":\"User Already Exist.\"}", HttpStatus.FOUND);
            }
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            userInfo.setIsDeletable("true");
            userInfo.setStatus("false");
            userInfo.setEmail(userInfo.getEmail().toLowerCase());
            userInfoRepository.save(userInfo);
            return new ResponseEntity<>("{\"message\":\"Signup Successful.\"}", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in addNewUser: {}", e);
            map.put("message", "Something Went Wrong.");
        }
        //return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        //or
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateUserInfo(UserInfo userInfo) {
        return !Objects.isNull(userInfo) && StringUtils.hasText(userInfo.getName())
                && StringUtils.hasText(userInfo.getEmail()) && StringUtils.hasText(userInfo.getPassword());
    }
}
