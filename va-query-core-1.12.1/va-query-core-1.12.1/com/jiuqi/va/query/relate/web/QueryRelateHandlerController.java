/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.relate.handler.IQueryRelateHandler
 *  com.jiuqi.va.query.relate.vo.QueryRelateHandlerVO
 *  com.jiuqi.va.query.relate.vo.QueryRelateParamVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.query.relate.web;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.relate.gather.IRelateQueryHandlerGather;
import com.jiuqi.va.query.relate.handler.IQueryRelateHandler;
import com.jiuqi.va.query.relate.vo.QueryRelateHandlerVO;
import com.jiuqi.va.query.relate.vo.QueryRelateParamVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryRelateHandlerController {
    private static final String FETCH_QUERY_BASE_API = "/api/datacenter/v1/userDefined";
    @Autowired
    private IRelateQueryHandlerGather relateQueryHandlerGather;

    @GetMapping(value={"/api/datacenter/v1/userDefined/getRelateQueryHandlers"})
    public BusinessResponseEntity<List<QueryRelateHandlerVO>> getFetchSchemes() {
        return BusinessResponseEntity.ok(this.relateQueryHandlerGather.getQueryHandlers());
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/getRelateQueryParams/{queryHandlerName}"})
    public BusinessResponseEntity<List<QueryRelateParamVO>> getQueryParams(@PathVariable(value="queryHandlerName") String queryHandlerName, @RequestBody String relateConfigStr) {
        IQueryRelateHandler relateQueryHandler = this.relateQueryHandlerGather.findQueryHandler(queryHandlerName);
        if (relateQueryHandler == null) {
            return BusinessResponseEntity.error((String)("\u4e0d\u5b58\u5728\u6807\u8bc6\u4e3a\u3010" + queryHandlerName + "\u3011\u7684\u8054\u67e5\u5904\u7406\u5668"));
        }
        return BusinessResponseEntity.ok((Object)relateQueryHandler.getQueryParams(relateConfigStr));
    }
}

