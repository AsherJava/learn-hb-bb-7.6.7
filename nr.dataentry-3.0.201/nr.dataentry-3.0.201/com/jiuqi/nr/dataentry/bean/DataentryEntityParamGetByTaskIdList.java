/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.context.infc.impl.NRContext;
import java.util.List;

public class DataentryEntityParamGetByTaskIdList
extends NRContext {
    private List<String> taskIdList;

    public List<String> getTaskIdList() {
        return this.taskIdList;
    }

    public void setTaskIdList(List<String> taskIdList) {
        this.taskIdList = taskIdList;
    }
}

