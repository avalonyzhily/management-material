package com.system.user;

import com.base.dao.DaoSupport;
import com.system.permission.RoleUser;
import com.system.permission.RoleUserService;
import com.util.UuidUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "roleUserService")
	private RoleUserService roleUserService;

	@Transactional
	public void save(User user, String roleNos) throws Exception {
		String id = UuidUtil.get32UUID();
		user.setId(id);
		String sha1pwd = new SimpleHash("SHA-1", user.getUserAccount(), user.getPassword()).toString();
		user.setPassword(sha1pwd);
		dao.save("UserMapper.save",user);
		if(!StringUtils.isEmpty(roleNos)){
			String[] roleNosArr = roleNos.split(",");
			RoleUser roleUser = null;
			if (roleNosArr != null && roleNosArr.length > 0) {
				for(String roleNo : roleNosArr){
					if(!StringUtils.isEmpty(roleNo)) {
						roleUser = new RoleUser();
						roleUser.setRoleId(roleNo);
						roleUser.setUserId(id);
						roleUserService.save(roleUser);
					}
				}
			}
		}
	}
	@Transactional
	public void update(User user, String roleNos) throws Exception {
		String id = user.getId();
		dao.update("UserMapper.update",user);
		roleUserService.deleteByUser(id);
		if(!StringUtils.isEmpty(roleNos)){
			String[] roleNosArr = roleNos.split(",");
			RoleUser roleUser = null;
			if (roleNosArr != null && roleNosArr.length > 0) {
				for(String roleNo : roleNosArr){
					if(!StringUtils.isEmpty(roleNo)) {
						roleUser = new RoleUser();
						roleUser.setRoleId(roleNo);
						roleUser.setUserId(id);
						roleUserService.save(roleUser);
					}
				}
			}
		}
	}
	@Transactional
	public void delete(String id, String updateBy) throws Exception  {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id",id);
		param.put("updateBy",updateBy);
		dao.delete("UserMapper.delete",param);
		roleUserService.deleteByUser(id);
	}
	@Transactional
	public void batchDelete(List<String> ids, String updateBy) throws Exception  {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id",ids);
		param.put("updateBy",updateBy);
		dao.delete("UserMapper.batchDelete",param);
		roleUserService.batchDeleteByUser(ids);
	}

	public List<Map<String,Object>> selectListGrid(User user) throws Exception {
		return (List<Map<String,Object>>) dao.selectList("UserMapper.selectListGrid",user);
	}

	public User selectOne(String id) throws Exception {
		return (User)dao.selectOne("UserMapper.selectOne",id);
	}
	@Transactional
    public void batchUpdateState(List<String> ids, Integer useState, String updateBy) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("useState",useState);
		param.put("list",ids);
		param.put("updateBy",updateBy);
		dao.update("UserMapper.batchUpdateState",param);
    }
	@Transactional
	public void updateState(String idsStr, Integer useState, String updateBy) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("useState",useState);
		param.put("id",idsStr);
		param.put("updateBy",updateBy);
		dao.update("UserMapper.updateState",param);
	}

    public User checkLogin(User userTemp) throws Exception {
		return (User)dao.selectOne("UserMapper.checkLogin",userTemp);
    }

	public void updateLastLogin(User user) throws Exception {
		dao.update("UserMapper.update",user);
	}

    public List<Map<String,Object>> selectableListByRoleId(String roleId) throws Exception {
		return (List<Map<String,Object>>) dao.selectList("UserMapper.selectableListByRoleId",roleId);
    }

	public List<Map<String,Object>> selectedListByRoleId(String roleId) throws Exception {
		return (List<Map<String,Object>>) dao.selectList("UserMapper.selectedListByRoleId",roleId);
	}

	public void updatePwd(User user) throws Exception {
		dao.update("UserMapper.updatePwd",user);
	}

    public List<User> selectList(User checkUserCode) throws Exception {
		return (List<User>)dao.selectList("UserMapper.selectList",checkUserCode);
    }
}
