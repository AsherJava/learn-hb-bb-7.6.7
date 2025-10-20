/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.relate.handler.IQueryRelateHandler
 *  com.jiuqi.va.query.relate.vo.QueryRelateHandlerVO
 *  com.jiuqi.va.query.relate.vo.QueryRelateParamVO
 *  com.jiuqi.va.query.relate.web.QueryRelateHandlerClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.query.relate.gather.join;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.relate.gather.IRelateQueryHandlerGather;
import com.jiuqi.va.query.relate.handler.IQueryRelateHandler;
import com.jiuqi.va.query.relate.vo.QueryRelateHandlerVO;
import com.jiuqi.va.query.relate.vo.QueryRelateParamVO;
import com.jiuqi.va.query.relate.web.QueryRelateHandlerClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Primary
public class QueryRelateHandlerClientImpl
implements QueryRelateHandlerClient {
    @Autowired
    private IRelateQueryHandlerGather relateQueryHandlerGather;

    public BusinessResponseEntity<List<QueryRelateHandlerVO>> getFetchSchemes() {
        return BusinessResponseEntity.ok(this.relateQueryHandlerGather.getQueryHandlers());
    }

    public BusinessResponseEntity<List<QueryRelateParamVO>> getQueryParams(@PathVariable(value="queryHandlerName") String queryHandlerName, @RequestBody String relateConfigStr) {
        IQueryRelateHandler relateQueryHandler = this.relateQueryHandlerGather.findQueryHandler(queryHandlerName);
        if (relateQueryHandler == null) {
            return BusinessResponseEntity.error((String)("\u4e0d\u5b58\u5728\u6807\u8bc6\u4e3a\u3010" + queryHandlerName + "\u3011\u7684\u8054\u67e5\u5904\u7406\u5668"));
        }
        return BusinessResponseEntity.ok((Object)relateQueryHandler.getQueryParams(relateConfigStr));
    }
}

