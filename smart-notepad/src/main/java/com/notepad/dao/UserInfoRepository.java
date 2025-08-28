package com.notepad.dao;

import com.notepad.entity.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);

    @Query("SELECT u FROM UserInfo u WHERE u.status = 'true' AND u.email <> :email")
    List<UserInfo> getAllAppUser(@Param("email") String email);

    @Modifying
    //@Transactional
    @Query("UPDATE UserInfo u set u.status =:status WHERE u.id =:id AND u.isDeletable ='true'" )
    Long updateUserStatus(@Param("status") String status, @Param("id") Long id);
}

























