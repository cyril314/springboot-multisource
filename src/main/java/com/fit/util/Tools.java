package com.fit.util;

import java.io.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具
 */
public class Tools {

    /**
     * 随机生成六位数验证码
     */
    public static int getRandomNum() {
        Random r = new Random();
        return r.nextInt(900000) + 100000;//(Math.random()*(999999-100000)+100000)
    }

    /**
     * 随机生成四位数验证码
     */
    public static int getRandomNum4() {
        Random r = new Random();
        return r.nextInt(9000) + 1000;
    }

    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s) {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }

    /**
     * 字符串转换为字符串数组
     *
     * @param str        字符串
     * @param splitRegex 分隔符
     */
    public static String[] str2StrArray(String str, String splitRegex) {
        if (isEmpty(str)) {
            return null;
        }
        return str.split(splitRegex);
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     *
     * @param str 字符串
     */
    public static String[] str2StrArray(String str) {
        return str2StrArray(str, ",\\s*");
    }

    /**
     * 验证邮箱
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 检测KEY是否正确
     *
     * @param paraname 传入参数
     * @param FKEY     接收的 KEY
     * @return 为空则返回true，不否则返回false
     */
    public static boolean checkKey(String paraname, String FKEY) {
        paraname = (null == paraname) ? "" : paraname;
        return MD5.md5(paraname + DateUtil.getNowDate() + ",fh,").equals(FKEY);
    }

    /**
     * 读取txt里的全部内容
     *
     * @param fileP    文件路径
     * @param encoding 编码
     */
    public static String readTxtFileAll(String fileP, String encoding) {
        try {
            if (!(fileP.startsWith("/") || fileP.startsWith("\\"))) {
                fileP = File.separator + fileP;
            }
            String filePath = PathUtil.getClasspath() + "static" + fileP.trim();    //项目路径
            if (filePath.indexOf(":") != 1) {
                filePath = File.separator + filePath;
            }
            return readFileAllContent(filePath, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Projectpath某文件里的全部内容
     *
     * @param fileP 文件路径
     */
    public static String readTxtFile(String fileP) {
        try {
            return readFileAllContent(PathUtil.getProjectpath() + fileP, null);
        } catch (IOException e) {
            return readTxtFileAll(fileP, null);
        }
    }

    /**
     * 读取文件里的全部内容
     *
     * @param fileP    文件路径
     * @param encoding 编码
     */
    public static String readFileAllContent(String fileP, String encoding) throws IOException {
        StringBuffer fileContent = new StringBuffer();
        if (isEmpty(encoding)) {
            encoding = "utf-8";
        }
        File file = new File(fileP);//文件路径
        if (file.isFile() && file.exists()) {// 判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);    // 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                fileContent.append(lineTxt);
                fileContent.append("\n");
            }
            read.close();
        } else {
            throw new FileNotFoundException("找不到指定的文件,查看此路径是否正确:" + fileP);
        }
        return fileContent.toString();
    }

    /**
     * 往文件里的内容
     *
     * @param fileP    文件路径
     * @param content  写入的内容
     * @param encoding 编码格式
     */
    public static void writingFile(String fileP, String content, String encoding) {
        try {
            if (isEmpty(encoding)) {
                encoding = "utf-8";
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(fileP), encoding);
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 往文件里的内容（Projectpath下）
     *
     * @param fileP   文件路径
     * @param content 写入的内容
     */
    public static void writeFileCR(String fileP, String content) {
        String filePath = PathUtil.getProjectpath() + fileP;
        File file = new File(fileP);//文件路径
        if (file.isFile() && file.exists()) {// 判断文件是否存在
            writingFile(filePath, content, null);
        } else {
            writeFile(fileP, content);
        }
    }

    /**
     * 往文件里的内容
     *
     * @param fileP   文件路径
     * @param content 写入的内容
     */
    public static void writeFile(String fileP, String content) {
        if (!(fileP.startsWith("/") || fileP.startsWith("\\"))) {
            fileP = File.separator + fileP;
        }
        String filePath = PathUtil.getClasspath() + "static" + fileP.trim();//项目路径
        if (filePath.indexOf(":") != 1) {
            filePath = File.separator + filePath;
        }
        writingFile(filePath, content, null);
    }
}
