/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.controller2.RunTimeViewController
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 */
package com.jiuqi.gcreport.nr.impl.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.controller2.RunTimeViewController;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import java.util.List;

public class GcOrgTypeUtils {
    public static final void setContextEntityId(String orgType) {
        if (StringUtils.isEmpty((String)orgType)) {
            return;
        }
        DsContextImpl dsContext = (DsContextImpl)DsContextHolder.getDsContext();
        dsContext.setEntityId(orgType + "@ORG");
    }

    public static final String getContextEntityId() {
        DsContextImpl dsContext = (DsContextImpl)DsContextHolder.getDsContext();
        String contextEntityId = dsContext.getContextEntityId();
        if (StringUtils.isEmpty((String)contextEntityId)) {
            return null;
        }
        return contextEntityId.replace("@ORG", "");
    }

    public static String getOrgTypeByContextOrTaskId(String taskId) {
        String orgType = GcOrgTypeUtils.getContextEntityId();
        if (!StringUtils.isEmpty((String)orgType)) {
            return orgType;
        }
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        TaskDefine taskDefine = iRunTimeViewController.queryTaskDefine(taskId);
        if (taskDefine != null) {
            return taskDefine.getDw().replace("@ORG", "");
        }
        return "";
    }

    public static String getEntityIdByTaskIdAndOrgType(String taskId, String orgType) {
        if (StringUtils.isEmpty((String)taskId)) {
            return "";
        }
        RunTimeViewController runTimeViewController = (RunTimeViewController)SpringContextUtils.getBean(RunTimeViewController.class);
        TaskOrgLinkListStream taskOrgLinkListStream = runTimeViewController.listTaskOrgLinkStreamByTask(taskId);
        List taskOrgLinkList = taskOrgLinkListStream.auth().i18n().getList();
        if (taskOrgLinkList == null || taskOrgLinkList.size() <= 1) {
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            TaskDefine taskDefine = iRunTimeViewController.queryTaskDefine(taskId);
            if (taskDefine != null) {
                return taskDefine.getDw();
            }
            return "";
        }
        if (StringUtils.isEmpty((String)orgType)) {
            return "";
        }
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkList) {
            String entity = taskOrgLinkDefine.getEntity();
            if (StringUtils.isEmpty((String)entity)) continue;
            return entity.replace("@ORG", "");
        }
        return "";
    }

    public static String getOrgTypeByTaskIdOrEntityId(String taskId, String entityId) {
        if (StringUtils.isEmpty((String)entityId)) {
            if (StringUtils.isEmpty((String)taskId)) {
                return "";
            }
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            TaskDefine taskDefine = iRunTimeViewController.queryTaskDefine(taskId);
            if (taskDefine != null) {
                return taskDefine.getDw().replace("@ORG", "");
            }
            return "";
        }
        return entityId.replace("@ORG", "");
    }

    public static String getEntityIdByTaskIdOrEntityId(String taskId, String entityId) {
        if (StringUtils.isEmpty((String)entityId)) {
            if (StringUtils.isEmpty((String)taskId)) {
                return "";
            }
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            TaskDefine taskDefine = iRunTimeViewController.queryTaskDefine(taskId);
            if (taskDefine != null) {
                return taskDefine.getDw();
            }
            return "";
        }
        return entityId;
    }
}

