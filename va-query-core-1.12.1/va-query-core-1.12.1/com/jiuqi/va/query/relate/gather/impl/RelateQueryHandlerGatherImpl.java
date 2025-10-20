/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.relate.handler.IQueryRelateHandler
 *  com.jiuqi.va.query.relate.vo.QueryRelateHandlerVO
 */
package com.jiuqi.va.query.relate.gather.impl;

import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.relate.gather.IRelateQueryHandlerGather;
import com.jiuqi.va.query.relate.handler.IQueryRelateHandler;
import com.jiuqi.va.query.relate.vo.QueryRelateHandlerVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelateQueryHandlerGatherImpl
implements IRelateQueryHandlerGather,
InitializingBean {
    private final Map<String, IQueryRelateHandler> relateQueryHandlerMap = new ConcurrentHashMap<String, IQueryRelateHandler>();
    private final List<QueryRelateHandlerVO> queryRelateHandlerVOList = new ArrayList<QueryRelateHandlerVO>();
    @Autowired(required=false)
    private List<IQueryRelateHandler> relateQueryHandlers;

    @Override
    public void afterPropertiesSet() {
        if (this.relateQueryHandlers == null || this.relateQueryHandlers.isEmpty()) {
            return;
        }
        for (IQueryRelateHandler relateQueryHandler : this.relateQueryHandlers) {
            if (this.relateQueryHandlerMap.containsKey(relateQueryHandler.getName())) {
                throw new DefinedQueryRuntimeException("\u5df2\u7ecf\u5b58\u5728\u91cd\u590d\u6807\u8bc6\u7684IRelateQueryHandler\u3010" + relateQueryHandler.getName() + "\u3011");
            }
            this.relateQueryHandlerMap.put(relateQueryHandler.getName(), relateQueryHandler);
            this.queryRelateHandlerVOList.add(new QueryRelateHandlerVO(relateQueryHandler.getName(), relateQueryHandler.getTitle()));
        }
    }

    @Override
    public IQueryRelateHandler findQueryHandler(String queryHandlerName) {
        return this.relateQueryHandlerMap.get(queryHandlerName);
    }

    @Override
    public List<QueryRelateHandlerVO> getQueryHandlers() {
        return this.queryRelateHandlerVOList;
    }
}

