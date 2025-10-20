/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.taskscheduling.core.util.TaskHandlerManager
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.exp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.taskscheduling.core.util.TaskHandlerManager;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public enum ExecuteRecordColumn {
    TASKTYPE("\u4efb\u52a1\u7c7b\u578b", Boolean.FALSE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            String taskName;
            String taskType = rs.getString(this.name());
            return Objects.equals(taskType, taskName = TaskHandlerManager.getTaskNameByCode((String)taskType)) ? taskType : String.format("%1$s|%2$s", taskType, taskName);
        }
    }
    ,
    DIMCODE("\u7ef4\u5ea6\u4ee3\u7801", Boolean.FALSE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            return rs.getString(this.name());
        }
    }
    ,
    EXECUTESTATE("\u6267\u884c\u72b6\u6001", Boolean.FALSE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            int excuteState = rs.getInt(this.name());
            DataHandleState handleState = DataHandleState.typeof((Integer)excuteState);
            return handleState == null ? Integer.valueOf(excuteState) : String.format("%1$d|%2$s", excuteState, handleState.getTitle());
        }
    }
    ,
    MESSAGE("\u6d88\u606f\u53c2\u6570", Boolean.FALSE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            return rs.getString(this.name());
        }
    }
    ,
    STARTTIME("\u4efb\u52a1\u6267\u884c\u5f00\u59cb\u65f6\u95f4", Boolean.FALSE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            if (!Objects.isNull(rs.getObject(this.name()))) {
                return new Date(rs.getTimestamp(this.name()).getTime());
            }
            return null;
        }
    }
    ,
    ENDTIME("\u4efb\u52a1\u6267\u884c\u7ed3\u675f\u65f6\u95f4", Boolean.FALSE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            if (!Objects.isNull(rs.getObject(this.name()))) {
                return new Date(rs.getTimestamp(this.name()).getTime());
            }
            return null;
        }
    }
    ,
    DURATION("\u4efb\u52a1\u6267\u884c\u65f6\u957f", Boolean.FALSE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            Timestamp startTime = rs.getTimestamp(STARTTIME.name());
            Timestamp endTime = rs.getTimestamp(ENDTIME.name());
            if (!Objects.isNull(startTime) && !Objects.isNull(endTime)) {
                return (((Date)endTime).getTime() - ((Date)startTime).getTime()) / 1000L;
            }
            return null;
        }
    }
    ,
    RESULTLOG("\u8be6\u7ec6\u65e5\u5fd7", Boolean.FALSE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            return rs.getString(this.name());
        }
    }
    ,
    SQLFULLTEXT("SQL\u6587\u672c", Boolean.TRUE){

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
    SQLSTARTTIME("SQL\u6267\u884c\u5f00\u59cb\u65f6\u95f4", Boolean.TRUE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            if (!Objects.isNull(rs.getObject(this.name()))) {
                return new Date(rs.getLong(this.name()));
            }
            return null;
        }
    }
    ,
    SQLENDTIME("SQL\u6267\u884c\u7ed3\u675f\u65f6\u95f4", Boolean.TRUE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            if (!Objects.isNull(rs.getObject(this.name()))) {
                return new Date(rs.getLong(this.name()));
            }
            return null;
        }
    }
    ,
    SQLDURATION("SQL\u6267\u884c\u65f6\u957f", Boolean.TRUE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            if (!Objects.isNull(rs.getObject(SQLSTARTTIME.name())) && !Objects.isNull(rs.getObject(SQLENDTIME.name()))) {
                return (rs.getLong(SQLENDTIME.name()) - rs.getLong(SQLSTARTTIME.name())) / 1000L;
            }
            return null;
        }
    }
    ,
    EXECUTEPARAM("SQL\u6267\u884c\u53c2\u6570", Boolean.TRUE){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            return rs.getString(this.name());
        }
    };

    private String title;
    private Boolean isSqlColumn;

    private ExecuteRecordColumn(String title, Boolean isSqlColumn) {
        this.title = title;
        this.isSqlColumn = isSqlColumn;
    }

    public String getTitle() {
        return this.title;
    }

    public Boolean isSqlColumn() {
        return this.isSqlColumn;
    }

    public abstract Object getObject(ResultSet var1) throws SQLException;
}

