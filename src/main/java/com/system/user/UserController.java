package com.system.user;


import com.base.entity.ResponseObj;
import com.core.constant.Consts;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leicb
 * @Date 2017年6月29日
 */
@Controller
@RequestMapping(value = "system/user")
public class UserController{

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "userService")
	private UserService userService;

	@RequestMapping(value = "save",method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseObj save(@RequestBody RequestUserObj requestUserObj){
		try {
			if(requestUserObj!=null) {
				Session session = SecurityUtils.getSubject().getSession();
				User loginUser = (User) session.getAttribute(Consts.LOGIN_USER);
				User user = requestUserObj.getUser();
				String roleNos = requestUserObj.getRoleNos();
				String id = user.getId();
				//用户名去重
				String userAccount = user.getUserAccount();
				User checkUserAccount = new User();
				checkUserAccount.setUserAccount(userAccount);
				List<User> users = userService.selectList(checkUserAccount);
				//用户编号去重
				String userCode = user.getUserAccount();
				User checkUserCode= new User();
				checkUserCode.setUserCode(userCode);
				List<User> users1 = userService.selectList(checkUserCode);
				if (StringUtils.isEmpty(id)) {
					if(users!=null&&users.size()>0){
						return new ResponseObj("fail", "用户名已存在", user);
					}
					if(users1!=null&&users1.size()>0){
						return new ResponseObj("fail", "用户编号已存在", user);
					}
					user.setCreateBy(loginUser.getId());
					user.setUpdateBy(loginUser.getId());
					userService.save(user,roleNos);
					return new ResponseObj("success", "保存成功", user);
				} else {
					if(users!=null&&users.size()>1){
						return new ResponseObj("fail", "用户名已存在", user);
					}else if(users!=null&&users.size()==1){
						User userTemp = users.get(0);
						String idTemp = userTemp.getId();
						if(!id.equals(idTemp)){
							return new ResponseObj("fail", "用户名已存在", user);
						}
					}
					if(users1!=null&&users1.size()>1){
						return new ResponseObj("fail", "用户编号已存在", user);
					}else if(users1!=null&&users1.size()==1){
						User userTemp1 = users1.get(0);
						String idTemp1 = userTemp1.getId();
						if(!id.equals(idTemp1)){
							return new ResponseObj("fail", "用户编号已存在", user);
						}
					}
					user.setUpdateBy(loginUser.getId());
					userService.update(user,roleNos);
					return new ResponseObj("success", "更新成功", null);
				}
			}else {
				return new ResponseObj("success", "数据传输失败", null);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseObj("fail","保存失败",null);
		}
	}

	@RequestMapping("updateState")
	@ResponseBody
	public ResponseObj updateState(String idsStr,Integer useState) throws Exception {
		try{
			Session session = SecurityUtils.getSubject().getSession();
			User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
			if(idsStr.indexOf(",")>-1) {
				List<String> ids = Arrays.asList(idsStr.split(","));
				userService.batchUpdateState(ids,useState,loginUser.getId());
			}else {
				userService.updateState(idsStr,useState,loginUser.getId());
			}
			return new ResponseObj("success","更新成功",null);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseObj("fail","更新失败",null);
		}
	}

	@RequestMapping("delete")
	@ResponseBody
	public ResponseObj delete(String idsStr) throws Exception {
		try{
			Session session = SecurityUtils.getSubject().getSession();
			User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
			if(idsStr.indexOf(",")>-1) {
				List<String> ids = Arrays.asList(idsStr.split(","));
				userService.batchDelete(ids,loginUser.getId());
			}else {
				userService.delete(idsStr,loginUser.getId());
			}
			return new ResponseObj("success","删除成功",null);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseObj("fail","删除失败",null);
		}
	}
	@RequestMapping(value = "updatePwd",method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseObj updatePwd(@RequestBody User user) throws Exception {
		String sha1pwd = new SimpleHash("SHA-1", user.getUserAccount(), user.getPassword()).toString();
		user.setPassword(sha1pwd);
		User userTemp = userService.checkLogin(user);
		if(userTemp!=null) {
			String sha1pwdnew = new SimpleHash("SHA-1", user.getUserAccount(), user.getNewPassword()).toString();
			userTemp.setPassword(sha1pwdnew);
			userService.updatePwd(userTemp);
			return new ResponseObj("success","修改成功",null);
		}else {
			return new ResponseObj("fail","修改失败",null);
		}
	}
	@RequestMapping(value = "selectListGridHasPage")
	@ResponseBody
	public Map<String,Object> selectListGridHasPage(User user,
													@RequestParam("page") Integer page,
													@RequestParam("rows")Integer pageSize) throws Exception {
		PageHelper.startPage(page,pageSize);
		List<Map<String,Object>> users = userService.selectListGrid(user);
		PageInfo p = new PageInfo(users);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",p.getTotal());
		res.put("rows",users);
		return res;
	}
	@RequestMapping(value = "selectListGridNoPage")
	@ResponseBody
	public Map<String,Object> selectListGridNoPage(User user) throws Exception {
		List<Map<String,Object>> users = userService.selectListGrid(user);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",users.size());
		res.put("rows",users);
		return res;
	}
	@RequestMapping(value = "selectableListByRoleId")
	@ResponseBody
	public Map<String,Object> selectableListByRoleId(String roleId) throws Exception {
		List<Map<String,Object>> users = userService.selectableListByRoleId(roleId);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",users.size());
		res.put("rows",users);
		return res;
	}
	@RequestMapping(value = "selectedListByRoleId")
	@ResponseBody
	public Map<String,Object> selectedListByRoleId(String roleId) throws Exception {
		List<Map<String,Object>> users = userService.selectedListByRoleId(roleId);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",users.size());
		res.put("rows",users);
		return res;
	}

	@RequestMapping("selectOne")
	@ResponseBody
	public User selectOne(String id) throws Exception {
		User user = userService.selectOne(id);
		return user;
	}

	@RequestMapping(value = "index")
	public String userindex(Model model){
		Subject s =	SecurityUtils.getSubject();
		User currentUser = (User)s.getSession().getAttribute(Consts.LOGIN_USER);
		String userAccount = currentUser.getUserAccount();
		model.addAttribute("s",s);
		model.addAttribute("userAccount",userAccount);
		Map<String,Map<String,String>> allDict = (Map<String,Map<String,String>>)s.getSession().getAttribute(Consts.ALL_DICTIONARY);
		model.addAttribute("allDict",allDict);
		return "system/user/userindex";
	}

	@RequestMapping(value = "add")
	public String useradd() throws Exception {
		return "system/user/useradd";
	}

	@RequestMapping(value = "edit")
	public String useredit(String id,Model model) throws Exception {
		User user = userService.selectOne(id);
		model.addAttribute("user",user);
		return "system/user/useredit";
	}
	@RequestMapping(value = "pwdedit")
	public String pwdedit(String id,Model model) throws Exception {
		User user = userService.selectOne(id);
		model.addAttribute("user",user);
		return "system/user/pwdedit";
	}
	@RequestMapping(value = "look")
	public String userlook(String id,Model model) throws Exception {
		User user = userService.selectOne(id);
		model.addAttribute("user",user);
		return "system/user/userlook";
	}
}
