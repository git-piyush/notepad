package com.notepad.restImpl;

import com.notepad.entity.UserInfo;
import com.notepad.rest.UserInfoRest;
import com.notepad.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserInfoRestImpl implements UserInfoRest {

    Logger log = LoggerFactory.getLogger(UserInfoRestImpl.class);

    Map<String, String> map = new HashMap<>();

    @Autowired
    UserInfoService userInfoService;

    @Override
    public ResponseEntity<?> addNewAppUser(UserInfo userInfo) {
        try {
            return userInfoService.addNewAppUser(userInfo);
        } catch (Exception e) {
            log.error("Exception in addNewUser: {}", e);
            map.put("message", "Something Went Wrong.");
        }
        //return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        //or
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
