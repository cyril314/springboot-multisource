package com.fit.mapper.dsno1.system;

import com.fit.entity.Page;
import com.fit.entity.PageData;

import java.util.List;

/**
 * 站内信Mapper
 */
public interface FhsmsMapper {

    /**
     * 新增
     *
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd);

    /**
     * 删除
     *
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd);

    /**
     * 修改
     *
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd);

    /**
     * 列表
     *
     * @param page
     * @throws Exception
     */
    public List<PageData> datalistPage(Page page);

    /**
     * 列表(全部)
     *
     * @param pd
     * @throws Exception
     */
    public List<PageData> listAll(PageData pd);

    /**
     * 通过id获取数据
     *
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd);

    /**
     * 获取未读总数
     */
    public PageData findFhsmsCount(String USERNAME);

    /**
     * 批量删除
     *
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS);

}
