/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.ReportTagDefine;

@DBAnno.DBTable(dbTable="NR_PARAM_RPT_TAG")
public class ReportTagDefineImpl
implements ReportTagDefine {
    @DBAnno.DBField(dbField="RT_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="RT_RPT_KEY")
    private String rptKey;
    @DBAnno.DBField(dbField="RT_TYPE")
    private int type;
    @DBAnno.DBField(dbField="RT_CONTENT")
    private String content;
    @DBAnno.DBField(dbField="RT_EXPRESSION")
    private String expression;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getRptKey() {
        return this.rptKey;
    }

    public void setRptKey(String rptKey) {
        this.rptKey = rptKey;
    }

    @Override
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

