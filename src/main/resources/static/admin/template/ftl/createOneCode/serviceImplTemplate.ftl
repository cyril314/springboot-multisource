package com.fit.service.${packageName}.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fit.entity.Page;
import com.fit.entity.PageData;
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
	* 批量删除
	* @param ArrayDATA_IDS
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		${objectNameLower}Mapper.deleteAll(ArrayDATA_IDS);
	}
}