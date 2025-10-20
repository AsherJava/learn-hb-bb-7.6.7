/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.plugin.gcreport.condition;

import com.jiuqi.common.base.util.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DataCenterEnableCondition
implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String gcreportUrl = context.getEnvironment().getProperty("jiuqi.nvwa.datasources.gcreport.url", String.class);
        return StringUtils.isEmpty((String)gcreportUrl);
    }
}

