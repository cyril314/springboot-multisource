var vm = new Vue({
	el: '#app',
	
	data:{
		serverurl: '',			//后台地址
		hitaskList: [],			//历史任务节点列表
		varList: [],			//流程变量列表
		PROC_INST_ID_: '',		//流程实例ID
		ID_: '',				//任务ID
		OPINION: '',			//审批意见
		ASSIGNEE_: '',			//待办人
		messagetext: '',		//作废缘由
		FILENAME: '',			//流程图文件名ID
		imgSrc: '',				//流程图base64数据
		Reject:false,			//驳回按钮权限
		Abolish:false,			//作废按钮权限
		NextASSIGNEE_:false,	//指定下一办理人按钮权限
		msg:true,				//判断是否从办理任务进入
		CONNULL: false,			//判断是否输入文本
		loading:false,			//加载状态
		formKey:""				//外部表单
	},
	
	computed: {
		varListSelected: function () {
			var self = this;
			var originVarList = [{
				vars: this.varList
			}];
			if (this.hitaskList && this.hitaskList.length > 0) {
				console.log("hitaskList");
				var taskNameList = {};
				this.hitaskList.forEach(function (task) {
					console.log(task.ID_, self.ID_)
					if (task.ID_ <= self.ID_ && task.ACT_NAME_) {
						taskNameList[task.ACT_NAME_] = "";
						console.log(taskNameList);
					}
				});
				for (const key in taskNameList) {
					if (taskNameList.hasOwnProperty(key)) {
						const name = key;
						var taskVars = this.hitaskList.filter(function (hitask) {
							return hitask.ACT_NAME_ == name;
						});
						console.log(name, taskVars.length);
						if (taskVars.length > 0) {
							originVarList.push({
								name: name,
								vars: taskVars
							});
						}
					}
				}
				originVarList.pop();
			}
			return originVarList;
		},
		hitaskListSelected: function () {
			var result = []
			if (this.hitaskList && this.hitaskList.length > 0) {
				this.hitaskList.reduce(function (previous, current) {
					console.log(previous.ACT_NAME_, current.ACT_NAME_);
					if (previous.ACT_ID_ != current.ACT_ID_) {
						result.push(previous);
					}
					return current;
				});
				result.push(this.hitaskList[this.hitaskList.length - 1]);
			}
			return result;
		}
	},
    
    mixins: [formatDate],	//混入模块
    
	methods: {
		
        //初始执行
        init() {
        	var msg = this.getUrlKey('msg');	//链接参数
        	if(null != msg){
        		this.msg = false;
        	}
        	this.serverurl = httpurl;
        	this.PROC_INST_ID_ = this.getUrlKey('PROC_INST_ID_');	//链接参数
        	this.ID_ = this.getUrlKey('ID_');						//链接参数
        	this.FILENAME = this.getUrlKey('FILENAME');				//链接参数
       		this.getData();
       		this.hasButton();
        },
        
    	//进入页面获取数据
    	getData: function(){
    		this.loading = true;
    		//发送 post 请求
            $.ajax({
            	xhrFields: {
                    withCredentials: true
                },
				type: "POST",
				url: httpurl+'rutask/getHandleData',
		    	data: {PROC_INST_ID_:this.PROC_INST_ID_,ID_:this.ID_,FILENAME:this.FILENAME,tm:new Date().getTime()},
				dataType:"json",
				success: function(data){
                     if("success" == data.result){
                    	 vm.hitaskList = data.hitaskList;
                    	 vm.varList = data.varList;
                    	 vm.imgSrc = data.imgSrc;
						 vm.loading = false;
						 $.ajax({
							 xhrFields: {
								 withCredentials: true
							 },
							 url: "/WebContent/" + data.formKey,
							 dataType: "html",
							 success: function (formBody) {
								 vm.formKey = formBody
							 }
						 })
                     }else if ("exception" == data.result){
                     	showException("流程信息",data.exception);	//显示异常
                     	$("#showform").show();
                 		$("#jiazai").hide();
                     }
                  }
			}).done().fail(function(){
                  swal("登录失效!", "请求服务器无响应，稍后再试", "warning");
                  $("#showform").show();
          		  $("#jiazai").hide();
               });
    	},
    	
      	//查看用户
        viewUser: function (USERNAME){
        	if('admin' == USERNAME){
        		swal("禁止查看!", "不能查看admin用户!", "warning");
        		return;
        	}
        	 var diag = new top.Dialog();
        	 diag.Drag=true;
        	 diag.Title ="资料";
        	 diag.URL = '../../sys/user/user_view.html?USERNAME='+USERNAME;
        	 diag.Width = 600;
        	 diag.Height = 319;
        	 diag.Modal = false;			//有无遮罩窗口
        	 diag.CancelEvent = function(){ //关闭事件
        		diag.close();
        	 };
        	 diag.show();
        },
        
      	//审批意见详情页
        details: function (htmlId){
        	 var content = $("#"+htmlId).val().split(',fh,');
        	 top.vm.handleDetails(content[1]);
        	 var diag = new top.Dialog();
        	 diag.Modal = false;			//有无遮罩窗口
        	 diag.Drag=true;
        	 diag.Title ="审批意见";
        	 diag. ShowMaxButton = true;	//最大化按钮
             diag.ShowMinButton = true;		//最小化按钮
        	 diag.URL = '../../act/rutask/handle_details.html';
        	 diag.Width = 760;
        	 diag.Height = 500;
        	 diag.CancelEvent = function(){ //关闭事件
        		diag.close();
        	 };
        	 diag.show();
        },

    	//选择办理人
    	getUser: function (){
    		 var diag = new top.Dialog();
    		 diag.Drag=true;
    		 diag.Title ="选择办理人";
    		 diag.URL = '../user/window_user_list.html';
    		 diag.Width = 800;
    		 diag.Height = 600;
    		 diag.Modal = true;				//有无遮罩窗口
    		 diag. ShowMaxButton = true;	//最大化按钮
    	     diag.ShowMinButton = true;		//最小化按钮
    		 diag.CancelEvent = function(){ //关闭事件
    			 var USERNAME = diag.innerFrame.contentWindow.document.getElementById('USERNAME').value;
    			 if("" != USERNAME){
    				 vm.ASSIGNEE_ = USERNAME;
    			 }
    			diag.close();
    		 };
    		 diag.show();
    	},

    	//选择角色
    	getRole: function (){
    		 var diag = new top.Dialog();
    		 diag.Drag=true;
    		 diag.Title ="选择角色";
    		 diag.URL = '../role/window_role_list.html';
    		 diag.Width = 700;
    		 diag.Height = 545;
    		 diag.Modal = true;				//有无遮罩窗口
    		 diag. ShowMaxButton = true;	//最大化按钮
    	     diag.ShowMinButton = true;		//最小化按钮
    		 diag.CancelEvent = function(){ //关闭事件
    			 var RNUMBER = diag.innerFrame.contentWindow.document.getElementById('RNUMBER').value;
    			 if("" != RNUMBER){
    				 vm.ASSIGNEE_ = RNUMBER;
    			 }
    			diag.close();
    		 };
    		 diag.show();
    	},

        //清空下一任务对象
        clean: function (){
        	vm.ASSIGNEE_ = '';
        },

        //办理任务
        handle: function (msg){
			var taskVariables = {};
			$(".form-variables").each(function (index, element) {
				var key = $(element).attr("name");
				var value = $(element).val();
				taskVariables[key] = value;
				console.log("form-variables", key, value);
			});
        	this.getContent('acthandle');
        	if(!this.CONNULL || this.CONNULL){
				console.log("ok!");
            	$("#showform").hide();
            	$("#jiazai").show();
            	$.ajax({
            		xhrFields: {
	                    withCredentials: true
	                },
            		type: "POST",
            		url: httpurl+'rutask/handle',
                	data: {taskVariables:taskVariables,msg:msg,ID_:this.ID_,ASSIGNEE_:this.ASSIGNEE_,PROC_INST_ID_:this.PROC_INST_ID_,OPINION:this.OPINION,tm:new Date().getTime()},
            		dataType:'json',
            		success: function(data){
            			if("success" == data.result){
            				if('null' != data.ASSIGNEE_){
            					top.vm.fhtaskmsg(data.ASSIGNEE_); 	//websocket即时通讯用于给待办人发送新任务消息 ，fhtaskmsg()函数 在 WebRoot\assets\js-v\index.js
            				}
            				if(undefined != data.FHSMS){
            					top.vm.fhsmsmsg(data.FHSMS); 			//websocket即时通讯用于给待办人发送站内信消息 ，fhsmsmsg()函数 WebRoot\assets\js-v\index.js
            				}
                        	swal("", "审批完成", "success");
                        	setTimeout(function(){
                        		top.Dialog.close();//关闭弹窗
                            },1000);
                        }else if ("exception" == data.result){
                        	showException("提交审批",data.exception);//显示异常
                        	$("#showform").show();
                    		$("#jiazai").hide();
                        }
            		}
            	}).done().fail(function(){
                    swal("登录失效!", "请求服务器无响应，稍后再试", "warning");
                    $("#showform").show();
            		   $("#jiazai").hide();
                 });
        	}
        },
        
        //获取富文本内容
        getContent: function (TYPE){
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'ueditor/getContent',
            	data: {TYPE:TYPE,tm:new Date().getTime()},	//这个TYPE这里的值是 email
        		dataType:'json',
        		async: false,
        		success: function(data){
        			if(data.result == 'success'){
                    	vm.OPINION = data.pd.CONTENT;	//带标签的
        				if('' == vm.OPINION){
        					$("#ueFrame").tips({
                       			side:1,
                                   msg:'请输入内容',
                                   bg:'#AE81FF',
                                   time:3
                             });
        				}else{
        					vm.CONNULL = true;
        				}
        			}else{
                   		$("#ueFrame").tips({
                   			side:1,
                               msg:'请输入内容',
                               bg:'#AE81FF',
                               time:3
                         });
        			}
        		}
        	});
        },

        //是否作废
        isDel: function (){
        	$("#showform").hide();
        },

    	//提交作废
    	goDel: function (){
    		if('' == this.messagetext){
    			swal("", "还未写作废缘由!", "warning");
    			return false;
    		}
    		this.loading = true;
    		$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'ruprocdef/delete?',
        		data: {PROC_INST_ID_:this.PROC_INST_ID_,reason:encodeURI(encodeURI(this.messagetext)),tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			 swal("", "已经作废!", "success");
        			 $("#aclose").click();
        			 setTimeout(function(){
                 		top.Dialog.close();//关闭弹窗
                     },1000);
        			 vm.loading = false;
        		 }else if ("exception" == data.result){
                 	showException("作废流程",data.exception);//显示异常
                 }
        		}
        	});
    	},

        //取消作废
        wclose: function (){
        	$("#showform").show();
        },

        //关闭弹窗
        twclose: function (){
        	top.Dialog.close();
        },
        
    	//判断按钮权限，用于是否显示按钮
        hasButton: function(){
        	var keys = 'Reject,Abolish,NextASSIGNEE_';
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'head/hasButton',
        		data: {keys:keys,tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			vm.Reject = data.Reject;				//驳回按钮权限
        			vm.Abolish = data.Abolish;				//作废按钮权限
        			vm.NextASSIGNEE_ = data.NextASSIGNEE_;	//指定下一办理人按钮权限
        		 }else if ("exception" == data.result){
                 	showException("按钮权限",data.exception);//显示异常
                 }
        		}
        	})
        },
        
        formatDate: function (time){
        	let date = new Date(time);
        	return this.formatDateUtil(date, "yyyy-MM-dd hh:mm:ss");
        },
        
      	//根据url参数名称获取参数值
        getUrlKey: function (name) {
            return decodeURIComponent(
                (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
        }
		
	},
	
	mounted(){
        this.init();
    }
})