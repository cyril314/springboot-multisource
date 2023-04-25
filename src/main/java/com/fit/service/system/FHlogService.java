package com.fit.service.system;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;

/**
 * 操作日志记录接口
 */
public interface FHlogService {
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(String USERNAME, String CONTENT)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

}
