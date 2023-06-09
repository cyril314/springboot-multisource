package com.fit.service.fhdb.impl;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.mapper.dsno1.fhdb.BRdbMapper;
import com.fit.service.fhdb.BRdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据库管理接口实现类
 */
@Service(value="bRdbServiceImpl")
@Transactional //开启事物
public class BRdbServiceImpl implements BRdbService {
	
	@Autowired
	private BRdbMapper bRdbMapper;

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		bRdbMapper.save(pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		bRdbMapper.delete(pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		bRdbMapper.edit(pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception{
		return bRdbMapper.datalistPage(page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return bRdbMapper.listAll(pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return bRdbMapper.findById(pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		bRdbMapper.deleteAll(ArrayDATA_IDS);
	}
	
}
