/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.bde.common.constant;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;

public enum AgingFetchTypeEnum {
    NC,
    YE;


    public static AgingFetchTypeEnum fromName(String name) {
        Assert.isNotEmpty((String)name);
        for (AgingFetchTypeEnum type : AgingFetchTypeEnum.values()) {
            if (!type.name().equals(name)) continue;
            return type;
        }
        throw new BusinessRuntimeException(String.format("\u4e0d\u652f\u6301\u7684\u671f\u95f4\u7c7b\u578b\u3010%1$s\u3011", name));
    }
}

