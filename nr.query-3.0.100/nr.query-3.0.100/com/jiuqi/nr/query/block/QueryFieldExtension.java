/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public final class QueryFieldExtension {
    private static final Logger log = LoggerFactory.getLogger(QueryFieldExtension.class);
    private boolean rightAxis = false;
    @Autowired
    private ObjectMapper objectMapper;

    public QueryFieldExtension() {
    }

    public boolean isRightAxis() {
        return this.rightAxis;
    }

    public void setRightAxis(boolean rightAxis) {
        this.rightAxis = rightAxis;
    }

    public String toString() {
        try {
            return this.objectMapper.writeValueAsString((Object)this);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public QueryFieldExtension(String queryGridPropertyStr) {
        if (StringUtils.isEmpty((String)queryGridPropertyStr)) {
            this.rightAxis = false;
        }
        try {
            QueryFieldExtension item = (QueryFieldExtension)this.objectMapper.readValue(queryGridPropertyStr, QueryFieldExtension.class);
            this.rightAxis = item.isRightAxis();
        }
        catch (IOException e) {
            this.rightAxis = false;
            log.error(e.getMessage(), e);
        }
    }
}

