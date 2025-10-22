/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import java.util.ArrayList;
import java.util.List;

public class TasksAndSchemesVO {
    private DesignTaskDefine designTaskDefine;
    private List<DesignFormSchemeDefine> designFormSchemeDefines;

    public TasksAndSchemesVO() {
    }

    public TasksAndSchemesVO(DesignTaskDefine designTaskDefine, List<DesignFormSchemeDefine> designFormSchemeDefines) {
        this.designTaskDefine = designTaskDefine;
        this.designFormSchemeDefines = designFormSchemeDefines == null ? new ArrayList() : designFormSchemeDefines;
    }

    public DesignTaskDefine getDesignTaskDefine() {
        return this.designTaskDefine;
    }

    public void setDesignTaskDefine(DesignTaskDefine designTaskDefine) {
        this.designTaskDefine = designTaskDefine;
    }

    public List<DesignFormSchemeDefine> getDesignFormSchemeDefines() {
        if (this.designFormSchemeDefines == null) {
            return new ArrayList<DesignFormSchemeDefine>();
        }
        return this.designFormSchemeDefines;
    }

    public void setDesignFormSchemeDefines(List<DesignFormSchemeDefine> designFormSchemeDefines) {
        this.designFormSchemeDefines = designFormSchemeDefines;
    }
}

