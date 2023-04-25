var vm = new Vue({
	el: '#app',
	
	data:{
		pd: [],	//存放字段参数
		TYPE: ''
    },
	
	methods: {
		
        //初始执行
        init() {
        	setTimeout(function(){
        		vm.getDic();
            },200);
        },
        
        //去保存
    	save: function (){
    		
    		this.pd.REPORTERNAME = $("#REPORTERNAME").val();
    		if(this.pd.REPORTERNAME  == '' || this.pd.REPORTERNAME == undefined){
    			$("#REPORTERNAME").tips({
    				side:3,
    				msg:'请输入报案人姓名',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.pd.REPORTERNAME = '';
    			this.$refs.REPORTERNAME.focus();
    		return false;
    		}
    		this.pd.TIME = $("#TIME").val();
    		if(this.pd.TIME  == '' || this.pd.TIME == undefined){
    			$("#TIME").tips({
    				side:3,
    				msg:'请输入报案时间',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.pd.TIME = '';
    			this.$refs.TIME.focus();
    		return false;
    		}
    		if(this.TYPE  == ''){
    			$("#TYPE").tips({
    				side:3,
    				msg:'请选择报案类型',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.TYPE = '';
    			this.$refs.TYPE.focus();
    		return false;
    		}
    		if(this.pd.REASON  == '' || this.pd.REASON == undefined){
    			$("#REASON").tips({
    				side:3,
    				msg:'请输入报案事由',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.pd.REASON = '';
    			this.$refs.REASON.focus();
    		return false;
    		}
    		
    		$("#showform").hide();
    		$("#jiazai").show();
    		
            //发送 post 请求提交保存
            $.ajax({
	            	xhrFields: {
	                    withCredentials: true
	                },
					type: "POST",
					url: httpurl+'casehandle/add',
			    	data: {REPORTERNAME:this.pd.REPORTERNAME,
			    		TIME:this.pd.TIME,
			    		TYPE:this.TYPE,
			    		//WHENLONG:this.pd.WHENLONG,
			    		REASON:this.pd.REASON,
			    		tm:new Date().getTime()},
					dataType:"json",
					success: function(data){
                        if("success" == data.result){
                        	top.vm.fhtaskmsg(data.ASSIGNEE_); //websocket即时通讯用于给待办人发送新任务消息 ，fhtaskmsg()函数 在 WebRoot\assets\js-v\index.js
                        	swal("", "保存成功", "success");
                        	setTimeout(function(){
                        		top.Dialog.close();//关闭弹窗
                            },1000);
                        }else if ("errer" == data.result){
                        	swal("审批流启动失败!", "请联系管理员部署相应业务流程!", "warning");//显示异常
                        	$("#showform").show();
                    		$("#jiazai").hide();
                        }else if ("exception" == data.result){
                        	showException("添加请假单",data.exception);//显示异常
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
    	
    	//调用数据字典(请假类型)
    	getDic: function(){
    		$.ajax({
    			xhrFields: {
    	            withCredentials: true
    	        },
    			type: "POST",
    			url: httpurl+'dictionaries/getLevels',
    	    	data: {DICTIONARIES_ID:'de6242c433fa45cf9be9cc2ae84f2802',tm:new Date().getTime()},//请假类型
    			dataType:'json',
    			success: function(data){
    				$("#TYPE").html('<option value="">办案类型</option>');
    				 $.each(data.list, function(i, dvar){
    					 $("#TYPE").append("<option value="+dvar.NAME+">"+dvar.NAME+"</option>");
    				 });
    			}
    		});
    	}
    	
	},
	
	mounted(){
        this.init();
    }
})