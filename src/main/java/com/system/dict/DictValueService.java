package com.system.dict;

import com.base.dao.DaoSupport;
import com.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dictValueService")
public class DictValueService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    @Transactional
    public void save(DictValue dictValue) throws Exception {
        String id =  UuidUtil.get32UUID();
        dictValue.setId(id);
        dao.save("DictValueMapper.save",dictValue);
    }
    @Transactional
    public void update(DictValue dictValue) throws Exception {
        dao.update("DictValueMapper.update",dictValue);
    }
    @Transactional
    public void delete(String id, String updateBy) throws Exception  {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id",id);
        param.put("updateBy",updateBy);
        dao.delete("DictValueMapper.delete",param);
    }
    @Transactional
    public void batchDelete(List<String> ids, String updateBy) throws Exception  {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id",ids);
        param.put("updateBy",updateBy);
        dao.delete("DictValueMapper.batchDelete",param);
    }

    public List<DictValue> selectListGrid(DictValue dictValue) throws Exception {
        return (List<DictValue>) dao.selectList("DictValueMapper.selectListGrid",dictValue);
    }

    public DictValue selectOne(String id) throws Exception {
        return (DictValue)dao.selectOne("DictValueMapper.selectOne",id);
    }

    @Transactional
    public void batchUpdateState(List<String> ids, Integer useState, String updateBy) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("useState",useState);
        param.put("list",ids);
        param.put("updateBy",updateBy);
        dao.update("DictValueMapper.batchUpdateState",param);
    }
    @Transactional
    public void updateState(String idsStr, Integer useState, String updateBy) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("useState",useState);
        param.put("id",idsStr);
        param.put("updateBy",updateBy);
        dao.update("DictValueMapper.updateState",param);
    }

    public List<DictValue> selectListByType(String type) throws Exception {
        return (List<DictValue>) dao.selectList("DictValueMapper.selectListByType",type);
    }

    public List<DictValue> selectList(DictValue checkDictVal) throws Exception {
        return (List<DictValue>) dao.selectList("DictValueMapper.selectList",checkDictVal);
    }
}
