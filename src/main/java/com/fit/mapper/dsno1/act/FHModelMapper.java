package com.fit.mapper.dsno1.act;

import java.util.List;
import com.fit.entity.Page;
import com.fit.entity.PageData;

/** 
 *  模型管理Mapper
 */
public interface FHModelMapper{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> datalistPage(Page page);
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findById(PageData pd);
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	void edit(PageData pd);
	
}

