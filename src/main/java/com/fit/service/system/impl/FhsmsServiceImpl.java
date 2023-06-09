package com.fit.service.system.impl;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.mapper.dsno1.system.FhsmsMapper;
import com.fit.service.system.FhsmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 站内信服务接口实现类
 */
@Service
@Transactional //开启事物
public class FhsmsServiceImpl implements FhsmsService {

    @Autowired
    private FhsmsMapper fhsmsMapper;

    /**
     * 新增
     *
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd) throws Exception {
        fhsmsMapper.save(pd);
    }

    /**
     * 删除
     *
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd) throws Exception {
        fhsmsMapper.delete(pd);
    }

    /**
     * 修改
     *
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception {
        fhsmsMapper.edit(pd);
    }

    /**
     * 列表
     *
     * @param page
     * @throws Exception
     */
    public List<PageData> list(Page page) throws Exception {
        return fhsmsMapper.datalistPage(page);
    }

    /**
     * 列表(全部)
     *
     * @param pd
     * @throws Exception
     */
    public List<PageData> listAll(PageData pd) throws Exception {
        return fhsmsMapper.listAll(pd);
    }

    /**
     * 通过id获取数据
     *
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception {
        return fhsmsMapper.findById(pd);
    }

    /**
     * 获取未读总数
     */
    public PageData findFhsmsCount(String USERNAME) throws Exception {
        return fhsmsMapper.findFhsmsCount(USERNAME);
    }

    /**
     * 批量删除
     *
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        fhsmsMapper.deleteAll(ArrayDATA_IDS);
    }

}
