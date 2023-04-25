package com.fit.service.bm.impl;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.entity.bm.Bumen;
import com.fit.mapper.dsno1.bm.BumenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fit.service.bm.BumenService;

/** 
 *  部门管理接口实现类
 */
@Service
@Transactional //开启事物
public class BumenServiceImpl implements BumenService{

	@Autowired
	private BumenMapper bumenMapper;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		bumenMapper.save(pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		bumenMapper.delete(pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		bumenMapper.edit(pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception{
		return bumenMapper.datalistPage(page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return bumenMapper.listAll(pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return bumenMapper.findById(pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Bumen> listByParentId(String parentId) throws Exception {
		return bumenMapper.listByParentId(parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Bumen> listTree(String parentId) throws Exception {
		List<Bumen> valueList = this.listByParentId(parentId);
		for(Bumen fhentity : valueList){
			fhentity.setTreeurl("bumen_list.html?BUMEN_ID="+fhentity.getBUMEN_ID());
			fhentity.setSubBumen(this.listTree(fhentity.getBUMEN_ID()));
			fhentity.setTarget("treeFrame");
		}
		return valueList;
	}
		
}

