/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.datacheck;

import com.jiuqi.va.query.datacheck.DataBaseInterceptor;
import com.jiuqi.va.query.datacheck.InterceptorEnum;

public abstract class QueryTemplateDeleteInterceptor
implements DataBaseInterceptor {
    @Override
    public InterceptorEnum getType() {
        return InterceptorEnum.QueryTemplateDelete;
    }
}

