/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.resource;

import com.jiuqi.nr.designer.resource.ResourcePrivilegeParam;

public class WorkFlowPrivilegeParam
extends ResourcePrivilegeParam {
    private boolean subTable = false;
    private boolean subGroup = false;
    private boolean flow = false;
    private boolean isSubmit = false;

    public boolean isSubTable() {
        return this.subTable;
    }

    public void setSubTable(boolean subTable) {
        this.subTable = subTable;
    }

    public boolean isSubGroup() {
        return this.subGroup;
    }

    public void setSubGroup(boolean subGroup) {
        this.subGroup = subGroup;
    }

    public boolean isFlow() {
        return this.flow;
    }

    public void setFlow(boolean flow) {
        this.flow = flow;
    }

    public boolean isSubmit() {
        return this.isSubmit;
    }

    public void setSubmit(boolean submit) {
        this.isSubmit = submit;
    }
}

