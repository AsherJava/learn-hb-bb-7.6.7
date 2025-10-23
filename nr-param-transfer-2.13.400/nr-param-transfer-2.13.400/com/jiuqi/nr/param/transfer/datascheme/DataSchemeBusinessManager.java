/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.param.transfer.datascheme;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.param.transfer.datascheme.DataSchemeFolderManager;
import com.jiuqi.nr.param.transfer.datascheme.IDesignDataSchemeCacheProxy;
import com.jiuqi.nr.param.transfer.datascheme.TransferId;
import com.jiuqi.nr.param.transfer.datascheme.TransferIdParse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSchemeBusinessManager
extends AbstractBusinessManager {
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private DataSchemeFolderManager dataSchemeFolderManager;
    @Autowired
    private IDesignDataSchemeCacheProxy iDesignDataSchemeCacheProxy;

    public List<BusinessNode> getBusinessNodes(String s) throws TransferException {
        if (s == null) {
            s = this.dataSchemeFolderManager.getRootParentId();
        }
        TransferId transferId = TransferIdParse.parseId(s);
        NodeType nodeType = transferId.getNodeType();
        String key = transferId.getKey();
        ArrayList<BusinessNode> list = new ArrayList<BusinessNode>();
        switch (nodeType) {
            case SCHEME_GROUP: {
                if (!"00000000-0000-0000-0000-000000000000".equals(key)) break;
                return list;
            }
            case SCHEME: {
                DesignDataScheme dataScheme = this.iDesignDataSchemeCacheProxy.getDataScheme(key);
                BusinessNode businessNode = this.schemeToBusinessNode(dataScheme);
                if (businessNode != null) {
                    list.add(businessNode);
                }
                List dataTableByScheme = this.iDesignDataSchemeService.getDataTableByScheme(key);
                list.addAll(this.tableToBusinessNodes(dataTableByScheme));
                break;
            }
            case GROUP: {
                List dataTableByGroup = this.iDesignDataSchemeService.getDataTableByGroup(key);
                list.addAll(this.tableToBusinessNodes(dataTableByGroup));
                break;
            }
            default: {
                throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
            }
        }
        return list;
    }

    private <E extends DataTable> List<BusinessNode> tableToBusinessNodes(List<E> data) throws TransferException {
        ArrayList<BusinessNode> list = new ArrayList<BusinessNode>();
        for (DataTable datum : data) {
            BusinessNode businessNode = this.tableToBusinessNode(datum);
            if (businessNode == null) continue;
            list.add(businessNode);
        }
        return list;
    }

    public BusinessNode getBusinessNode(String s) throws TransferException {
        TransferId transferId = TransferIdParse.parseId(s);
        if (!transferId.isBusiness()) {
            return null;
        }
        NodeType nodeType = transferId.getNodeType();
        String key = transferId.getKey();
        switch (nodeType) {
            case SCHEME_GROUP: 
            case GROUP: {
                DesignDataGroup dataGroup = this.iDesignDataSchemeCacheProxy.getDataGroup(key);
                return this.groupToBusinessNode(dataGroup);
            }
            case SCHEME: {
                DesignDataScheme dataScheme = this.iDesignDataSchemeCacheProxy.getDataScheme(key);
                return this.schemeToBusinessNode(dataScheme);
            }
            case TABLE: 
            case MD_INFO: 
            case ACCOUNT_TABLE: 
            case DETAIL_TABLE: 
            case MUL_DIM_TABLE: {
                DesignDataTable dataTable = this.iDesignDataSchemeCacheProxy.getDataTable(key);
                return this.tableToBusinessNode(dataTable);
            }
        }
        throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
    }

    public BusinessNode getBusinessByNameAndType(String s, String s1) {
        return null;
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        return this.dataSchemeFolderManager.getPathFolders(s);
    }

    public void moveBusiness(BusinessNode businessNode, String s) {
    }

    private <E extends DataTable> BusinessNode tableToBusinessNode(E data) throws TransferException {
        BusinessNode businessNode;
        if (data == null) {
            return null;
        }
        DataTableType dataTableType = data.getDataTableType();
        if (dataTableType == null) {
            return null;
        }
        switch (dataTableType) {
            case TABLE: {
                businessNode = this.toBusinessNode(data, NodeType.TABLE);
                break;
            }
            case DETAIL: {
                businessNode = this.toBusinessNode(data, NodeType.DETAIL_TABLE);
                break;
            }
            case MULTI_DIM: {
                businessNode = this.toBusinessNode(data, NodeType.MUL_DIM_TABLE);
                break;
            }
            case ACCOUNT: {
                businessNode = this.toBusinessNode(data, NodeType.ACCOUNT_TABLE);
                break;
            }
            case MD_INFO: {
                businessNode = this.toBusinessNode(data, NodeType.MD_INFO);
                break;
            }
            default: {
                throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25" + dataTableType);
            }
        }
        String groupKey = data.getDataGroupKey();
        NodeType parentType = NodeType.GROUP;
        if (groupKey == null) {
            groupKey = data.getDataSchemeKey();
            parentType = NodeType.SCHEME;
        }
        String parentGuid = TransferIdParse.toTransferId(parentType, groupKey);
        if (businessNode != null) {
            businessNode.setFolderGuid(parentGuid);
            businessNode.setOrder(data.getOrder());
        }
        return businessNode;
    }

    private <E extends DataGroup> BusinessNode groupToBusinessNode(E data) {
        NodeType nodeType;
        if (data == null) {
            return null;
        }
        DataGroupKind dataGroupKind = data.getDataGroupKind();
        if (dataGroupKind == null) {
            return null;
        }
        switch (dataGroupKind) {
            case SCHEME_GROUP: {
                nodeType = NodeType.SCHEME_GROUP;
                break;
            }
            case TABLE_GROUP: {
                nodeType = NodeType.GROUP;
                break;
            }
            default: {
                return null;
            }
        }
        String parentGuid = TransferIdParse.toTransferId(nodeType, data.getKey());
        BusinessNode businessNode = this.toBusinessNode(data, nodeType);
        if (businessNode != null) {
            businessNode.setFolderGuid(parentGuid);
            businessNode.setOrder(data.getOrder());
        }
        return businessNode;
    }

    private <E extends Basic> BusinessNode toBusinessNode(E data, NodeType nodeType) {
        if (data == null) {
            return null;
        }
        BusinessNode businessNode = new BusinessNode();
        businessNode.setGuid(TransferIdParse.toBusinessId(nodeType, data.getKey()));
        businessNode.setName(data.getCode());
        businessNode.setTitle(data.getTitle());
        businessNode.setType(nodeType.name());
        businessNode.setTypeTitle(nodeType.getTitle());
        return businessNode;
    }

    private <E extends DataScheme> BusinessNode schemeToBusinessNode(E data) {
        if (data == null) {
            return null;
        }
        BusinessNode businessNode = this.toBusinessNode(data, NodeType.SCHEME);
        if (businessNode != null) {
            String parentGuid = TransferIdParse.toTransferId(NodeType.SCHEME, data.getKey());
            businessNode.setFolderGuid(parentGuid);
            businessNode.setOrder(data.getOrder());
        }
        return businessNode;
    }
}

