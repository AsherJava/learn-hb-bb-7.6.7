/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.PrintComTemDefine
 *  com.jiuqi.nr.definition.facade.PrintSettingDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.param.transfer.definition;

import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.param.transfer.definition.FormulaGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class NodeUtil {
    public static <E extends IMetaItem> FolderNode toFolderNode(E data) {
        FolderNode folderNode = new FolderNode();
        folderNode.setOrder(data.getOrder());
        folderNode.setTitle(data.getTitle());
        folderNode.setName(data.getTitle());
        folderNode.setIcon(null);
        return folderNode;
    }

    public static <E extends IMetaItem> FolderNode metaToFolderNode(E data, TransferNodeType transferNodeType) {
        if (data == null) {
            return null;
        }
        FolderNode folderNode = NodeUtil.toFolderNode(data);
        folderNode.setType(transferNodeType.getTitle());
        folderNode.setGuid(TransferGuidParse.toTransferId(transferNodeType, data.getKey()));
        return folderNode;
    }

    public static <E extends IMetaItem> List<FolderNode> metaToFolderNodes(List<E> list, String s, TransferNodeType transferNodeType) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<FolderNode>();
        }
        ArrayList<FolderNode> rList = new ArrayList<FolderNode>();
        for (IMetaItem data : list) {
            FolderNode folderNode = NodeUtil.metaToFolderNode(data, transferNodeType);
            folderNode.setParentGuid(s);
            rList.add(folderNode);
        }
        return rList;
    }

    public static FolderNode taskToFolderNode(TaskDefine taskDefine) {
        FolderNode folderNode = NodeUtil.metaToFolderNode(taskDefine, TransferNodeType.TASK);
        folderNode.setParentGuid(null);
        folderNode.setName(taskDefine.getTaskCode());
        return folderNode;
    }

    public static <E extends TaskDefine> List<FolderNode> taskToFolderNodes(List<E> rootTasks, String s) {
        if (CollectionUtils.isEmpty(rootTasks)) {
            return new ArrayList<FolderNode>();
        }
        ArrayList<FolderNode> rList = new ArrayList<FolderNode>();
        for (TaskDefine data : rootTasks) {
            FolderNode folderNode = NodeUtil.taskToFolderNode(data);
            folderNode.setParentGuid(s);
            rList.add(folderNode);
        }
        return rList;
    }

    public static <E extends IMetaItem> BusinessNode toBusinessNode(E data, TransferNodeType nodeType) {
        if (data == null) {
            return null;
        }
        BusinessNode businessNode = new BusinessNode();
        businessNode.setGuid(TransferGuidParse.toBusinessId(nodeType, data.getKey()));
        businessNode.setName(data.getTitle());
        businessNode.setTitle(data.getTitle());
        businessNode.setType(nodeType.name());
        businessNode.setTypeTitle(nodeType.getTitle());
        return businessNode;
    }

    public static <E extends IMetaItem> BusinessNode metaToBusinessNode(E datum, String parent, String bindingFolderGuid, TransferNodeType transferNodeType) {
        BusinessNode businessNode = NodeUtil.toBusinessNode(datum, transferNodeType);
        if (businessNode != null) {
            businessNode.setFolderGuid(parent);
        }
        return businessNode;
    }

    public static <E extends IMetaItem> List<BusinessNode> metaToBusinessNodes(List<E> data, String parent, String bindingFolderGuid, TransferNodeType transferNodeType) {
        if (data == null) {
            return Collections.emptyList();
        }
        ArrayList<BusinessNode> list = new ArrayList<BusinessNode>(data.size());
        for (IMetaItem datum : data) {
            BusinessNode businessNode = NodeUtil.metaToBusinessNode(datum, parent, bindingFolderGuid, transferNodeType);
            if (businessNode == null) continue;
            list.add(businessNode);
        }
        return list;
    }

    public static BusinessNode metaToBusinessNode(String parent, PrintComTemDefine define) {
        BusinessNode node = new BusinessNode();
        node.setGuid(TransferGuidParse.toBusinessId(TransferNodeType.PRINT_COMTEM, define.getKey()));
        node.setTitle(define.getTitle());
        node.setName(define.getCode());
        node.setType(TransferNodeType.PRINT_COMTEM.name());
        node.setTypeTitle(TransferNodeType.PRINT_COMTEM.getTitle());
        node.setFolderGuid(parent);
        return node;
    }

    public static BusinessNode metaToBusinessNode(String parent, FormDefine formDefine, PrintSettingDefine settingDefine) {
        BusinessNode node = new BusinessNode();
        String key = FormulaGuidParse.toFormulaId(settingDefine.getPrintSchemeKey(), settingDefine.getFormKey());
        node.setGuid(TransferGuidParse.toBusinessId(TransferNodeType.PRINT_SETTING, key));
        node.setTitle(formDefine.getTitle() + "_Excel\u6253\u5370\u8bbe\u7f6e");
        node.setName(node.getTitle());
        node.setType(TransferNodeType.PRINT_SETTING.name());
        node.setTypeTitle(TransferNodeType.PRINT_SETTING.getTitle());
        node.setFolderGuid(parent);
        return node;
    }

    public static <E extends TaskDefine> BusinessNode taskToBusinessNode(E datum, String parent, String bindingFolderGuid) {
        BusinessNode businessNode = NodeUtil.toBusinessNode(datum, TransferNodeType.TASK);
        if (businessNode != null) {
            businessNode.setName(datum.getTaskCode());
            businessNode.setFolderGuid(parent);
        }
        return businessNode;
    }

    public static <E extends FormDefine> List<BusinessNode> formToBusinessNodes(List<E> data, String parent) {
        if (data == null) {
            return Collections.emptyList();
        }
        ArrayList<BusinessNode> list = new ArrayList<BusinessNode>(data.size());
        for (FormDefine datum : data) {
            BusinessNode businessNode = NodeUtil.metaToBusinessNode(datum, parent, null, TransferNodeType.FORM);
            if (businessNode == null) continue;
            businessNode.setName(datum.getFormCode());
            list.add(businessNode);
        }
        return list;
    }
}

