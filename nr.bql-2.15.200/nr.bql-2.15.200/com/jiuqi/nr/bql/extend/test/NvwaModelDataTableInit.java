/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.nr.bql.extend.test;

import com.jiuqi.nr.bql.extend.test.AbstractDataTableInit;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import org.springframework.stereotype.Component;

@Component
public class NvwaModelDataTableInit
extends AbstractDataTableInit {
    @Override
    public void init() throws Exception {
        srcTables.add("SYS_CKR_LLBLC3XZ");
        super.init();
    }

    @Override
    protected DesignDataTable initTable(String srcTableKey) throws Exception {
        return null;
    }

    @Override
    protected String getSrcType() {
        return "sqlDataSet";
    }
}

