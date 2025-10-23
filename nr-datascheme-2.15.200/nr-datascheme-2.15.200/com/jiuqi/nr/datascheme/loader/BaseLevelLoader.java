/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.loader;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class BaseLevelLoader {
    public static int getNodeTypeByTableType(DataTableType dataTableType) {
        if (dataTableType == DataTableType.TABLE) {
            return NodeType.TABLE.getValue();
        }
        if (dataTableType == DataTableType.MD_INFO) {
            return NodeType.MD_INFO.getValue();
        }
        if (dataTableType == DataTableType.ACCOUNT) {
            return NodeType.ACCOUNT_TABLE.getValue();
        }
        if (dataTableType == DataTableType.DETAIL || dataTableType == DataTableType.SUB_TABLE) {
            return NodeType.DETAIL_TABLE.getValue();
        }
        if (dataTableType == DataTableType.MULTI_DIM) {
            return NodeType.MUL_DIM_TABLE.getValue();
        }
        throw new IllegalArgumentException("\u6570\u636e\u8868\u7f3a\u5c11\u7c7b\u578b");
    }

    public static <E, DG extends DataGroup> List<SchemeNode<E>> group2SchemeNode(List<DG> groups, Map<String, E> other) {
        ArrayList<SchemeNode<SchemeNode>> list = new ArrayList<SchemeNode<SchemeNode>>();
        for (DataGroup dataGroup : groups) {
            String dataGroupKey = dataGroup.getKey();
            DataGroupKind dataGroupKind = dataGroup.getDataGroupKind();
            int type = BaseLevelLoader.getNodeTypeByGroupType(dataGroupKind);
            SchemeNode node = new SchemeNode(dataGroupKey, type);
            if (other != null) {
                node.setOther(other.get(dataGroupKey));
            }
            list.add(node);
        }
        return list;
    }

    public static <E, DT extends DataTable> List<SchemeNode<E>> table2SchemeNode(List<DT> dataTables, Map<String, E> otherPar) {
        ArrayList<SchemeNode<SchemeNode>> list = new ArrayList<SchemeNode<SchemeNode>>();
        for (DataTable dataTable : dataTables) {
            String key = dataTable.getKey();
            SchemeNode node = new SchemeNode(key, BaseLevelLoader.getNodeTypeByTableType(dataTable.getDataTableType()));
            if (otherPar != null) {
                node.setOther(otherPar.get(key));
            }
            list.add(node);
        }
        return list;
    }

    public static int getNodeTypeByGroupType(DataGroupKind dataGroupKind) {
        if (dataGroupKind == DataGroupKind.TABLE_GROUP) {
            return NodeType.GROUP.getValue();
        }
        if (dataGroupKind == DataGroupKind.SCHEME_GROUP || dataGroupKind == DataGroupKind.QUERY_SCHEME_GROUP) {
            return NodeType.SCHEME_GROUP.getValue();
        }
        throw new IllegalArgumentException("\u6570\u636e\u8868\u7f3a\u5c11\u7c7b\u578b");
    }

    public static <E, DS extends DataScheme> List<SchemeNode<E>> scheme2SchemeNode(List<DS> schemes, Map<String, E> other) {
        ArrayList<SchemeNode<SchemeNode>> list = new ArrayList<SchemeNode<SchemeNode>>();
        for (DataScheme schemeDO : schemes) {
            String scKey = schemeDO.getKey();
            SchemeNode node = new SchemeNode(scKey, NodeType.SCHEME.getValue());
            if (other != null) {
                node.setOther(other.get(scKey));
            }
            list.add(node);
        }
        return list;
    }

    public static List<DesignDataGroupDO> copyGroup(List<DesignDataGroupDO> groups) {
        ArrayList<DesignDataGroupDO> copy = new ArrayList<DesignDataGroupDO>(groups.size());
        for (DesignDataGroupDO dataGroup : groups) {
            copy.add(dataGroup.clone());
        }
        return copy;
    }

    public static List<DesignDataSchemeDO> copyScheme(List<DesignDataSchemeDO> schemes) {
        ArrayList<DesignDataSchemeDO> copy = new ArrayList<DesignDataSchemeDO>(schemes.size());
        for (DesignDataSchemeDO field : schemes) {
            copy.add(field.clone());
        }
        return copy;
    }

    public static List<DataSchemeDO> copyRunScheme(List<DataSchemeDO> schemes) {
        ArrayList<DataSchemeDO> copy = new ArrayList<DataSchemeDO>(schemes.size());
        for (DataSchemeDO field : schemes) {
            copy.add(field.clone());
        }
        return copy;
    }

    public static List<DesignDataTableDO> copyTable(List<DesignDataTableDO> dataTables) {
        ArrayList<DesignDataTableDO> copyTables = new ArrayList<DesignDataTableDO>(dataTables.size());
        for (DesignDataTableDO dataTable : dataTables) {
            copyTables.add(dataTable.clone());
        }
        return copyTables;
    }

    public static List<DesignDataDimDO> copyDims(List<DesignDataDimDO> dims) {
        ArrayList<DesignDataDimDO> copyDims = new ArrayList<DesignDataDimDO>(dims.size());
        for (DesignDataDimDO dim : dims) {
            copyDims.add(dim.clone());
        }
        return copyDims;
    }

    public static List<DesignDataFieldDO> copyField(List<DesignDataFieldDO> fields) {
        ArrayList<DesignDataFieldDO> copy = new ArrayList<DesignDataFieldDO>(fields.size());
        for (DesignDataFieldDO field : fields) {
            copy.add(field.clone());
        }
        return copy;
    }

    public static Predicate<DataTable> getPredicate(int interestType) {
        return r -> {
            DataTableType dataTableType = r.getDataTableType();
            if (dataTableType == null) {
                return false;
            }
            switch (dataTableType) {
                case TABLE: {
                    return (NodeType.TABLE.getValue() & interestType) != 0;
                }
                case MD_INFO: {
                    return (NodeType.MD_INFO.getValue() & interestType) != 0;
                }
                case ACCOUNT: {
                    return (NodeType.ACCOUNT_TABLE.getValue() & interestType) != 0;
                }
                case DETAIL: 
                case SUB_TABLE: {
                    return (NodeType.DETAIL_TABLE.getValue() & interestType) != 0;
                }
                case MULTI_DIM: {
                    return (NodeType.MUL_DIM_TABLE.getValue() & interestType) != 0;
                }
            }
            throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b");
        };
    }

    public static int nodeType2FieldKind(int nodeType) {
        if (nodeType == 0) {
            return 0;
        }
        int kind = 0;
        if ((NodeType.FIELD_ZB.getValue() & nodeType) != 0) {
            kind |= DataFieldKind.FIELD_ZB.getValue();
        }
        if ((NodeType.FIELD.getValue() & nodeType) != 0) {
            kind |= DataFieldKind.FIELD.getValue();
        }
        if ((NodeType.TABLE_DIM.getValue() & nodeType) != 0) {
            kind |= DataFieldKind.TABLE_FIELD_DIM.getValue();
        }
        return kind;
    }
}

