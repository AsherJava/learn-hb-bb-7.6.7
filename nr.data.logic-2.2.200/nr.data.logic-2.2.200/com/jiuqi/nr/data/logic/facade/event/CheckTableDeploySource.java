/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.data.logic.facade.event;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.io.Serializable;

public class CheckTableDeploySource
implements Serializable {
    private static final long serialVersionUID = 8700719532488633396L;
    private final TaskDefine task;
    private final FormSchemeDefine formScheme;

    public CheckTableDeploySource(TaskDefine task, FormSchemeDefine formScheme) {
        this.task = task;
        this.formScheme = formScheme;
    }

    public TaskDefine getTask() {
        return this.task;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }
}

