package com.system.permission;

import com.base.dao.DaoSupport;
import com.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("roleButtonService")
public class RoleButtonService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Transactional
    public void save(RoleButton roleButton) throws Exception {
        String id = UuidUtil.get32UUID();
        roleButton.setId(id);
        dao.save("RBMapper.save",roleButton);
    }
    @Transactional
    public void deleteByRole(String id) throws Exception {
        dao.delete("RBMapper.deleteByRole",id);
    }
    @Transactional
    public void batchDeleteByRole(List<String> ids) throws Exception {
        dao.delete("RBMapper.batchDeleteByRole",ids);
    }
}
