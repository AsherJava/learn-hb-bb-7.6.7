/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.common.taskschedule.stream.util;

import com.jiuqi.common.base.util.StringUtils;
import org.springframework.context.annotation.ConditionContext;

public class EntMqEnvironmentMatchUtil {
    public static boolean matchType(ConditionContext context, String type) {
        if (StringUtils.isEmpty((String)type)) {
            return false;
        }
        String defaultBinder = context.getEnvironment().getProperty("spring.cloud.stream.default-binder", String.class);
        if (StringUtils.isEmpty((String)defaultBinder) && StringUtils.isEmpty((String)(defaultBinder = context.getEnvironment().getProperty("spring.cloud.stream.default", String.class)))) {
            return false;
        }
        String defaultBinderType = context.getEnvironment().getProperty("spring.cloud.stream.binders." + defaultBinder + ".type", String.class);
        if (StringUtils.isEmpty((String)defaultBinderType)) {
            return false;
        }
        return defaultBinderType.equals(type);
    }
}

