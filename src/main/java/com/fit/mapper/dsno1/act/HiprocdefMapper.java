package com.fit.mapper.dsno1.act;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;

/** 
 *  历史流程Mapper
 */
public interface HiprocdefMapper {
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> datalistPage(Page page)throws Exception;
	
	/**历史流程变量列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> hivarList(PageData pd)throws Exception;

}
