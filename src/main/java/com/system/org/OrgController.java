package com.system.org;

import com.base.entity.ResponseObj;
import com.system.user.User;
import com.core.constant.Consts;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("system/org")
@Controller
public class OrgController {

    private static Logger logger = LoggerFactory.getLogger(OrgController.class);

    @Resource(name = "orgService")
    private OrgService orgService;

    @RequestMapping(value="save",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseObj save(@RequestBody Org org){
        try {
            Session session = SecurityUtils.getSubject().getSession();
            User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
            String id = org.getId();
            //机构编码去重
            String orgCode = org.getOrgCode();
            Org checkOrgCode = new Org();
            checkOrgCode.setOrgCode(orgCode);
            List<Org> orgs = orgService.selectList(checkOrgCode);
            if(StringUtils.isEmpty(id)){
                if(orgs!=null&&orgs.size()>0){
                    return new ResponseObj("fail", "机构代码已存在", org);
                }
                org.setCreateBy(loginUser.getId());
                org.setUpdateBy(loginUser.getId());
                orgService.save(org);
                return new ResponseObj("success", "保存成功", org);
            }else {
                if(orgs!=null&&orgs.size()>1){
                    return new ResponseObj("fail", "机构代码已存在", org);
                }else if(orgs!=null&&orgs.size()==1){
                    Org orgTemp = orgs.get(0);
                    String idTemp = orgTemp.getId();
                    if(!id.equals(idTemp)){
                        return new ResponseObj("fail", "机构代码已存在", org);
                    }
                }
                org.setUpdateBy(loginUser.getId());
                orgService.update(org);
                return new ResponseObj("success", "更新成功", null);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseObj("fail","保存失败",null);
        }
    }

    @RequestMapping("updateState")
    @ResponseBody
    public ResponseObj updateState(String idsStr, Integer useState) throws Exception {
        try{
            Session session = SecurityUtils.getSubject().getSession();
            User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
            if(idsStr.indexOf(",")>-1) {
                List<String> ids = Arrays.asList(idsStr.split(","));
                orgService.batchUpdateState(ids,useState,loginUser.getId());
            }else {
                orgService.updateState(idsStr,useState,loginUser.getId());
            }
            return new ResponseObj("success","更新成功",null);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseObj("fail","更新失败",null);
        }
    }

    @RequestMapping(value =  "delete")
    @ResponseBody
    public ResponseObj delete(String idsStr) throws Exception {
        try{
            Session session = SecurityUtils.getSubject().getSession();
            User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
            if(idsStr.indexOf(",")>-1) {
                List<String> ids = Arrays.asList(idsStr.split(","));
                orgService.batchDelete(ids,loginUser.getId());
            }else {
                orgService.delete(idsStr,loginUser.getId());
            }
            return new ResponseObj("success","删除成功",null);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseObj("fail","删除失败",null);
        }
    }

    @RequestMapping(value = "selectListHasRoot")
    @ResponseBody
    public List<Org> selectListHasRoot(Org org) throws Exception {
        List<Org> orgs = orgService.selectList(org);
        Org org1 = new Org();
        org1.setId("0");
        org1.setOrgName("顶级机构");
        orgs.add(0,org1);
        return orgs;
    }
    @RequestMapping(value = "selectListNoRoot")
    @ResponseBody
    public List<Org> selectListNoRoot(Org org) throws Exception {
        List<Org> orgs = orgService.selectList(org);
        return orgs;
    }
    @RequestMapping(value = "selectTree")
    @ResponseBody
    public List<Org> selectTree(Org org) throws Exception {
        List<Org> orgs = orgService.selectTree(org);
        return orgs;
    }
    @RequestMapping(value = "selectListGrid")
    @ResponseBody
    public Map<String,Object> selectListGrid(Org org) throws Exception {
        List<Map<String,Object>> orgs = orgService.selectListGrid(org);
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("total",orgs.size());
        res.put("rows",orgs);
        return res;
    }
    @RequestMapping("selectOne")
    public Org selectOne(String id) throws Exception {
        Org org = orgService.selectOne(id);
        return org;
    }
    @RequestMapping("getOrgCodeByParent")
    @ResponseBody
    public ResponseObj getOrgCodeByParent(String parentId) throws Exception {
        String parentOrgCode = "";
        String parentType = "0";
        if(!parentId.equals("0")) {
            //说明选择的不是顶级菜单
            Org parentOrg = this.selectOne(parentId);
            //获取上级组织的编号
            parentOrgCode = parentOrg.getOrgCode();
            parentType = parentOrg.getOrgType();
        }
        String nextCodeStr = orgService.getNextCodeByParent(parentOrgCode,parentId);
        ResponseObj ro = new ResponseObj("success",nextCodeStr,parentType);
        return ro;
    }
    @RequestMapping(value = "index")
    public String orgindex(Model model){
        Subject s =	SecurityUtils.getSubject();
        User currentUser = (User)s.getSession().getAttribute(Consts.LOGIN_USER);
        String userAccount = currentUser.getUserAccount();
        model.addAttribute("s",s);
        model.addAttribute("userAccount",userAccount);
        Map<String,Map<String,String>> allDict = (Map<String,Map<String,String>>)s.getSession().getAttribute(Consts.ALL_DICTIONARY);
        model.addAttribute("allDict",allDict);
        return "system/org/orgindex";
    }

    @RequestMapping(value = "add")
    public String orgadd() throws Exception {
        return "system/org/orgadd";
    }

    @RequestMapping(value = "edit")
    public String orgedit(String id,Model model) throws Exception {
        Org org = orgService.selectOne(id);
        model.addAttribute("org",org);
        return "system/org/orgedit";
    }

    @RequestMapping(value = "look")
    public String orglook(String id,Model model) throws Exception {
        Org org = orgService.selectOne(id);
        model.addAttribute("org",org);
        return "system/org/orglook";
    }
}
