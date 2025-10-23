/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.tree;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeBeanUtils;

public class TypeDeter {
    public static boolean isLeafByTable(NodeType nodeType, int interestType) {
        switch (nodeType) {
            case TABLE: 
            case MD_INFO: {
                return (interestType & NodeType.FIELD_ZB.getValue()) == 0;
            }
            case DETAIL_TABLE: 
            case ACCOUNT_TABLE: {
                return (interestType & NodeType.FIELD.getValue()) == 0;
            }
        }
        throw new UnsupportedOperationException("\u7c7b\u578b\u672a\u652f\u6301");
    }

    public static boolean isLeafByTable(DataTable dataTable, int interestType) {
        DataTableType dataTableType = dataTable.getDataTableType();
        return TypeDeter.isLeafByTable(dataTableType, interestType);
    }

    public static boolean isLeafByTable(DataTableType dataTableType, int interestType) {
        switch (dataTableType) {
            case TABLE: 
            case MD_INFO: {
                return (interestType & NodeType.FIELD_ZB.getValue()) == 0;
            }
            case DETAIL: 
            case ACCOUNT: 
            case SUB_TABLE: {
                return (interestType & NodeType.FIELD.getValue()) == 0;
            }
        }
        throw new UnsupportedOperationException("\u7c7b\u578b\u672a\u652f\u6301");
    }

    public static boolean canRead(DataScheme scheme) {
        return DataSchemeBeanUtils.getDataSchemeAuthService().canReadScheme(scheme.getKey());
    }

    public static boolean canRead(DataGroup group) {
        if (DataGroupKind.SCHEME_GROUP == group.getDataGroupKind() || DataGroupKind.QUERY_SCHEME_GROUP == group.getDataGroupKind()) {
            return DataSchemeBeanUtils.getDataSchemeAuthService().canReadGroup(group.getKey());
        }
        return true;
    }
}

