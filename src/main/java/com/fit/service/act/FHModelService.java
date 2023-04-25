package com.fit.service.act;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;

/** 
 *  模型管理接口
 */
public interface FHModelService{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
}

