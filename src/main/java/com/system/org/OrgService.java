package com.system.org;

import com.base.dao.DaoSupport;
import com.util.Tools;
import com.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orgService")
public class OrgService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Transactional
    public void save(Org org) throws Exception {
        String id = UuidUtil.get32UUID();
        org.setId(id);
        dao.save("OrgMapper.save",org);
    }
    @Transactional
    public void update(Org org) throws Exception {
        dao.update("OrgMapper.update",org);
    }
    @Transactional
    public void delete(String id, String updateBy) throws Exception  {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id",id);
        param.put("updateBy",updateBy);
        dao.delete("OrgMapper.delete",param);
    }
    @Transactional
    public void batchDelete(List<String> ids, String updateBy) throws Exception  {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("list",ids);
        param.put("updateBy",updateBy);
        dao.delete("OrgMapper.batchDelete",param);
    }

    public List<Org> selectList(Org org) throws Exception {
        return (List<Org>) dao.selectList("OrgMapper.selectList",org);
    }
    public List<Map<String,Object>> selectListGrid(Org org) throws Exception {
        return (List<Map<String,Object>>) dao.selectList("OrgMapper.selectListGrid",org);
    }
    public Org selectOne(String id) throws Exception {
        return (Org)dao.selectOne("OrgMapper.selectOne",id);
    }
    @Transactional
    public void batchUpdateState(List<String> ids, Integer useState, String updateBy) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("useState",useState);
        param.put("list",ids);
        param.put("updateBy",updateBy);
        dao.update("OrgMapper.batchUpdateState",param);
    }
    @Transactional
    public void updateState(String idsStr, Integer useState, String updateBy) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("useState",useState);
        param.put("id",idsStr);
        param.put("updateBy",updateBy);
        dao.update("OrgMapper.updateState",param);
    }

    public List<Org> selectTree(Org org) throws Exception {
        return (List<Org>) dao.selectList("OrgMapper.selectTree",org);
    }

    public String getMaxCurrentOrgCode(String parentId) throws Exception {
        return (String)dao.selectOne("OrgMapper.getMaxCurrentOrgCode",parentId);
    }

    public String getNextCodeByParent(String parentOrgCode,String parentId) throws Exception {
        //获取该上级组织直接下级已有的编号最大值
        String maxCurrentOrgCode = this.getMaxCurrentOrgCode(parentId);
        String nextCodeStr = Tools.nextCodeStr(parentOrgCode,maxCurrentOrgCode);
        return nextCodeStr;
    }
}
