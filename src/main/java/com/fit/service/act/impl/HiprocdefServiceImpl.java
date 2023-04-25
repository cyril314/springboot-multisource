package com.fit.service.act.impl;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.mapper.dsno1.act.HiprocdefMapper;
import com.fit.service.act.HiprocdefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 
 *  历史流程任务接口实现类
 */
@Service(value="hiprocdefServiceImpl")
@Transactional //开启事物
public class HiprocdefServiceImpl implements HiprocdefService {

	@Autowired
	private HiprocdefMapper hiprocdefMapper;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception {
		return hiprocdefMapper.datalistPage(page);
	}

	/**历史流程变量列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> hivarList(PageData pd) throws Exception {
		return hiprocdefMapper.hivarList(pd);
	}

}
