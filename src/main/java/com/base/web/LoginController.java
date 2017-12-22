package com.base.web;

import com.core.constant.Consts;
import com.system.dict.DictTypeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/*
 * 总入口
 */
@Controller
@RequestMapping("login")
public class LoginController{

    @Resource(name = "dictTypeService")
    private DictTypeService dictTypeService;

    @Value("${system.name}")
    private String systemName;
    @Value("${system.defaultModule}")
    private String defaultModule;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "toLogin")
    public String  toLogin(Model model) throws Exception {
        model.addAttribute("systemName",systemName);
        model.addAttribute("defaultModule",defaultModule);
        return "login";
    }

    /**
     * 请求登录，验证用户
     */
    @RequestMapping(value = "/userLogin", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object login(String code) throws Exception {

        String errInfo = "";
        String[] KEYDATA = code.split(",");

        if (null != KEYDATA && KEYDATA.length == 3) {
            String USERNAME = KEYDATA[0];
            String PASSWORD = KEYDATA[1];
            Subject subject = SecurityUtils.getSubject();
            String pwd = new SimpleHash("SHA-1",USERNAME,PASSWORD).toString();
            UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, pwd);
            try {
                subject.login(token);
            } catch (AuthenticationException e) {
                errInfo = e.getMessage();
            }
            if(StringUtils.isEmpty(errInfo)){
                //登录成功，加入字典缓存
                Map<String,Map<String,String>> allDictionary = dictTypeService.getALlStaticDictionary();
                subject.getSession().setAttribute(Consts.ALL_DICTIONARY,allDictionary);
                errInfo = "success";
            }
        } else {
            errInfo = Consts.PARAM_MISS; // 缺少参数
        }
        Map<String,String> res = new HashMap<String,String>();
        res.put("result", errInfo);
        return res;
    }

    /**
     * 用户注销
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(Model model) {
        // shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.removeAttribute(Consts.LOGIN_USER);
        // shiro销毁登录
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        model.addAttribute("systemName",systemName);
        return "login";
    }
}
