package com.yml.rabbitmq.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Liuym
 * @date 2018/8/14 0014
 */
public class InstanceUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceUtil.class);

    /**
     * 反射获取实例,不需要强转
     */
    public static <T> T getInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error("Class Cast Exception", e);
            throw new RuntimeException(e);
        }
    }
}
