package com.fit.mapper.dsno1.tools;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;

/**
 * 代码生成器Mapper
 */
public interface CreateCodeMapper {
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd);
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd);
	
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
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	void deleteAll(String[] ArrayDATA_IDS);
	
	/**列表(主表)
	 * @param page
	 * @throws Exception
	 */
	List<PageData> listFa();

}
