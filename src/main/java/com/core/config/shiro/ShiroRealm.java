package com.core.config.shiro;

import com.system.button.Button;
import com.system.menu.Menu;
import com.system.user.User;
import com.core.constant.Consts;
import com.system.button.ButtonService;
import com.system.menu.MenuService;
import com.system.role.Role;
import com.system.role.RoleService;
import com.system.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author leicb
 * @Date 2017年6月28日
 */
public class ShiroRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "menuService")
	private MenuService menuService;
	@Resource(name = "buttonService")
	private ButtonService buttonService;
	/*
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = new User();
		user.setUserAccount(token.getUsername());
		user.setPassword(new String(token.getPassword()));
		// 从数据库获取对应用户名密码的用户
		try {
			user = userService.checkLogin(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AuthenticationException(Consts.CHECK_FAIL);
		}
		if (null == user) {
			throw new AccountException(Consts.ACCOUNT_PWD_ERROR);
		}else if(!user.getUseState().equals(0)){
			/**
			 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
			 */
			throw new DisabledAccountException(Consts.USER_INVAILD);
		}else{
			//更新登录时间 last login time
			user.setLastLogin(new Date());
			try {
				userService.updateLastLogin(user);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new AuthenticationException(Consts.CHECK_FAIL);
			}
		}
		SecurityUtils.getSubject().getSession().setAttribute(Consts.LOGIN_USER,user);
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}

	/*
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("权限认证方法：MyShiroRealm.doGetAuthenticationInfo()");
		User token = (User) SecurityUtils.getSubject().getPrincipal();
		String userId = token.getId();
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();

		//根据用户ID查询角色（role），放入到Authorization里。
		List<Role> roleList = null;
		try {
			roleList = roleService.selectByUserId(userId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		Set<String> roleSet = new HashSet<String>();
		for(Role role : roleList){
			roleSet.add(role.getRoleCode());
		}
		info.setRoles(roleSet);
		//根据用户ID查询权限（permission），放入到Authorization里。
		//菜单权限
		List<Menu> menuList = null;
		try {
			menuList = menuService.selectByUserId(userId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		Set<String> permissionSet = new HashSet<String>();
		if(menuList!=null) {
			for (Menu menu : menuList) {
				permissionSet.add(menu.getMenuCode());
			}
		}
		//按钮权限
		List<Button> buttonList = null;
		try {
			buttonList = buttonService.selectByUserId(userId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if(buttonList!=null) {
			for (Button button : buttonList) {
				permissionSet.add(button.getButtonCode());
			}
		}
		info.setStringPermissions(permissionSet);
		return info;

	}

}
