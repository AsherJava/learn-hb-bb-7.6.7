/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.formtype.facade.FormTypeDefine
 *  com.jiuqi.nr.formtype.facade.FormTypeGroupDefine
 *  com.jiuqi.nr.formtype.service.IFormTypeGroupService
 *  com.jiuqi.nr.formtype.service.IFormTypeService
 */
package com.jiuqi.nr.param.transfer.formtype;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import com.jiuqi.nr.formtype.service.IFormTypeGroupService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.nr.param.transfer.formtype.FormTypeFolderManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class FormTypeBusinessManager
extends AbstractBusinessManager {
    private final IFormTypeService formTypeService;
    private final IFormTypeGroupService formTypeGroupService;
    private final FormTypeFolderManager formTypeFolderManager;

    public FormTypeBusinessManager(IFormTypeService formTypeService, IFormTypeGroupService formTypeGroupService, FormTypeFolderManager formTypeFolderManager) {
        this.formTypeService = formTypeService;
        this.formTypeGroupService = formTypeGroupService;
        this.formTypeFolderManager = formTypeFolderManager;
    }

    public List<BusinessNode> getBusinessNodes(String uid) throws TransferException {
        if (uid == null) {
            uid = this.formTypeFolderManager.getRootParentId();
        }
        ArrayList<BusinessNode> nodes = new ArrayList<BusinessNode>();
        List formTypeDefineList = this.formTypeService.queryByGroup(uid);
        for (FormTypeDefine formTypeDefine : formTypeDefineList) {
            BusinessNode node = new BusinessNode();
            node.setType("FORM_TYPE_TYPE");
            node.setTypeTitle("\u62a5\u8868\u7c7b\u578b");
            node.setName(formTypeDefine.getCode());
            node.setGuid(formTypeDefine.getCode());
            node.setTitle(formTypeDefine.getTitle());
            node.setFolderGuid(uid);
            nodes.add(node);
        }
        return nodes;
    }

    public BusinessNode getBusinessNode(String nodeId) throws TransferException {
        if (nodeId == null) {
            return null;
        }
        try {
            FormTypeDefine formTypeDefine = this.formTypeService.queryFormType(nodeId);
            if (formTypeDefine == null) {
                return null;
            }
            BusinessNode node = new BusinessNode();
            node.setType("FORM_TYPE_TYPE");
            node.setTypeTitle("\u62a5\u8868\u7c7b\u578b");
            node.setName(formTypeDefine.getCode());
            node.setGuid(formTypeDefine.getCode());
            node.setTitle(formTypeDefine.getTitle());
            node.setFolderGuid(formTypeDefine.getGroupId());
            return node;
        }
        catch (JQException e) {
            throw new TransferException((Throwable)e);
        }
    }

    public BusinessNode getBusinessByNameAndType(String name, String type) throws TransferException {
        return null;
    }

    public List<FolderNode> getPathFolders(String nodeId) throws TransferException {
        try {
            ArrayList<FolderNode> list = new ArrayList<FolderNode>();
            FormTypeDefine formTypeDefine = this.formTypeService.queryFormType(nodeId);
            if (formTypeDefine == null) {
                return Collections.emptyList();
            }
            String groupId = formTypeDefine.getGroupId();
            Map<String, FormTypeGroupDefine> collect = this.formTypeGroupService.queryAll().stream().collect(Collectors.toMap(FormTypeGroupDefine::getId, p -> p));
            while (!"--".equals(groupId)) {
                FormTypeGroupDefine groupDefine = collect.get(groupId);
                list.add(this.formTypeFolderManager.convertFolderNode(groupDefine));
                groupId = groupDefine.getGroupId();
            }
            Collections.reverse(list);
            return list;
        }
        catch (JQException e) {
            throw new TransferException((Throwable)e);
        }
    }

    public void moveBusiness(BusinessNode businessNode, String targetFolderGuid) throws TransferException {
    }
}

