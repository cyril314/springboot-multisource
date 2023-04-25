package com.fit.service.system;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.entity.system.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UsersService {

    /**
     * 通过用户名获取用户信息
     */
    public PageData findByUsername(PageData pd) throws Exception;

    /**
     * 通过用户ID获取用户信息
     */
    public PageData findById(PageData pd) throws Exception;

    /**
     * 用户列表
     */
    public List<PageData> userlistPage(Page page) throws Exception;

    /**
     * 通过用户ID获取用户信息和角色信息
     */
    public User getUserAndRoleById(String USER_ID) throws Exception;

    /**
     * 保存用户IP
     */
    public void saveIP(PageData pd) throws Exception;

    /**
     * 通过邮箱获取数据
     */
    public PageData findByEmail(PageData pd) throws Exception;

    /**
     * 通过编码获取数据
     */
    public PageData findByNumbe(PageData pd) throws Exception;

    /**
     * 列出某角色下的所有用户
     */
    public List<PageData> listAllUserByRoldId(PageData pd) throws Exception;

    /**
     * 用户列表(全部)
     */
    public List<PageData> listAllUser(PageData pd) throws Exception;

    /**
     * 用户列表(弹窗选择用)
     */
    public List<PageData> listUsersBystaff(Page page) throws Exception;

    /**
     * 保存用户
     */
    public void saveUser(PageData pd) throws Exception;

    /**
     * 保存用户系统皮肤
     */
    public void saveSkin(PageData pd) throws Exception;

    /**
     * 修改用户
     */
    public void editUser(PageData pd) throws Exception;

    /**
     * 删除用户
     */
    public void deleteUser(PageData pd) throws Exception;

    /**
     * 批量删除用户
     */
    public void deleteAllUser(String[] USER_IDS) throws Exception;

}
