package com.fit.service.act.impl;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.mapper.dsno1.act.ProcdefMapper;
import com.fit.service.act.ProcdefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** 
 *  流程管理接口实现类
 */
@Service(value="procdefServiceImpl")
@Transactional //开启事物
public class ProcdefServiceImpl implements ProcdefService {

	@Autowired
	private ProcdefMapper procdefMapper;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception{
		return procdefMapper.datalistPage(page);
	}
	
}

