package com.system.dict;

import com.base.dao.DaoSupport;
import com.util.Tools;
import com.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dictTypeService")
public class DictTypeService {

    @Resource(name="daoSupport")
    private DaoSupport dao;

    @Transactional
    public void save(DictType dictType) throws Exception {
        String id =  UuidUtil.get32UUID();
        dictType.setId(id);
        dao.save("DictTypeMapper.save",dictType);
    }
    @Transactional
    public void update(DictType dictType) throws Exception {
        dao.update("DictTypeMapper.update",dictType);
    }
    @Transactional
    public void delete(String id, String updateBy) throws Exception  {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id",id);
        param.put("updateBy",updateBy);
        dao.delete("DictTypeMapper.delete",param);
    }
    @Transactional
    public void batchDelete(List<String> ids, String updateBy) throws Exception  {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id",ids);
        param.put("updateBy",updateBy);
        dao.delete("DictTypeMapper.batchDelete",param);
    }

    public List<DictType> selectListGrid(DictType dictType) throws Exception {
        return (List<DictType>) dao.selectList("DictTypeMapper.selectListGrid",dictType);
    }

    public DictType selectOne(String id) throws Exception {
        return (DictType)dao.selectOne("DictTypeMapper.selectOne",id);
    }

    @Transactional
    public void batchUpdateState(List<String> ids, Integer useState, String updateBy) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("useState",useState);
        param.put("list",ids);
        param.put("updateBy",updateBy);
        dao.update("DictTypeMapper.batchUpdateState",param);
    }
    @Transactional
    public void updateState(String idsStr, Integer useState, String updateBy) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("useState",useState);
        param.put("id",idsStr);
        param.put("updateBy",updateBy);
        dao.update("DictTypeMapper.updateState",param);
    }

    public List<DictType> selectList(DictType dictType) throws Exception {
        return (List<DictType>) dao.selectList("DictTypeMapper.selectList",dictType);
    }

    public Map<String,Map<String,String>> getALlStaticDictionary() throws Exception {
        List<Map<String,String>> res = (List<Map<String,String>>)dao.selectList("DictTypeMapper.getALlStaticDictionary",null);
        Map<String,Map<String,String>> allDict = Tools.listToMapForDict(res);
        return allDict;
    }
}
