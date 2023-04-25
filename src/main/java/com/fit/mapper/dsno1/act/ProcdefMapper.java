package com.fit.mapper.dsno1.act;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;

/** 
 *  流程管理Mapper
 */
public interface ProcdefMapper{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> datalistPage(Page page);
	
}

