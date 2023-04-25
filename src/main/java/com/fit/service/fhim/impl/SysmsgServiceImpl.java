package com.fit.service.fhim.impl;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.service.fhim.SysmsgService;
import com.fit.mapper.dsno1.fhim.SysmsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统消息服务接口实现类
 */
@Service
@Transactional //开启事物
public class SysmsgServiceImpl implements SysmsgService {
	
	@Autowired
	private SysmsgMapper sysmsgMapper;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		sysmsgMapper.save(pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		sysmsgMapper.delete(pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		sysmsgMapper.edit(pd);
	}
	
	/**消息设置成已读
	 * @param pd
	 * @throws Exception
	 */
	public void read(PageData pd)throws Exception{
		sysmsgMapper.read(pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> datalistPage(Page page)throws Exception{
		return sysmsgMapper.datalistPage(page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return sysmsgMapper.listAll(pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return sysmsgMapper.findById(pd);
	}
	
	/**获取未读总数 
	 * @param pd
	 * @throws Exception
	 */
	public PageData getMsgCount(PageData pd)throws Exception{
		return sysmsgMapper.getMsgCount(pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		sysmsgMapper.deleteAll(ArrayDATA_IDS);
	}
	
}
