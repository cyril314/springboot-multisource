package com.fit.service.tools.impl;

import java.util.List;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.mapper.dsno1.tools.CreateCodeMapper;
import com.fit.service.tools.CreateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 代码生成器接口实现类
 */
@Service
@Transactional //开启事物
public class CreateCodeServiceImpl implements CreateCodeService {

    @Autowired
    private CreateCodeMapper createCodeMapper;

    /**
     * 新增
     *
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd) throws Exception {
        createCodeMapper.save(pd);
    }

    /**
     * 删除
     *
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd) throws Exception {
        createCodeMapper.delete(pd);
    }

    /**
     * 列表
     *
     * @param page
     * @throws Exception
     */
    public List<PageData> list(Page page) throws Exception {
        return createCodeMapper.datalistPage(page);
    }

    /**
     * 通过id获取数据
     *
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception {
        return createCodeMapper.findById(pd);
    }

    /**
     * 批量删除
     *
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        createCodeMapper.deleteAll(ArrayDATA_IDS);
    }

    /**
     * 列表(主表)
     */
    public List<PageData> listFa() throws Exception {
        return createCodeMapper.listFa();
    }
}
