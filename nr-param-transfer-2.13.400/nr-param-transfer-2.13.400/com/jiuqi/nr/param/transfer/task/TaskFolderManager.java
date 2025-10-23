/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 */
package com.jiuqi.nr.param.transfer.task;

import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.param.transfer.definition.NodeUtil;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class TaskFolderManager
extends AbstractFolderManager {
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    public List<FolderNode> getFolderNodes(String s) throws TransferException {
        try {
            if (s == null) {
                List childTaskGroups = this.designTimeViewController.getChildTaskGroups(null, false);
                List<FolderNode> list = NodeUtil.metaToFolderNodes(childTaskGroups, null, TransferNodeType.TASK_GROUP);
                return list;
            }
            TransferGuid parse = TransferGuidParse.parseId(s);
            String key = parse.getKey();
            TransferNodeType transferNodeType = parse.getTransferNodeType();
            switch (transferNodeType) {
                case TASK_GROUP: {
                    List childTaskGroups = this.designTimeViewController.getChildTaskGroups(key, false);
                    List<FolderNode> list = NodeUtil.metaToFolderNodes(childTaskGroups, s, TransferNodeType.TASK_GROUP);
                    return list;
                }
                case TASK: {
                    return Collections.emptyList();
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

    public FolderNode getFolderNode(String s) throws TransferException {
        if (!StringUtils.hasLength(s)) {
            return null;
        }
        TransferGuid parse = TransferGuidParse.parseId(s);
        String key = parse.getKey();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    DesignTaskGroupDefine taskGroupDefine = this.designTimeViewController.queryTaskGroupDefine(key);
                    if (taskGroupDefine == null) {
                        return null;
                    }
                    FolderNode folderNode = NodeUtil.metaToFolderNode(taskGroupDefine, TransferNodeType.TASK_GROUP);
                    String parentKey = taskGroupDefine.getParentKey();
                    folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, parentKey));
                    return folderNode;
                }
                case TASK: {
                    DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(key);
                    if (taskDefine == null) {
                        return null;
                    }
                    FolderNode folderNode = NodeUtil.taskToFolderNode((TaskDefine)taskDefine);
                    List link = this.designTimeViewController.getGroupLinkByTaskKey(key);
                    if (!CollectionUtils.isEmpty(link)) {
                        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, ((DesignTaskGroupLink)link.get(0)).getGroupKey()));
                    }
                    return folderNode;
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

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        TransferGuid parse = TransferGuidParse.parseId(s);
        String key = parse.getKey();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        ArrayList<FolderNode> list = new ArrayList<FolderNode>();
        try {
            while (StringUtils.hasLength(key)) {
                FolderNode folderNode;
                switch (transferNodeType) {
                    case TASK_GROUP: {
                        DesignTaskGroupDefine taskGroupDefine = this.designTimeViewController.queryTaskGroupDefine(key);
                        folderNode = NodeUtil.metaToFolderNode(taskGroupDefine, TransferNodeType.TASK_GROUP);
                        key = taskGroupDefine.getParentKey();
                        if (!StringUtils.hasLength(key)) break;
                        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, key));
                        break;
                    }
                    case TASK: {
                        DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(key);
                        folderNode = NodeUtil.taskToFolderNode((TaskDefine)taskDefine);
                        List link = this.designTimeViewController.getGroupLinkByTaskKey(key);
                        if (link == null || link.isEmpty()) {
                            key = null;
                            break;
                        }
                        key = ((DesignTaskGroupLink)link.get(0)).getGroupKey();
                        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, key));
                        transferNodeType = TransferNodeType.TASK_GROUP;
                        break;
                    }
                    default: {
                        throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
                    }
                }
                list.add(folderNode);
            }
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
        Collections.reverse(list);
        return list;
    }

    public String addFolder(FolderNode folderNode) {
        return null;
    }

    public void modifyFolder(FolderNode folderNode) {
    }

    public FolderNode getFolderByTitle(String s, String s1, String s2) {
        return null;
    }
}

