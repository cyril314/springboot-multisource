package com.fit.mapper.dsno1.system;

import java.util.List;

import com.fit.entity.PageData;

/**
 * 按钮权限Mapper
 */
public interface ButtonrightsMapper {
	
	/**列表(全部)左连接按钮表,查出安全权限标识(主副职角色综合)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAllBrAndQxnameByZF(String[] ROLE_IDS);
	
	/**列表(全部)左连接按钮表,查出安全权限标识(主职角色)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAllBrAndQxname(PageData pd);
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAll(PageData pd);
	
	/**通过(角色ID和按钮ID)获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findById(PageData pd);
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd);
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd);

}
