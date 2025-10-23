/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 *  com.jiuqi.nr.task.api.common.Constants$ActionType
 *  com.jiuqi.nr.task.api.face.ITaskAction
 */
package com.jiuqi.nr.task.internal.action.service;

import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.face.ITaskAction;
import com.jiuqi.nr.task.dto.TaskActionDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ActionService {
    @Autowired(required=false)
    private List<ITaskAction> taskActions;

    public List<TaskActionDTO> listActions(Constants.ActionType actionType, String key) {
        if (CollectionUtils.isEmpty(this.taskActions)) {
            return Collections.emptyList();
        }
        ArrayList<TaskActionDTO> actions = new ArrayList<TaskActionDTO>(this.taskActions.size());
        for (ITaskAction action : this.taskActions) {
            ComponentDefine component;
            if (!action.apply(actionType, key) || (component = action.component()) == null) continue;
            TaskActionDTO taskActionDTO = new TaskActionDTO();
            taskActionDTO.setCode(action.code());
            taskActionDTO.setTitle(action.title());
            taskActionDTO.setOrder(action.order());
            taskActionDTO.setProductLine(action.component().getProductLine());
            taskActionDTO.setComponent(action.component().getComponentName());
            taskActionDTO.setEntry(action.component().getEntry());
            taskActionDTO.setDisable(!action.enable(actionType, key));
            actions.add(taskActionDTO);
        }
        return actions;
    }
}

