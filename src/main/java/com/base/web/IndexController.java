package com.base.web;

import com.core.constant.Consts;
import com.system.menu.Menu;
import com.system.menu.MenuService;
import com.system.user.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("index")
public class IndexController {
    @Value("${system.name}")
    private String systemName;
    @Resource(name = "menuService")
    private MenuService menuService;
    @Value("${system.defaultModule}")
    private String defaultModule;
    /**
     * 登陆成功到主页
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "index")
    public String  index(String id,Model model) throws Exception {
        Subject s =  SecurityUtils.getSubject();
        User currentUser = (User)s.getSession().getAttribute(Consts.LOGIN_USER);
        String userAccount = currentUser.getUserAccount();
        String userName = currentUser.getUserName();
        //查询菜单
        Menu condition = new Menu();
        condition.setMenuType("E");
        List<Menu> navList = menuService.selectList(condition);
        List<Menu> availableMenu = new ArrayList<Menu>();

        if(navList!=null){
            for(Menu menu :navList){
                String code = menu.getMenuCode();
                if(code.equals(defaultModule)
                        && StringUtils.isEmpty(id)){
                    id = menu.getId();
                }
                if(s.isPermitted(code)||"admin".equals(userAccount)){
                    availableMenu.add(menu);
                }
            }
        }
        //查询菜单下面的模块
        Menu condition1 = new Menu();
        condition1.setMenuType("O");
        condition1.setParentId(id);
        List<Menu> moduleMenus = menuService.selectList(condition1);
        List<Menu> availableModule = new ArrayList<Menu>();
        if(moduleMenus!=null){
            for(Menu menu : moduleMenus){
                String code = menu.getMenuCode();
                if(s.isPermitted(code)||"admin".equals(userAccount)){
                    availableModule.add(menu);
                }
            }
        }
        model.addAttribute("moduleList",availableModule);
        model.addAttribute("systemName",systemName);
        model.addAttribute("navList",availableMenu);
        model.addAttribute("defaultModule",defaultModule);
        model.addAttribute("userName",userName);
        Map<String,Map<String,String>> allDict = (Map<String,Map<String,String>>)s.getSession().getAttribute(Consts.ALL_DICTIONARY);
        model.addAttribute("allDict",allDict);
        return "index/index";
    }
}
