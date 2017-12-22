package com.system.dict;

import com.base.entity.ResponseObj;
import com.system.user.User;
import com.core.constant.Consts;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("system/dicttype")
public class DictTypeController {

    private static Logger logger = LoggerFactory.getLogger(DictTypeController.class);

    @Resource(name="dictTypeService")
    private DictTypeService dictTypeService;

    @RequestMapping(value = "save",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseObj save(@RequestBody DictType dictType){
        try {
            Session session = SecurityUtils.getSubject().getSession();
            User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
            String id = dictType.getId();
            //字典类型值去重
            String typeVal = dictType.getTypeVal();
            DictType checkTypeVal = new DictType();
            checkTypeVal.setTypeVal(typeVal);
            List<DictType> dictTypes = dictTypeService.selectList(checkTypeVal);
            if(StringUtils.isEmpty(id)) {
                if(dictTypes!=null&&dictTypes.size()>0){
                    return new ResponseObj("fail", "字典类型值已存在", dictType);
                }
                dictType.setCreateBy(loginUser.getId());
                dictType.setUpdateBy(loginUser.getId());
                dictTypeService.save(dictType);
                return new ResponseObj("success", "保存成功", dictType);
            }else {
                if(dictTypes!=null&&dictTypes.size()>1){
                    return new ResponseObj("fail", "字典类型值已存在", dictType);
                }else if(dictTypes!=null&&dictTypes.size()==1){
                    DictType dictTypeTemp = dictTypes.get(0);
                    String idTemp = dictTypeTemp.getId();
                    if(!id.equals(idTemp)){
                        return new ResponseObj("fail", "字典类型值已存在", dictType);
                    }
                }
                dictType.setUpdateBy(loginUser.getId());
                dictTypeService.update(dictType);
                return new ResponseObj("success","更新成功",null);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseObj("fail","保存失败",null);
        }
    }

    @RequestMapping("updateState")
    @ResponseBody
    public ResponseObj updateState(String idsStr,Integer useState) throws Exception {
        try{
            Session session = SecurityUtils.getSubject().getSession();
            User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
            if(idsStr.indexOf(",")>-1) {
                List<String> ids = Arrays.asList(idsStr.split(","));
                dictTypeService.batchUpdateState(ids,useState,loginUser.getId());
            }else {
                dictTypeService.updateState(idsStr,useState,loginUser.getId());
            }
            return new ResponseObj("success","更新成功",null);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseObj("fail","更新失败",null);
        }
    }

    @RequestMapping(value = "selectListGridHasPage")
    @ResponseBody
    public Map<String,Object> selectListGridHasPage(DictType dictType,
                                                    @RequestParam("page") Integer page,
                                                    @RequestParam("rows")Integer pageSize) throws Exception {
        PageHelper.startPage(page,pageSize);
        List<DictType> dictTypes = dictTypeService.selectListGrid(dictType);
        PageInfo p = new PageInfo(dictTypes);
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("total",p.getTotal());
        res.put("rows",dictTypes);
        return res;
    }
    @RequestMapping(value = "selectList")
    @ResponseBody
    public List<DictType> selectList(DictType dictType) throws Exception {
        List<DictType> dictTypes = dictTypeService.selectList(dictType);
        return dictTypes;
    }
    @RequestMapping("selectOne")
    @ResponseBody
    public DictType selectOne(String id, HttpServletRequest req) throws Exception {
        DictType dictType = dictTypeService.selectOne(id);
        return dictType;
    }

    @RequestMapping(value = "index")
    public String dictTypeindex(Model model){
        Subject s =	SecurityUtils.getSubject();
        User currentUser = (User)s.getSession().getAttribute(Consts.LOGIN_USER);
        String userAccount = currentUser.getUserAccount();
        model.addAttribute("s",s);
        model.addAttribute("userAccount",userAccount);
        Map<String,Map<String,String>> allDict = (Map<String,Map<String,String>>)s.getSession().getAttribute(Consts.ALL_DICTIONARY);
        model.addAttribute("allDict",allDict);
        return "system/dict/dictindex";
    }

    @RequestMapping(value = "add")
    public String buttonadd() throws Exception {
        return "system/dict/dictTypeadd";
    }

    @RequestMapping(value = "edit")
    public String buttonedit(String id,Model model) throws Exception {
        DictType dictType = dictTypeService.selectOne(id);
        model.addAttribute("dictType",dictType);
        return "system/dict/dictTypeedit";
    }

}
