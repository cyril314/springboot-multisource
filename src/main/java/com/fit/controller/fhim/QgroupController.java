package com.fit.controller.fhim;

import com.fit.controller.base.BaseController;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.util.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.fit.service.fhim.IQgroupService;
import com.fit.service.fhim.QgroupService;
import com.fit.service.fhim.SysmsgService;
import com.fit.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 群组
 */
@Controller
@RequestMapping("/qgroup")
public class QgroupController extends BaseController {

    @Autowired
    private QgroupService qgroupService;
    @Autowired
    private IQgroupService iQgroupService;
    @Autowired
    private SysmsgService sysmsgService;

    /**
     * 保存
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions("qgroup:add")
    @ResponseBody
    public Object add(
            @RequestParam(value = "FIMG", required = false) MultipartFile file,
            @RequestParam(value = "NAME", required = false) String NAME,
            @RequestParam(value = "QID", required = false) String QGROUP_ID
    ) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        String ffile = DateUtil.getNowDate(), fileName = "";
        if (null != file && !file.isEmpty()) {
            String filePath = PathUtil.getProjectpath() + Const.FILEPATHIMG + ffile;    //文件上传路径
            fileName = FileUpload.fileUp(file, filePath, this.get32UUID());                //执行上传
            pd.put("PHOTO", Const.FILEPATHIMG + ffile + "/" + fileName);                //群名
            pd.put("NAME", NAME);                            //群名
            pd.put("CTIME", DateUtil.getTime());    //创建时间
            pd.put("USERNAME", Jurisdiction.getUsername());    //群主
            pd.put("QGROUP_ID", QGROUP_ID);                    //主键
            qgroupService.save(pd);                            //存入群组数据库表
            PageData ipd = new PageData();
            ipd = iQgroupService.findById(pd);
            if (null == ipd) {                                //当我没有任何群时添加数据，否则修改
                pd.put("QGROUPS", "('" + pd.getString("QGROUP_ID") + "',");
                pd.put("IQGROUP_ID", this.get32UUID());        //主键
                iQgroupService.save(pd);
            } else {
                pd.put("QGROUPS", ipd.getString("QGROUPS") + "'" + pd.getString("QGROUP_ID") + "',");
                iQgroupService.edit(pd);
            }
        } else {
            errInfo = "error";
        }
        map.put("result", errInfo);                //返回结果
        return map;
    }

    /**
     * 退群或者解散群
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions("qgroup:del")
    @ResponseBody
    public Object delete() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("USERNAME", Jurisdiction.getUsername());                                    //当前用户
        String TYPE = pd.getString("TYPE");
        if ("del".equals(TYPE)) {                                                            //解散群（删除群的操作在ChatServer中处理）
            if (Tools.notEmpty(pd.getString("PATH").trim())) {
                DelFileUtil.delFolder(PathUtil.getProjectpath() + pd.getString("PATH")); //删除群头像
            }
        } else {                                                //退出群
            PageData qggpd = new PageData();
            qggpd = qgroupService.findById(pd);

            PageData msgpd = new PageData();
            /*存入IM系统消息表中IM_SYSMSG*/
            msgpd.put("SYSMSG_ID", this.get32UUID());                        //主键
            msgpd.put("USERNAME", qggpd.getString("USERNAME"));                //接收者用户名(即群主)
            msgpd.put("FROMUSERNAME", "系统");                                //发送者
            msgpd.put("CTIME", DateUtil.getTime());                //操作时间
            msgpd.put("REMARK", "");                                        //留言
            msgpd.put("TYPE", "group");                                        //类型
            msgpd.put("CONTENT", Jurisdiction.getName() + " 从群：" + qggpd.getString("NAME") + " 退出了");    //事件内容
            msgpd.put("ISDONE", "yes");                                        //是否完成
            msgpd.put("DTIME", DateUtil.getTime());                //完成时间
            msgpd.put("QGROUP_ID", pd.getString("QGROUP_ID"));                //群ID
            msgpd.put("DREAD", "0");                                        //阅读状态 0 未读
            sysmsgService.save(msgpd);

            PageData ipd = new PageData();
            ipd = iQgroupService.findById(pd);
            pd.put("QGROUPS", ipd.getString("QGROUPS").replaceAll("'" + pd.getString("QGROUP_ID") + "',", ""));
            iQgroupService.edit(pd);
        }
        map.put("result", errInfo);                //返回结果
        return map;
    }

    /**
     * 踢出群
     */
    @RequestMapping(value = "/kickout")
    @ResponseBody
    public Object kickout() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData qggpd = new PageData();
        qggpd = qgroupService.findById(pd);
        if (!Jurisdiction.getUsername().equals(qggpd.getString("USERNAME"))) {
            return null;
        }//如果当前用户不是群主，禁止后续操作
        PageData msgpd = new PageData();
        /*存入IM系统消息表中IM_SYSMSG*/
        msgpd.put("SYSMSG_ID", this.get32UUID());                        //主键
        msgpd.put("USERNAME", pd.getString("USERNAME"));                //被踢出的成员用户名
        msgpd.put("FROMUSERNAME", "系统");                                //发送者
        msgpd.put("CTIME", DateUtil.getTime());                //操作时间
        msgpd.put("REMARK", "");                                        //留言
        msgpd.put("TYPE", "group");                                        //类型
        msgpd.put("CONTENT", Jurisdiction.getName() + " 从群：" + qggpd.getString("NAME") + " 踢出了您");    //事件内容
        msgpd.put("ISDONE", "yes");                                        //是否完成
        msgpd.put("DTIME", DateUtil.getTime());                //完成时间
        msgpd.put("QGROUP_ID", pd.getString("QGROUP_ID"));                //群ID
        msgpd.put("DREAD", "0");                                        //阅读状态 0 未读
        sysmsgService.save(msgpd);
        PageData ipd = new PageData();
        ipd = iQgroupService.findById(pd);
        pd.put("QGROUPS", ipd.getString("QGROUPS").replaceAll("'" + pd.getString("QGROUP_ID") + "',", ""));
        iQgroupService.edit(pd);
        map.put("result", errInfo);                //返回结果
        return map;
    }

    /**
     * 删除图片
     */
    @RequestMapping(value = "/delImg")
    @ResponseBody
    public Object delImg() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        String PATH = pd.getString("PATH");
        if (Tools.notEmpty(pd.getString("PATH").trim())) {                                //图片路径
            DelFileUtil.delFolder(PathUtil.getProjectpath() + pd.getString("PATH"));    //删除硬盘中的图片
        }
        if (PATH != null) {
            qgroupService.delTp(pd);                                                    //删除数据库中图片数据
        }
        map.put("result", errInfo);                //返回结果
        return map;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    @RequiresPermissions("qgroup:edit")
    @ResponseBody
    public Object edit(
            @RequestParam(value = "FIMG", required = false) MultipartFile file,
            @RequestParam(value = "FIMGZ", required = false) String FIMGZ,
            @RequestParam(value = "QGROUP_ID", required = false) String QGROUP_ID,
            @RequestParam(value = "NAME", required = false) String NAME
    ) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("NAME", NAME);                            //群名
        pd.put("QGROUP_ID", QGROUP_ID);                    //主键
        if (null != file && !file.isEmpty()) {
            String ffile = DateUtil.getNowDate(), fileName = "";
            String filePath = PathUtil.getProjectpath() + Const.FILEPATHIMG + ffile;    //文件上传路径
            fileName = FileUpload.fileUp(file, filePath, this.get32UUID());                //执行上传
            pd.put("PHOTO", Const.FILEPATHIMG + ffile + "/" + fileName);                //路径
        } else {
            pd.put("PHOTO", FIMGZ);
        }
        qgroupService.edit(pd);
        map.put("result", errInfo);                //返回结果
        return map;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions("qgroup:list")
    @ResponseBody
    public Object list(Page page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        String keywords = pd.getString("keywords");                    //关键词检索条件
        if (Tools.notEmpty(keywords)) pd.put("keywords", keywords.trim());
        pd.put("USERNAME", Jurisdiction.getUsername());                //当前用户
        PageData ipd = new PageData();
        ipd = iQgroupService.findById(pd);
        if (null == ipd) {
            pd.put("item", "('null')");
        } else {
            pd.put("item", ipd.getString("QGROUPS") + "'fh')");
        }
        page.setPd(pd);
        List<PageData> varList = qgroupService.datalistPage(page);    //列出Qgroup列表
        map.put("varList", varList);
        map.put("page", page);
        map.put("pd", pd);
        map.put("QID", this.get32UUID());
        map.put("result", errInfo);                //返回结果
        return map;
    }

    /**
     * 群检索
     */
    @RequestMapping(value = "/search")
    @ResponseBody
    public Object search() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        int fcount = 3;
        if (null != pd.get("fcount")) {
            fcount = Integer.parseInt(pd.get("fcount").toString());        //一列数量
        }
        String keywords = pd.getString("keywords");                //关键词检索条件
        if (Tools.notEmpty(keywords)) pd.put("keywords", keywords.trim());
        List<PageData> varList = qgroupService.searchListAll(pd);
        List<List<PageData>> zlist = new ArrayList<List<PageData>>();
        List<PageData> list = null;
        for (int i = 0; i < varList.size(); i++) {
            if (i % fcount == 0) {
                list = new ArrayList<PageData>();
            }
            list.add(varList.get(i));
            if ((i + 1) % fcount == 0 || (i + 1) == varList.size()) {
                zlist.add(list);
            }
        }
        map.put("varList", zlist);
        map.put("pd", pd);
        map.put("result", errInfo);                //返回结果
        return map;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    @ResponseBody
    public Object goEdit() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = qgroupService.findById(pd);    //根据ID读取
        map.put("pd", pd);
        map.put("result", errInfo);                //返回结果
        return map;
    }

    /**
     * 获取此群的信息
     */
    @RequestMapping(value = "/getThisQgroup")
    @ResponseBody
    public Object getThisQgroup() throws Exception {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = qgroupService.findById(pd);
        map.put("avatar", pd.getString("PHOTO"));    //群头像
        map.put("groupname", pd.getString("NAME"));    //群名称
        return map;
    }

    /**
     * 群成员
     */
    @RequestMapping(value = "/groupMembers")
    @ResponseBody
    public Object groupMembers(Page page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        pd = this.getPageData();
        String keywords = pd.getString("keywords");                            //关键词检索条件
        if (Tools.notEmpty(keywords)) pd.put("keywords", keywords.trim());
        pd.put("USERNAME", Jurisdiction.getUsername());                        //排除本人(即群主)
        page.setPd(pd);
        List<PageData> varList = iQgroupService.memberslistPage(page);        //列出群成员列表
        map.put("varList", varList);
        map.put("page", page);
        map.put("result", errInfo);                //返回结果
        return map;
    }

}
