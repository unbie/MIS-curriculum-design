package com.market.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileUtil {
    /**
     * 文件上传工具类服务方法
     *
     * @param file     文件字节数组
     * @param filePath 文件保存路径
     * @param fileName 文件名
     * @throws Exception 文件写入异常
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        // 创建目标文件对象
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            // 如果目标文件路径不存在，则创建对应的目录
            targetFile.mkdirs();
        }
        // 创建文件输出流
        FileOutputStream out = new FileOutputStream(targetFile.getAbsolutePath() + "/" + fileName);
        // 写入文件
        out.write(file);
        // 刷新缓冲区
        out.flush();
        // 关闭文件输出流
        out.close();
    }

    /**
     * 获取文件上传的保存路径
     *
     * @return 文件上传的保存路径
     */
    public static String getUpLoadFilePath() {
        File path = null;
        try {
            // 获取classpath的绝对路径
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (!path.exists()) {
            path = new File("");
        }
        // 获取文件上传保存的目录路径
        File filePath = new File(path.getAbsolutePath(), "static/images/upload/");
        return filePath.getAbsolutePath();
    }
}
