package com.system.permission;

import com.base.dao.DaoSupport;
import com.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("roleUserService")
public class RoleUserService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Transactional
    public void save(RoleUser roleUser) throws Exception {
        String id = UuidUtil.get32UUID();
        roleUser.setId(id);
        dao.save("RUMapper.save",roleUser);
    }
    @Transactional
    public void deleteByRole(String id) throws Exception {
        dao.delete("RUMapper.deleteByRole",id);
    }
    @Transactional
    public void batchDeleteByRole(List<String> ids) throws Exception {
        dao.delete("RUMapper.batchDeleteByRole",ids);
    }
    @Transactional
    public void deleteByUser(String id) throws Exception {
        dao.delete("RUMapper.deleteByUser",id);
    }
    @Transactional
    public void batchDeleteByUser(List<String> ids) throws Exception {
        dao.delete("RUMapper.batchDeleteByUser",ids);
    }
}