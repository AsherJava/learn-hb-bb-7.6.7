/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 */
package com.jiuqi.nr.designer.planpublish.service;

import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObject;

public interface TaskPlanPublishExternalService {
    public TaskPlanPublishObject queryPlanPublishOfTask(String var1) throws Exception;

    public TaskPlanPublish queryPlanPublishByKey(String var1) throws Exception;

    public boolean taskCanEdit(String var1);

    public void checkParamLockingByTask(String var1);

    public boolean schemeCanEdit(String var1);

    public boolean formCanEdit(String var1);

    public boolean formulaSchemeCanEdit(String var1);

    public boolean printSchemeCanEdit(String var1);

    public String beforeTaskPublish(String var1) throws Exception;

    public void afterTaskPublish(String var1, String var2, boolean var3, String var4) throws Exception;

    public boolean taskNeverPublish(String var1) throws Exception;

    public void createMsgRollBackSuccess(String var1) throws Exception;
}

