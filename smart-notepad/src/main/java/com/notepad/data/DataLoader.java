package com.notepad.data;

import com.notepad.dao.UserInfoRepository;
import com.notepad.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(!userInfoRepository.findByEmail("admin@email.com").isPresent()){
            UserInfo userInfo = new UserInfo();
            userInfo.setName("admin");
            userInfo.setEmail("admin@email.com");
            userInfo.setIsDeletable("false");
            userInfo.setStatus("true");
            userInfo.setPassword(passwordEncoder.encode("admin"));
            userInfoRepository.save(userInfo);
        }
        System.out.println("====> The admin credentials inserted.");
    }
}
