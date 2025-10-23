/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils {
    static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
    private static Properties properties;

    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream("temptable.properties");){
            if (input == null) {
                logger.error("\u914d\u7f6e\u6587\u4ef6\u672a\u627e\u5230: temptable.properties");
                return;
            }
            properties.load(input);
        }
        catch (IOException e) {
            logger.error("\u52a0\u8f7d\u914d\u7f6e\u6587\u4ef6\u5931\u8d25\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
    }

    public static String getPropertyValue(String key) {
        if (properties == null) {
            PropertiesUtils.loadProperties();
        }
        return properties.getProperty(key);
    }

    public static String getPropertyValue(String key, String defaultValue) {
        String value = PropertiesUtils.getPropertyValue(key);
        return value == null ? defaultValue : value;
    }

    public static int getIntPropertyValue(String key) {
        String value = PropertiesUtils.getPropertyValue(key);
        int intValue = -1;
        if (value != null) {
            try {
                intValue = Integer.parseInt(value);
            }
            catch (NumberFormatException e) {
                logger.error("\u914d\u7f6e\u9879\u3010{}\u3011\u4e3a\u975e\u6570\u503c\u7c7b\u578b\uff0c\u8f6c\u6362\u5931\u8d25", (Object)key);
                throw e;
            }
        }
        return intValue;
    }

    public static int getIntPropertyValue(String key, int defaultValue) {
        String value = PropertiesUtils.getPropertyValue(key);
        int intValue = -1;
        if (value != null) {
            try {
                intValue = Integer.parseInt(value);
            }
            catch (NumberFormatException e) {
                intValue = defaultValue;
                logger.error("\u914d\u7f6e\u9879\u3010{}\u3011\u4e3a\u975e\u6570\u503c\u7c7b\u578b\uff0c\u8f6c\u6362\u5931\u8d25\uff0c\u4f7f\u7528\u9ed8\u8ba4\u503c\uff1a{}", (Object)key, (Object)defaultValue);
            }
        }
        return value == null ? defaultValue : intValue;
    }
}

