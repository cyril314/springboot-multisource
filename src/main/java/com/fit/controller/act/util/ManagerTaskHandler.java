package com.fit.controller.act.util;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.shiro.session.Session;
import com.fit.util.Jurisdiction;

/**
 * 指定下一任务待办人
 */
@SuppressWarnings("serial")
public class ManagerTaskHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		Session session = Jurisdiction.getSession();
		session.setAttribute("TASKID", delegateTask.getId());			//任务ID
		session.setAttribute("YAssignee", delegateTask.getAssignee());	//默认待办人
	}

}

