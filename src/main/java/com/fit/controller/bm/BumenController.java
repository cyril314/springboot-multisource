package com.fit.controller.bm;

import com.fit.controller.base.BaseController;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.fit.service.bm.BumenService;
import com.fit.util.JacksonUtils;
import com.fit.util.ObjectExcelView;
import com.fit.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 */
@Controller
@RequestMapping("/bumen")
public class BumenController extends BaseController {

    @Autowired
    private BumenService bumenService;

    /**
     * 保存
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions("bumen:add")
    @ResponseBody
    public Object add() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("BUMEN_ID", this.get32UUID());    //主键
        bumenService.save(pd);
        map.put("result", errInfo);
        return map;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions("bumen:del")
    @ResponseBody
    public Object delete(@RequestParam String BUMEN_ID) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd.put("BUMEN_ID", BUMEN_ID);
        if (bumenService.listByParentId(BUMEN_ID).size() > 0) {        //判断是否有子级，是：不允许删除
            errInfo = "error";
        } else {
            bumenService.delete(pd);
        }
        map.put("result", errInfo);                //返回结果
        return map;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    @RequiresPermissions("bumen:edit")
    @ResponseBody
    public Object edit() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = this.getPageData();
        bumenService.edit(pd);
        map.put("result", errInfo);
        return map;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions("bumen:list")
    @ResponseBody
    public Object list(Page page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        String KEYWORDS = pd.getString("KEYWORDS");                                //关键词检索条件
        if (Tools.notEmpty(KEYWORDS)) pd.put("KEYWORDS", KEYWORDS.trim());
        String BUMEN_ID = null == pd.get("BUMEN_ID") ? "" : pd.get("BUMEN_ID").toString();
        pd.put("BUMEN_ID", BUMEN_ID);                    //当作上级ID
        page.setPd(pd);
        List<PageData> varList = bumenService.list(page);            //列出Bumen列表
        if ("".equals(BUMEN_ID) || "0".equals(BUMEN_ID)) {
            map.put("PARENT_ID", "0");                                            //上级ID
        } else {
            map.put("PARENT_ID", bumenService.findById(pd).getString("PARENT_ID"));    //上级ID
        }
        map.put("varList", varList);
        map.put("page", page);
        map.put("result", errInfo);
        return map;
    }

    /**
     * 显示列表ztree
     */
    @RequestMapping(value = "/listTree")
    @RequiresPermissions("bumen:list")
    @ResponseBody
    public Object listTree() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        String json = JacksonUtils.obj2json(bumenService.listTree("0"));
        json = json.replaceAll("BUMEN_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subBumen", "nodes").replaceAll("hasBumen", "checked").replaceAll("treeurl", "url");
        map.put("zTreeNodes", json);
        map.put("result", errInfo);
        return map;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    @RequiresPermissions("bumen:add")
    @ResponseBody
    public Object goAdd() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        String BUMEN_ID = null == pd.get("BUMEN_ID") ? "" : pd.get("BUMEN_ID").toString();
        pd.put("BUMEN_ID", BUMEN_ID);                    //上级ID
        map.put("pds", bumenService.findById(pd));                    //传入上级所有信息
        map.put("result", errInfo);
        return map;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    @RequiresPermissions("bumen:edit")
    @ResponseBody
    public Object goEdit() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = bumenService.findById(pd);                                    //根据ID读取
        map.put("pd", pd);                                                                //放入视图容器
        pd.put("BUMEN_ID", pd.get("PARENT_ID").toString());                    //用作上级信息
        map.put("pds", bumenService.findById(pd));                            //传入上级所有信息
        map.put("result", errInfo);
        return map;
    }

    /**
     * 导出到excel
     */
    @RequestMapping(value = "/excel")
    @RequiresPermissions("toExcel")
    public ModelAndView exportExcel() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("部门名称");    //1
        dataMap.put("titles", titles);
        List<PageData> varOList = bumenService.listAll(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("BNAME"));        //1
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }
}
