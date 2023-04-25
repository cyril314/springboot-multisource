package com.fit.service.act;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;

/** 
 *  流程管理接口
 */
public interface ProcdefService{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
}

