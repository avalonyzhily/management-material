package com.system.button;

import com.base.dao.DaoSupport;
import com.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("buttonService")
public class ButtonService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Transactional
	public void save(Button button) throws Exception {
		String id = UuidUtil.get32UUID();
		button.setId(id);
		dao.save("ButtonMapper.save",button);
	}
	@Transactional
	public void update(Button button) throws Exception {
		dao.update("ButtonMapper.update",button);
	}
	@Transactional
	public void delete(String id, String updateBy) throws Exception  {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id",id);
		param.put("updateBy",updateBy);
		dao.delete("ButtonMapper.delete",param);
	}
	@Transactional
	public void batchDelete(List<String> ids, String updateBy) throws Exception  {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id",ids);
		param.put("updateBy",updateBy);
		dao.delete("ButtonMapper.batchDelete",param);
	}

	public List<Button> selectListGrid(Button button) throws Exception {
		return (List<Button>) dao.selectList("ButtonMapper.selectListGrid",button);
	}

	public Button selectOne(String id) throws Exception {
		return (Button)dao.selectOne("ButtonMapper.selectOne",id);
	}

    public List<Button> selectByUserId(String userId) throws Exception {
		return (List<Button>)dao.selectList("ButtonMapper.selectByUserId",userId);
    }

    public List<Map<String,Object>> selectListByRoleId(String menuId, String roleId) throws Exception {
		Map<String,String> param = new HashMap<String, String>();
		param.put("menuId",menuId);
		param.put("roleId",roleId);
		return (List<Map<String,Object>>) dao.selectList("ButtonMapper.selectListByRoleId",param);
    }

	public List<Map<String,Object>> selectListByRoleIdEdit(String roleId) throws Exception {
		return (List<Map<String,Object>>) dao.selectList("ButtonMapper.selectListByRoleIdEdit",roleId);
	}

    public List<Button> selectList(Button checkButtonCode) throws Exception {
		return (List<Button>)dao.selectList("ButtonMapper.selectList",checkButtonCode);
    }
}
