package com.fit.controller.enterprise;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.fit.controller.base.BaseController;
import com.fit.entity.AjaxResult;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.service.enterprise.EnterpriseService;
import com.fit.util.ObjectExcelView;
import com.fit.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业信息
 */
@Controller
@RequestMapping("/enterprise")
public class EnterpriseController extends BaseController {

    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 保存
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions("enterprise:add")
    @ResponseBody
    public Object add() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("ENTERPRISE_ID", this.get32UUID());    //主键
        enterpriseService.save(pd);
        return AjaxResult.success(0, "success");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions("enterprise:del")
    @ResponseBody
    public Object delete() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        enterpriseService.delete(pd);
        return AjaxResult.success(0, "success");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    @RequiresPermissions("enterprise:edit")
    @ResponseBody
    public Object edit() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        enterpriseService.edit(pd);
        return AjaxResult.success(0, "success");
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions("enterprise:list")
    @ResponseBody
    public Object list(Page page) throws Exception {
        Map<String, Object> map = AjaxResult.success(0, "success");
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        String KEYWORDS = pd.getString("KEYWORDS");                        //关键词检索条件
        if (Tools.notEmpty(KEYWORDS)) pd.put("KEYWORDS", KEYWORDS.trim());
        page.setPd(pd);
        List<PageData> varList = enterpriseService.list(page);    //列出Enterprise列表
        map.put("varList", varList);
        map.put("page", page);
        return map;
    }

    /**
     * 去修改页面获取数据
     */
    @RequestMapping(value = "/goEdit")
    @RequiresPermissions("enterprise:edit")
    @ResponseBody
    public Object goEdit() throws Exception {
        Map<String, Object> map = AjaxResult.success(0, "success");
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = enterpriseService.findById(pd);    //根据ID读取
        map.put("pd", pd);
        return map;
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAll")
    @RequiresPermissions("enterprise:del")
    @ResponseBody
    public Object deleteAll() throws Exception {
        Map<String, Object> map = new AjaxResult();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (Tools.notEmpty(DATA_IDS)) {
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            enterpriseService.deleteAll(ArrayDATA_IDS);
            errInfo = "success";
        } else {
            errInfo = "error";
        }
        map.put("result", errInfo);                //返回结果
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
        titles.add("单位名称");    //1
        titles.add("统一社会信用代码");    //2
        titles.add("单位地址");    //3
        titles.add("组织机构代码");    //4
        titles.add("经营方式");    //5
        titles.add("电子邮箱");    //6
        titles.add("单位网址");    //7
        titles.add("产业类别");    //8
        titles.add("传真");    //9
        titles.add("单位成立日期");    //10
        titles.add("单位简称");    //11
        titles.add("单位类型");    //12
        titles.add("经济类型");    //13
        titles.add("行业类型");    //14
        titles.add("（注册资金）币种");    //15
        titles.add("注册资金（万元）");    //16
        titles.add("隶属监管关系");    //17
        titles.add("主管部门/主要投资人/控股单位");    //18
        titles.add("主营范围");    //19
        titles.add("兼营范围");    //20
        titles.add("工商营业执照号码");    //21
        titles.add("法定代表人");    //22
        titles.add("法定代表人联系电话");    //23
        titles.add("法定代表人公民身份证号码");    //24
        titles.add("单位负责人姓名");    //25
        titles.add("单位负责人电话");    //26
        titles.add("劳资负责人姓名");    //27
        titles.add("劳资负责人联系电话");    //28
        titles.add("联系人");    //29
        titles.add("联系人电话");    //30
        titles.add("工商登记发照日期");    //31
        titles.add("工商登记有效期限");    //32
        titles.add("单位开户行");    //33
        titles.add("开户行");    //34
        titles.add("银行账户");    //35
        dataMap.put("titles", titles);
        List<PageData> varOList = enterpriseService.listAll(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("ENTERPRISENAME"));        //1
            vpd.put("var2", varOList.get(i).getString("SOCIALCREDITCODE"));        //2
            vpd.put("var3", varOList.get(i).getString("ENTERPRISEADDRESS"));        //3
            vpd.put("var4", varOList.get(i).getString("ORGANIZATIONCODE"));        //4
            vpd.put("var5", varOList.get(i).getString("MANAGEMENTSTYLE"));        //5
            vpd.put("var6", varOList.get(i).getString("EMAIL"));        //6
            vpd.put("var7", varOList.get(i).getString("WEBSITE"));        //7
            vpd.put("var8", varOList.get(i).getString("INDUSTRIALCATEGORY"));        //8
            vpd.put("var9", varOList.get(i).getString("FAX"));        //9
            vpd.put("var10", varOList.get(i).getString("ESTABLISHDATE"));        //10
            vpd.put("var11", varOList.get(i).getString("SIMPLENAME"));        //11
            vpd.put("var12", varOList.get(i).getString("ENTERPRISETYPE"));        //12
            vpd.put("var13", varOList.get(i).getString("ECONOMICTYPE"));        //13
            vpd.put("var14", varOList.get(i).getString("INDUSTRYTYPE"));        //14
            vpd.put("var15", varOList.get(i).getString("MONEYTYPE"));        //15
            vpd.put("var16", varOList.get(i).getString("REGISMONEY"));        //16
            vpd.put("var17", varOList.get(i).getString("SUBORDINATERELATION"));        //17
            vpd.put("var18", varOList.get(i).getString("MAJORCHARGE"));        //18
            vpd.put("var19", varOList.get(i).getString("MAINBUSINESS"));        //19
            vpd.put("var20", varOList.get(i).getString("PARTBUSINESS"));        //20
            vpd.put("var21", varOList.get(i).getString("BUSINESSNUMBER"));        //21
            vpd.put("var22", varOList.get(i).getString("LEGALREPRESENTATIVE"));        //22
            vpd.put("var23", varOList.get(i).getString("REPRETELEPHONE"));        //23
            vpd.put("var24", varOList.get(i).getString("REPREID"));        //24
            vpd.put("var25", varOList.get(i).getString("ENTERPRISECHARGENAME"));        //25
            vpd.put("var26", varOList.get(i).getString("ENTERPRISECHARGETELEPHONE"));        //26
            vpd.put("var27", varOList.get(i).getString("MONEYCHARGENAME"));        //27
            vpd.put("var28", varOList.get(i).getString("MONEYCHARGETELEPHONE"));        //28
            vpd.put("var29", varOList.get(i).getString("LINKPERSON"));        //29
            vpd.put("var30", varOList.get(i).getString("LINKPERSONTELEPHONE"));        //30
            vpd.put("var31", varOList.get(i).getString("HANDOUTDATE"));        //31
            vpd.put("var32", varOList.get(i).getString("EFFECTIVEDATE"));        //32
            vpd.put("var33", varOList.get(i).getString("ENTERPRISEACCOUNTOPENBANK"));        //33
            vpd.put("var34", varOList.get(i).getString("ACCOUNTOPENBANK"));        //34
            vpd.put("var35", varOList.get(i).getString("BANKACCOUNT"));        //35
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }

}
