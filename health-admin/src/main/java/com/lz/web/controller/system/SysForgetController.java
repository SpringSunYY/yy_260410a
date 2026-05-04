package com.lz.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.core.domain.model.ForgetBody;
import com.lz.common.utils.StringUtils;
import com.lz.framework.web.service.SysForgetService;

/**
 * 找回密码
 *
 * @author YY
 */
@RestController
public class SysForgetController extends BaseController {

    @Autowired
    private SysForgetService forgetService;

    /**
     * 发送验证码
     */
    @PostMapping("/sendVerifyCode")
    public AjaxResult sendVerifyCode(@RequestBody ForgetBody forgetBody) {
        String msg = forgetService.sendVerifyCode(forgetBody.getUsername());
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

    /**
     * 找回密码
     */
    @PostMapping("/forgetPassword")
    public AjaxResult forgetPassword(@RequestBody ForgetBody forgetBody) {
        String msg = forgetService.forgetPassword(forgetBody);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
