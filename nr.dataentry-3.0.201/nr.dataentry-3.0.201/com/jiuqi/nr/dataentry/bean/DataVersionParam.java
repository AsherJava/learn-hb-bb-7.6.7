/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.uniformity.service.JUniformityService
 */
package com.jiuqi.nr.dataentry.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.uniformity.service.JUniformityService;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataVersionParam
extends JtableLog
implements JUniformityService {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String dataVersionId;
    private String title;
    private String describe;
    private boolean autoCreated;
    private boolean edit;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isAutoCreated() {
        return this.autoCreated;
    }

    public void setAutoCreated(boolean autoCreated) {
        this.autoCreated = autoCreated;
    }

    public String getDataVersionId() {
        return this.dataVersionId;
    }

    public void setDataVersionId(String dataVersionId) {
        this.dataVersionId = dataVersionId;
    }

    public boolean isEdit() {
        return this.edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}

