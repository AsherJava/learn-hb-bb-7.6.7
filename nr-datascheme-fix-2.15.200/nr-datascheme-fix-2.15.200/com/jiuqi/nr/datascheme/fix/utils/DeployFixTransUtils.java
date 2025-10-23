/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.dao.impl.TransUtil
 */
package com.jiuqi.nr.datascheme.fix.utils;

import com.jiuqi.nr.datascheme.internal.dao.impl.TransUtil;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeployFixTransUtils {
    private static final Logger log = LoggerFactory.getLogger(DeployFixTransUtils.class);

    public Instant transTimeStamp(Timestamp time) {
        return time != null ? time.toInstant() : null;
    }

    public Timestamp transTimeStamp(Instant date) {
        return date != null ? Timestamp.from(date) : null;
    }

    public String transClob(String type) throws SQLException {
        return type;
    }

    public String transClob(Clob type) throws SQLException {
        return TransUtil.transClob((Object)type);
    }

    public String transTableNames(String[] bizKeys) {
        if (bizKeys == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bizKeys.length; ++i) {
            sb.append(bizKeys[i]);
            if (i == bizKeys.length - 1) continue;
            sb.append(";");
        }
        return sb.toString();
    }

    public String[] transTableNames(String bizKeys) {
        if (bizKeys == null) {
            return null;
        }
        return bizKeys.split(";");
    }

    public Integer transBoolean(Boolean b) {
        return b != false ? 1 : 0;
    }

    public Boolean transBoolean(Integer b) {
        return b == 1;
    }
}

