/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 */
package com.jiuqi.nr.datascheme.common;

import com.jiuqi.nr.datascheme.api.core.NodeType;

public class NodeIconGetter {
    protected static final String GROUP_0 = "#icon-16_SHU_A_NR_fenzu";
    protected static final String SCHEME = "#icon-16_SHU_A_NR_shujufangan";
    protected static final String GROUP_1 = "#icon-16_SHU_A_NR_mulu";
    protected static final String TABLE_0 = "#icon-16_SHU_A_NR_duoweibiao";
    protected static final String TABLE_1 = "#icon-16_SHU_A_NR_fengmiandaimabiao";
    protected static final String TABLE_2 = "#icon-16_SHU_A_NR_mingxibiao";
    protected static final String TABLE_3 = "#icon-16_SHU_A_NR_zhibiaobiao";
    protected static final String TABLE_4 = "#icon-16_SHU_A_NR_taizhangbiao";
    protected static final String FIELD_0 = "#icon-16_SHU_A_NR_biaoneiweiduziduan";
    protected static final String FIELD_1 = "#icon-16_SHU_A_NR_zhibiao";
    protected static final String FIELD_2 = "#icon-16_SHU_A_NR_ziduan";
    protected static final String FIELD_3 = "#icon-16_SHU_A_NR_fengmiandaimaziduan";
    protected static final String ENTITY_ATTRIBUTE = "#icon-16_GJ_A_NR_lieshuxing";
    protected static final String ENTITY = "#icon-16_SHU_A_NR_weidumulu";

    public static String getIconByType(NodeType type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case SCHEME: {
                return SCHEME;
            }
            case SCHEME_GROUP: {
                return GROUP_0;
            }
            case GROUP: {
                return GROUP_1;
            }
            case TABLE: {
                return TABLE_3;
            }
            case MD_INFO: 
            case FMDM_TABLE: {
                return TABLE_1;
            }
            case ACCOUNT_TABLE: {
                return TABLE_4;
            }
            case DETAIL_TABLE: {
                return TABLE_2;
            }
            case MUL_DIM_TABLE: {
                return TABLE_0;
            }
            case FIELD_ZB: {
                return FIELD_1;
            }
            case TABLE_DIM: {
                return FIELD_0;
            }
            case FIELD: {
                return FIELD_2;
            }
            case ENTITY_ATTRIBUTE: {
                return FIELD_3;
            }
            case DIM: {
                return ENTITY;
            }
        }
        return null;
    }

    public static String getIconByType(int type) {
        NodeType nodeType = NodeType.valueOf((int)type);
        return NodeIconGetter.getIconByType(nodeType);
    }
}

