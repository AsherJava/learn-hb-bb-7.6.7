/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 */
package com.jiuqi.va.bizmeta.domain.metabase;

import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;

public class MeatExportTreeInfoDTO
extends MetaTreeInfoDTO {
    private static final long serialVersionUID = 1L;
    private String treeID;
    private String treePID;
    private String metaType;

    public String getMetaType() {
        return this.metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getTreeID() {
        return this.treeID;
    }

    public void setTreeID(String treeID) {
        this.treeID = treeID;
    }

    public String getTreePID() {
        return this.treePID;
    }

    public void setTreePID(String treePID) {
        this.treePID = treePID;
    }
}

