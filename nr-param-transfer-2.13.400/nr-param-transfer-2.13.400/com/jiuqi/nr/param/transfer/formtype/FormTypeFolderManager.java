/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.nr.formtype.facade.FormTypeGroupDefine
 *  com.jiuqi.nr.formtype.service.IFormTypeGroupService
 */
package com.jiuqi.nr.param.transfer.formtype;

import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import com.jiuqi.nr.formtype.service.IFormTypeGroupService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class FormTypeFolderManager
extends AbstractFolderManager {
    private final IFormTypeGroupService formTypeGroupService;

    public FormTypeFolderManager(IFormTypeGroupService formTypeGroupService) {
        this.formTypeGroupService = formTypeGroupService;
    }

    public String getRootParentId() {
        return "--";
    }

    public List<FolderNode> getFolderNodes(String nodeId) throws TransferException {
        ArrayList<FolderNode> list = new ArrayList<FolderNode>();
        if (nodeId == null) {
            nodeId = this.getRootParentId();
        }
        List groupDefines = this.formTypeGroupService.queryByParentId(nodeId);
        for (FormTypeGroupDefine groupDefine : groupDefines) {
            list.add(this.convertFolderNode(groupDefine));
        }
        return list;
    }

    public FolderNode getFolderNode(String nodeId) throws TransferException {
        if ("--".equals(nodeId)) {
            return null;
        }
        FormTypeGroupDefine groupDefine = this.formTypeGroupService.queryById(nodeId);
        if (groupDefine == null) {
            return null;
        }
        return this.convertFolderNode(groupDefine);
    }

    public FolderNode convertFolderNode(FormTypeGroupDefine groupDefine) {
        FolderNode folderNode = new FolderNode();
        folderNode.setType("FORM_TYPE_GROUP_TYPE");
        folderNode.setParentGuid(groupDefine.getGroupId());
        folderNode.setName(groupDefine.getCode());
        folderNode.setGuid(groupDefine.getId());
        folderNode.setTitle(groupDefine.getTitle());
        return folderNode;
    }

    public List<FolderNode> getPathFolders(String nodeId) throws TransferException {
        ArrayList<FolderNode> nodes = new ArrayList<FolderNode>();
        List groupDefines = this.formTypeGroupService.queryAll();
        Map<String, FormTypeGroupDefine> collect = groupDefines.stream().collect(Collectors.toMap(FormTypeGroupDefine::getId, p -> p));
        if (collect.get(nodeId) == null) {
            return Collections.emptyList();
        }
        FormTypeGroupDefine current = collect.get(nodeId);
        nodes.add(this.convertFolderNode(current));
        String parentId = current.getGroupId();
        while (!parentId.equals("--")) {
            FormTypeGroupDefine groupDefine = collect.get(parentId);
            nodes.add(this.convertFolderNode(groupDefine));
            parentId = groupDefine.getGroupId();
        }
        Collections.reverse(nodes);
        return nodes;
    }

    public String addFolder(FolderNode folderNode) throws TransferException {
        return null;
    }

    public void modifyFolder(FolderNode folderNode) throws TransferException {
    }

    public FolderNode getFolderByTitle(String s, String s1, String s2) throws TransferException {
        return null;
    }
}

