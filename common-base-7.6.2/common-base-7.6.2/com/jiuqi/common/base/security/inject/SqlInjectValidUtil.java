/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.security.inject;

import com.jiuqi.common.base.BusinessRuntimeException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class SqlInjectValidUtil {
    private static Logger logger = LoggerFactory.getLogger(SqlInjectValidUtil.class);

    public static void valid(String content) {
        Matcher matcher;
        Pattern sqlPattern = Pattern.compile("\\b(select|update|and|or|delete|insert|trancate|char|ascii|into|drop|execute)\\b", 2);
        if (StringUtils.hasText(content) && (matcher = sqlPattern.matcher(content)).find()) {
            logger.error("\u5305\u542b\u975e\u6cd5\u5b57\u7b26={}", (Object)content);
            throw new BusinessRuntimeException("\u8bf7\u6c42\u5185\u5bb9\u5305\u542b\u975e\u6cd5\u5b57\u7b26\u3002");
        }
    }
}

