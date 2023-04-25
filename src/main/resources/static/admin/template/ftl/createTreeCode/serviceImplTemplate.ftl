package com.fit.service.${packageName}.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.entity.${packageName}.${objectName};
import com.fit.mapper.dsno1.${packageName}.${objectName}Mapper;
import com.fit.service.${packageName}.${objectName}Service;

/** 
 *  ${TITLE}接口实现类
 * 时间：${nowDate?string("yyyy-MM-dd")}
 * @version
 */
@Service
@Transactional //开启事物
public class ${objectName}ServiceImpl implements ${objectName}Service{

	@Autowired
	private ${objectName}Mapper ${objectNameLower}Mapper;
	
	/**
	 * 新增
	 */
	public void save(PageData pd)throws Exception{
		${objectNameLower}Mapper.save(pd);
	}
	
	/**
	 * 删除
	 */
	public void delete(PageData pd)throws Exception{
		${objectNameLower}Mapper.delete(pd);
	}
	
	/**
	 * 修改
	 */
	public void edit(PageData pd)throws Exception{
		${objectNameLower}Mapper.edit(pd);
	}
	
	/**
	 * 列表
	 */
	public List<PageData> list(Page page)throws Exception{
		return ${objectNameLower}Mapper.datalistPage(page);
	}
	
	/**
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return ${objectNameLower}Mapper.listAll(pd);
	}
	
	/**
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd)throws Exception{
		return ${objectNameLower}Mapper.findById(pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 */
	public List<${objectName}> listByParentId(String parentId) throws Exception {
		return ${objectNameLower}Mapper.listByParentId(parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 */
	public List<${objectName}> listTree(String parentId) throws Exception {
		List<${objectName}> valueList = this.listByParentId(parentId);
		for(${objectName} fhentity : valueList){
			fhentity.setTreeurl("${objectNameLower}_list.html?${objectNameUpper}_ID="+fhentity.get${objectNameUpper}_ID());
			fhentity.setSub${objectName}(this.listTree(fhentity.get${objectNameUpper}_ID()));
			fhentity.setTarget("treeFrame");
		}
		return valueList;
	}
}