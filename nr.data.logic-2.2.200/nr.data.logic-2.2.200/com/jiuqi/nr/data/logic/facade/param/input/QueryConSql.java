/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import java.util.List;

public class QueryConSql {
    private String conSql;
    private List<Object> conArgs;

    public QueryConSql(String conSql, List<Object> conArgs) {
        this.conSql = conSql;
        this.conArgs = conArgs;
    }

    public String getConSql() {
        return this.conSql;
    }

    public void setConSql(String conSql) {
        this.conSql = conSql;
    }

    public List<Object> getConArgs() {
        return this.conArgs;
    }

    public void setConArgs(List<Object> conArgs) {
        this.conArgs = conArgs;
    }
}

