package com.fit.config.shiro;

import com.fit.entity.PageData;
import com.fit.service.system.UsersService;
import com.fit.util.Const;
import com.fit.util.Jurisdiction;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collection;
import java.util.HashSet;

/**
 * Shiro身份认证
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UsersService usersService;

    /**
     * 登录认证,登录信息和用户验证信息验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;    //UsernamePasswordToken用于存放提交的登录信息
        PageData pd = new PageData();
        pd.put("USERNAME", token.getUsername());
        try {
            pd = usersService.findByUsername(pd);
            if (pd != null) {
                return new SimpleAuthenticationInfo(pd.getString("USERNAME"), pd.getString("PASSWORD"), getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String USERNAME = (String) super.getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Session session = Jurisdiction.getSession();
        Collection<String> shiroSet = new HashSet<String>();
        shiroSet = (Collection<String>) session.getAttribute(USERNAME + Const.SHIROSET);
        if (null != shiroSet) {
            info.addStringPermissions(shiroSet);
            return info;
        } else {
            return null;
        }
    }
}
