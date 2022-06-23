package com.example.williammall.service.impl;

import com.example.williammall.common.Constants;
import com.example.williammall.common.ServiceResultEnum;
import com.example.williammall.domain.User;
import com.example.williammall.domain.repo.UserRepo;
import com.example.williammall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public String register(String loginName, String password){
        if (userRepo.findUserByLoginName(loginName) != null){
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        User registerUser = new User();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        registerUser.setIntroduceSign(Constants.USER_INTRO);
        registerUser.setPassword(password);
        if (userRepo.save(registerUser) != null) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
}
