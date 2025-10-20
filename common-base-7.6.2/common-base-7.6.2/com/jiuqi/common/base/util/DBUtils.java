/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class DBUtils {
    @Value(value="${spring.datasource.dbType}")
    private String dbType;

    public String getDBType() {
        if (ObjectUtils.isEmpty(this.dbType)) {
            throw new RuntimeException("\u83b7\u53d6\u6570\u636e\u5e93\u7c7b\u578b\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5spring.datasource.dbType\u53c2\u6570\u3002");
        }
        return this.dbType;
    }
}

