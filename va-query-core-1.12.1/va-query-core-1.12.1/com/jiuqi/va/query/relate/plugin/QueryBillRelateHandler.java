/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.query.exception.DefinedQuerySqlException
 *  com.jiuqi.va.query.relate.handler.IQueryRelateHandler
 */
package com.jiuqi.va.query.relate.plugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.query.exception.DefinedQuerySqlException;
import com.jiuqi.va.query.relate.handler.IQueryRelateHandler;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class QueryBillRelateHandler
implements IQueryRelateHandler {
    public String getName() {
        return "QueryBill";
    }

    public String getTitle() {
        return "\u8054\u67e5\u5355\u636e";
    }

    public List getQueryParams(String relateConfigStr) {
        String jsonStr = "[{\"name\": \"toolBarShowStrategy\", \"title\": \"\u5de5\u5177\u680f\u914d\u7f6e\",\"valueType\": \"fixed\", \"needFlag\": false,\"value\": \"hideToolBar\"},\n{\"name\": \"billCode\", \"title\": \"\u5355\u636e\u7f16\u53f7\", \"needFlag\": true},{\"name\": \"verifyCode\", \"title\": \"\u9a8c\u8bc1\u7801\", \"needFlag\": false},\n{\"name\": \"schemeCode\", \"title\": \"\u754c\u9762\u65b9\u6848\", \"needFlag\": false}]";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (List)objectMapper.readValue(jsonStr, List.class);
        }
        catch (JsonProcessingException e) {
            throw new DefinedQuerySqlException((Throwable)e);
        }
    }
}

