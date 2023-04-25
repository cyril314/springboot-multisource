package com.fit.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.util.StringUtils;

/**
 * 权限工具类
 */
public class Jurisdiction {

    /**
     * shiro管理的session
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取当前登录的用户名
     */
    public static String getUsername() {
        Object obj = getSession().getAttribute(Const.SESSION_USERNAME);
        if (StringUtils.isEmpty(obj)) {
            return "";
        } else {
            return obj.toString();
        }
    }

    /**
     * 获取当前登录的姓名
     */
    public static String getName() {
        return getSession().getAttribute(Const.SESSION_U_NAME).toString();
    }

    /**
     * 获取当前登录用户的角色编码
     */
    public static String getRnumbers() {
        Object obj = getSession().getAttribute(Const.SESSION_RNUMBERS);
        if (StringUtils.isEmpty(obj)) {
            return null;
        } else {
            return obj.toString();
        }
    }
}
