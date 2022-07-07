package com.example.williammall.domain;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

@Data
public class UserToken {

    @Id
    private Long userId;

    private String token;

    private Date updateTime;

    private Date expireTime;
}
