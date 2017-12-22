package com.system.role;

import com.base.dao.DaoSupport;
import com.system.permission.*;
import com.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("roleService")
public class RoleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "roleButtonService")
	private RoleButtonService roleButtonService;
	@Resource(name = "roleMenuService")
	private RoleMenuService roleMenuService;
	@Resource(name = "roleUserService")
	private RoleUserService roleUserService;

	@Transactional
	public void save(Role role, String userNos, String menuNos, String buttonNos) throws Exception {
		String id = UuidUtil.get32UUID();
		role.setId(id);
		dao.save("RoleMapper.save",role);
		if(!StringUtils.isEmpty(userNos)){
			String[] userNosArr = userNos.split(",");
			RoleUser roleUser = null;
			if (userNosArr != null && userNosArr.length > 0) {
				for(String userNo : userNosArr){
					if(!StringUtils.isEmpty(userNo)) {
						roleUser = new RoleUser();
						roleUser.setRoleId(id);
						roleUser.setUserId(userNo);
						roleUserService.save(roleUser);
					}
				}
			}
		}
		if(!StringUtils.isEmpty(menuNos)){
			String[] menuNosArr = menuNos.split(",");
			RoleMenu roleMenu = null;
			if (menuNosArr != null && menuNosArr.length > 0) {
				for(String menuNo : menuNosArr){
					if(!StringUtils.isEmpty(menuNo)) {
						roleMenu = new RoleMenu();
						roleMenu.setRoleId(id);
						roleMenu.setMenuId(menuNo);
						roleMenuService.save(roleMenu);
					}
				}
			}
		}
		if(!StringUtils.isEmpty(buttonNos)){
			String[] buttonNosArr = buttonNos.split(",");
			RoleButton roleButton = null;
			if (buttonNosArr != null && buttonNosArr.length > 0) {
				for(String buttonNo : buttonNosArr){
					if(!StringUtils.isEmpty(buttonNo)) {
						roleButton = new RoleButton();
						roleButton.setRoleId(id);
						roleButton.setButtonId(buttonNo);
						roleButtonService.save(roleButton);
					}
				}
			}
		}
	}

	@Transactional
	public void update(Role role, String userNos, String menuNos, String buttonNos) throws Exception {
		String id = role.getId();
		dao.update("RoleMapper.update",role);
		roleUserService.deleteByRole(id);
		if(!StringUtils.isEmpty(userNos)){
			String[] userNosArr = userNos.split(",");
			RoleUser roleUser = null;
			if (userNosArr != null && userNosArr.length > 0) {
				for(String userNo : userNosArr){
					if(!StringUtils.isEmpty(userNo)) {
						roleUser = new RoleUser();
						roleUser.setRoleId(id);
						roleUser.setUserId(userNo);
						roleUserService.save(roleUser);
					}
				}
			}
		}
		roleMenuService.deleteByRole(id);
		if(!StringUtils.isEmpty(menuNos)){
			String[] menuNosArr = menuNos.split(",");
			RoleMenu roleMenu = null;
			if (menuNosArr != null && menuNosArr.length > 0) {
				for(String menuNo : menuNosArr){
					if(!StringUtils.isEmpty(menuNo)) {
						roleMenu = new RoleMenu();
						roleMenu.setRoleId(id);
						roleMenu.setMenuId(menuNo);
						roleMenuService.save(roleMenu);
					}
				}
			}
		}
		roleButtonService.deleteByRole(id);
		if(!StringUtils.isEmpty(buttonNos)){
			String[] buttonNosArr = buttonNos.split(",");
			RoleButton roleButton = null;
			if (buttonNosArr != null && buttonNosArr.length > 0) {
				for(String buttonNo : buttonNosArr){
					if(!StringUtils.isEmpty(buttonNo)) {
						roleButton = new RoleButton();
						roleButton.setRoleId(id);
						roleButton.setButtonId(buttonNo);
						roleButtonService.save(roleButton);
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
		dao.delete("RoleMapper.delete",param);
		roleUserService.deleteByRole(id);
		roleMenuService.deleteByRole(id);
		roleButtonService.deleteByRole(id);
	}

	@Transactional
	public void batchDelete(List<String> ids, String updateBy) throws Exception  {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id",ids);
		param.put("updateBy",updateBy);
		dao.delete("RoleMapper.batchDelete",param);
		roleUserService.batchDeleteByRole(ids);
		roleMenuService.batchDeleteByRole(ids);
		roleButtonService.batchDeleteByRole(ids);
	}

	public List<Map<String,Object>> selectListGrid(Role role) throws Exception {
		return (List<Map<String,Object>>) dao.selectList("RoleMapper.selectListGrid",role);
	}

	public Role selectOne(String id) throws Exception {
		return (Role)dao.selectOne("RoleMapper.selectOne",id);
	}

	public List<Role> selectByUserId(String userId) throws Exception {
		return (List<Role>)dao.selectList("RoleMapper.selectByUserId",userId);
	}

    public List<Map<String,Object>> selectableListByUserId(String userId) throws Exception {
		return (List<Map<String,Object>>) dao.selectList("RoleMapper.selectableListByUserId",userId);
    }

	public List<Map<String,Object>> selectedListByUserId(String userId) throws Exception {
		return (List<Map<String,Object>>) dao.selectList("RoleMapper.selectedListByUserId",userId);
	}

    public List<Role> selectList(Role checkRoleCode) throws Exception {
		return (List<Role>)dao.selectList("RoleMapper.selectList",checkRoleCode);
    }
}
