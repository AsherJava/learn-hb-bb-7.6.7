/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine;

import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import java.util.List;

public interface InputDataTableDefineService {
    public void createInputDataTableByDataSchemeKey(String var1);

    public List<DBIndex> listNeedAddInputDataIndex(DesignDataTable var1);

    public void addInputDataIndexes(DesignDataTable var1, List<DBIndex> var2);
}

