/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.service;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV;
import java.util.List;

public interface TableDefinitionService {
    default public void initTableDefines(DefinitionTableV[] definitionTables) {
        TableDefinitionService defineProvider = (TableDefinitionService)SpringContextUtils.getBean(TableDefinitionService.class);
        if (!1.$assertionsDisabled && defineProvider == null) {
            throw new AssertionError();
        }
        for (DefinitionTableV tab : definitionTables) {
            try {
                defineProvider.initTableDefine(tab);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initTableDefine(DefinitionTableV var1) throws Exception;

    public void initTableDefineByTableName(String var1) throws Exception;

    public void initData(List<DefinitionValueV> var1);

    public void saveOrUpdateData(DefinitionValueV var1);

    static {
        if (1.$assertionsDisabled) {
            // empty if block
        }
    }
}

