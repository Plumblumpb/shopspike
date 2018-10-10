package com.plumblum.miaosha.service;

import com.plumblum.miaosha.dao.MiaoshaUserDao;
import com.plumblum.miaosha.entity.MiaoshaUser;
import com.plumblum.miaosha.exception.GlobalException;
import com.plumblum.miaosha.redis.RedisService;
import com.plumblum.miaosha.redis.key.MiaoshaUserKey;
import com.plumblum.miaosha.result.CodeMsg;
import com.plumblum.miaosha.utils.MD5Util;
import com.plumblum.miaosha.utils.UUIDUtil;
import com.plumblum.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IDEA
 * author:plumblum
 * Date:2018/10/9
 * Time:22:56
 */
@Service("miaoshaUserService")
public class MiaoshaUserService {
    public static final String COOKI_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(long id) {
        return miaoshaUserDao.getById(id);
    }


    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if(user != null) {
            addCookie(response, token, user);
        }
        return user;
    }


    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token	 = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    /*
    * 将user信息存入redis中redis_key
    * 将redis_key存入cookie中
    * **/
    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {

        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
//        配置存活时间
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
//        设置根url
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
