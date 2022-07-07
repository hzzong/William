package com.example.williammall.service.impl;

import com.example.williammall.common.Constants;
import com.example.williammall.common.ServiceResultEnum;
import com.example.williammall.domain.User;
import com.example.williammall.domain.repo.UserRepo;
import com.example.williammall.service.UserService;
import com.example.williammall.util.NumberUtil;
import com.example.williammall.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private UserRepo userRepo;

    private NumberUtil numberUtil;

    private SystemUtil systemUtil;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, NumberUtil numberUtil, SystemUtil systemUtil) {
        this.userRepo = userRepo;
        this.numberUtil = numberUtil;
        this.systemUtil = systemUtil;
    }

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

    @Override
    public String login(String loginName, String password){
        User user = userRepo.getUserByLoginNameAndPassword(loginName, password);
        if (user != null) {
            return ServiceResultEnum.LOGIN_ERROR.getResult();
        }

        //登录后即执行修改token的操作
        String token = getNewToken(System.currentTimeMillis() + "", user.getId());
        MallUserToken mallUserToken = newBeeMallUserTokenMapper.selectByPrimaryKey(user.getUserId());
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//过期时间 48 小时
        if (mallUserToken == null) {
            mallUserToken = new MallUserToken();
            mallUserToken.setUserId(user.getUserId());
            mallUserToken.setToken(token);
            mallUserToken.setUpdateTime(now);
            mallUserToken.setExpireTime(expireTime);
            //新增一条token数据
            if (newBeeMallUserTokenMapper.insertSelective(mallUserToken) > 0) {
                //新增成功后返回
                return token;
            }
        } else {
            mallUserToken.setToken(token);
            mallUserToken.setUpdateTime(now);
            mallUserToken.setExpireTime(expireTime);
            //更新
            if (newBeeMallUserTokenMapper.updateByPrimaryKeySelective(mallUserToken) > 0) {
                //修改成功后返回
                return token;
            }
        }
    }

    /**
     * 获取token值
     *
     * @param timeStr
     * @param userId
     * @return
     */
    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + numberUtil.genRandomNum(4);
        return systemUtil.genToken(src);
    }

}
