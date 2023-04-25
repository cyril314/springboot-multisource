package com.fit.controller.base;

import com.fit.entity.PageData;
import org.apache.shiro.session.Session;
import com.fit.util.Const;
import com.fit.util.Jurisdiction;
import com.fit.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 所有处理类父类
 */
public class BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    /**
     * new PageData对象
     */
    public PageData getPageData() {
        return new PageData(this.getRequest());
    }

    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    /**
     * 得到32位的uuid
     */
    public String get32UUID() {
        return UuidUtil.get32UUID();
    }

    /**
     * 清理session
     */
    public void removeSession(String USERNAME) {
        Session session = Jurisdiction.getSession();    //以下清除session缓存
        session.removeAttribute(Const.SESSION_USER);
        session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
        session.removeAttribute(USERNAME + Const.SESSION_ALLMENU);
        session.removeAttribute(USERNAME + Const.SHIROSET);
        session.removeAttribute(Const.SESSION_USERNAME);
        session.removeAttribute(Const.SESSION_U_NAME);
        session.removeAttribute(Const.SESSION_USERROL);
        session.removeAttribute(Const.SESSION_RNUMBERS);
        session.removeAttribute(Const.SKIN);
    }
}
