/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataresource.DataResource
 *  com.jiuqi.nr.dataresource.DataResourceDefine
 *  com.jiuqi.nr.dataresource.DataResourceDefineGroup
 *  com.jiuqi.nr.dataresource.DataResourceKind
 *  com.jiuqi.nr.dataresource.DataResourceNode
 *  com.jiuqi.nr.dataresource.DimAttribute
 *  com.jiuqi.nr.dataresource.NodeType
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.period.common.utils.TimeDimField
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.period.common.utils.TimeDimField;
import com.jiuqi.nr.zbquery.bean.facade.IResourceTreeNode;
import org.springframework.util.StringUtils;

public final class ResTreeUtils {
    public static String getDimGroupKey(String dimId) {
        if (!dimId.contains("_dim_")) {
            return dimId;
        }
        String[] splits = dimId.split("_");
        int index = 0;
        for (String split : splits) {
            if (split.equalsIgnoreCase("dim")) break;
            ++index;
        }
        if (index + 1 == splits.length - 1) {
            return splits[splits.length - 1];
        }
        if (dimId.contains("_zb_")) {
            return splits[splits.length - 1];
        }
        StringBuilder dimKeySB = new StringBuilder();
        for (int i = index + 1; i < splits.length && !splits[i].equals("dimattr"); ++i) {
            dimKeySB.append(splits[i]).append("_");
        }
        dimKeySB.setLength(dimKeySB.length() - 1);
        return dimKeySB.toString();
    }

    public static String getRealKey(String id) {
        if (!StringUtils.hasLength(id)) {
            return null;
        }
        String[] keys = id.split("_");
        return keys[keys.length - 1];
    }

    public static String generateKey(DataResourceNode dataResourceNode) {
        int type = dataResourceNode.getType();
        String id = "nrst";
        if (type == NodeType.RESOURCE_GROUP.getValue()) {
            id = id + "_resgroup_" + dataResourceNode.getKey();
        } else if (type == NodeType.TREE.getValue()) {
            id = id + "_tree_" + dataResourceNode.getKey();
        } else if (type == NodeType.TREE_GROUP.getValue()) {
            id = id + "_treegroup_" + dataResourceNode.getKey();
        } else if (type == NodeType.DIM_GROUP.getValue()) {
            id = id + "_dim_" + dataResourceNode.getKey();
        } else if (type == NodeType.TABLE_DIM_GROUP.getValue()) {
            id = id + "_dim_" + dataResourceNode.getKey();
        } else if (type == NodeType.DIM_FMDM_GROUP.getValue()) {
            id = id + "_dim_" + dataResourceNode.getKey();
        } else if (type == NodeType.MD_INFO.getValue()) {
            id = id + "_dim_" + dataResourceNode.getKey();
        }
        return id;
    }

    public static String generateKey(DataField dataField, IResourceTreeNode resGroupNode) {
        String id = resGroupNode.getKey();
        DataFieldKind dataFieldKind = dataField.getDataFieldKind();
        switch (dataFieldKind) {
            case FIELD: {
                id = id + "_field";
                break;
            }
            case FIELD_ZB: {
                id = id + "_zb";
                break;
            }
            case TABLE_FIELD_DIM: {
                id = id + "_dim";
            }
        }
        id = id + "_" + dataField.getKey();
        return id;
    }

    public static String generateKey(DimAttribute dimAttribute, IResourceTreeNode dimGroupNode) {
        String id = dimGroupNode.getKey();
        id = id + "_dimattr";
        id = id + "_" + dimAttribute.getKey();
        return id;
    }

    public static String generateKey(TimeDimField timeDimField, IResourceTreeNode dimGroupNode) {
        String id = dimGroupNode.getKey();
        id = id + "_dimattr";
        id = id + "_" + timeDimField.getName();
        return id;
    }

    public static String generateKey(DataResource dataResource) {
        String id = "nrst";
        DataResourceKind dataResourceKind = dataResource.getResourceKind();
        switch (dataResourceKind) {
            case RESOURCE_GROUP: {
                id = id + "_resgroup";
                break;
            }
            case DIM_GROUP: {
                id = id + "_dim";
                break;
            }
            case DIM_FMDM_GROUP: {
                id = id + "_dim";
                break;
            }
            case TABLE_DIM_GROUP: {
                id = id + "_dim";
            }
        }
        id = id + "_" + dataResource.getKey();
        return id;
    }

    public static String generateKey(DataResourceDefine dataResourceDefine) {
        String id = "nrst";
        id = id + "_tree";
        id = id + "_" + dataResourceDefine.getKey();
        return id;
    }

    public static String generateKey(DataResourceDefineGroup dataResourceDefineGroup) {
        String id = "nrst";
        id = id + "_treegroup";
        id = id + "_" + dataResourceDefineGroup.getKey();
        return id;
    }

    public static String generateKey(String key, NodeType nodeType) {
        String id = "nrst";
        if (nodeType == NodeType.RESOURCE_GROUP) {
            id = id + "_resgroup";
        } else if (nodeType == NodeType.TREE) {
            id = id + "_tree";
        } else if (nodeType == NodeType.TREE_GROUP) {
            id = id + "_treegroup";
        } else if (nodeType == NodeType.DIM_GROUP) {
            id = id + "_dim";
        } else if (nodeType == NodeType.TABLE_DIM_GROUP) {
            id = id + "_dim";
        } else if (nodeType == NodeType.DIM_FMDM_GROUP) {
            id = id + "_dim";
        }
        id = id + "_" + key;
        return id;
    }
}

