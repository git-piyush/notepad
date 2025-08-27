package com.notepad.service;

import com.notepad.entity.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {
    ResponseEntity<?> addNewAppUser(UserInfo userInfo);
}
