/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.query.dataset.defines;

import com.jiuqi.np.core.context.NpContextHolder;

public class DataSetConst {
    public static final String DS_ID = "DS_ID";
    public static final String DS_NAME = "DS_NAME";
    public static final String DS_TITLE = "DS_TITLE";
    public static final String DS_PARENT = "DS_PARENT";
    public static final String DS_TYPE = "DS_TYPE";
    public static final String DS_ORDER = "DS_ORDER";
    public static final String DS_MODEL = "DS_MODEL";
    public static final String DS_UPDATETIME = "UPDATETIME";
    public static final String DS_CREATOR = "DS_CREATOR";
    public static final String TABLE_NAME_DATASETDEFINE = "SYS_DATASET";
    public static final String ROOT_GROUP_ID = "0000-0000-0000-00000";
    public static final String DSG_ID = "DSG_ID";
    public static final String DSG_TITLE = "DSG_TITLE";
    public static final String DSG_PARENT = "DSG_PARENT";
    public static final String DSG_ORDER = "DSG_ORDER";
    public static final String DSG_UPDATETIME = "UPDATETIME";
    public static final String DSG_CREATOR = "DSG_CREATOR";
    public static final String TABLE_NAME_DATASETGROUPDEFINE = "SYS_DATASETGROUP";

    public static String getCreator() {
        if (NpContextHolder.getContext().getUser() != null) {
            return NpContextHolder.getContext().getUserId();
        }
        return null;
    }
}

