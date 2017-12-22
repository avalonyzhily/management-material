package com.system.dict;

import com.base.entity.ResponseObj;
import com.system.user.User;
import com.core.constant.Consts;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("system/dictvalue")
public class DictValueController {

    private static Logger logger = LoggerFactory.getLogger(DictValueController.class);

    @Resource(name="dictValueService")
    private DictValueService dictValueService;

    @RequestMapping(value = "save",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseObj save(@RequestBody DictValue dictValue){
        try {
            Session session = SecurityUtils.getSubject().getSession();
            User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
            String id = dictValue.getId();
            //字典类型值去重
            String typeId = dictValue.getTypeId();
            String dictVal = dictValue.getDictVal();
            DictValue checkDictVal = new DictValue();
            checkDictVal.setTypeId(typeId);
            checkDictVal.setDictVal(dictVal);
            List<DictValue> dictValues = dictValueService.selectList(checkDictVal);
            if(StringUtils.isEmpty(id)) {
                if(dictValues!=null&&dictValues.size()>0){
                    return new ResponseObj("fail", "该类型下字典选项值已存在", dictValue);
                }
                dictValue.setCreateBy(loginUser.getId());
                dictValue.setUpdateBy(loginUser.getId());
                dictValueService.save(dictValue);
                return new ResponseObj("success", "保存成功", dictValue);
            }else {
                if(dictValues!=null&&dictValues.size()>1){
                    return new ResponseObj("fail", "该类型下字典选项值已存在", dictValue);
                }else if(dictValues!=null&&dictValues.size()==1){
                    DictValue dictValueTemp = dictValues.get(0);
                    String idTemp = dictValueTemp.getId();
                    if(!id.equals(idTemp)){
                        return new ResponseObj("fail", "该类型下字典选项值已存在", dictValue);
                    }
                }
                dictValue.setUpdateBy(loginUser.getId());
                dictValueService.update(dictValue);
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
                dictValueService.batchUpdateState(ids,useState,loginUser.getId());
            }else {
                dictValueService.updateState(idsStr,useState,loginUser.getId());
            }
            return new ResponseObj("success","更新成功",null);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseObj("fail","更新失败",null);
        }
    }

    @RequestMapping(value = "selectListGridHasPage")
    @ResponseBody
    public Map<String,Object> selectListGridHasPage(DictValue dictValue,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("rows")Integer pageSize) throws Exception {
        PageHelper.startPage(page,pageSize);
        List<DictValue> dictValues = dictValueService.selectListGrid(dictValue);
        PageInfo p = new PageInfo(dictValues);
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("total",p.getTotal());
        res.put("rows",dictValues);
        return res;
    }
    @RequestMapping(value = "selectListByType")
    @ResponseBody
    public List<DictValue> selectListByType(String type) throws Exception {
        List<DictValue> dictTypes = dictValueService.selectListByType(type);
        return dictTypes;
    }
    @RequestMapping("selectOne")
    @ResponseBody
    public DictValue selectOne(String id) throws Exception {
        DictValue dictValue = dictValueService.selectOne(id);
        return dictValue;
    }

    @RequestMapping(value = "add")
    public String buttonadd() throws Exception {
        return "system/dict/dictValueadd";
    }

    @RequestMapping(value = "edit")
    public String buttonedit(String id,Model model) throws Exception {
        DictValue dictValue = dictValueService.selectOne(id);
        model.addAttribute("dictValue",dictValue);
        return "system/dict/dictValueedit";
    }
}
