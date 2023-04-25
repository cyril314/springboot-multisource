package com.fit.service.system.impl;

import com.fit.entity.PageData;
import com.fit.mapper.dsno1.system.PhotoMapper;
import com.fit.service.system.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 头像编辑服务接口实现类
 */
@Service
@Transactional //开启事物
public class PhotoServiceImpl implements PhotoService {
	
	@Autowired
	private PhotoMapper photoMapper;

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		photoMapper.save(pd);
	}

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		photoMapper.edit(pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return photoMapper.findById(pd);
	}

}
