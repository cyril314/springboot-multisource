package com.fit.service.system.impl;

import com.fit.entity.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fit.mapper.dsno1.system.UeditorMapper;
import com.fit.service.system.UeditorService;

/** 
 *  富文本编辑器内容缓存接口实现类
 */
@Service
@Transactional //开启事物
public class UeditorServiceImpl implements UeditorService{

	@Autowired
	private UeditorMapper ueditorMapper;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return ueditorMapper.findById(pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		ueditorMapper.edit(pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		ueditorMapper.delete(pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		ueditorMapper.deleteAll(ArrayDATA_IDS);
	}
	
}

