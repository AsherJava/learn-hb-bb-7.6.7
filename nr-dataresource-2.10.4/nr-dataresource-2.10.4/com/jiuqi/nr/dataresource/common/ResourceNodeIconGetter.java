/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 */
package com.jiuqi.nr.dataresource.common;

import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;

public class ResourceNodeIconGetter
extends NodeIconGetter {
    protected static final String GROUP = "#icon-16_SHU_A_NR_weidumulu";
    protected static final String TREE = "#icon-16_SHU_A_NR_shujuziyuanshu";

    public static String getIconByType(NodeType nodeType) {
        if (nodeType == null) {
            return null;
        }
        switch (nodeType) {
            case RESOURCE_GROUP: 
            case MD_INFO: {
                return "#icon-16_SHU_A_NR_mulu";
            }
            case DIM_GROUP: 
            case DIM_FMDM_GROUP: 
            case TABLE_DIM_GROUP: {
                return GROUP;
            }
            case TREE_GROUP: {
                return "#icon-16_SHU_A_NR_fenzu";
            }
            case TREE: {
                return TREE;
            }
            case FIELD_ZB_LINK: {
                return "#icon-16_SHU_A_NR_zhibiao";
            }
            case FIELD_LINK: {
                return "#icon-16_SHU_A_NR_ziduan";
            }
        }
        return null;
    }

    public static String getIconByType(int type) {
        NodeType nodeType = NodeType.valueOf(type);
        return ResourceNodeIconGetter.getIconByType(nodeType);
    }
}

