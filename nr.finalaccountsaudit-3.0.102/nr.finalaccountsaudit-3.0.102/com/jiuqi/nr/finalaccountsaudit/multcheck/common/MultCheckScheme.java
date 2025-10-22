/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.common;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.sql.Clob;
import org.springframework.stereotype.Repository;

@Repository
@DBAnno.DBTable(dbTable="SYS_MULTCHECK_SCHEME")
public class MultCheckScheme {
    @DBAnno.DBField(dbField="S_KEY", dbType=String.class, isPk=true)
    private String key;
    @DBAnno.DBField(dbField="S_NAME", dbType=String.class, isPk=false)
    private String name;
    @DBAnno.DBField(dbField="S_DW", dbType=Clob.class, isPk=false)
    private String dw;
    @DBAnno.DBField(dbField="S_ORDER", dbType=String.class)
    private String order;
    @DBAnno.DBField(dbField="S_CONTENT", dbType=Clob.class)
    private String content;
    @DBAnno.DBField(dbField="S_TASKKEY", dbType=String.class, isPk=false)
    private String taskKey;
    @DBAnno.DBField(dbField="S_FORMSCHEMEKEY", dbType=String.class, isPk=false)
    private String formSchemeKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

