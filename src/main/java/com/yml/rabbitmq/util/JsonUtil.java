package com.yml.rabbitmq.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 注意：json转换为对象时，该对象需要有无参的构造函数（jackson存在该问题，fastjson没有这个问题）
 * <p>
 * Created by Yuming-Liu
 * 日期： 2018-08-07
 * 时间： 23:26
 */
public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将POJO转换为JSON
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将JSON转为POJO
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
            LOGGER.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }

    /**
     * 将Json串转为List<T>
     */
    public static <T> List<T> decodeJson2Array(String jsonStr, Class<T> cls) {
        List<T> objList;
        try {
            JavaType type = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, cls);
            objList = OBJECT_MAPPER.readValue(jsonStr, type);
        } catch (Exception e) {
            LOGGER.error("convert JSON to List<POJO> failure", e);
            throw new RuntimeException(e);
        }
        return objList;
    }

}
