/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.datacheck;

import com.jiuqi.va.query.datacheck.QueryTemplateDeleteInterceptor;
import com.jiuqi.va.query.domain.DataCheckParam;
import com.jiuqi.va.query.domain.DataCheckResult;
import org.springframework.stereotype.Component;

@Component(value="dafaultQueryTemplateDelete")
public class DefaultQueryTemplateDeleteInterceptor
extends QueryTemplateDeleteInterceptor {
    @Override
    public DataCheckResult preHandler(DataCheckParam dataCheckParam) {
        return DataCheckResult.pass();
    }
}

