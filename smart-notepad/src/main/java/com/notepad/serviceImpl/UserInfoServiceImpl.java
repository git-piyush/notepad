package com.notepad.serviceImpl;

import com.notepad.dao.UserInfoRepository;
import com.notepad.dto.AuthRequest;
import com.notepad.entity.UserInfo;
import com.notepad.filter.JwtAuthFilter;
import com.notepad.jwtService.JwtService;
import com.notepad.jwtService.UserInfoDetails;
import com.notepad.restImpl.UserInfoRestImpl;
import com.notepad.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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

    @Override
    public ResponseEntity<?> login(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail().toLowerCase(), authRequest.getPassword()));
            if(authentication != null && authentication.isAuthenticated()){
                UserInfoDetails userInfoDetails = (UserInfoDetails)authentication.getPrincipal();
                if("true".equalsIgnoreCase(userInfoDetails.getStatus())){
                    map.put("token", jwtService.generateToken(authRequest.getEmail()));
                    return new ResponseEntity<>(map, HttpStatus.OK);

                }else{
                    return new ResponseEntity<>("{\"message\":\"Wait for admin approval.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else{
                throw new UsernameNotFoundException("Invalid Username/Password.");
            }
        }catch (BadCredentialsException ex){
            return new ResponseEntity<>("{\"message\":\"Invalid Credentials.\"}", HttpStatus.BAD_REQUEST);
        }catch (UsernameNotFoundException ex){
            return new ResponseEntity<>("{\"message\":\"Invalid Credentials.\"}", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Exception in login : {}", e);
            map.put("message", "Something Went Wrong.");
        }
        //return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        //or
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllAppUser() {
        try {
            List<UserInfo> userInfoList = userInfoRepository.getAllAppUser(jwtAuthFilter.getEmail());
            if(userInfoList!=null && userInfoList.size()>0){
                return new ResponseEntity<>(userInfoList, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("{\"message\":\"No Data.\"}", HttpStatus.OK);
            }
        }catch (Exception e){
            log.error("Exception in login : {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateUserStatus(UserInfo userInfo) {
        try {
            if(!Objects.isNull(userInfo) && !Objects.isNull(userInfo.getId())
                    && !Objects.isNull(userInfo.getStatus())){
                Optional<UserInfo> dbUser = userInfoRepository.findById(userInfo.getId());
                if(!dbUser.isPresent()){
                    return new ResponseEntity<>("{\"message\":\"User Not Present.\"}", HttpStatus.BAD_REQUEST);
                }else{
                    UserInfo userInfo1 = dbUser.get();
                    if(!userInfo1.getIsDeletable().equalsIgnoreCase("true")){
                        return new ResponseEntity<>("{\"message\":\"User is not deletable.\"}", HttpStatus.BAD_REQUEST);
                    }else{
                        userInfo1.setStatus(userInfo.getStatus());
                        userInfoRepository.save(userInfo1);
                        return new ResponseEntity<>("{\"message\":\"User Update Successful.\"}", HttpStatus.OK);
                    }
                }
            }else{
                return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Exception in addNewUser: {}", e.getMessage());
            map.put("message", "Something Went Wrong.");
        }
        //return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        //or
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
