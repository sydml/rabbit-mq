package com.yml.rabbitmq.util;

/**
 * Created by Yuming-Liu
 * 日期： 2018-07-30
 * 时间： 23:42
 */
public final class CastUtil {

    /**
     * 转换为String
     */
    public static String castString(Object object) {
        return CastUtil.castString(object, "");
    }

    /**
     * 转换为String,提供默认值
     */
    public static String castString(Object object, String defaultVlaue) {
        return object != null ? String.valueOf(object) : defaultVlaue;
    }

    /**
     * 转换为double
     */
    public static double castDouble(Object object) {
        return CastUtil.castDouble(object, 0);
    }

    /**
     * 转换为double,提供默认值
     */
    public static double castDouble(Object object, double defaultValue) {
        double value = defaultValue;
        if (object != null) {
            String strValue = castString(object);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    value = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转换为long
     */
    public static long castLong(Object object) {
        return CastUtil.castLong(object, 0);
    }

    /**
     * 转换为long,提供默认值
     */
    public static long castLong(Object object, long defaultValue) {
        long value = defaultValue;
        if (object != null) {
            String strValue = castString(object);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    value = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转换为int
     */
    public static int castInt(Object object) {
        return CastUtil.castInt(object, 0);
    }

    /**
     * 转换为long,提供默认值
     */
    public static int castInt(Object object, int defaultValue) {
        int value = defaultValue;
        if (object != null) {
            String strValue = castString(object);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    value = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转换为boolean
     */
    public static boolean castBoolean(Object object) {
        return CastUtil.castBoolean(object, false);
    }

    /**
     * 转换为long,提供默认值
     */
    public static boolean castBoolean(Object object, boolean defaultValue) {
        boolean value = defaultValue;
        if (object != null) {
            value = Boolean.parseBoolean(castString(object));
        }
        return value;
    }
    
    
}
