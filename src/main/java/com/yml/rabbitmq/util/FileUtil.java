package com.yml.rabbitmq.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by Yuming-Liu
 * 日期： 2018-08-14
 * 时间： 21:11
 */
public final class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取真实的文件名(自动去掉文件的路径)
     */
    public static String getRealFileName(String fileName) {
        return FilenameUtils.getName(fileName);
    }

    /**
     * 创建文件
     */
    public static File createFile(String filePath) {
        File file;
        try {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                FileUtils.forceMkdir(parentDir);
            }
        } catch (Exception e) {
            LOGGER.error("create file failure", e);
            throw new RuntimeException(e);
        }
        return file;
    }

}
