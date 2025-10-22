/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;

public interface IDataAssist {
    public void setDesignTimeData(boolean var1, IDataDefinitionDesignTimeController var2);

    public String getDimensionName(EntityViewDefine var1);

    public FieldDefine getDimensionField(String var1, String var2);

    public String getDimensionName(FieldDefine var1);

    public DimensionSet getDimensionsByTableName(String var1);

    public DimensionSet getDimensions(String var1);

    public ReportFormulaParser createFormulaParser(boolean var1);
}

