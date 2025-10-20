/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.common;

import com.jiuqi.va.query.common.DefinedQueryConfigProperties;
import java.util.regex.Pattern;

public enum SQLKeywordCheck {
    INSERT("[\\s\\S]*insert\\s+into\\s+[\\s\\S]*values[\\s\\S]*", "SQL\u8bed\u53e5\u4e2d\u51fa\u73b0\u4e86\u4e0d\u88ab\u5141\u8bb8\u7684insert\u8bed\u53e5"){

        @Override
        public boolean isAllowed(DefinedQueryConfigProperties queryConfig) {
            return queryConfig.isAllowInsertSql();
        }
    }
    ,
    UPDATE("[\\s\\S]*update\\s+[\\s\\S]+set\\s+[\\s\\S]*", "SQL\u8bed\u53e5\u4e2d\u51fa\u73b0\u4e86\u4e0d\u88ab\u5141\u8bb8\u7684update\u8bed\u53e5"){

        @Override
        public boolean isAllowed(DefinedQueryConfigProperties queryConfig) {
            return queryConfig.isAllowUpdateSql();
        }
    }
    ,
    MERGE("[\\s\\S]*merge\\s+into\\s+[\\s\\S]*", "SQL\u8bed\u53e5\u4e2d\u51fa\u73b0\u4e86\u4e0d\u88ab\u5141\u8bb8\u7684merge\u8bed\u53e5"){

        @Override
        public boolean isAllowed(DefinedQueryConfigProperties queryConfig) {
            return queryConfig.isAllowMergeSql();
        }
    }
    ,
    DELETE("[\\s\\S]*delete\\s+from\\s+[\\s\\S]*", "SQL\u8bed\u53e5\u4e2d\u51fa\u73b0\u4e86\u4e0d\u88ab\u5141\u8bb8\u7684delete\u8bed\u53e5"){

        @Override
        public boolean isAllowed(DefinedQueryConfigProperties queryConfig) {
            return queryConfig.isAllowDeleteSql();
        }
    }
    ,
    TRUNCATE("[\\s\\S]*truncate\\s+table\\s+[\\s\\S]*", "SQL\u8bed\u53e5\u4e2d\u51fa\u73b0\u4e86\u4e0d\u88ab\u5141\u8bb8\u7684truncate\u8bed\u53e5"){

        @Override
        public boolean isAllowed(DefinedQueryConfigProperties queryConfig) {
            return queryConfig.isAllowTruncateSql();
        }
    }
    ,
    CREATE("[\\s\\S]*create\\s+((table+)|(user+)|(tablespace+)|(database+)|([\\s\\S]*index+))\\s+[\\s\\S]*", "SQL\u8bed\u53e5\u4e2d\u51fa\u73b0\u4e86\u4e0d\u88ab\u5141\u8bb8\u7684create\u8bed\u53e5"){

        @Override
        public boolean isAllowed(DefinedQueryConfigProperties queryConfig) {
            return queryConfig.isAllowCreateSql();
        }
    }
    ,
    ALTER("[\\s\\S]*alter\\s+table\\s+[\\s\\S]*", "SQL\u8bed\u53e5\u4e2d\u51fa\u73b0\u4e86\u4e0d\u88ab\u5141\u8bb8\u7684alter\u8bed\u53e5"){

        @Override
        public boolean isAllowed(DefinedQueryConfigProperties queryConfig) {
            return queryConfig.isAllowAlterSql();
        }
    }
    ,
    DROP("[\\s\\S]*drop\\s+((table+)|(user+)|(tablespace+)|(database+)|(index+))\\s+[\\s\\S]*", "SQL\u8bed\u53e5\u4e2d\u51fa\u73b0\u4e86\u4e0d\u88ab\u5141\u8bb8\u7684drop\u8bed\u53e5"){

        @Override
        public boolean isAllowed(DefinedQueryConfigProperties queryConfig) {
            return queryConfig.isAllowDropSql();
        }
    };

    private final String regex;
    private final String errorMessage;

    private SQLKeywordCheck(String regex, String errorMessage) {
        this.regex = regex;
        this.errorMessage = errorMessage;
    }

    public boolean matches(String sql) {
        return Pattern.matches(this.regex, sql);
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public abstract boolean isAllowed(DefinedQueryConfigProperties var1);
}

