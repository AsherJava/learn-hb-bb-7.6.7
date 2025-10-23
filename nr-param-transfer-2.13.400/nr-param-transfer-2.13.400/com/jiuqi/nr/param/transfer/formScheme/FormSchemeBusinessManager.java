/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 */
package com.jiuqi.nr.param.transfer.formScheme;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.param.transfer.definition.DefinitionBusinessManager;
import com.jiuqi.nr.param.transfer.definition.NodeUtil;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.param.transfer.formScheme.FormSchemeFolderManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormSchemeBusinessManager
extends AbstractBusinessManager {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private FormSchemeFolderManager formSchemeFolderManager;
    @Autowired
    private DefinitionBusinessManager definitionBusinessManager;
    private static final Logger logger = LoggerFactory.getLogger(FormSchemeBusinessManager.class);

    public List<BusinessNode> getBusinessNodes(String s) throws TransferException {
        if (s == null) {
            return Collections.emptyList();
        }
        TransferGuid parse = TransferGuidParse.parseId(s);
        if (parse.isBusiness()) {
            return Collections.emptyList();
        }
        String key = parse.getKey();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        ArrayList<BusinessNode> list = new ArrayList<BusinessNode>();
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    DesignTaskGroupDefine taskGroupDefine = this.designTimeViewController.queryTaskGroupDefine(key);
                    if (taskGroupDefine != null) {
                        BusinessNode businessNode = NodeUtil.metaToBusinessNode(taskGroupDefine, s, TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, taskGroupDefine.getKey()), TransferNodeType.TASK_GROUP);
                        businessNode.setTitle(businessNode.getTitle() + "_\u57fa\u672c\u4fe1\u606f");
                        list.add(businessNode);
                    }
                    return list;
                }
                case TASK: {
                    List<BusinessNode> businessNodes = this.definitionBusinessManager.getBusinessNodes(s);
                    DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(key);
                    if (taskDefine != null) {
                        List designFormSchemeDefines = this.designTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                        List<BusinessNode> formSchemeBusinessNodes = NodeUtil.metaToBusinessNodes(designFormSchemeDefines, s, "", TransferNodeType.FORM_SCHEME);
                        formSchemeBusinessNodes.forEach(a -> a.setTitle(a.getTitle() + "_\u5168\u91cf"));
                        businessNodes.addAll(formSchemeBusinessNodes);
                    }
                    return businessNodes;
                }
                case FORM_SCHEME: {
                    return list;
                }
            }
            throw new TransferException("\u4e0d\u5b58\u5728\u7684\u8d44\u6e90\u7c7b\u578b\uff1a" + (Object)((Object)transferNodeType));
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
        BusinessNode businessNode = null;
        TransferNodeType transferNodeType = transferGuid.getTransferNodeType();
        String key = transferGuid.getKey();
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    DesignTaskGroupDefine taskGroupDefine = this.designTimeViewController.queryTaskGroupDefine(key);
                    if (taskGroupDefine == null) {
                        return null;
                    }
                    String parent = TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, taskGroupDefine.getKey());
                    businessNode = NodeUtil.metaToBusinessNode(taskGroupDefine, parent, "", TransferNodeType.TASK_GROUP);
                    break;
                }
                case TASK: {
                    DesignTaskDefine define = this.designTimeViewController.queryTaskDefine(key);
                    if (define == null) {
                        return null;
                    }
                    String parent = TransferGuidParse.toTransferId(TransferNodeType.TASK, define.getKey());
                    String bindingFolderGuid = TransferGuidParse.toTransferId(TransferNodeType.TASK, define.getKey());
                    businessNode = NodeUtil.taskToBusinessNode(define, parent, bindingFolderGuid);
                    break;
                }
                case FORM_SCHEME: {
                    DesignFormSchemeDefine designFormSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(key);
                    if (designFormSchemeDefine == null) {
                        return null;
                    }
                    String parent = TransferGuidParse.toTransferId(TransferNodeType.TASK, designFormSchemeDefine.getTaskKey());
                    businessNode = NodeUtil.metaToBusinessNode(designFormSchemeDefine, parent, "", TransferNodeType.FORM_SCHEME);
                    businessNode.setTitle(designFormSchemeDefine.getTitle() + "_\u5168\u91cf");
                    break;
                }
                case CUSTOM_DATA: {
                    businessNode = this.definitionBusinessManager.getBusinessNode(s);
                    break;
                }
                default: {
                    throw new TransferException("\u4e0d\u5b58\u5728\u7684\u8d44\u6e90\u7c7b\u578b\uff1a" + (Object)((Object)transferNodeType));
                }
            }
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
        return businessNode;
    }

    public BusinessNode getBusinessByNameAndType(String s, String s1) {
        return null;
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        return this.formSchemeFolderManager.getPathFolders(s);
    }

    public void moveBusiness(BusinessNode businessNode, String s) {
    }
}

