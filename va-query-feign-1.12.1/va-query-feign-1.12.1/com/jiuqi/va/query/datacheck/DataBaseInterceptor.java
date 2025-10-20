/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.datacheck;

import com.jiuqi.va.query.datacheck.InterceptorEnum;
import com.jiuqi.va.query.domain.DataCheckParam;
import com.jiuqi.va.query.domain.DataCheckResult;
import java.util.List;

public interface DataBaseInterceptor {
    public InterceptorEnum getType();

    public DataCheckResult preHandler(DataCheckParam var1);

    default public void afterHandler(List<String> codes) {
    }
}

