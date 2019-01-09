package com.yml.rabbitmq.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Yuming-Liu
 * 日期： 2018-07-31
 * 时间： 00:07
 */
public final class CollectionUtil {
    /**
     * 判断Colletion是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断Collection是否非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断Map是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

    /**
     * 判断Map是否非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !MapUtils.isEmpty(map);
    }
}
