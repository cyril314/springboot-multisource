package com.fit.mapper.dsno2.system;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.entity.system.User;

import java.util.List;

/**
 * 用户Mapper
 */
public interface Users2Mapper {

    /**
     * 通过用户获取数据
     */
    PageData findByUsername(PageData pd);

    /**
     * 用户列表
     */
    List<PageData> userlistPage(Page page);

    /**
     * 通过用户ID获取用户信息和角色信息
     */
    User getUserAndRoleById(String USER_ID);

    /**
     * 通过邮箱获取数据
     */
    PageData findByEmail(PageData pd);

    /**
     * 通过编码获取数据
     */
    PageData findByNumbe(PageData pd);

    /**
     * 列出某角色下的所有用户
     */
    List<PageData> listAllUserByRoldId(PageData pd);

    /**
     * 通过用户ID获取数据
     */
    PageData findById(PageData pd);

    /**
     * 保存用户IP
     */
    void saveIP(PageData pd);

    /**
     * 保存用户
     */
    void saveUser(PageData pd);

    /**
     * 保存用户系统皮肤
     */
    void saveSkin(PageData pd);

    /**
     * 修改用户
     */
    void editUser(PageData pd);

    /**
     * 删除用户
     */
    void deleteUser(PageData pd);

    /**
     * 批量删除用户
     */
    void deleteAllUser(String[] USER_IDS);

}
