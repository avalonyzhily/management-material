package com.system.button;

import com.system.user.User;
import com.core.constant.Consts;
import com.base.entity.ResponseObj;
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
 * @Date 2017年6月27日
 */
@Controller
@RequestMapping(value = "system/button")
public class ButtonController{

	private static Logger logger = LoggerFactory.getLogger(ButtonController.class);

	@Resource(name = "buttonService")
	private ButtonService buttonService;

	@RequestMapping(value = "save",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseObj save(@RequestBody Button button){
		try {
			Session session = SecurityUtils.getSubject().getSession();
			User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
			String id = button.getId();
			//按钮编码去重
			String buttonCode = button.getButtonCode();
			Button checkButtonCode = new Button();
			checkButtonCode.setButtonCode(buttonCode);
			List<Button> buttons = buttonService.selectList(checkButtonCode);
			if(StringUtils.isEmpty(id)) {
				if(buttons!=null&&buttons.size()>0){
					return new ResponseObj("fail", "按钮编码已存在", button);
				}
				button.setCreateBy(loginUser.getId());
				button.setUpdateBy(loginUser.getId());
				buttonService.save(button);
				return new ResponseObj("success", "保存成功", button);
			}else {
				if(buttons!=null&&buttons.size()>1){
					return new ResponseObj("fail", "按钮编码已存在", button);
				}else if(buttons!=null&&buttons.size()==1){
					Button buttonTemp = buttons.get(0);
					String idTemp = buttonTemp.getId();
					if(!id.equals(idTemp)){
						return new ResponseObj("fail", "按钮编码已存在", button);
					}
				}
				button.setUpdateBy(loginUser.getId());
				buttonService.update(button);
				return new ResponseObj("success","更新成功",null);
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
				buttonService.batchDelete(ids,loginUser.getId());
			}else {
				buttonService.delete(idsStr,loginUser.getId());
			}
			return new ResponseObj("success","删除成功",null);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseObj("fail","删除失败",null);
		}
	}
	@RequestMapping(value = "selectListGridHasPage")
	@ResponseBody
	public Map<String,Object> selectListGridHasPage(Button button,
													@RequestParam("page") Integer page,
													@RequestParam("rows")Integer pageSize) throws Exception {
		PageHelper.startPage(page,pageSize);
		List<Button> buttons = buttonService.selectListGrid(button);
		PageInfo p = new PageInfo(buttons);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",p.getTotal());
		res.put("rows",buttons);
		return res;
	}
	@RequestMapping(value = "selectListGridNoPage")
	@ResponseBody
	public Map<String,Object> selectListGridNoPage(Button button) throws Exception {
		List<Button> buttons = buttonService.selectListGrid(button);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",buttons.size());
		res.put("rows",buttons);
		return res;
	}
	@RequestMapping(value = "selectListByRoleId")
	@ResponseBody
	public Map<String,Object> selectListByRoleId(String menuId,String roleId) throws Exception {
		List<Map<String,Object>> buttons = buttonService.selectListByRoleId(menuId,roleId);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",buttons.size());
		res.put("rows",buttons);
		return res;
	}
	@RequestMapping(value = "selectListByRoleIdEdit")
	@ResponseBody
	public List<Map<String,Object>> selectListByRoleIdEdit(String roleId) throws Exception {
		List<Map<String,Object>> buttons = buttonService.selectListByRoleIdEdit(roleId);
		return buttons;
	}
	@RequestMapping("selectOne")
	@ResponseBody
	public Button selectOne(String id) throws Exception {
		Button button = buttonService.selectOne(id);
		return button;
	}

	@RequestMapping(value = "index")
	public String buttonindex(Model model){
		Subject s =	SecurityUtils.getSubject();
		User currentUser = (User)s.getSession().getAttribute(Consts.LOGIN_USER);
		String userAccount = currentUser.getUserAccount();
		model.addAttribute("s",s);
		model.addAttribute("userAccount",userAccount);
		Map<String,Map<String,String>> allDict = (Map<String,Map<String,String>>)s.getSession().getAttribute(Consts.ALL_DICTIONARY);
		model.addAttribute("allDict",allDict);
		return "system/button/buttonindex";
	}

	@RequestMapping(value = "add")
	public String buttonadd() throws Exception {
		return "system/button/buttonadd";
	}

	@RequestMapping(value = "edit")
	public String buttonedit(String id,Model model) throws Exception {
		Button button = buttonService.selectOne(id);
		model.addAttribute("button",button);
		return "system/button/buttonedit";
	}

	@RequestMapping(value = "look")
	public String buttonlook(String id,Model model) throws Exception {
		Button button = buttonService.selectOne(id);
		model.addAttribute("button",button);
		return "system/button/buttonlook";
	}
}
