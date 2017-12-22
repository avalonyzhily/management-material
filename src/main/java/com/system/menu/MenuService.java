package com.system.menu;

import com.base.dao.DaoSupport;
import com.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("menuService")
public class MenuService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Transactional
	public void save(Menu menu) throws Exception {
		String id = UuidUtil.get32UUID();
		menu.setId(id);
		dao.save("MenuMapper.save",menu);
	}
	@Transactional
	public void update(Menu menu) throws Exception {
		dao.update("MenuMapper.update",menu);
	}
	@Transactional
	public void delete(String id, String updateBy) throws Exception  {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id",id);
		param.put("updateBy",updateBy);
		dao.delete("MenuMapper.delete",param);
	}
	@Transactional
	public void batchDelete(List<String> ids, String updateBy) throws Exception  {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("list",ids);
		param.put("updateBy",updateBy);
		dao.delete("MenuMapper.batchDelete",param);
	}

	public List<Menu> selectList(Menu menu) throws Exception {
		return (List<Menu>) dao.selectList("MenuMapper.selectList",menu);
	}

	public Menu selectOne(String id) throws Exception {
		return (Menu)dao.selectOne("MenuMapper.selectOne",id);
	}

	public List<Menu> selectByUserId(String userId) throws Exception {
		return (List<Menu>)dao.selectList("MenuMapper.selectByUserId",userId);
	}

	public List<Map<String,Object>> selectListGrid(Menu menu) throws Exception {
		return (List<Map<String,Object>>) dao.selectList("MenuMapper.selectListGrid",menu);
	}

	public List<Map<String,Object>> selectTree() throws Exception {
		return (List<Map<String,Object>>) dao.selectList("MenuMapper.selectTree",null);
	}

    public List<Map<String,Object>> selectTreeByRoleId(String roleId) throws Exception {
		return (List<Map<String,Object>>) dao.selectList("MenuMapper.selectTreeByRoleId",roleId);
    }
}
