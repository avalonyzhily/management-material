package com.system.role;

import com.core.constant.Consts;
import com.base.entity.ResponseObj;
import com.system.user.User;
import com.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leicb
 * @Date 2017年6月28日
 */
@Controller
@RequestMapping(value = "system/role")
public class RoleController {

	private static Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Resource(name = "roleService")
	private RoleService roleService;

	@RequestMapping(value = "save",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseObj save(@RequestBody RequestRoleObj requestRoleObj){
		try {
			if(requestRoleObj!=null) {
				Session session = SecurityUtils.getSubject().getSession();
				User loginUser = (User) session.getAttribute(Consts.LOGIN_USER);
				loginUser.setId(UuidUtil.get32UUID());
				Role role = requestRoleObj.getRole();
				String userNos = requestRoleObj.getUserNos();
				String menuNos = requestRoleObj.getMenuNos();
				String buttonNos = requestRoleObj.getButtonNos();
				String id = role.getId();

				//角色代码去重
				String roleCode = role.getRoleCode();
				Role checkRoleCode = new Role();
				checkRoleCode.setRoleCode(roleCode);
				List<Role> roles = roleService.selectList(checkRoleCode);

				if (StringUtils.isEmpty(id)) {
					if(roles!=null&&roles.size()>0){
						return new ResponseObj("fail", "角色代码已存在", role);
					}
					role.setCreateBy(loginUser.getId());
					role.setUpdateBy(loginUser.getId());
					roleService.save(role, userNos, menuNos, buttonNos);
					return new ResponseObj("success", "保存成功", role);
				} else {
					if(roles!=null&&roles.size()>1){
						return new ResponseObj("fail", "角色代码已存在", role);
					}else if(roles!=null&&roles.size()==1){
						Role roleTemp = roles.get(0);
						String idTemp = roleTemp.getId();
						if(!id.equals(idTemp)){
							return new ResponseObj("fail", "角色代码已存在", role);
						}
					}
					role.setUpdateBy(loginUser.getId());
					roleService.update(role, userNos, menuNos, buttonNos);
					return new ResponseObj("success", "更新成功", null);
				}
			}else {
				return new ResponseObj("fail","数据传输失败",null);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseObj("fail","保存失败",null);
		}
	}

	@RequestMapping("delete")
	@ResponseBody
	public ResponseObj delete(String idsStr, HttpServletRequest req) throws Exception {
		try{
			Session session = SecurityUtils.getSubject().getSession();
			User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
			if(idsStr.indexOf(",")>-1) {
				List<String> ids = Arrays.asList(idsStr.split(","));
				roleService.batchDelete(ids,loginUser.getId());
			}else {
				roleService.delete(idsStr,loginUser.getId());
			}
			return new ResponseObj("success","删除成功",null);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseObj("fail","删除失败",null);
		}
	}
	@RequestMapping(value = "selectListGridHasPage")
	@ResponseBody
	public Map<String,Object> selectListGridHasPage(Role role,
													@RequestParam("page") Integer page,
													@RequestParam("rows")Integer pageSize) throws Exception {
		PageHelper.startPage(page,pageSize);
		List<Map<String,Object>> roles = roleService.selectListGrid(role);
		PageInfo p = new PageInfo(roles);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",p.getTotal());
		res.put("rows",roles);
		return res;
	}
	@RequestMapping(value = "selectListGridNoPage")
	@ResponseBody
	public Map<String,Object> selectListGridNoPage(Role role) throws Exception {
		List<Map<String,Object>> roles = roleService.selectListGrid(role);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",roles.size());
		res.put("rows",roles);
		return res;
	}
	/**
	 * 根据用户id查找可选角色
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectableListByUserId")
	@ResponseBody
	public Map<String,Object> selectableListByUserId(String userId) throws Exception {
		List<Map<String,Object>> roles = roleService.selectableListByUserId(userId);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",roles.size());
		res.put("rows",roles);
		return res;
	}

	/**
	 * 根据用户id查找已选角色
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectedListByUserId")
	@ResponseBody
	public Map<String,Object> selectedListByUserId(String userId) throws Exception {
		List<Map<String,Object>> roles = roleService.selectedListByUserId(userId);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",roles.size());
		res.put("rows",roles);
		return res;
	}
	@RequestMapping("selectOne")
	@ResponseBody
	public Role selectOne(String id) throws Exception {
		Role role = roleService.selectOne(id);
		return role;
	}

	@RequestMapping(value = "index")
	public String roleindex(Model model){
		Subject s =	SecurityUtils.getSubject();
		User currentUser = (User)s.getSession().getAttribute(Consts.LOGIN_USER);
		String userAccount = currentUser.getUserAccount();
		model.addAttribute("s",s);
		model.addAttribute("userAccount",userAccount);
		Map<String,Map<String,String>> allDict = (Map<String,Map<String,String>>)s.getSession().getAttribute(Consts.ALL_DICTIONARY);
		model.addAttribute("allDict",allDict);
		return "system/role/roleindex";
	}

	@RequestMapping(value = "add")
	public String roleadd() throws Exception {
		return "system/role/roleadd";
	}

	@RequestMapping(value = "edit")
	public String roleedit(String id,Model model) throws Exception {
		Role role = roleService.selectOne(id);
		model.addAttribute("role",role);
		return "system/role/roleedit";
	}

	@RequestMapping(value = "look")
	public String rolelook(String id,Model model) throws Exception {
		Role role = roleService.selectOne(id);
		model.addAttribute("role",role);
		return "system/role/rolelook";
	}
}
