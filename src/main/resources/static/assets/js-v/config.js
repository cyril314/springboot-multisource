function getRoot() {
    let protocol = location.protocol;
    let hostname = location.hostname;
    let port = location.port;
    return protocol + "//" + hostname + ":" + port + "/";
}

//服务器host
var httpurl = getRoot();

//显示异常
function showException(modular, exception) {
    swal("[" + modular + "]程序异常!", "抱歉！您访问的页面出现异常，请稍后重试或联系管理员 " + exception, "warning");
}