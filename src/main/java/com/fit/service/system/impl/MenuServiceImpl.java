package com.fit.service.system.impl;

import com.fit.entity.PageData;
import com.fit.entity.system.Menu;
import com.fit.service.system.MenuService;

import java.util.List;

import com.fit.mapper.dsno1.system.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 菜单服务接口实现类
 */
@Service
@Transactional //开启事物
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 新增菜单
     *
     * @param menu
     * @throws Exception
     */
    @CacheEvict(value = "menucache", allEntries = true)
    public void addMenu(Menu menu) throws Exception {
        menuMapper.addMenu(menu);
    }

    /**
     * 保存修改菜单
     *
     * @param menu
     */
    @CacheEvict(value = "menucache", allEntries = true)
    public void edit(Menu menu) throws Exception {
        menuMapper.edit(menu);
    }

    /**
     * 通过菜单ID获取数据
     *
     * @param pd
     */
    public PageData getMenuById(PageData pd) throws Exception {
        return menuMapper.getMenuById(pd);
    }

    /**
     * 获取最大的菜单ID
     *
     * @param pd
     */
    public PageData findMaxId(PageData pd) throws Exception {
        return menuMapper.findMaxId(pd);
    }

    /**
     * 通过ID获取其子一级菜单
     *
     * @param parentId
     */
    @Cacheable(key = "'menu-'+#parentId", value = "menucache")
    public List<Menu> listSubMenuByParentId(String parentId) throws Exception {
        return menuMapper.listSubMenuByParentId(parentId);
    }

    /**
     * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
     *
     * @param MENU_ID
     */
    public List<Menu> listAllMenu(String MENU_ID) throws Exception {
        List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
        for (Menu menu : menuList) {
            menu.setMENU_URL("menu_edit.html?MENU_ID=" + menu.getMENU_ID());
            menu.setSubMenu(this.listAllMenu(menu.getMENU_ID()));
            menu.setTarget("treeFrame");
        }
        return menuList;
    }

    /**
     * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
     *
     * @param MENU_ID
     */
    public List<Menu> listAllMenuQx(String MENU_ID) throws Exception {
        List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
        for (Menu menu : menuList) {
            menu.setSubMenu(this.listAllMenuQx(menu.getMENU_ID()));
            menu.setTarget("treeFrame");
        }
        return menuList;
    }

    /**
     * 删除菜单
     *
     * @param MENU_ID
     */
    @CacheEvict(value = "menucache", allEntries = true)
    public void deleteMenuById(String MENU_ID) throws Exception {
        menuMapper.deleteMenuById(MENU_ID);
    }

    /**
     * 保存菜单图标
     *
     * @param pd
     */
    @CacheEvict(value = "menucache", allEntries = true)
    public void editicon(PageData pd) throws Exception {
        menuMapper.editicon(pd);
    }
}
