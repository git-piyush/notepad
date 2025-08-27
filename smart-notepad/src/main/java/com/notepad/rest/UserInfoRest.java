package com.notepad.rest;

import com.notepad.dto.AuthRequest;
import com.notepad.entity.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/appuser")
public interface UserInfoRest {

    @PostMapping(path = "/addNewAppuser")
    ResponseEntity<?> addNewAppUser(@RequestBody(required = true) UserInfo userInfo);

    @PostMapping(path="/login")
    ResponseEntity<?> login(@RequestBody(required = true)AuthRequest authRequest);
}
