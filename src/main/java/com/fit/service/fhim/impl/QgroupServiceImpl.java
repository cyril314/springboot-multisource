package com.fit.service.fhim.impl;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.service.fhim.QgroupService;
import com.fit.mapper.dsno1.fhim.QgroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 群组服务接口实现类
 */
@Service
@Transactional //开启事物
public class QgroupServiceImpl implements QgroupService {
	
	@Autowired
	private QgroupMapper qgroupMapper;

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		qgroupMapper.save(pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		qgroupMapper.delete(pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		qgroupMapper.edit(pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> datalistPage(Page page)throws Exception{
		return qgroupMapper.datalistPage(page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return qgroupMapper.listAll(pd);
	}
	
	/**群检索列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> searchListAll(PageData pd)throws Exception{
		return qgroupMapper.searchListAll(pd);
	}
	
	/**我在的全部群列表 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> mylistAll(PageData pd)throws Exception{
		return qgroupMapper.mylistAll(pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return qgroupMapper.findById(pd);
	}
	
	/**删除图片
	 * @param pd
	 * @throws Exception
	 */
	public void delTp(PageData pd)throws Exception{
		qgroupMapper.delTp(pd);
	}
	
}
