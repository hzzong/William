package com.example.williammall.api.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginParam {

    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @NotBlank(message = "密码不能为空")
    private String password;
}
