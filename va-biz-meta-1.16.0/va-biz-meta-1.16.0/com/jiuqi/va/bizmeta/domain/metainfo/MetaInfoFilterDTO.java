/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 */
package com.jiuqi.va.bizmeta.domain.metainfo;

import com.jiuqi.va.domain.meta.MetaInfoDO;
import java.util.List;

public class MetaInfoFilterDTO
extends MetaInfoDO {
    private static final long serialVersionUID = 1L;
    private String tableName;
    private List<String> defineCodes;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getDefineCodes() {
        return this.defineCodes;
    }

    public void setDefineCodes(List<String> defineCodes) {
        this.defineCodes = defineCodes;
    }
}

