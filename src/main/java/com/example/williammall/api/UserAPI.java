package com.example.williammall.api;

import com.example.williammall.api.param.UserLoginParam;
import com.example.williammall.api.param.UserRegisterParam;
import com.example.williammall.common.ServiceResultEnum;
import com.example.williammall.service.UserService;
import com.example.williammall.util.NumberUtil;
import com.example.williammall.util.Result;
import com.example.williammall.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserAPI {

    private NumberUtil numberUtil;

    private ResultGenerator resultGenerator;

    private UserService userService;

    @Autowired
    public UserAPI(NumberUtil numberUtil, ResultGenerator resultGenerator, UserService userService) {
        this.numberUtil = numberUtil;
        this.resultGenerator = resultGenerator;
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public Result register(@RequestBody @Valid UserRegisterParam param){
        if (numberUtil.isNotPhone(param.getLoginName())){
            return resultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String registerResult = userService.register(param.getLoginName(), param.getPassword());

        log.info("register api,loginName={},loginResult={}", param.getLoginName(), registerResult);

        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return resultGenerator.genSuccessResult();
        }
        //注册失败
        return resultGenerator.genFailResult(registerResult);
    }

    @PostMapping("/user/register")
    public Result login(@RequestBody @Valid UserLoginParam param){
        if (numberUtil.isNotPhone(param.getLoginName())){
            return resultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }

    }
}
