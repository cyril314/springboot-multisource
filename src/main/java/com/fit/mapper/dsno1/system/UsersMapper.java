package com.fit.mapper.dsno1.system;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.entity.system.User;

/**
 * 用户Mapper
 */
public interface UsersMapper {
	
	/**通过用户获取数据
	 * @param pd
	 * @return
	 */
	PageData findByUsername(PageData pd);
	
	/**用户列表
	 * @param page
	 * @return
	 */
	List<PageData> userlistPage(Page page);
	
	/**通过用户ID获取用户信息和角色信息
	 * @param USER_ID
	 * @return
	 */
	User getUserAndRoleById(String USER_ID);
	
	/**通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findByEmail(PageData pd);
	
	/**通过编码获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findByNumbe(PageData pd);
	
	/**列出某角色下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> listAllUserByRoldId(PageData pd);
	
	/**用户列表(全部)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> listAllUser(PageData pd);
	
	/**用户列表(弹窗选择用)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> userBystafflistPage(Page page);
	
	/**通过用户ID获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findById(PageData pd);
	
	/**保存用户IP
	 * @param pd
	 */
	void saveIP(PageData pd);
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	void saveUser(PageData pd);
	
	/**保存用户系统皮肤
	 * @param pd
	 * @throws Exception
	 */
	void saveSkin(PageData pd);
	
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	void editUser(PageData pd);
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	void deleteUser(PageData pd);
	
	/**批量删除用户
	 * @param pd
	 * @throws Exception
	 */
	void deleteAllUser(String[] USER_IDS);

}
