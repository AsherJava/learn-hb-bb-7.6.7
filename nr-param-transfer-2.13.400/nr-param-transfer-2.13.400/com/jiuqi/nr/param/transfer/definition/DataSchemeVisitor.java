/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.param.transfer.definition;

import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.param.transfer.datascheme.IDesignDataSchemeCacheProxy;
import com.jiuqi.nr.param.transfer.datascheme.TransferIdParse;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSchemeVisitor
implements SchemeNodeVisitor<Void> {
    private final List<ResItem> resItems = new ArrayList<ResItem>();
    private IDesignDataSchemeCacheProxy iDesignDataSchemeCacheProxy;
    private DesignDataScheme dataScheme;
    private boolean nullSet;

    public VisitorResult preVisitNode(SchemeNode<Void> ele) {
        if (this.dataScheme == null && this.nullSet) {
            return VisitorResult.TERMINATE;
        }
        return null;
    }

    public Void visitRootIsSchemeNode(DesignDataScheme scheme) {
        if (scheme == null) {
            return null;
        }
        this.nullSet = true;
        this.dataScheme = scheme;
        String businessId = TransferIdParse.toBusinessId(NodeType.SCHEME, scheme.getKey());
        ResItem resItem = new ResItem(businessId, NodeType.SCHEME.getTitle(), "DATASCHEME_TRANSFER_FACTORY_ID");
        this.resItems.add(resItem);
        return null;
    }

    public Void visitRootIsGroupNode(DesignDataGroup group) {
        return null;
    }

    public Void visitRootIsTableNode(DesignDataTable table) {
        return null;
    }

    public <DG extends DesignDataGroup, DS extends DesignDataScheme> Map<String, Void> visitSchemeGroupNode(SchemeNode<Void> next, List<DG> groups, List<DS> schemes) {
        return null;
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable, DM extends DesignDataDimension> Map<String, Void> visitSchemeNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        this.visitGroupNode(null, dataGroups, dataTables);
        return null;
    }

    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<Void> ele, List<DA> attributes) {
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable> Map<String, Void> visitGroupNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables) {
        for (DesignDataTable dataTable : dataTables) {
            this.iDesignDataSchemeCacheProxy.putDataTable(dataTable);
            DataTableType dataTableType = dataTable.getDataTableType();
            NodeType type = NodeType.TABLE;
            switch (dataTableType) {
                case DETAIL: {
                    type = NodeType.DETAIL_TABLE;
                    break;
                }
                case MULTI_DIM: {
                    type = NodeType.MUL_DIM_TABLE;
                    break;
                }
                case MD_INFO: {
                    type = NodeType.MD_INFO;
                    break;
                }
                case ACCOUNT: {
                    type = NodeType.ACCOUNT_TABLE;
                }
            }
            String businessId = TransferIdParse.toBusinessId(type, dataTable.getKey());
            ResItem resItem = new ResItem(businessId, type.getTitle(), "DATASCHEME_TRANSFER_FACTORY_ID");
            this.resItems.add(resItem);
        }
        for (DesignDataGroup dataGroup : dataGroups) {
            this.iDesignDataSchemeCacheProxy.putDataGroup(dataGroup);
        }
        return null;
    }

    public <DF extends DesignDataField> void visitTableNode(SchemeNode<Void> ele, List<DF> dataFields) {
    }

    public List<ResItem> getResItems() {
        return this.resItems;
    }

    public void setIDesignDataSchemeCacheProxy(IDesignDataSchemeCacheProxy iDesignDataSchemeCacheProxy) {
        this.iDesignDataSchemeCacheProxy = iDesignDataSchemeCacheProxy;
    }
}

