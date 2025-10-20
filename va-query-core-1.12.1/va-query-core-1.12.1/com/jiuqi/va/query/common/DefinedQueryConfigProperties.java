/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value={"classpath:/config/DefinedQueryConfig.properties"}, ignoreResourceNotFound=true)
public class DefinedQueryConfigProperties {
    @Value(value="${allowInsertSql:false}")
    private boolean allowInsertSql;
    @Value(value="${allowUpdateSql:false}")
    private boolean allowUpdateSql;
    @Value(value="${allowMergeSql:false}")
    private boolean allowMergeSql;
    @Value(value="${allowDeleteSql:false}")
    private boolean allowDeleteSql;
    @Value(value="${allowTruncateSql:false}")
    private boolean allowTruncateSql;
    @Value(value="${allowCreateSql:false}")
    private boolean allowCreateSql;
    @Value(value="${allowAlterSql:false}")
    private boolean allowAlterSql;
    @Value(value="${allowDropSql:false}")
    private boolean allowDropSql;

    public boolean isAllowInsertSql() {
        return this.allowInsertSql;
    }

    public void setAllowInsertSql(boolean allowInsertSql) {
        this.allowInsertSql = allowInsertSql;
    }

    public boolean isAllowUpdateSql() {
        return this.allowUpdateSql;
    }

    public void setAllowUpdateSql(boolean allowUpdateSql) {
        this.allowUpdateSql = allowUpdateSql;
    }

    public boolean isAllowMergeSql() {
        return this.allowMergeSql;
    }

    public void setAllowMergeSql(boolean allowMergeSql) {
        this.allowMergeSql = allowMergeSql;
    }

    public boolean isAllowDeleteSql() {
        return this.allowDeleteSql;
    }

    public void setAllowDeleteSql(boolean allowDeleteSql) {
        this.allowDeleteSql = allowDeleteSql;
    }

    public boolean isAllowTruncateSql() {
        return this.allowTruncateSql;
    }

    public void setAllowTruncateSql(boolean allowTruncateSql) {
        this.allowTruncateSql = allowTruncateSql;
    }

    public boolean isAllowCreateSql() {
        return this.allowCreateSql;
    }

    public void setAllowCreateSql(boolean allowCreateSql) {
        this.allowCreateSql = allowCreateSql;
    }

    public boolean isAllowAlterSql() {
        return this.allowAlterSql;
    }

    public void setAllowAlterSql(boolean allowAlterSql) {
        this.allowAlterSql = allowAlterSql;
    }

    public boolean isAllowDropSql() {
        return this.allowDropSql;
    }

    public void setAllowDropSql(boolean allowDropSql) {
        this.allowDropSql = allowDropSql;
    }
}

