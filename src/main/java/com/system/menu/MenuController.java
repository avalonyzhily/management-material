package com.system.menu;

import com.base.entity.ResponseObj;
import com.system.user.User;
import com.core.constant.Consts;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leicb
 * @Date 2017年6月27日
 */
@Controller
@RequestMapping(value = "system/menu")
public class MenuController {

	private static Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Resource(name = "menuService")
	private MenuService menuService;

	@RequestMapping(value = "save",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseObj save(@RequestBody Menu menu){
		try {
			Session session = SecurityUtils.getSubject().getSession();
			User loginUser = (User)session.getAttribute(Consts.LOGIN_USER);
			String id = menu.getId();
			String parentId = menu.getParentId();
			if(!StringUtils.isEmpty(parentId)
					&&!"0".equals(parentId)){
				menu.setMenuType("O");
			}else {
				menu.setMenuType("E");
				menu.setParentId("0");
			}
			//菜单编码去重
			String menuCode = menu.getMenuCode();
			Menu checkMenuCode = new Menu();
			checkMenuCode.setMenuCode(menuCode);
			List<Menu> menus = menuService.selectList(checkMenuCode);
			if(StringUtils.isEmpty(id)) {
				if(menus!=null&&menus.size()>0){
					return new ResponseObj("fail", "菜单编码已存在", menu);
				}
				menu.setCreateBy(loginUser.getId());
				menu.setUpdateBy(loginUser.getId());
				menuService.save(menu);
				return new ResponseObj("success", "保存成功", menu);
			}else {
				if(menus!=null&&menus.size()>1){
					return new ResponseObj("fail", "菜单编码已存在", menu);
				}else if(menus!=null&&menus.size()==1){
					Menu menuTemp = menus.get(0);
					String idTemp = menuTemp.getId();
					if(!id.equals(idTemp)){
						return new ResponseObj("fail", "菜单编码已存在", menu);
					}
				}
				menu.setUpdateBy(loginUser.getId());
				menuService.update(menu);
				return new ResponseObj("success","更新成功",null);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseObj("fail","保存失败",null);
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
				menuService.batchDelete(ids,loginUser.getId());
			}else {
				menuService.delete(idsStr,loginUser.getId());
			}
			return new ResponseObj("success","删除成功",null);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseObj("fail","删除失败",null);
		}
	}

	@RequestMapping(value = "selectList")
	@ResponseBody
	public List<Menu> selectList(Menu menu) throws Exception {
		List<Menu> menus = menuService.selectList(menu);
		return menus;
	}
	@RequestMapping(value = "selectListHasRoot")
	@ResponseBody
	public List<Menu> selectListHasRoot(Menu menu) throws Exception {
		menu.setMenuType("E");
		List<Menu> menus = menuService.selectList(menu);
		Menu menu1 = new Menu();
		menu1.setId("0");
		menu1.setMenuName("顶级目录");
		menus.add(0,menu1);
		return menus;
	}
	@RequestMapping(value = "selectTree")
	@ResponseBody
	public List<Map<String,Object>> selectTree() throws Exception {
		List<Map<String,Object>> menus = menuService.selectTree();
		return menus;
	}
	@RequestMapping(value = "selectTreeByRoleId")
	@ResponseBody
	public List<Map<String,Object>> selectTreeByRoleId(String roleId) throws Exception {
		List<Map<String,Object>> menus = menuService.selectTreeByRoleId(roleId);
		return menus;
	}
	@RequestMapping(value = "selectListGridNoPage")
	@ResponseBody
	public Map<String,Object> selectListGridNoPage(Menu menu) throws Exception {
		List<Map<String,Object>> menus = menuService.selectListGrid(menu);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("total",menus.size());
		res.put("rows",menus);
		return res;
	}
	@RequestMapping("selectOne")
	@ResponseBody
	public Menu selectOne(String id) throws Exception {
		Menu menu = menuService.selectOne(id);
		return menu;
	}

	@RequestMapping(value = "index")
	public String menuindex(Model model){
		Subject s =	SecurityUtils.getSubject();
		User currentUser = (User)s.getSession().getAttribute(Consts.LOGIN_USER);
		String userAccount = currentUser.getUserAccount();
		model.addAttribute("s",s);
		model.addAttribute("userAccount",userAccount);
		Map<String,Map<String,String>> allDict = (Map<String,Map<String,String>>)s.getSession().getAttribute(Consts.ALL_DICTIONARY);
		model.addAttribute("allDict",allDict);
		return "system/menu/menuindex";
	}

	@RequestMapping(value = "add")
	public String menuadd() throws Exception {
		return "system/menu/menuadd";
	}

	@RequestMapping(value = "edit")
	public String menuedit(String id,Model model) throws Exception {
		Menu menu = menuService.selectOne(id);
		model.addAttribute("menu",menu);
		return "system/menu/menuedit";
	}

	@RequestMapping(value = "look")
	public String menulook(String id,Model model) throws Exception {
		Menu menu = menuService.selectOne(id);
		model.addAttribute("menu",menu);
		return "system/menu/menulook";
	}
}
