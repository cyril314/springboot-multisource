package com.fit.mapper.dsno1.system;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.entity.system.Dictionaries;

/**
 * 数据字典Mapper
 */
public interface DictionariesMapper {

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<Dictionaries> listSubDictByParentId(String parentId);
	
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
	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findByBianma(PageData pd);
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd);
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	void edit(PageData pd);
	
	/**排查表检查是否被占用
	 * @param pd
	 * @throws Exception
	 */
	PageData findFromTbs(PageData pd);
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd);
}
