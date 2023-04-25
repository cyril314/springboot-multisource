package com.fit.controller.tools;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.fit.controller.base.BaseController;
import com.fit.entity.AjaxResult;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.service.system.CodeEditorService;
import com.fit.util.DateUtil;
import com.fit.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器模版编辑
 */
@Controller
@RequestMapping(value = "/codeeditor")
public class CodeEditorController extends BaseController {

    @Autowired
    private CodeEditorService codeEditorService;

    /**
     * 获取code
     */
    @RequestMapping(value = "/getCode")
    @RequiresPermissions("codeeditor:list")
    @ResponseBody
    public Object getCode() throws Exception {
        Map<String, Object> map = AjaxResult.success(0, "success");
        PageData pd = new PageData();
        pd = this.getPageData();
        String type = pd.getString("type");
        String ftlNmame = pd.getString("ftlNmame");
        String code = Tools.readTxtFile("/admin/template/ftl/" + type + "/" + ftlNmame + ".ftl");    //从原始模版获取
        map.put("code", code);
        return map;
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    @RequiresPermissions("codeeditor:add")
    @ResponseBody
    public Object save() throws Exception {
        Map<String, Object> map = AjaxResult.success(0, "success");
        PageData pd = new PageData();
        pd = this.getPageData();
        String codeTxt = pd.getString("codeTxt");
        String type = pd.getString("type");
        String ftlNmame = pd.getString("ftlNmame");
        Tools.writeFileCR("/admin/template/ftl/" + type + "/" + ftlNmame + ".ftl", codeTxt);    //写入到文件
        pd.put("TYPE", type);
        pd.put("FTLNMAME", ftlNmame);
        pd.put("CODECONTENT", codeTxt);
        pd.put("CODEEDITOR_ID", this.get32UUID());        //主键
        pd.put("CTIME", DateUtil.getTime());    //创建时间
        codeEditorService.save(pd);                        //记录存储到数据库
        return map;
    }

    /**
     * 还原
     */
    @RequestMapping(value = "/reduction")
    @RequiresPermissions("codeeditor:edit")
    @ResponseBody
    public Object reduction() throws Exception {
        Map<String, Object> map = AjaxResult.success(0, "success");
        PageData pd = new PageData();
        pd = this.getPageData();
        String type = pd.getString("type");
        String ftlNmame = pd.getString("ftlNmame");
        String msg = pd.getString("msg");
        String code = "";
        if ("fromHistory".equals(msg)) {
            code = codeEditorService.findById(pd).getString("CODECONTENT");                                //从历史编辑获取
        } else {
            code = Tools.readTxtFile("/admin/template/ftl_backups/" + type + "/" + ftlNmame + ".ftl");    //从原始模版获取
        }
        Tools.writeFileCR("/admin/template/ftl/" + type + "/" + ftlNmame + ".ftl", code);                        //写入到现在模版
        map.put("code", code);
        return map;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions("codeeditor:list")
    @ResponseBody
    public Object list(Page page) throws Exception {
        Map<String, Object> map = AjaxResult.success(0, "success");
        PageData pd = new PageData();
        pd = this.getPageData();
        page.setPd(pd);
        List<PageData> varList = codeEditorService.list(page);    //列出CodeEditor列表
        map.put("varList", varList);
        map.put("page", page);
        return map;
    }

    /**
     * 获取code
     */
    @RequestMapping(value = "/getCodeFromView")
    @RequiresPermissions("codeeditor:list")
    @ResponseBody
    public Object getCodeFromView() throws Exception {
        Map<String, Object> map = AjaxResult.success(0, "success");
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = codeEditorService.findById(pd);    //根据ID读取
        map.put("code", pd.getString("CODECONTENT"));
        return map;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions("codeeditor:del")
    @ResponseBody
    public Object delete() throws Exception {
        Map<String, Object> map = AjaxResult.success(0, "success");
        PageData pd = new PageData();
        pd = this.getPageData();
        codeEditorService.delete(pd);
        return map;
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAll")
    @RequiresPermissions("codeeditor:del")
    @ResponseBody
    public Object deleteAll() throws Exception {
        Map<String, Object> map = new AjaxResult();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (Tools.notEmpty(DATA_IDS)) {
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            codeEditorService.deleteAll(ArrayDATA_IDS);
        } else {
            errInfo = "error";
        }
        map.put("result", errInfo);                //返回结果
        return map;
    }
}
