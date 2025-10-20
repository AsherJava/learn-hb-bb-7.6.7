/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class FetchResultConfig {
    public static Integer fetchResultTableNum = 10;
    public static Integer fetchResultTableFieldNum = 20;

    @Value(value="${jiuqi.bde.fetch.result.table.num:10}")
    public void setFetchResultTableNum(Integer fetchResultTableNum) {
        FetchResultConfig.fetchResultTableNum = Math.max(Math.min(fetchResultTableNum, 50), 10);
    }

    @Value(value="${jiuqi.bde.fetch.result.table.field.num:20}")
    public void setFetchResultTableFieldNum(Integer fetchResultTableFieldNum) {
        FetchResultConfig.fetchResultTableFieldNum = Math.max(Math.min(fetchResultTableFieldNum, 200), 20);
    }
}

