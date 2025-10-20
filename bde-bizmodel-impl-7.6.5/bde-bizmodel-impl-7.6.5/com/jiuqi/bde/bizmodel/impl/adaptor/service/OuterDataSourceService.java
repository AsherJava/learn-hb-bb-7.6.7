/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.service;

import org.springframework.jdbc.core.ResultSetExtractor;

public interface OuterDataSourceService {
    public <T> T query(String var1, Object[] var2, ResultSetExtractor<T> var3);
}

