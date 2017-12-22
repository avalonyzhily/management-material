package com.system.permission;

import com.base.dao.DaoSupport;
import com.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("roleMenuService")
public class RoleMenuService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Transactional
    public void save(RoleMenu roleMenu) throws Exception {
        String id = UuidUtil.get32UUID();
        roleMenu.setId(id);
        dao.save("RMMapper.save",roleMenu);
    }
    @Transactional
    public void deleteByRole(String id) throws Exception {
        dao.delete("RMMapper.deleteByRole",id);
    }
    @Transactional
    public void batchDeleteByRole(List<String> ids) throws Exception {
        dao.delete("RMMapper.batchDeleteByRole",ids);
    }
}