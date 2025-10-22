/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.nr.data.estimation.sub.database.intf.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.data.estimation.sub.database.intf.ICopiedTableGenerator;

public class CopiedTableGenerator
implements ICopiedTableGenerator {
    private String databaseCode;

    public CopiedTableGenerator(String databaseCode) {
        this.databaseCode = databaseCode;
    }

    @Override
    public String madeCopiedId(String oriId) {
        return oriId + this.databaseCode.toLowerCase();
    }

    @Override
    public String madeCopiedTableCode(String oriTableCode) {
        String newTableCode = oriTableCode + this.databaseCode;
        if (newTableCode.length() > 30) {
            // empty if block
        }
        return newTableCode;
    }

    @Override
    public String madeCopiedColumnCode(String oriColumnCode) {
        return oriColumnCode;
    }

    @Override
    public String madeCopiedTableIndexName(String oriTableIndex) {
        String newIndex = oriTableIndex + this.databaseCode;
        if (newIndex.length() > 30) {
            return "UQ_" + OrderGenerator.newOrder() + this.databaseCode;
        }
        return newIndex;
    }

    @Override
    public String madeCopiedTitle(String oriTitle) {
        return oriTitle + this.databaseCode;
    }
}

