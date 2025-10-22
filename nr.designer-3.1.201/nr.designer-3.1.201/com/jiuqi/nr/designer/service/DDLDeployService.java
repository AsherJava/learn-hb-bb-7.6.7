/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import java.util.List;

public interface DDLDeployService {
    public boolean isEnableDDL();

    public List<String> preDeploy(String var1);

    public void deploy(String var1);

    public void cancelDeploy(String var1);

    public boolean executeDDL(String var1);

    public TaskPlanPublish getTaskPlanByTask(String var1);

    public boolean paramLocking(String var1);

    public void checkByTask(String var1) throws JQException;

    public boolean canCancel(String var1);
}

