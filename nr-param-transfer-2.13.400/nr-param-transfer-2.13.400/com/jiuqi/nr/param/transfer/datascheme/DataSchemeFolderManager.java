/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 */
package com.jiuqi.nr.param.transfer.datascheme;

import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.param.transfer.datascheme.IDesignDataSchemeCacheProxy;
import com.jiuqi.nr.param.transfer.datascheme.TransferId;
import com.jiuqi.nr.param.transfer.datascheme.TransferIdParse;
import com.jiuqi.nr.param.transfer.datascheme.dto.DataSchemeFolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DataSchemeFolderManager
extends AbstractFolderManager {
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private IDesignDataSchemeCacheProxy iDesignDataSchemeCacheProxy;

    public String getRootParentId() {
        return NodeType.SCHEME_GROUP.getValue() + "_" + "00000000-0000-0000-0000-000000000000";
    }

    public List<FolderNode> getFolderNodes(String s) throws TransferException {
        if (s == null) {
            s = this.getRootParentId();
        }
        TransferId transferId = TransferIdParse.parseId(s);
        NodeType nodeType = transferId.getNodeType();
        String key = transferId.getKey();
        switch (nodeType) {
            case SCHEME_GROUP: {
                List dataGroups = this.iDesignDataSchemeService.getDataGroupByParent(key);
                List dataSchemes = this.iDesignDataSchemeService.getDataSchemeByParent(key);
                List<FolderNode> list = this.groupToFolderNodes(dataGroups);
                list.addAll(this.schemeToFolderNodes(dataSchemes));
                return list;
            }
            case SCHEME: {
                List dataGroups = this.iDesignDataSchemeService.getDataGroupByScheme(key);
                return this.groupToFolderNodes(dataGroups);
            }
            case GROUP: {
                List dataGroups = this.iDesignDataSchemeService.getDataGroupByParent(key);
                return this.groupToFolderNodes(dataGroups);
            }
        }
        throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
    }

    private <E extends DataScheme> List<FolderNode> schemeToFolderNodes(List<E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<FolderNode>();
        }
        ArrayList<FolderNode> rList = new ArrayList<FolderNode>();
        for (DataScheme data : list) {
            FolderNode folderNode = this.schemeToFolderNode(data);
            if (folderNode == null) continue;
            rList.add(folderNode);
        }
        return rList;
    }

    private <E extends DataScheme> FolderNode schemeToFolderNode(E data) {
        if (data == null) {
            return null;
        }
        if (data.getType() == DataSchemeType.NR) {
            FolderNode folderNode = this.toFolderNode(data, NodeType.SCHEME);
            if (folderNode != null) {
                String parentGuid = TransferIdParse.toTransferId(NodeType.SCHEME_GROUP, data.getDataGroupKey());
                folderNode.setParentGuid(parentGuid);
                folderNode.setOrder(data.getOrder());
            }
            return folderNode;
        }
        return null;
    }

    private <E extends DataGroup> List<FolderNode> groupToFolderNodes(List<E> list) throws TransferException {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<FolderNode>();
        }
        ArrayList<FolderNode> rList = new ArrayList<FolderNode>();
        for (DataGroup data : list) {
            FolderNode folderNode = this.groupToFolderNode(data);
            if (folderNode == null) continue;
            rList.add(folderNode);
        }
        return rList;
    }

    private <E extends DataGroup> FolderNode groupToFolderNode(E data) throws TransferException {
        String parentGuid;
        NodeType nodeType;
        if (data == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        DataGroupKind dataGroupKind = data.getDataGroupKind();
        if (dataGroupKind == null) {
            return null;
        }
        switch (dataGroupKind) {
            case SCHEME_GROUP: {
                NodeType parentType;
                nodeType = parentType = NodeType.SCHEME_GROUP;
                parentGuid = TransferIdParse.toTransferId(parentType, data.getParentKey());
                break;
            }
            case TABLE_GROUP: {
                NodeType parentType;
                nodeType = parentType = NodeType.GROUP;
                String parentKey = data.getParentKey();
                if (parentKey == null) {
                    parentKey = data.getDataSchemeKey();
                    parentType = NodeType.SCHEME;
                }
                if (parentKey == null) {
                    throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
                }
                parentGuid = TransferIdParse.toTransferId(parentType, parentKey);
                break;
            }
            default: {
                return null;
            }
        }
        FolderNode folderNode = this.toSchemeFolderNode(data, nodeType);
        if (folderNode != null) {
            folderNode.setParentGuid(parentGuid);
            folderNode.setOrder(data.getOrder());
        }
        return folderNode;
    }

    private <E extends DataGroup> FolderNode toSchemeFolderNode(E data, NodeType nodeType) {
        if (data == null) {
            return null;
        }
        DataSchemeFolder folderNode = new DataSchemeFolder();
        folderNode.setGuid(TransferIdParse.toTransferId(nodeType, data.getKey()));
        folderNode.setName(data.getCode());
        folderNode.setTitle(data.getTitle());
        folderNode.setType(nodeType.name());
        folderNode.setDataSchemeKey(data.getDataSchemeKey());
        folderNode.setDataGroupKind(data.getDataGroupKind());
        folderNode.setDesc(data.getDesc());
        folderNode.setUpdateTime(data.getUpdateTime());
        return folderNode;
    }

    private <E extends Basic> FolderNode toFolderNode(E data, NodeType nodeType) {
        if (data == null) {
            return null;
        }
        FolderNode folderNode = new FolderNode();
        folderNode.setGuid(TransferIdParse.toTransferId(nodeType, data.getKey()));
        folderNode.setName(data.getCode());
        folderNode.setTitle(data.getTitle());
        folderNode.setType(nodeType.name());
        return folderNode;
    }

    public FolderNode getFolderNode(String s) throws TransferException {
        TransferId transferId = TransferIdParse.parseId(s);
        NodeType nodeType = transferId.getNodeType();
        String key = transferId.getKey();
        switch (nodeType) {
            case SCHEME_GROUP: {
                if ("00000000-0000-0000-0000-000000000000".equals(key)) {
                    return null;
                }
                DesignDataGroup dataGroup = this.iDesignDataSchemeCacheProxy.getDataGroup(key);
                if (dataGroup == null) {
                    return null;
                }
                return this.groupToFolderNode(dataGroup);
            }
            case SCHEME: {
                DesignDataScheme dataScheme = this.iDesignDataSchemeCacheProxy.getDataScheme(key);
                if (dataScheme == null) {
                    return null;
                }
                return this.schemeToFolderNode(dataScheme);
            }
            case GROUP: {
                DesignDataGroup dataGroup = this.iDesignDataSchemeCacheProxy.getDataGroup(key);
                if (dataGroup == null) {
                    return null;
                }
                return this.groupToFolderNode(dataGroup);
            }
        }
        throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        TransferId transferId = TransferIdParse.parseId(s);
        NodeType nodeType = transferId.getNodeType();
        String key = transferId.getKey();
        ArrayList<FolderNode> list = new ArrayList<FolderNode>();
        block6: while (!"00000000-0000-0000-0000-000000000000".equals(key)) {
            FolderNode folderNode;
            switch (nodeType) {
                case SCHEME_GROUP: {
                    DesignDataGroup dataGroup = this.iDesignDataSchemeCacheProxy.getDataGroup(key);
                    folderNode = this.groupToFolderNode(dataGroup);
                    key = dataGroup.getParentKey();
                    break;
                }
                case SCHEME: {
                    DesignDataScheme dataScheme = this.iDesignDataSchemeCacheProxy.getDataScheme(key);
                    folderNode = this.schemeToFolderNode(dataScheme);
                    key = dataScheme.getDataGroupKey();
                    nodeType = NodeType.SCHEME_GROUP;
                    break;
                }
                case GROUP: {
                    DesignDataGroup dataGroup = this.iDesignDataSchemeCacheProxy.getDataGroup(key);
                    folderNode = this.groupToFolderNode(dataGroup);
                    key = dataGroup.getParentKey();
                    if (key != null) break;
                    key = dataGroup.getDataSchemeKey();
                    nodeType = NodeType.SCHEME;
                    break;
                }
                case TABLE: 
                case MD_INFO: 
                case DETAIL_TABLE: 
                case ACCOUNT_TABLE: 
                case MUL_DIM_TABLE: {
                    DesignDataTable dataTable = this.iDesignDataSchemeCacheProxy.getDataTable(key);
                    nodeType = NodeType.GROUP;
                    if (dataTable != null) {
                        key = dataTable.getDataGroupKey();
                        if (key != null) continue block6;
                        key = dataTable.getDataSchemeKey();
                        nodeType = NodeType.SCHEME;
                        continue block6;
                    }
                    throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
                }
                default: {
                    throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
                }
            }
            if (folderNode == null) {
                throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
            }
            list.add(folderNode);
        }
        Collections.reverse(list);
        return list;
    }

    public String addFolder(FolderNode folderNode) throws TransferException {
        DataSchemeFolder dataSchemeFolder = (DataSchemeFolder)folderNode;
        String type = dataSchemeFolder.getType();
        NodeType nodeType = NodeType.valueOf((String)type);
        switch (nodeType) {
            case SCHEME_GROUP: 
            case GROUP: {
                DesignDataScheme dataScheme;
                DesignDataGroup designDataGroup = this.folder2GroupDefine(dataSchemeFolder);
                if (designDataGroup.getDataSchemeKey() != null && (dataScheme = this.iDesignDataSchemeService.getDataScheme(designDataGroup.getDataSchemeKey())) == null) {
                    String log = String.format("%s\u3010%s\u3011\u5bfc\u5165\uff0c%s", nodeType.getTitle(), dataSchemeFolder.getTitle(), "\u6240\u5c5e\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728");
                    throw new TransferException(log);
                }
                try {
                    String newKey = this.iDesignDataSchemeService.insertDataGroup(designDataGroup);
                    return TransferIdParse.toTransferId(nodeType, newKey);
                }
                catch (SchemeDataException e) {
                    String log = String.format("%s\u3010%s\u3011\u5bfc\u5165\uff0c%s", nodeType.getTitle(), dataSchemeFolder.getTitle(), e.getMessage());
                    throw new TransferException(log, (Throwable)e);
                }
                catch (Exception e) {
                    String log = String.format("%s\u3010%s\u3011\u5bfc\u5165\uff0c%s", nodeType.getTitle(), dataSchemeFolder.getTitle(), "\uff1a\u672a\u77e5\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u7a0b\u5e8f\u548c\u6570\u636e\u5e93");
                    throw new TransferException(log, (Throwable)e);
                }
            }
        }
        return null;
    }

    public void modifyFolder(FolderNode folderNode) throws TransferException {
        DataSchemeFolder dataSchemeFolder = (DataSchemeFolder)folderNode;
        String type = dataSchemeFolder.getType();
        NodeType nodeType = NodeType.valueOf((String)type);
        switch (nodeType) {
            case SCHEME_GROUP: 
            case GROUP: {
                DesignDataGroup designDataGroup = this.folder2GroupDefine(dataSchemeFolder);
                try {
                    this.iDesignDataSchemeService.updateDataGroup(designDataGroup);
                    break;
                }
                catch (SchemeDataException e) {
                    String log = String.format("%s\u3010%s\u3011\u5bfc\u5165\uff0c%s", nodeType.getTitle(), dataSchemeFolder.getTitle(), e.getMessage());
                    throw new TransferException(log, (Throwable)e);
                }
                catch (Exception e) {
                    String log = String.format("%s\u3010%s\u3011\u5bfc\u5165\uff0c%s", nodeType.getTitle(), dataSchemeFolder.getTitle(), "\uff1a\u672a\u77e5\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u7a0b\u5e8f\u548c\u6570\u636e\u5e93");
                    throw new TransferException(log, (Throwable)e);
                }
            }
        }
    }

    private DesignDataGroup folder2GroupDefine(DataSchemeFolder dataSchemeFolder) throws TransferException {
        DesignDataGroup designDataGroup = this.iDesignDataSchemeService.initDataGroup();
        TransferId transferId = TransferIdParse.parseId(dataSchemeFolder.getGuid());
        designDataGroup.setKey(transferId.getKey());
        designDataGroup.setCode(dataSchemeFolder.getName());
        designDataGroup.setTitle(dataSchemeFolder.getTitle());
        String parentGuid = dataSchemeFolder.getParentGuid();
        if (StringUtils.hasLength(parentGuid)) {
            TransferId parseId = TransferIdParse.parseId(parentGuid);
            designDataGroup.setParentKey(parseId.getKey());
        }
        designDataGroup.setDataSchemeKey(dataSchemeFolder.getDataSchemeKey());
        designDataGroup.setDataGroupKind(dataSchemeFolder.getDataGroupKind());
        designDataGroup.setDesc(dataSchemeFolder.getDesc());
        designDataGroup.setUpdateTime(dataSchemeFolder.getUpdateTime());
        designDataGroup.setOrder(dataSchemeFolder.getOrder());
        return designDataGroup;
    }

    public FolderNode getFolderByTitle(String s, String s1, String s2) {
        return null;
    }
}

