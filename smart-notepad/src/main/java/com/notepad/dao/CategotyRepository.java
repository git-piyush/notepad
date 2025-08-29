package com.notepad.dao;

import com.notepad.entity.Category;
import com.notepad.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategotyRepository extends JpaRepository<Category, Long> {

    Boolean existsByNameIgnoreCase(String name);

    @Query("UPDATE Category cat set cat.name =:name WHERE cat.id =:id" )
    Integer updateCategory(@Param("name") String name, @Param("id") Long id);

    Optional<UserInfo> findByName(String name);
}
