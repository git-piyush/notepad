package com.notepad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Entity
@Table(name="tbl_appuser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable{

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String isDeletable;
    private String status;

    public UserInfo(String status, Long id, String name, String email) {
        this.status = status;
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
