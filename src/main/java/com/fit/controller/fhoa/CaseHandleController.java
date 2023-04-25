package com.fit.controller.fhoa;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.fit.controller.act.AcStartController;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.service.fhoa.CaseHandleService;
import com.fit.util.Jurisdiction;
import com.fit.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请假申请
 */
@Controller
@RequestMapping("/casehandle")
public class CaseHandleController extends AcStartController {

	@Autowired
	private CaseHandleService caseHandleService;
	
	//zty
	@Autowired
	private RuntimeService runtimeService;
	
	//zty
	@Autowired
	private FormService formService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	/**保存请假单
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	@RequiresPermissions("casehandle:add")
	@ResponseBody
	public Object add(){
		Map<String,Object> zmap = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CASE_ID", this.get32UUID());					//主键
		pd.put("USERNAME", Jurisdiction.getUsername());			//用户名
		try {
			/** 工作流的操作 **/
			Map<String,Object> map = new LinkedHashMap<String, Object>();
			map.put("案件受理人员", Jurisdiction.getName());			//当前用户的姓名
			map.put("报案人姓名", pd.getString("REPORTERNAME"));
			map.put("报案类型", pd.getString("TYPE"));
			map.put("报案时间", pd.getString("TIME"));
			//map.put("请假类型", pd.getString("TYPE"));
			map.put("报案事由", pd.getString("REASON"));
			
//			map.put("姓名", "zhangsan");
//			map.put("年龄", "23");
			
			//办案流程——假数据测试
//			map.put("报案人姓名","王铁柱");
//			map.put("报案人年龄","25");
//			map.put("报案信息","家里猪丢了！");
			
			
			map.put("USERNAME", Jurisdiction.getUsername());			//指派代理人为当前用户
			//startProcessInstanceByKeyHasVariables("KEY_leave",map);		//启动流程实例(请假单流程)通过KEY
			startProcessInstanceByKeyHasVariables("KEY_test",map);
			
			//zty
//			Map<String, String> testmap = new HashMap<String,String>();
////			testmap.put("new_property_1", "1");
//			
//			List<Deployment> depList = repositoryService.createDeploymentQuery().list();
//			for (int i = 0; i < depList.size(); i++) {
//				Deployment deployment = depList.get(i);
//				System.out.print("Deployment:");
//				System.out.print(deployment.getId());
//				System.out.println(deployment.getKey());
//			}
//			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//					.deploymentId("385001").singleResult();
//			
//			System.out.print("ProcessDefinition ID:");
//			System.out.println(processDefinition.getId());
//			
//			
//			formService.submitStartFormData(processDefinition.getId(),testmap);
			
			caseHandleService.save(pd);									//记录存入数据库
			zmap.put("ASSIGNEE_",Jurisdiction.getUsername());			//用于给待办人发送新任务消息
		} catch (Exception e) {
			errInfo = "errer";
		}								
		zmap.put("result", errInfo);				//返回结果
		return zmap;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	@RequiresPermissions("casehandle:list")
	@ResponseBody
	public Object list(Page page) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		String KEYWORDS = pd.getString("KEYWORDS");				//关键词检索条件
		if(Tools.notEmpty(KEYWORDS))pd.put("keywords", KEYWORDS.trim());
		pd.put("USERNAME", "admin".equals(Jurisdiction.getUsername())?"":Jurisdiction.getUsername()); //除admin用户外，只能查看自己的数据
		page.setPd(pd);
		List<PageData>	varList = caseHandleService.list(page);	//列出Myleave列表
		map.put("varList", varList);
		map.put("page", page);
		map.put("result", errInfo);				//返回结果
		return map;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@RequiresPermissions("casehandle:del")
	@ResponseBody
	public Object delete() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		caseHandleService.delete(pd);
		map.put("result", "success");
		return map;
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@RequiresPermissions("casehandle:del")
	@ResponseBody
	public Object deleteAll() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();		
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(Tools.notEmpty(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			caseHandleService.deleteAll(ArrayDATA_IDS);
		}else{
			errInfo = "error";
		}
		map.put("result", errInfo);				//返回结果
		return map;
	}
	
}

