/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionPath;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import java.util.Calendar;
import java.util.UUID;

public class ProcessRepositoryUtil {
    public static ProcessInstanceDO newInstance(String processDefinitionId, IBusinessKey businessKey, UserActionPath action, IActor actor) {
        ProcessInstanceDO instance = new ProcessInstanceDO();
        Calendar startTime = Calendar.getInstance();
        instance.setId(UUID.randomUUID().toString());
        instance.setBusinessKey(businessKey);
        instance.setProcessDefinitionId(processDefinitionId);
        instance.setCurNode(action.getDestUserTask().getCode());
        instance.setCurStatus(action.getDestStatus().getCode());
        instance.setCurTaskId(UUID.randomUUID().toString());
        instance.setStartTime(startTime);
        instance.setUpdateTime(startTime);
        instance.setStartUser(actor.getUserId());
        return instance;
    }

    public static ProcessInstanceDO newUnitInstance(String processDefinitionId, IBusinessKey businessKey, IActor actor) {
        ProcessInstanceDO instance = new ProcessInstanceDO();
        Calendar startTime = Calendar.getInstance();
        instance.setId(UUID.randomUUID().toString());
        instance.setBusinessKey(businessKey);
        instance.setProcessDefinitionId(processDefinitionId);
        instance.setCurNode("_NULL_");
        instance.setCurStatus("_NULL_");
        instance.setCurTaskId(UUID.randomUUID().toString());
        instance.setStartTime(startTime);
        instance.setUpdateTime(startTime);
        instance.setStartUser(actor.getUserId());
        return instance;
    }

    public static ProcessOperationDO newStartOperation(ProcessInstanceDO instance, UserActionPath startAction, IActor actor) {
        ProcessOperationDO operation = new ProcessOperationDO();
        operation.setId(UUID.randomUUID().toString());
        operation.setInstanceId(instance.getId());
        operation.setFromNode("tsk_start");
        operation.setAction("start");
        operation.setToNode(startAction.getDestUserTask().getCode());
        operation.setNewStatus(startAction.getDestStatus().getCode());
        operation.setOperate_user(actor.getUserId());
        operation.setOperate_identity(actor.getIdentityId());
        operation.setOperateTime(instance.getStartTime());
        return operation;
    }

    public static ProcessOperationDO operate(ProcessInstanceDO instance, IUserActionPath actionPath, IActor actor, IActionArgs args) {
        ProcessOperationDO operation = new ProcessOperationDO();
        operation.setId(UUID.randomUUID().toString());
        operation.setInstanceId(instance.getId());
        operation.setFromNode(instance.getCurNode());
        operation.setAction(actionPath.getUserAction().getCode());
        operation.setToNode(actionPath.getDestUserTask().getCode());
        operation.setNewStatus(actionPath.getDestStatus().getCode());
        operation.setOperate_user(actor.getUserId());
        operation.setOperate_identity(actor.getIdentityId());
        operation.setOperateTime(Calendar.getInstance());
        if (args != null) {
            operation.setComment(args.getString("COMMENT"));
            operation.setOperate_type(args.getString("RETURN_TYPE"));
            operation.setForceReport(args.getBoolean("FORCE_REPORT"));
        }
        instance.setCurTaskId(UUID.randomUUID().toString());
        instance.setCurNode(actionPath.getDestUserTask().getCode());
        instance.setCurStatus(actionPath.getDestStatus().getCode());
        instance.setUpdateTime(operation.getOperateTime());
        return operation;
    }

    public static ProcessOperationDO retrive(ProcessInstanceDO instance, String retriveToNode, String retriveToStatus, IActor actor) {
        ProcessOperationDO operation = new ProcessOperationDO();
        operation.setId(UUID.randomUUID().toString());
        operation.setInstanceId(instance.getId());
        operation.setFromNode(instance.getCurNode());
        operation.setAction("act_retrieve");
        operation.setToNode(retriveToNode);
        operation.setNewStatus(retriveToStatus);
        operation.setOperate_user(actor.getUserId());
        operation.setOperate_identity(actor.getIdentityId());
        operation.setOperateTime(Calendar.getInstance());
        instance.setCurTaskId(UUID.randomUUID().toString());
        instance.setCurNode(retriveToNode);
        instance.setCurStatus(retriveToStatus);
        instance.setUpdateTime(operation.getOperateTime());
        return operation;
    }
}

