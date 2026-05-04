package com.lz.framework.web.service;

import com.lz.common.constant.CacheConstants;
import com.lz.common.core.domain.model.ForgetBody;
import com.lz.common.core.redis.RedisCache;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 找回密码服务
 *
 * @author YY
 */
@Component
public class SysForgetService {

    private static final Logger log = LoggerFactory.getLogger(SysForgetService.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 发送验证码
     */
    public String sendVerifyCode(String username) {
        if (StringUtils.isEmpty(username)) {
            return "用户名不能为空";
        }

        // 验证用户是否存在
        var user = userService.selectUserByUserName(username);
        if (user == null) {
            return "用户不存在";
        }

        // 生成6位数字验证码
        String verifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

        // 存储验证码到Redis，5分钟有效
        String verifyKey = CacheConstants.FORGET_CODE_KEY + username;
        redisCache.setCacheObject(verifyKey, verifyCode, 5, TimeUnit.MINUTES);

        // 控制台打印验证码
        log.info("========== 找回密码验证码 ==========");
        log.info("用户: {}", username);
        log.info("验证码: {}", verifyCode);
        log.info("有效期: 5分钟");
        log.info("===================================");

        return null;
    }

    /**
     * 找回密码
     */
    public String forgetPassword(ForgetBody forgetBody) {
        String username = forgetBody.getUsername();
        String password = forgetBody.getPassword();
        String code = forgetBody.getCode();

        if (StringUtils.isEmpty(username)) {
            return "用户名不能为空";
        }
        if (StringUtils.isEmpty(password)) {
            return "密码不能为空";
        }
        if (StringUtils.isEmpty(code)) {
            return "验证码不能为空";
        }

        // 验证用户是否存在
        var user = userService.selectUserByUserName(username);
        if (user == null) {
            return "用户不存在";
        }

        // 验证验证码
        String verifyKey = CacheConstants.FORGET_CODE_KEY + username;
        String cachedCode = redisCache.getCacheObject(verifyKey);
        if (cachedCode == null) {
            return "验证码已过期，请重新获取";
        }
        if (!cachedCode.equals(code)) {
            return "验证码错误";
        }

        // 删除验证码
        redisCache.deleteObject(verifyKey);

        // 更新密码
        var sysUser = new com.lz.common.core.domain.entity.SysUser();
        sysUser.setUserId(user.getUserId());
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        int result = userService.resetPwd(sysUser);

        if (result > 0) {
            log.info("用户 {} 密码重置成功", username);
            return null;
        } else {
            return "密码重置失败，请稍后重试";
        }
    }
}
