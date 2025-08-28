package com.notepad.rest;

import com.notepad.dto.AuthRequest;
import com.notepad.entity.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/appuser")
public interface UserInfoRest {

    @PostMapping(path = "/addNewAppuser")
    ResponseEntity<?> addNewAppUser(@RequestBody(required = true) UserInfo userInfo);

    @PostMapping(path="/login")
    ResponseEntity<?> login(@RequestBody(required = true)AuthRequest authRequest);

    @GetMapping(path="/getAllAppUser")
    ResponseEntity<?> getAllAppUser();

    @PostMapping(path="/updateUserStatus")
    ResponseEntity<?> updateUserStatus(@RequestBody(required = true) UserInfo userInfo);
}
