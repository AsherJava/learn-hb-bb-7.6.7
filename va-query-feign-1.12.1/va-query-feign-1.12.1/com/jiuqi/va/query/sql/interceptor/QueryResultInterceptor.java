/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.interceptor;

import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import java.util.Map;

public interface QueryResultInterceptor {
    public String getProcessorName();

    public Boolean isNeedIntercept(TemplateRelateQueryVO var1);

    public Map<String, Object> intercept(Map<String, Object> var1, TemplateRelateQueryVO var2);
}

