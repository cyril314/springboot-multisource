package com.fit.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 路径工具类
 */
public class PathUtil {

    /**
     * 获取Projectpath
     */
    public static String getProjectpath() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getDumpFilePath(request.getServletContext().getRealPath("/"));
    }

    /**
     * 获取Classpath
     */
    public static String getClasspath() {
        String path = getDumpFilePath(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")));
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        //path = "H:\\";  //当项目以jar、war包运行时，路径改成实际硬盘位置
        return path;
    }

    public static String getDumpFilePath(String filePath) {
        return filePath.replaceAll("file:/", "").replaceAll("%20", " ").trim();
    }
}
