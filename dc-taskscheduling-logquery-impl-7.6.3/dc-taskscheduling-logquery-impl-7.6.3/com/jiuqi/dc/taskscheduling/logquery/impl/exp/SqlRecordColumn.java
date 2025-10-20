/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.exp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public enum SqlRecordColumn {
    SQLFULLTEXT("SQL\u6587\u672c"){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            String sql = rs.getString(this.name());
            String paramStr = rs.getString(EXECUTEPARAM.name());
            if (StringUtils.isEmpty((String)paramStr)) {
                return sql;
            }
            ArrayNode paramArr = (ArrayNode)JsonUtils.readValue((String)paramStr, ArrayNode.class);
            for (JsonNode paramNode : paramArr) {
                String param = null;
                param = paramNode.isNull() ? "''" : (paramNode.isNumber() ? paramNode.numberValue().toString() : String.format("'%1$s'", paramNode.asText()));
                sql = sql.replaceFirst("\\?", param);
            }
            return sql;
        }
    }
    ,
    EXECUTEPARAM("SQL\u6267\u884c\u53c2\u6570"){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            return rs.getString(this.name());
        }
    }
    ,
    STARTTIME("\u4efb\u52a1\u6267\u884c\u5f00\u59cb\u65f6\u95f4"){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            if (!Objects.isNull(rs.getObject(this.name()))) {
                return new Date(rs.getLong(this.name()));
            }
            return null;
        }
    }
    ,
    ENDTIME("\u4efb\u52a1\u6267\u884c\u7ed3\u675f\u65f6\u95f4"){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            if (!Objects.isNull(rs.getObject(this.name()))) {
                return new Date(rs.getLong(this.name()));
            }
            return null;
        }
    }
    ,
    DURATION("SQL\u6267\u884c\u65f6\u957f"){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            Date startTime = new Date(rs.getLong(STARTTIME.name()));
            Date endTime = new Date(rs.getLong(ENDTIME.name()));
            if (!Objects.isNull(startTime) && !Objects.isNull(endTime)) {
                return (endTime.getTime() - startTime.getTime()) / 1000L;
            }
            return null;
        }
    };

    private String title;

    private SqlRecordColumn(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public abstract Object getObject(ResultSet var1) throws SQLException;
}

