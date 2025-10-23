/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.nrdx.adapter.param.utils;

import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxGuidParse;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class ParamNodeUtil {
    public static <E extends IMetaItem> FolderNode toFolderNode(E data) {
        FolderNode folderNode = new FolderNode();
        folderNode.setOrder(data.getOrder());
        folderNode.setTitle(data.getTitle());
        folderNode.setName(data.getTitle());
        folderNode.setIcon(null);
        return folderNode;
    }

    public static <E extends IMetaItem> FolderNode metaToFolderNode(E data, NrdxParamNodeType nrdxParamNodeType) {
        if (data == null) {
            return null;
        }
        FolderNode folderNode = ParamNodeUtil.toFolderNode(data);
        folderNode.setType(nrdxParamNodeType.getTitle());
        folderNode.setGuid(NrdxGuidParse.toTransferId(nrdxParamNodeType, data.getKey()));
        return folderNode;
    }

    public static <E extends IMetaItem> List<FolderNode> metaToFolderNodes(List<E> list, String s, NrdxParamNodeType nrdxParamNodeType) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<FolderNode>();
        }
        ArrayList<FolderNode> rList = new ArrayList<FolderNode>();
        for (IMetaItem data : list) {
            FolderNode folderNode = ParamNodeUtil.metaToFolderNode(data, nrdxParamNodeType);
            folderNode.setParentGuid(s);
            rList.add(folderNode);
        }
        return rList;
    }

    public static FolderNode taskToFolderNode(TaskDefine taskDefine) {
        FolderNode folderNode = ParamNodeUtil.metaToFolderNode(taskDefine, NrdxParamNodeType.TASK);
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
            FolderNode folderNode = ParamNodeUtil.taskToFolderNode(data);
            folderNode.setParentGuid(s);
            rList.add(folderNode);
        }
        return rList;
    }

    public static <E extends IMetaItem> BusinessNode toBusinessNode(E data, NrdxParamNodeType nrdxParamNodeType) {
        if (data == null) {
            return null;
        }
        BusinessNode businessNode = new BusinessNode();
        businessNode.setGuid(NrdxGuidParse.toBusinessId(nrdxParamNodeType, data.getKey()));
        businessNode.setName(data.getTitle());
        businessNode.setTitle(data.getTitle());
        businessNode.setType(nrdxParamNodeType.name());
        businessNode.setTypeTitle(nrdxParamNodeType.getTitle());
        return businessNode;
    }

    public static <E extends IMetaItem> BusinessNode metaToBusinessNode(E datum, String parent, String bindingFolderGuid, NrdxParamNodeType nrdxParamNodeType) {
        BusinessNode businessNode = ParamNodeUtil.toBusinessNode(datum, nrdxParamNodeType);
        if (businessNode != null) {
            businessNode.setFolderGuid(parent);
        }
        return businessNode;
    }

    public static <E extends IMetaItem> List<BusinessNode> metaToBusinessNodes(List<E> data, String parent, String bindingFolderGuid, NrdxParamNodeType nrdxParamNodeType) {
        if (data == null) {
            return Collections.emptyList();
        }
        ArrayList<BusinessNode> list = new ArrayList<BusinessNode>(data.size());
        for (IMetaItem datum : data) {
            BusinessNode businessNode = ParamNodeUtil.metaToBusinessNode(datum, parent, bindingFolderGuid, nrdxParamNodeType);
            if (businessNode == null) continue;
            list.add(businessNode);
        }
        return list;
    }
}

