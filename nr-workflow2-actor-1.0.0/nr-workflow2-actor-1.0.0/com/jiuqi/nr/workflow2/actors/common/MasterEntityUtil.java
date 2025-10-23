/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.workflow2.actors.common;

import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class MasterEntityUtil {
    public static String getTaskMasterEntityId(TaskDefine task) {
        String entityId = DsContextHolder.getDsContext().getContextEntityId();
        if (entityId == null || entityId.length() == 0) {
            return task.getDw();
        }
        return entityId;
    }

    public static String getTaskMasterEntityTableKey(IEntityMetaService entityMetaService, TaskDefine task) {
        String entityId = MasterEntityUtil.getTaskMasterEntityId(task);
        TableModelDefine entityTableModel = entityMetaService.getTableModel(entityId);
        if (entityTableModel != null) {
            return entityTableModel.getName();
        }
        return null;
    }
}

