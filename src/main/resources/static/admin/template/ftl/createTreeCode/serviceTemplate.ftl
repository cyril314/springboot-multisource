package com.fit.service.${packageName};

import java.util.List;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.entity.${packageName}.${objectName};

/** 
 *  ${TITLE}接口
 * 时间：${nowDate?string("yyyy-MM-dd")}
 * @version
 */
public interface ${objectName}Service{

	/**
	 * 新增
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 删除
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 修改
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 列表
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 */
	public List<${objectName}> listByParentId(String parentId) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 */
	public List<${objectName}> listTree(String parentId) throws Exception;
}