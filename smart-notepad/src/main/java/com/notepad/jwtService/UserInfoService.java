package com.notepad.jwtService;

import com.notepad.dao.UserInfoRepository;
import com.notepad.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userDetails = userInfoRepository.findByEmail(email);
        return userDetails.map(UserInfoDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("User not found "+email));
    }
}






















