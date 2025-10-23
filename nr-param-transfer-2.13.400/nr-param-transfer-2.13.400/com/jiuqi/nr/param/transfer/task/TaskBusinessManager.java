/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.param.transfer.task;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.param.transfer.definition.NodeUtil;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.param.transfer.task.TaskFolderManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskBusinessManager
extends AbstractBusinessManager {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private TaskFolderManager taskFolderManager;

    public List<BusinessNode> getBusinessNodes(String s) throws TransferException {
        ArrayList<BusinessNode> list = new ArrayList<BusinessNode>();
        if (s == null) {
            List rootTasks = this.designTimeViewController.getAllTasksInGroup(null, false);
            if (rootTasks.size() != 0) {
                for (TaskDefine taskDefine : rootTasks) {
                    BusinessNode businessNode = NodeUtil.taskToBusinessNode(taskDefine, s, TransferGuidParse.toTransferId(TransferNodeType.TASK, taskDefine.getKey()));
                    list.add(businessNode);
                }
            }
            return list;
        }
        TransferGuid parse = TransferGuidParse.parseId(s);
        String key = parse.getKey();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    List allTasksInGroup = this.designTimeViewController.getAllTasksInGroup(key, false);
                    if (allTasksInGroup.size() != 0) {
                        for (TaskDefine taskDefine : allTasksInGroup) {
                            BusinessNode businessNode = NodeUtil.metaToBusinessNode(taskDefine, s, TransferGuidParse.toTransferId(TransferNodeType.TASK, taskDefine.getKey()), TransferNodeType.TASK);
                            list.add(businessNode);
                        }
                    }
                    return list;
                }
                case TASK: {
                    DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(key);
                    if (taskDefine != null) {
                        BusinessNode businessNode = NodeUtil.taskToBusinessNode(taskDefine, s, TransferGuidParse.toTransferId(TransferNodeType.TASK, taskDefine.getKey()));
                        list.add(businessNode);
                    }
                    return list;
                }
            }
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25" + (Object)((Object)transferNodeType));
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    public BusinessNode getBusinessNode(String s) throws TransferException {
        TransferGuid transferGuid = TransferGuidParse.parseId(s);
        if (!transferGuid.isBusiness()) {
            return null;
        }
        TransferNodeType transferNodeType = transferGuid.getTransferNodeType();
        String key = transferGuid.getKey();
        try {
            if (transferNodeType == TransferNodeType.TASK) {
                DesignTaskDefine define = this.designTimeViewController.queryTaskDefine(key);
                if (define == null) {
                    return null;
                }
                String parent = TransferGuidParse.toTransferId(TransferNodeType.TASK, define.getKey());
                String bindingFolderGuid = TransferGuidParse.toTransferId(TransferNodeType.TASK, define.getKey());
                return NodeUtil.taskToBusinessNode(define, parent, bindingFolderGuid);
            }
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
        return null;
    }

    public BusinessNode getBusinessByNameAndType(String s, String s1) {
        return null;
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        return this.taskFolderManager.getPathFolders(s);
    }

    public void moveBusiness(BusinessNode businessNode, String s) {
    }
}

