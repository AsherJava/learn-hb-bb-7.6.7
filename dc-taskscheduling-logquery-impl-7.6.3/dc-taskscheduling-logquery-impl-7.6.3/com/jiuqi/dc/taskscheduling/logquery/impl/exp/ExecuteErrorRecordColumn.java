/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.exp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public enum ExecuteErrorRecordColumn {
    TASKTYPE("\u4efb\u52a1\u7c7b\u578b"){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            return rs.getString(this.name());
        }
    }
    ,
    DIMCODE("\u7ef4\u5ea6\u4ee3\u7801"){

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
                return new Date(rs.getTimestamp(this.name()).getTime());
            }
            return null;
        }
    }
    ,
    ENDTIME("\u4efb\u52a1\u6267\u884c\u7ed3\u675f\u65f6\u95f4"){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            if (!Objects.isNull(rs.getObject(this.name()))) {
                return new Date(rs.getTimestamp(this.name()).getTime());
            }
            return null;
        }
    }
    ,
    DURATION("\u4efb\u52a1\u6267\u884c\u65f6\u957f"){

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
    RESULTLOG("\u5931\u8d25\u539f\u56e0"){

        @Override
        public Object getObject(ResultSet rs) throws SQLException {
            return rs.getString(this.name());
        }
    };

    private String title;

    private ExecuteErrorRecordColumn(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public abstract Object getObject(ResultSet var1) throws SQLException;
}

