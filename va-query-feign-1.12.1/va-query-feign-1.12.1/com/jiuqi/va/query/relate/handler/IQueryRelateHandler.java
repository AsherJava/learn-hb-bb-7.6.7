/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.relate.handler;

import com.jiuqi.va.query.relate.vo.QueryRelateParamVO;
import java.util.List;

public interface IQueryRelateHandler {
    public String getName();

    public String getTitle();

    public List<QueryRelateParamVO> getQueryParams(String var1);
}

