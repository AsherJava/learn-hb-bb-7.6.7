/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.relate.handler.IQueryRelateHandler
 *  com.jiuqi.va.query.relate.vo.QueryRelateHandlerVO
 */
package com.jiuqi.va.query.relate.gather;

import com.jiuqi.va.query.relate.handler.IQueryRelateHandler;
import com.jiuqi.va.query.relate.vo.QueryRelateHandlerVO;
import java.util.List;

public interface IRelateQueryHandlerGather {
    public IQueryRelateHandler findQueryHandler(String var1);

    public List<QueryRelateHandlerVO> getQueryHandlers();
}

