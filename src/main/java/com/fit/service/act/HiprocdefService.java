package com.fit.service.act;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;

/** 
 *  历史流程任务接口
 */
public interface HiprocdefService {
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**历史流程变量列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> hivarList(PageData pd)throws Exception;

}
