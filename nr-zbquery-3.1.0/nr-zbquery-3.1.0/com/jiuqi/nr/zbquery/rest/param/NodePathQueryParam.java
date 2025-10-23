/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package com.jiuqi.nr.zbquery.rest.param;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.nr.zbquery.bean.impl.ZBFieldEx;
import com.jiuqi.nr.zbquery.model.QueryObject;

public class NodePathQueryParam {
    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type", visible=true)
    @JsonSubTypes(value={@JsonSubTypes.Type(value=ZBFieldEx.class, name="ZB")})
    private QueryObject queryObject;

    public QueryObject getQueryObject() {
        return this.queryObject;
    }

    public void setQueryObject(QueryObject queryObject) {
        this.queryObject = queryObject;
    }
}

