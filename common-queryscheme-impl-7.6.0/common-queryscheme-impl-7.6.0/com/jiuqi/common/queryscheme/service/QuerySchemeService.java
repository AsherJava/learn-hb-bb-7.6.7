/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.queryscheme.service;

import com.jiuqi.common.queryscheme.vo.QuerySchemeVO;
import java.util.List;

public interface QuerySchemeService {
    public QuerySchemeVO getQueryScheme(String var1);

    public QuerySchemeVO save(QuerySchemeVO var1);

    public QuerySchemeVO moveUp(QuerySchemeVO var1);

    public QuerySchemeVO moveDown(QuerySchemeVO var1);

    public void moveTo(QuerySchemeVO var1, String var2);

    public void setDefault(QuerySchemeVO var1);

    public void delete(String var1);

    public List<QuerySchemeVO> listByResourceIdAndOptionType(String var1, String var2);

    public void cancelDefault(QuerySchemeVO var1);

    public void rename(QuerySchemeVO var1);
}

